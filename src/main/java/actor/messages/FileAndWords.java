package actor.messages;

public class FileAndWords{
	final private String fileName;
	final private int wordsCount;
	
	public FileAndWords(String fileName, int wordsCount){
		this.fileName = fileName;
		this.wordsCount = wordsCount;
	}
	
	public int getWordsCount(){
		return wordsCount;
	}
	
	public String getFileName(){
		return fileName;
	}
}