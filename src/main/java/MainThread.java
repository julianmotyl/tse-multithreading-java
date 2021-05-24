import java.awt.List;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

public class MainThread { //Doit �tre un singleton
	
	// private String searchedWord;
	private String repertory;
	
	private static Scanner clavier = new Scanner(System.in);

	/*public MainThread(String searchedWord, String repertory) {
		this.searchedWord = searchedWord;
		this.repertory = repertory;
	}*/

	public static void main(String[] args) {
		
		// on ne veut pas limiter la capacité de notre stack
		BlockingQueue<File> queue = new LinkedTransferQueue<>();
		
		System.out.println("Entrez votre mot: ");
		String searchedWord = clavier.nextLine();		
		
		System.out.println("Dans quel répertoire souhaiter vous chercher ce mot? ");		
		String directoryPath = clavier.nextLine();
		// String directoryPath = "src/main/ressources";
		
		clavier.close();
		
		/*RequeteurMongo requeteur = new RequeteurMongo();
		String found = requeteur.searchBestFile(searchedWord);
		System.out.println("FIle where the word appears the most: " + found );*/
		
		Crawler crawler = new Crawler(new File(directoryPath), queue);
		Thread crawlerThread = new Thread(crawler);
		crawlerThread.start();
		
		int numThreads = 3;
		Thread indexerThreads[] = new Thread[numThreads];
		for(int i = 0; i < numThreads; i++) {
			FileIndexer indexer = new FileIndexer(queue);
			indexerThreads[i] = new Thread(indexer);
			indexerThreads[i].start();	
		}
		
		
		
		
	
		
		
		
		
		
		// Ceci ne marche pas et je ne sais pas pourquoi....
		//WorkerThread.readDirectory(directoryPath);
		readDirectory(directoryPath);
				
		
		/*String searchedWord = args[0];
		String repertory = args[1];
		
		MainThread mainTread = new MainThread(searchedWord,repertory);
		
		mainTread.run();*/		
	}

	public static void readDirectory(String directoryPath) {
        
		String pattern = ".txt";
        
        File directory = new File(directoryPath);
 
        File[] contentsOfDirectory = directory.listFiles();

        if (contentsOfDirectory != null) {
            for (int i=0; i<contentsOfDirectory.length; i++) {
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
	
	/*public String getSearchedWord() {
		return searchedWord;
	}


	public String getSearchedWord() {
		return searchedWord;
	}*/
	
	private static void run(String location) {
		
		HashMap<String, Integer> occurences = new HashMap<String, Integer>();
		
		occurences.put("maison", 3);
		occurences.put("cabane", 4);
		occurences.put("villa", 1);
		
		String fileName = "Logements.txt";
		
		RequeteurMongo requeteur = new RequeteurMongo();
		requeteur.indexFile(location, fileName, occurences);


	}

}
