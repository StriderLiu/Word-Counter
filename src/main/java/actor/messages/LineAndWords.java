package actor.messages;

public class LineAndWords{
	final private int wordsCount;
	
	public LineAndWords(int wordsCount){
		this.wordsCount = wordsCount;
	}
	
	public int getWordsCount(){
		return wordsCount;
	}
}
