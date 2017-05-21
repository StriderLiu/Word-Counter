package actors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import actor.messages.FileAndWords;
import actor.messages.FileNameAndPath;
import actor.messages.LineAndWords;
import actor.messages.Messages;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * this actor reads the file line by line and sends them to
 * {@code WordsInLineActor} to count the words in line. Upon geting the results,
 * It sends the result to it's parent actor {@code WordCount}
 * 
 * @author akashnagesh
 *
 */

/**
 * modified by Wenbo Liu
 * @author vincentliu
 *
 */
public class WordCountInAFileActor extends UntypedActor {
	private String name;
	private int cnt;
	private Logger log = Logger.getLogger(WordCountInAFileActor.class.getName());
	public WordCountInAFileActor() {
		name = null;
		cnt = 0;
	}

	@Override
	public void onReceive(Object msg) throws Throwable {
		if(msg instanceof FileNameAndPath){
			FileNameAndPath fnap = (FileNameAndPath)msg;
			if(name == null){
				name = fnap.getFileName();
			}
			log.log(Level.INFO, name + " File and path received!");
			String absPath = fnap.getAbsPath();
			File file = new File(absPath);
			if(file.isFile() && file.exists()){
				InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file));
				BufferedReader bufferReader = new BufferedReader(streamReader);
				String line = null;
				while((line = bufferReader.readLine())!= null){
					//System.out.println(line.trim());//use this to test if there are any words
					Props wila = Props.create(WordsInLineActor.class);
					ActorRef wilaNode = getContext().actorOf(wila);
					wilaNode.tell(line, getSelf());
				}
				streamReader.close();
			}else{
				System.out.println("Can't find file!!!");
			}

		}else if(msg instanceof LineAndWords){
			LineAndWords law = (LineAndWords)msg;
			cnt += law.getWordsCount();
		}else if(msg instanceof Messages){
			if(((Messages) msg).getCommand().equals("getFileCount")){
				FileAndWords faw = new FileAndWords(name, cnt);
				System.out.println(name + "-" + cnt);
				getSender().tell(faw, getSelf());
			}else{
				unhandled(msg);
			}
		}else{
			unhandled(msg);
		}
	}

}
