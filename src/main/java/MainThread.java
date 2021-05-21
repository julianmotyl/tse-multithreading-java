import java.io.File;

public class MainThread { //Doit �tre un singleton
	
	private String searchedWord;
	private String repertory;
	

	public MainThread(String searchedWord, String repertory) {
		this.searchedWord = searchedWord;
		this.repertory = repertory;
	}

	public static void main(String[] args) {
		
		String directoryPath = "src/main/ressources";
		
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
	
	public String getSearchedWord() {
		return searchedWord;
	}

	public String getRepertory() {
		return repertory;
	}
	
	
	private void run() {
		
		//Do Something
	}

}
