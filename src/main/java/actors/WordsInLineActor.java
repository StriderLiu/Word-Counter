package actors;

import actor.messages.LineAndWords;
import akka.actor.UntypedActor;

/**
 * This actor counts number words in a single line
 * 
 * @author akashnagesh
 *
 */

/**
 * modified by Wenbo Liu
 * @author vincentliu
 *
 */
public class WordsInLineActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Throwable {
		if(msg instanceof String){
			String line = (String)msg;
			String[] words = line.split(" ");
			int cnt = words.length;
			LineAndWords lineAndWords = new LineAndWords(cnt);
			getSender().tell(lineAndWords, getSelf());
		}else{
			unhandled(msg);
		}
	}
}
