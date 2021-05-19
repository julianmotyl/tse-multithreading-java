import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;

public class RequeteurMongoTest {

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
		
		
		MongoCollection<Document>  collection = requeteur.getCollection();
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("name", fileName);
		
		Document expectedDocument = new Document();
		expectedDocument.append("location", location);
		expectedDocument.append("name", fileName);
		expectedDocument.append("word_occu", occurences);
		//
		assertNotNull(collection.find().first());
		
		assertEquals(expectedDocument, collection.find(whereQuery).first());
		
	}
}
