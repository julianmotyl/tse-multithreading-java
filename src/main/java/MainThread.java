import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainThread { //Doit �tre un singleton
	
	private String searchedWord;
	private String repertory;

	public MainThread(String searchedWord, String repertory) {
		this.searchedWord = searchedWord;
		this.repertory = repertory;
	}

	public static void main(String[] args) {
		
		
		/*String searchedWord = args[0];
		String repertory = args[1];
		
		MainThread mainTread = new MainThread(searchedWord,repertory);
		
		mainTread.run();*/
		
		File directory = new File("/Users/admin/Documents/cours/DE3/Projet BigData NoSQL/projet_bla/tse_de3_gr3_big-data-project/ML_model_for_predicting_CVs/Project_outputs/csv_files");
		
		File[] contentsOfDirectory = directory.listFiles();
		
		for(File object : contentsOfDirectory) {
			
			if(object.isFile()) {
				System.out.println("Filename: " + object.getName());
			} else if(object.isDirectory()){
				System.out.println("Directory Name: " + object.getName());
			}
		}
		
		System.out.println(" ");
		try
        {
          // Le fichier d'entrée
          File file = new File("/Users/admin/Documents/test.txt");
          // Créer l'objet File Reader
          FileReader fr = new FileReader(file);
          // Créer l'objet BufferedReader
          BufferedReader br = new BufferedReader(fr);
          StringBuffer sb = new StringBuffer();
          String line;
          while((line = br.readLine()) != null)
          {
            // ajoute la ligne au buffer
            sb.append(line);
          }
          fr.close();
          System.out.println("Contenu du fichier: ");
          System.out.println(sb.toString());
        }
        catch(IOException e)
        {
          e.printStackTrace();
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
