
import java.util.Arrays;
import java.util.HashMap;

import org.bson.Document;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Classe permettant de faire toutes les actions avec MongoDB en local. Pour le
 * moment, la base doit �tre nomm� "multithreading" et sa collection "files", la
 * base est sur localhost sans s�curit�
 * 
 * @author motyl_vegas_assontia
 *
 */
public class RequeteurMongo {

	private MongoClient client;

	// Pour les tests
	public MongoCollection<Document> getCollection() {
		return collection;
	}

	private MongoDatabase database;

	private MongoCollection<Document> collection;

	/**
	 * Constructeur par defaut, sans parametre
	 */
	public RequeteurMongo() {

		// Cr�ation d'un client, par d�faut sur localhost 27017
		this.client = MongoClients.create(); // creation d'une instance mongodb
		this.database = client.getDatabase("multithreading"); // nom de notre base de donne ici "multithreading"
		this.collection = database.getCollection("files"); // Nom de nos collections ici "files"

	}

	/**
	 * Permet d'indexer un fichier dans la base mongo
	 * 
	 * @param location
	 * @param name
	 * @param occurences
	 */
	public void indexFile(String location, String name, HashMap<String, Integer> occurences) {
		Document doc = new Document("location", location).append("name", name).append("word_occu", occurences);  // on cree un file a notre collection avec sa location
																												// son nom
																												// se occurences de mots
		this.collection.insertOne(doc); // et on l'insert
	}

	/**
	 * Permet de trouver le fichier avec le plus d'occurence du mot d'entr�
	 * 
	 * @param word le mot recherch�
	 * @return L'adresse du fichier le plus pertinant
	 */
	public AggregateIterable<Document> searchBestFiles(String word, Long nbDocs) {

		AggregateIterable<Document> result = collection.aggregate(Arrays
				.asList(new Document("$sort", new Document("word_occu." + word, -1L)), new Document("$limit", nbDocs))); // On trie les 5 élément les plus pertinant

		return result; // des documents
	}
}
