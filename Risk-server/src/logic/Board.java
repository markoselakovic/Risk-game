package logic;



import entity.Player;
import entity.WorldS;

public class Board implements Runnable{
	
	    				
	WorldS world;
	Player[] players;
	int playingPlayer=0;
	int territoryId=0;
	boolean randomT=false;
	//GAME STATE
	private static int LapsToEnd = 15;
	private static int GAMESTATE = 0;
	private static final int ZAUZIMANJE_TERITORIJE = 1;
	private static final int RASPOREDJIVANJE_OSTATKA_ARMIJA = 2;
	private static final int NAPAD = 3;
	private static final int POJACAVANJE_SNAGA = 4;
	private static final int KRAJ = 5;
	int playersPlayed=0;
	
	
	
	public Board(WorldS world, Player[] players, boolean randomT){
		this.world = world;
		this.players = players;
		playingPlayer=0;
		this.randomT=randomT;
		GAMESTATE = ZAUZIMANJE_TERITORIJE;
		new Thread(this).start();
	}

	
	
	//Game Logic
	public void run(){while(true){
		if(GAMESTATE==ZAUZIMANJE_TERITORIJE){
			while(world.unSetTerritories()){
				if(randomT){
					String line = process("210 "+playingPlayer+" "+territoryId);
					if(line.substring(0, 3).equals("220")){
						broadcast(line);
						nextPlayer(false);
						territoryId++; 
					}
					continue;
				}
				boolean chosen = false;
				while(!chosen){
					broadcast("150 "+playingPlayer+" - Players turn.");
					players[playingPlayer].sendMessage("200 Choose your territory");
					String line=process(players[playingPlayer].readLine());
					if(line.substring(0, 3).equals("220")){
							if(line.substring(6).equals("-1"))continue;
							
						broadcast(line);
						nextPlayer(false);		//playingPlayer++						
						chosen = true;	
					}else {players[playingPlayer].sendMessage(line);
					
					}
				}
			}
			System.out.println("Zauzimanje teritorija gotovo");
			GAMESTATE = RASPOREDJIVANJE_OSTATKA_ARMIJA;
		}
		
		if(GAMESTATE == RASPOREDJIVANJE_OSTATKA_ARMIJA){
			while(world.unSetArmies()){
				if(players[playingPlayer].getUnusedArmy()>=0){	
					boolean chosen = false;
					while(!chosen){
						broadcast("150 "+playingPlayer+" - Players turn.");
						players[playingPlayer].sendMessage("160 "+players[playingPlayer].getUnusedArmy()+"  Armies left.");
						players[playingPlayer].sendMessage("300 Choose territory to set more armies");
						String line=process(players[playingPlayer].readLine());
							if(line.substring(0, 3).equals("320")){
							if(line.substring(6).equals("-1"))continue;
						
							broadcast(line);
							players[playingPlayer].sendMessage("160 "+players[playingPlayer].getUnusedArmy()+"  Armies left.");
							nextPlayer(false);		//playingPlayer++						
							chosen = true;	
					}else {players[playingPlayer].sendMessage(line);
					}	
				}
				 
			}
				else nextPlayer(false);	
			}
			System.out.println("Rasporedjivanje snaga gotovo");
			GAMESTATE = NAPAD;
			
		}
		
		if(GAMESTATE==NAPAD){
			boolean lapEnded = false;
			playingPlayer=0;
			System.out.println("Napad");
			while(moreLaps() && !isGameOver()){
				if(!playerHasTerritories()){			//u slucaju da igrac vise nema teritorija, sa njim vise ne komuniciramo
					nextPlayer(true);
					continue;
				}
				boolean attacking=true;
				while(attacking){
					broadcast("150 "+playingPlayer+" - Players turn.");
					players[playingPlayer].sendMessage("400 Your turn to attack!");
					String line=process(players[playingPlayer].readLine());
					if(line.substring(0, 3).equals("411"))players[playingPlayer].sendMessage(line);//javljamo gresku korisniku
					if(line.substring(0, 3).equals("415")){
						players[playingPlayer].sendMessage(line);
						String processedAttack = world.processAttack(line);
						broadcast("420 "+processedAttack);
					}
					
					if(line.substring(0, 3).equals("430")){
						if(playingPlayer==players.length-1)lapEnded = true;
						nextPlayer(true);
						attacking=false;
						break;
					}
				}
				if(lapEnded){
					GAMESTATE=POJACAVANJE_SNAGA;
					break;
				}
				
			}
			
		}
	
		
		if(GAMESTATE == POJACAVANJE_SNAGA){
			System.out.println("Pojacavanje snaga.");
			draftArmies();
			while(world.unSetArmies()){
				System.out.println("UnsetArmies.");
				if(players[playingPlayer].haveUnSetArmies()){	
					boolean chosen = false;
					while(!chosen){
						broadcast("150 "+playingPlayer+" - Players turn.");
						players[playingPlayer].sendMessage("160 "+players[playingPlayer].getUnusedArmy()+"  Armies left.");
						players[playingPlayer].sendMessage("500 Choose territory to set more armies");
						String line=process(players[playingPlayer].readLine());
							if(line.substring(0, 3).equals("520")){
							if(line.substring(6).equals("-1"))continue;
						
							broadcast(line);
							players[playingPlayer].sendMessage("160 "+players[playingPlayer].getUnusedArmy()+"  Armies left.");
							nextPlayer(false);		//playingPlayer++						
							chosen = true;	
					}else {players[playingPlayer].sendMessage(line);
					}	
				}
				if(!world.unSetArmies()){
					GAMESTATE=NAPAD;
					System.out.println("Pojacavanje snaga gotovo");
					break;
				}
			}	
				else nextPlayer(false);	
				continue;
			}
			 
			
			
			
		}
		
		if(GAMESTATE==KRAJ){
			
			
		}
		
	}	
	}
	
	
	public String process(String msg){
		String code = msg.substring(0, 3);
		//ChooseTerritory
		if(code.equals("210")){
				if(world.chooseTerritory(msg)){				
				return "220 "+msg.substring(4, 5)+" "+msg.substring(6);
			}
			else return "215 Territory in use";
		}
		if(code.equals("310")){
			if(world.increaseArmies(msg)){				
				return "320 "+msg.substring(4, 5)+" "+msg.substring(6);
			}
			else return "311 Enemy territory";		
		}
		if(code.equals("410"))
			if(world.isAttackOk(msg))return "415 "+world.getDicesNumbers(msg);
				else return "411 Error - Not enough armies or forbidden territory ";
		
		if(code.equals("430"))return "430 End of attackig phase";
		
		if(code.equals("510")){
			if(world.increaseArmies(msg)){				
				return "520 "+msg.substring(4, 5)+" "+msg.substring(6);
			}
			else return "511 Enemy territory";		
		}
		if(code.equals("999"))
			try{
				int id = Integer.parseInt(msg.substring(4, 5));
				players[id].closeLink();
			}catch(Exception e){
				System.out.println("problem in process method!!!!");
			}
			
			
			
		return "999 Error";				
	}
	
	
	
