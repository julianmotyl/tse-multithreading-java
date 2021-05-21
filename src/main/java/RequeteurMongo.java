
import java.util.Arrays;
import java.util.HashMap;

import org.bson.Document;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
/**
 * Classe permettant de faire toutes les actions avec MongoDB en local.
 * Pour le moment, la base doit être nommé "multithreading" et sa collection "files", la base est sur localhost sans sécurité
 * @author motyl
 *
 */
public class RequeteurMongo {
	
	private MongoClient client;
	
	//Pour les tests
	public MongoCollection<Document> getCollection() {
		return collection;
	}


	private MongoDatabase database;
	
	private MongoCollection<Document> collection;
	
	/**
	 * Constructeur par defaut, sans parametre 
	 */
	public RequeteurMongo() {
		
		//Création d'un client, par défaut sur localhost 27017
		this.client = MongoClients.create();
		this.database = client.getDatabase("multithreading");
		this.collection = database.getCollection("files");
		
	}
	
	/**
	 * Permet d'indexer un fichier dans la base mongo
	 * @param location
	 * @param name
	 * @param occurences
	 */
	public void indexFile(String location, String name, HashMap<String, Integer> occurences) {
		 Document doc = new Document("location", location)
	                .append("name", name)
	                .append("word_occu", occurences);
		 
		this.collection.insertOne(doc);
	}
	
	/**
	 * Permet de trouver le fichier avec le plus d'occurence du mot d'entré
	 * @param word le mot recherché
	 * @return L'adresse du fichier le plus pertinant
	 */
	public String searchBestFile(String word) {
		
		AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$sort", 
			    new Document("word_occu." + word, -1L)), 
			    new Document("$limit", 1L)));
		String filename = (String) result.first().get("name");
		String location = (String) result.first().get("location");
		
		return location + filename;
		
	}
}
