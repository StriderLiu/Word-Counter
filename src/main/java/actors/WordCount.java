package actors;

import java.util.concurrent.TimeUnit;

import actor.messages.Messages;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;

/**
 * Main class for your wordcount actor system.
 * 
 * @author akashnagesh
 *
 */
/**
 * modified by Wenbo Liu
 * @author vincentliu
 *
 */
public class WordCount {

	public static void main(String[] args) throws Exception {
		ActorSystem system = ActorSystem.create("wordcounter");
		/*
		 * Create the WordCountActor and send it the StartProcessingFolder
		 * message. Once you get back the response, use it to print the result.
		 * Remember, there is only one actor directly under the ActorSystem.
		 * Also, do not forget to shutdown the actorsystem
		 */
		Props wcProps = Props.create(WordCountActor.class);
		ActorRef wcaNode = system.actorOf(wcProps, "actor");
		Messages m0 = new Messages("getStarted");
		wcaNode.tell(m0, null);
		Thread.sleep(5000);
		Messages m1 = new Messages("getResult");
		wcaNode.tell(m1, null);
		Thread.sleep(1000);
		Messages m2 = new Messages("getCount");
		
		final Timeout timeout = new Timeout(1, TimeUnit.SECONDS);
        final Future<Object> future = Patterns.ask(wcaNode, m2, timeout);
        final Messages result = (Messages) Await.result(future, timeout.duration());
        System.out.println("The word count result is: " + result.getCommand());
		Thread.sleep(1000);
		
		system.terminate();
	}

}
