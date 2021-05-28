import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * Classe permettant de parcourir tout les fichiers se toruvant sous un
 * repertoire donnee et de stocker tout les fichier txt dans une queue
 * 
 * @author motyl_vegas_assontia
 *
 */
public class Crawler implements Runnable {

	private final BlockingQueue<File> queue;

	private final File folder;

	private final ArrayList<File> alreadyDoneFiles;

	public Crawler(File folder, BlockingQueue<File> queue) {
		this.folder = folder;
		this.queue = queue;
		this.alreadyDoneFiles = new ArrayList<File>();
	}

	public void run() {

		while (true) {

			crawl(folder);

			try {
				Thread.sleep(5000); // le crawler analyse le dossier toutes les 5 secondes
			} catch (InterruptedException e) { // exception
			}
		}
	}

	private void crawl(File file) {

		String pattern = ".txt";

		if (file.isDirectory()) { // si on est est dans notre arboresence de fichier
			File[] files = file.listFiles(); // on l'ajout a la liste
			for (File f : files) {
				crawl(f); // on parcours tous les fichiers
			}
		} else {
			if (file.getName().endsWith(pattern)) { // si fichier fini par txt

				if (alreadyDoneFiles.contains(file)) {
					// Do nothing
				} else {
					queue.add(file); // on l'ajout a notre queue
					alreadyDoneFiles.add(file); // on signal qu'on a déjà traité le fichier
				}

			}
		}

	}

}
