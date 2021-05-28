import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

public class RequeteurMongoTest {
	/**
	 * Test de l'indexation d'un fichier dans MongoDB
	 */
	@Test
	public void indexFileTest() {

		HashMap<String, Integer> occurences = new HashMap<String, Integer>();

		occurences.put("maison", 3);
		occurences.put("cabane", 4);
		occurences.put("villa", 1);

		String fileName = "Logements.txt";
		String location = "data/test/";

		RequeteurMongo requeteur = new RequeteurMongo();
		requeteur.indexFile(location, fileName, occurences);

		MongoCollection<Document> collection = requeteur.getCollection();
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("name", fileName);

		Document expectedDocument = new Document();
		expectedDocument.append("location", location);
		expectedDocument.append("name", fileName);
		expectedDocument.append("word_occu", occurences);

		assertNotNull(collection.find(whereQuery).first());

		// Netoyage de la base
		collection.deleteOne(collection.find(whereQuery).first());
	}

	/**
	 * Test si on trouve bien le bon document
	 */
	@Test
	public void searchBestFileTest() {

		RequeteurMongo requeteur = new RequeteurMongo();

		// Fichier 1
		String fileName1 = "Logements.txt";
		String location1 = "data/test/";
		HashMap<String, Integer> occurences1 = new HashMap<String, Integer>();
		occurences1.put("maison", 6);
		occurences1.put("cabane", 4);
		occurences1.put("villa", 1);

		requeteur.indexFile(location1, fileName1, occurences1);

		// Fichier 2
		String fileName2 = "Amenagements.txt";
		String location2 = "data/test/";
		HashMap<String, Integer> occurences2 = new HashMap<String, Integer>();
		occurences2.put("maison", 3);
		occurences2.put("cabane", 4);
		occurences2.put("villa", 2);

		requeteur.indexFile(location2, fileName2, occurences2);

		assertEquals("data/test/Amenagements.txt", requeteur.searchBestFile("villa"));
		assertEquals("data/test/Logements.txt", requeteur.searchBestFile("maison"));

		// Netoyage de la base
		BasicDBObject whereQuery = new BasicDBObject();
		MongoCollection<Document> collection = requeteur.getCollection();

		whereQuery.put("name", fileName1);

		collection.deleteOne(collection.find(whereQuery).first());

		whereQuery.put("name", fileName2);

		collection.deleteOne(collection.find(whereQuery).first());

	}

}
