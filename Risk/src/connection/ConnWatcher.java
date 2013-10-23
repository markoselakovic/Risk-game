package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import screens.GameScreen;
import screens.WelcomeScreen;

import logic.Logic;
import entity.World;

public class ConnWatcher implements Runnable{
	//Connection settings
	 private GameScreen client;   // reference to top-level client
	 private Logic logic;
	 private BufferedReader in;   // stream coming from the server
	 private static int PORT;     // server details
	 private static String HOST;
	 private Socket sock;
	 private PrintWriter out;  // output to the server
	 
	  
	 public ConnWatcher(GameScreen gameScreen,World world){
		PORT=WelcomeScreen.PORT;
		HOST=WelcomeScreen.ADDRESS;
		client = gameScreen;
		logic = new Logic(world,this);
		  {
		    try {
		      sock = new Socket(HOST, PORT);
		       in  = new BufferedReader(new InputStreamReader( sock.getInputStream() ) );
		      out = new PrintWriter( sock.getOutputStream(), true );  // autoflush
		    
		    	}
		    catch(IOException e)
		    	{  System.out.println("Problem"+e.getMessage());  }
		   }
	 }
	 
	 
	 public void sendMessage(String msg){
		 out.println(msg);
		 }
	  
	  
	 public void closeLink()	  {
		out.close();
	 }
	 
	 
	@Override
	public void run() {
		System.out.println("Run method!!!");
		{ String line;
	    try {
	      while ((line = in.readLine()) != null) {
	    	  if(line.substring(0,3).equals("900"))closeLink();
	        client.stampaj(line);
	      logic.process(line);
	        }
	    }
	    catch(Exception e){    // socket closure will cause termination of while
	    	 System.out.println("Socket closed!!!");  
	    	 System.out.println(e.getLocalizedMessage());
	    	 }
		}

	}
}	
