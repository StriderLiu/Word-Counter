package actors;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import actor.messages.FileAndWords;
import actor.messages.FileNameAndPath;
import actor.messages.Messages;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import scala.concurrent.impl.Future;

/**
 * This is the main actor and the only actor that is created directly under the
 * {@code ActorSystem} This actor creates more child actors
 * {@code WordCountInAFileActor} depending upon the number of files in the given
 * directory structure
 * 
 * @author akashnagesh
 *
 */

/***
 * modified by Wenbo Liu
 * @author vincentliu
 *
 */
public class WordCountActor extends UntypedActor {
	private final HashMap<String, Integer> map = new HashMap<String, Integer>();
	private long total;
	private ArrayList<ActorRef> children = new ArrayList<ActorRef>();
	private Logger log = Logger.getLogger(WordCountActor.class.getName());
	public WordCountActor() {
		total = 0;		
	}

	@Override
	public void onReceive(Object msg) throws Throwable {
		if(msg instanceof FileAndWords) {
			FileAndWords faw = (FileAndWords)msg;
			map.put(faw.getFileName(), faw.getWordsCount());
		}
		if(msg instanceof Messages) {
			Messages m = (Messages) msg;
			if(m.getCommand().equals("getResult")) {
				for(ActorRef wcfaNode: children) {
					Messages getFileCount = new Messages("getFileCount");
					wcfaNode.tell(getFileCount, getSelf());
				}
			} else if(m.getCommand().equals("getCount")) {
				for(String s: map.keySet()){
					total += map.get(s);
				}
//				System.out.println("Total number of words is " + total);
				
				Messages resMsg = new Messages(String.valueOf(total));		
				getSender().tell(resMsg, getSelf());
			} else if(m.getCommand().equals("getStarted")) {
				String path = "input_data";
				File file = new File(path);
				File[] files = file.listFiles();
				for(File txt : files){
					//only count the .txt file
					String name = txt.getPath();
					String[] extension = name.split("\\.");
					if(!extension[extension.length - 1].equals("txt")){
						continue;
					}

					log.log(Level.FINE, txt.getPath());
					log.log(Level.INFO, txt.getAbsolutePath());
					Props wcfa = Props.create(WordCountInAFileActor.class);
					ActorRef wcfaNode = getContext().actorOf(wcfa, txt.getName());
					children.add(wcfaNode);
					FileNameAndPath fnap = new FileNameAndPath(txt.getName(), txt.getAbsolutePath());
					wcfaNode.tell(fnap, getSelf());
				}
			} else{
				unhandled(msg);
			}
		}
	}

}
