import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Crawler implements Runnable{
	
	private final BlockingQueue<File> queue;
	
	private final File folder;
	
	public Crawler(File folder, BlockingQueue<File> queue) {
		this.folder = folder;
		this.queue = queue;
	}
	
	public void run() {
		
		while (true) {
			
			crawl(folder);
			
			try {
				Thread.sleep(5000); // le crawler analyse le dossier toutes les 5 secondes
			} 
			catch (InterruptedException e) {
			}
		}
	}
	
	private void crawl(File file) {
		
		String pattern = ".txt";
		
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			for(File f : files) {
				crawl(f);
			}
		} else {
			if (file.getName().endsWith(pattern)) {
				queue.add(file);
			}
		}
		
	}
	
}

