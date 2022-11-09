import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import client.ChatClient;
import common.ChatIF;
// this class was added as part of e50 ' AW'
public class ServerConsole implements ChatIF{

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
	 * @throws InterruptedException 
	   */
	  
	  public ServerConsole(String host, int port) 
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
	        client.handleMessageFromServer( "SERVER MSG> " + message);
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
	  


	/**
	   * This method overrides the method in the ChatIF interface.  It
	   * displays a message onto the screen.
	   *
	   * @param message The string to be displayed.
	   */
	  public void display(String message) 
	  {
	    System.out.println(message);
	  }

	  
	  //Class methods ***************************************************
	  
	  /**
	   * This method is responsible for the creation of the Client UI.
	   *
	   * @param args[0] The host to connect to.
	 * @throws InterruptedException 
	   */
	  public static void main(String[] args) throws InterruptedException 
	  {
	    String host = "";
	    int port;;  //The port number
	    host = "localhost";
	    //*** start of mod E49 part b 'AW'
	    try {
	   port = Integer.parseInt(args[0]);
	    }
	    catch (ArrayIndexOutOfBoundsException e) {
	    	port = DEFAULT_PORT;
	    }
	   //*** end of E49 b mod
	    ServerConsole chat= new ServerConsole(host, port);
	    chat.accept();  //Wait for console data
	  }
}

