package server;

import entity.Player;
import entity.WorldS;

public class ServerS implements Runnable{

	
	public static int maxPlayers;
	static WorldS world;
	Player[] players;
	boolean randomT=false;
	static int i; 
	
	
	
	public ServerS(String add, int port,int numPlayers, boolean randomT ) {
		maxPlayers = numPlayers;
		new Thread(new ServerConnHandler(add,port)).start();
		new Thread(this).start();
		 players = ServerConnHandler.players;
		 this.randomT=randomT;
	}

	@Override
	public void run() {
		boolean done = false;
		System.out.println("run method - ServerS");
		while(done!=true){
		 if(namesFull()==true){ 
			 for(int i=0;i<players.length;i++){
				 players[i].sendMessage("120 Welcome "+players[i].getName()+". Game starts");
			 }
			 System.out.println("Game starts.");
			 sendNames();
			 System.out.println("making world");
      	   world = new WorldS(players,randomT);
      	   //serverSoket.close();
      	   done=true;
      	  }	   
		}
		
	}
	
	//Return true if all players names are present. This is true if all players responded initial connection.
	 boolean namesFull(){
		for(int i=0;i<players.length;i++){
			if (players[i]==null || players[i].isNameSet()==false){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				return false;		
			}
		}
			
		return true;
	}
	 
	 
	 //125 Sending names and id`s to players
	 public void sendNames(){
			for(int i =0;i<players.length;i++){
				players[i].sendMessage("125 "+players.length+" "+makeGroup());
				players[i].sendMessage("130 "+players[i].getId());
			}
		}
	//125 building names and id`s string line
		private String makeGroup(){
			String line="";
			for(int i=0;i<players.length;i++){
				line=line+players[i].getName()+":"+players[i].getId()+":";
			}
			return line;
		}
}
