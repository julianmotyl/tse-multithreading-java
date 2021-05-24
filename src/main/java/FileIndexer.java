import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class FileIndexer implements Runnable{
	
	private final BlockingQueue<File> queue;
	private boolean running;
	private RequeteurMongo requeteur = new RequeteurMongo();
	
	public FileIndexer(BlockingQueue<File> queue) {
		this.running = false;
		this.queue = queue;
	}
	
	public void run() {
		this.running = true;
		while(this.running) {
			try {
				File file = queue.take();
				String location = file.getPath();
				String fileName = file.getName();
				System.out.println(file.getPath());
				String phrase = FileIndexer.openFile(file);
				HashMap<String, Integer> occurences = decoupePhrase(phrase);
				requeteur.indexFile(location, fileName, occurences);
				
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
				this.running = false;
				Thread.currentThread().interrupt();
			}
		}
		
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	
	
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
			System.out.println("Contenu du fichier: ");
			System.out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static HashMap<String, Integer> decoupePhrase(String phrase) {

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		String[] ignored = phrase.split(" ");

		// CAN BE FIX THIS POINT ON.
		for (String ignore : ignored) {
			Integer count = map.get(ignore);
			if (count == null) {
				count = 0;
			}
			map.put(ignore, count + 1);
		}

		for (int i = 0; i < ignored.length; i++) {
			System.out.println(ignored[i]);
		}
		System.out.println(map);
		
		return map;
	}
	
	

}
