
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
 * moment, la base doit être nommé "multithreading" et sa collection "files", la
 * base est sur localhost sans sécurité
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

		// Création d'un client, par défaut sur localhost 27017
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
		Document doc = new Document("location", location) // on cree un file a notre collection avec sa location
				.append("name", name) // son nom
				.append("word_occu", occurences); // se occurences de mots

		this.collection.insertOne(doc); // et on l'insert
	}

	/**
	 * Permet de trouver le fichier avec le plus d'occurence du mot d'entré
	 * 
	 * @param word le mot recherché
	 * @return L'adresse du fichier le plus pertinant
	 */
	public String searchBestFile(String word) {

		AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$sort", // on trie
				new Document("word_occu." + word, -1L)), // on tire par occurence de mot, -1L trie decroissant
				new Document("$limit", 5L))); // on limit à 5 documents les plus pertinents
		String filename = (String) result.first().get("name"); // on retourne le nom du fichier
		String location = (String) result.first().get("location"); // et sa localisation

		return location + filename; // retur chemin absolu + nom fichier

	}
}
