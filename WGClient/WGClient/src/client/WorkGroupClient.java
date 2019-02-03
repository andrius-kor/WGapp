/**
 * Class representing client of a application.
 * 
 * @author ak
 */
package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import gui.ChatTextPane;

public class WorkGroupClient implements Runnable
{

  private Socket connection;
  private String message;
  private Scanner console;
  private String ipaddress;
  private int portaddress;
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private String nickName;

  /**
   * ChatClient constructor.
   * Getting server IP address, port and nick name of a client.
   */
  public WorkGroupClient() {
    console = new Scanner(System.in);

    System.out.println("Enter the IP Address of the server");
    //ipaddress = console.nextLine();
    ipaddress = "127.0.0.1";

    System.out.println("Enter the TCP Port");
    //portaddress  = console.nextInt();
    portaddress = 2005;

    System.out.println("Enter nick name: ");
    nickName = console.next();

  }
  /**
   * Method writing to ObjectOutputStream instance to send messages.
   * 
   * @param msg Type String representing message to be sent.
   */
  public void sendMessage(String msg){
    try{
      out.writeObject(msg);
      out.flush();
    }
    catch(IOException ioException){
      ioException.printStackTrace();
    }
  }
  /**
   * Method starting client code execution.
   * Initializing Socket connection and input/output streams.
   * Do while loop for reading from std input and sending to output stream to server.
   */
  public void execute(){
    
    
    
    try {
      connection = new Socket(ipaddress,portaddress);

      out = new ObjectOutputStream(connection.getOutputStream());
      out.flush();
      in = new ObjectInputStream(connection.getInputStream());
      System.out.println("Client Side ready to communicate");
      System.out.println((String)in.readObject());
      sendMessage(nickName);
      
      ChatTextPane chat = new ChatTextPane(this);
      
      /*SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          try {
            ChatTextPane frame = new ChatTextPane();
            frame.setVisible(true);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });*/
      new Thread(chat).start();

      new Thread(new ReadServer(in, chat)).start();

      do {
        message = console.next();
        sendMessage(message);
      } while (!message.equalsIgnoreCase("-12"));
    }catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  /**
   * Runnable interface implemented method.
   * Sets thread name to clients nickname.
   * Calls start method to begin client execution.
   */
  @Override
  public void run() {
    Thread.currentThread().setName(nickName);
    execute();
    
  }
  
  public WorkGroupClient getClient() {
    return this;
  }
  
}