	//return the id of the next player, and updates the playingPlayer
	private int nextPlayer(boolean inAttack){
		if(playingPlayer!=players.length-1)playingPlayer++;
		else {
			playingPlayer=0;
			if(inAttack){
			LapsToEnd--;
			}
			System.out.println("Laps to end: "+LapsToEnd);
		}
		return playingPlayer;
	}
	
	public void broadcast(String line){
		for(int i = 0;i<players.length;i++){
			players[i].sendMessage(line);
		}
	}
	
	
	public boolean isGameOver(){
		int id1 = world.territoryS.get(0).getOwner();
		for(int i=0;i<world.territoryS.size();i++)
			if(id1!=world.territoryS.get(i).getOwner())return false;
		
			broadcast("600 Game over. Player "+id1+" wins.");
			
			System.out.println("600 Game over. Player "+id1+" wins.");
						for(int i = 0;i<players.length;i++){
				players[i].closeLink();
				GAMESTATE=KRAJ;
			}
		return true;
	}
	
	public boolean playerHasTerritories(){
		for(int i=0;i<world.territoryS.size();i++){
			if(playingPlayer==world.territoryS.get(i).getOwner())return true;
		}
		return false;
	}
	
	private boolean moreLaps(){
		if(LapsToEnd>0)return true;
		broadcast("900 Game Over.");
		System.out.println("GAME OVER.");
		for(int i = 0;i<players.length;i++){
			players[i].closeLink();
		}
		return false;
	}
	
	private void draftArmies(){
		int player=0;
		while(player!=players.length){
			
			int numOfTer=0;
			int armies=0;
			for(int i=0;i<world.territoryS.size();i++){
				if(player==world.territoryS.get(i).getOwner())numOfTer++;
			}
			if(numOfTer==0){
				player++;
				continue;
			}
			armies = numOfTer/3;
			if(armies>3)players[player].setUnusedArmy(armies);
			else players[player].setUnusedArmy(3);
			player++;
		}
	}
	
	
}
