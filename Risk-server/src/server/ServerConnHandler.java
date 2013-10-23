package server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import entity.Player;

public class ServerConnHandler  implements Runnable{


	public int port=2222;
	public static Player[] players = new Player[ServerS.maxPlayers];
	int i=0;
	String add="127.0.0.1";
	
	
	public ServerConnHandler(String add, int port){
		this.add=add;
		this.port = port;
	}
	
	@Override
	public void run() {
		enableContact();
		
	}

	
	private void enableContact(){
		 Socket soket = null;
	        try{
	            InetAddress addr = InetAddress.getByName(add);
	            @SuppressWarnings("resource")
	            ServerSocket serverSoket = new ServerSocket(port,0,addr);	           
	            System.out.println("Server running...");
	            while(true){
	                soket = serverSoket.accept();
	               
	                for (i=0; i < ServerS.maxPlayers; i++) {
	                    if(players[i]==null){
	                        players[i]=new Player(soket,i);
	                        System.out.println("New player");
	                        System.out.println(players[0].address+" "+players[0].port);
	                        new Thread(players[i]).start();
	                        players[i].sendMessage("110 Welcome player. Waiting for others. Give us your name.");
	                        break;
	                    }
	                }
	                 
	            }
	        }catch(Exception e){
	            System.out.println(e.getMessage());
	        }
	}
}
