import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * Classe permettant d'indexer nos fichiers avec leur nom, localisation et
 * dictionnaire de mot avec leur occurence et les envoyer sur notre base de
 * donnee Mango
 * 
 * @author motyl_vegas_assontia
 *
 */
public class FileIndexer implements Runnable {

	private final BlockingQueue<File> queue;
	private boolean running;
	private RequeteurMongo requeteur = new RequeteurMongo();

	public FileIndexer(BlockingQueue<File> queue) {
		this.running = false;
		this.queue = queue;
	}

	public void run() {
		this.running = true;
		while (this.running) {
			try {
				File file = queue.take();// on recup�re un element de la queue
				String location = file.getPath(); // on recupere sa localisation
				String fileName = file.getName(); // on recuprere son nom
				String phrase = FileIndexer.openFile(file); // on ouvrir le fichier et met son contenu dans un string
															// "phrase" grace a lamethode openFile
				HashMap<String, Integer> occurences = decoupePhrase(phrase); // on appel la methode decoupagePhrase qui
																				// nous retourne un hashmap de mot
																				// assoice nb d'occurence de chaque mot
				requeteur.indexFile(location, fileName, occurences); // on appel la methode indexFile qui ajout le
																		// fichier � la base mongo

			} catch (InterruptedException e) { // si interruption
				System.out.println(e.getMessage());
				this.running = false;
				Thread.currentThread().interrupt();
			}
		}

	}

	public boolean isRunning() {
		return this.running;
	}

	/**
	 * Permet d'ouvrir un fichier et de copier son contenu dans un String
	 * 
	 * @param chemin du fichier txt � lire
	 * @return strin, le contenu du fichier txt
	 */
	public static String openFile(File file) {
		StringBuffer sb = new StringBuffer();
		try {
			// Le fichier d'entrée
			// File file = new File("src/main/ressources/test.txt");
			// Créer l'objet File Reader
			FileReader fr = new FileReader(file);
			// Créer l'objet BufferedReader
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				// ajoute la ligne au buffer
				sb.append(line);
			}

			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * Permet de creer un hash map de mot avec ses occurences � partir d'une phrase
	 * 
	 * @param string, phrase
	 * @return HashMap(string - mot, integer - occurence du mot)
	 */
	public static HashMap<String, Integer> decoupePhrase(String phrase) {

		HashMap<String, Integer> map = new HashMap<String, Integer>();
       // on decoupe la phrase par rapport aux differents points et  espaces
		String[] ignored = phrase.toLowerCase().replace(".", "").replace("?", "").replace(",", "").replace("!", "").split("['@' ]");// on decoupe la phrase par rapport auxx espaces
		
		for (String ignore : ignored) {
			Integer count = map.get(ignore);
			if (count == null) {
				count = 0;
			}
			map.put(ignore, count + 1); // on incremente le nb occurence
		}

		return map;// on retourne le hashmap
	}

}
