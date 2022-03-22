import java.io.File;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

import org.bson.Document;

import com.mongodb.client.AggregateIterable;

/**
 * Classe permettant de lancer notre appli, demande utilisateur, creation de
 * threads, renvoi de pertinence du fichier le plus pertinent
 * 
 * @author motyl_vegas_assontia
 *
 */
public class MainThread {

	private static Scanner clavier = new Scanner(System.in);

	public static void main(String[] args) {

		// on ne veut pas limiter la capacité de notre stack
		BlockingQueue<File> queue = new LinkedTransferQueue<File>();

		System.out.println("Dans quel répertoire souhaiter vous chercher ce mot? ");
		String directoryPath = clavier.nextLine();
		
		Crawler crawler = new Crawler(new File(directoryPath), queue); // appel du crawler dans le chemin specifi�
		Thread crawlerThread = new Thread(crawler); // le crawler va lister tout les noms de fichoiers txt qu'il va
													// trovuer
		crawlerThread.start(); // on le demarre

		int numThreads = 3; // nb de thread pour indexer les fichier dans mango
		Thread indexerThreads[] = new Thread[numThreads];
		for (int i = 0; i < numThreads; i++) { // tant qu'on a pas 3 thread
			FileIndexer indexer = new FileIndexer(queue); // classe FileIndexer
			indexerThreads[i] = new Thread(indexer);
			indexerThreads[i].start(); // on le demarre
		}

		readDirectory(directoryPath);
		
		System.out.println("Entrez votre mot: ");
		String searchedWord = clavier.nextLine();


		clavier.close();

		 RequeteurMongo requeteur = new RequeteurMongo(); 
		 AggregateIterable<Document> founds = requeteur.searchBestFiles(searchedWord, 5L);
		 if (founds.first() != null) {
			for (Document document : founds) {
				Document docOccurence = (Document) document.get("word_occu");
				Integer nbocurence = docOccurence.getInteger(searchedWord);
				if (nbocurence != null) {
					System.out.println("Le document : " + document.get("location") + "contient " + nbocurence + " fois le mot : " + searchedWord);
				}
			} 
		 }
		 

	}

	public static void readDirectory(String directoryPath) {

		String pattern = ".txt";

		File directory = new File(directoryPath);

		File[] contentsOfDirectory = directory.listFiles();

		if (contentsOfDirectory != null) {
			for (int i = 0; i < contentsOfDirectory.length; i++) {
				if (contentsOfDirectory[i].isDirectory()) {
					readDirectory(contentsOfDirectory[i].getAbsolutePath());
				} else {
					if (contentsOfDirectory[i].getName().endsWith(pattern)) {
						System.out.println(contentsOfDirectory[i].getPath());
					}
				}
			}
		}
	}
}
