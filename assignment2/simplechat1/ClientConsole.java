// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

import client.ChatClient;
import common.*;
import ocsf.client.*;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String host, int port) 
  {
    try 
    {
      client= new ChatClient(host, port, this);
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
    try
    {
      BufferedReader fromConsole = 
        new BufferedReader(new InputStreamReader(System.in));
      String message;

      while (true) 
      {
        message = fromConsole.readLine();
       //***E50 'AW'
        if(message.charAt(0)=='#') {
        	command(message);
        }else {
        client.handleMessageFromClientUI(message);
        }
      }
    }
    //**** changed for E49
    catch (IOException e) {
    	System.out.println("Server Disconnected");
    }
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }
//******method created / changed for E50 'AW'
  private void command(String message) throws IOException {
	String command = message.substring(1);
	switch(command) {
	case "quit":
		client.quit();
		break;
	case "logoff":
		client.closeConnection();
		break;
	case "sethost":
		System.out.println("Enter new Host");
		BufferedReader readHost = new BufferedReader(new InputStreamReader(System.in));
		String newHost = readHost.readLine();
		client.setHost(newHost);
		break;
	case "setport":
		System.out.println("Enter new Port Number");
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int newPort = Integer.parseInt(read.readLine());
		client.setPort(newPort);
		break;
	case "login":
		client.openConnection();
		break;
	case "gethost":
		System.out.println(client.getHost());
		break;
	case "getport":
		System.out.println(client.getPort());
		break;
	default:
		System.out.println(message +" is not a valid command");
		break;
	}
}


/**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println("> " + message);
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {
    String host = "";
    int port;;  //The port number

    try
    {
      host = args[0];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
    
      host = "localhost";
    }
    //*** start of mod E49 part b 'AW'
    BufferedReader reader = new	BufferedReader(new InputStreamReader(System.in));
    try {
    	if(args[1]==null) {

    	    System.out.println("Enter port number");
    	    port = reader.read();
    	}
    	else {
    		port = Integer.parseInt(args[1]);
    	}
	} catch (IOException e) {
		port = DEFAULT_PORT;
		System.out.println("Port set to default port 1");
	}
    //*** end of E49 b mod
    ClientConsole chat= new ClientConsole(host, port);
    chat.accept();  //Wait for console data
  }
}
//End of ConsoleChat class
