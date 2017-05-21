package actor.messages;

/**
 * Messages that are passed around the actors are usually immutable classes.
 * Think how you go about creating immutable classes:) Make them all static
 * classes inside the Messages class.
 * 
 * This class should have all the immutable messages that you need to pass
 * around actors. You are free to add more classes(Messages) that you think is
 * necessary
 * 
 * @author akashnagesh
 *
 */
/**
 * modified by Wenbo Liu
 * @author vincentliu
 *
 */
public class Messages {
		private String command;
		public Messages(String command){
			this.command = command;
		}
		public String getCommand() {
			return command;
		}
}