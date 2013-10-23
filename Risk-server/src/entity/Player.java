package entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player implements Runnable{
	//Server-side Player
	private String name;
	public String address;			
	public int port;
	int id;
	int unusedArmy;						
	public PrintWriter out;
	BufferedReader in;
	Socket socket;
	String line="";

	public Player(Socket s,int id){
		socket = s;
		address = s.getInetAddress().getHostAddress();
		port = s.getPort();
		this.id = id;
		try{
		in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(),true);
		
		}catch(Exception e){
			System.out.println(e);
		}
	}

	
	
	@Override
	public void run() {
		
	     readLine();
	}
	
	
	/*
	public Player( String address, int port, int id,PrintWriter out){
		this.out = out;
		this.address = address;
		this.port = port;
		this.id = id;
	}
	 */
	
	public void checkIfName(String line){
		String code = line;
		code = code.substring(0, 3);
		if(code.equals("111")){
			name = line.substring(4);
		}
	}
	
	
	public String readLine(){
		
		 boolean done = false;
	     try {
	       while (!done) {
	       
	         if((line = in.readLine()) == null)
	           done = true;
	         else {
	        	System.out.println("Player ID("+id+")= "+line);
	        	checkIfName(line);
	        	return line;
	        	
	         }
	        
	      }
	     }catch(IOException e){
	     return "999 "+id;
	     }
		
		
		return line;
		
	
	}
	
	
	public boolean isNameSet(){
		if (name!=null)return true;
		return false;
	}
	
	 public void sendMessage(String msg)
	  {  out.println(msg);  }
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getUnusedArmy() {
		return unusedArmy;
	}


	public void setUnusedArmy(int unusedArmy) {
		this.unusedArmy = unusedArmy;
	}

	public String getName(){
		return name;
	}
	
	public void decreaseUnusedArmies(){
		--unusedArmy;
	}
	
	public void closeLink(){
		System.out.println("Player ("+id+") left.");
		out.close();
	}
	
	public boolean haveUnSetArmies(){
		if (unusedArmy>0)return true;
		else return false;
	}
}
