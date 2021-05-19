
import java.util.HashMap;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class RequeteurMongo {
	
	private MongoClient client;
	
	//Pour les tests
	public MongoCollection<Document> getCollection() {
		return collection;
	}


	private MongoDatabase database;
	
	private MongoCollection<Document> collection;
	
	public RequeteurMongo() {
		
		//Création d'un client, par défaut sur localhost 27017
		this.client = MongoClients.create();
		this.database = client.getDatabase("multithreading");
		this.collection = database.getCollection("files");
		
	}
	
	
	public void indexFile(String location, String name, HashMap<String, Integer> occurences) {
		 Document doc = new Document("location", location)
	                .append("name", name)
	                .append("word_occu", occurences);
		 
		this.collection.insertOne(doc);
	}
	
}
