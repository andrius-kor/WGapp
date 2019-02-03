/**
 * ReadServer class implementing Runnable interface for separate thread execution.
 * This class deals with messages sent from server and displays them for client.
 * 
 * @author ak
 */
package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import gui.ChatTextPane;

public class ReadServer implements Runnable {
  
  private ObjectInputStream in;
  private String message;
  private ChatTextPane chat;

  /**
   * ReadServer constructor.
   * 
   * @param in ObjectInputStream type parameter, containing servers input stream.
   */
  public ReadServer(ObjectInputStream in, ChatTextPane chat) {
    this.in = in;
    this.chat = chat;
  }

  /**
   * Runnable interface implemented method.
   * Keeps reading from servers ObjectInputStream and displaying messages.
   */
  @Override
  public void run() {
    do {
      try {
        message = (String) in.readObject();
        chat.displayMesssage(message);
      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      System.out.println(message);
    }while(!message.equalsIgnoreCase("closed"));
  }

}
