public class MainThread { //Doit être un singleton
	
	private String searchedWord;
	private String repertory;

	public MainThread(String searchedWord, String repertory) {
		this.searchedWord = searchedWord;
		this.repertory = repertory;
	}

	public static void main(String[] args) {
		
		
		String searchedWord = args[0];
		String repertory = args[1];
		
		MainThread mainTread = new MainThread(searchedWord,repertory);
		
		mainTread.run();
		
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
