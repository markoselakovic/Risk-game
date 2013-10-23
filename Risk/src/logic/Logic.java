package logic;

import screens.WelcomeScreen;
import connection.ConnWatcher;
import entity.Player;
import entity.World;

public class Logic {

	
	World world;
	ConnWatcher conn;
	Player[] players;
	
	public Logic(World world,ConnWatcher conn){
		this.world = world;
		this.conn = conn;
		
	}
	
	public void process(String msg){		
		String code = msg.substring(0, 3);		
		if(code.equals("110"))sendName();
		if(code.equals("125"))setNames(msg);
		if(code.equals("130"))setID(msg);
		if(code.equals("150"))setPlayersTurn(msg);
		if(code.equals("160"))setUnsetArmies(msg);
		if(code.equals("200"))conn.sendMessage("210 "+getId()+" "+world.chooseTerritory(false));	//uraditi proveru za -1 i izbaciti to	
		if(code.equals("220"))setTerritory(msg);
		if(code.equals("300"))conn.sendMessage("310 "+getId()+" "+world.chooseTerritory(false));
		if(code.equals("320"))increaseArmies(msg);
		if(code.equals("400"))conn.sendMessage(processAttack());
		if(code.equals("415"))updateDices(msg);
		if(code.equals("420"))processAttackResults(msg);
		if(code.equals("500"))conn.sendMessage("510 "+getId()+" "+world.chooseTerritory(false));
		if(code.equals("520"))increaseArmies(msg);
		if(code.equals("600"))gameOver(msg);
						
		
	}
	
	//111
	public void sendName(){
		conn.sendMessage("111 "+WelcomeScreen.nameString);
	}
	//125 For given line makes an array of Players names, id`s pairs.
	void setNames(String msg){
		int j=0;
		String[] niz = msg.substring(6).split(":");
		players = new Player[niz.length/2];
		try{
		for(int i=0;i<players.length;i++){
		players[i] = new Player(niz[j], Integer.parseInt(niz[j+1]));
				j+=2;
		}
		world.setPlayers(players);
		world.setThisPlayerId(getId());
		world.setNamesVisible();
		}catch(Exception e){
			System.out.println("Problem parsin id`s in Logic method - setNames "+e);
		}
		
	}
	
	

	//130
	
	public void setID(String msg){
		int id=-1;
		String temp=msg.substring(4, 5);
		try{
			 id = Integer.parseInt(temp);
		}catch(Exception e){
			System.out.println("Problem in setID method - Logic");
			System.out.println(e.getMessage());
			}
		world.setThisPlayerId(id);
	}
	
	//150
	public void setPlayersTurn(String msg){
		int id=-1;
		try{
			 id = Integer.parseInt(msg.substring(4,5));
		}catch(Exception e){
			System.out.println("Problem in setPlayersTurn method - Logic");
			}
		for(int i=0;i<world.namesEntity.length;i++){
		if(i!=id)
			world.namesEntity[i].setAlp(0.47f);
		else world.namesEntity[id].setAlp(1);
		}
	}	
	
	
	
	//220 Sets the owner of the territory
	public void setTerritory(String msg){
		int id=-1;
		int territory = -1;
		String idString = msg.substring(4, 5);
		String ter = msg.substring(6);
		try{
			 id = Integer.parseInt(idString);
			 territory = Integer.parseInt(ter);			
		}catch(Exception e){
			System.out.println("Problem in setTerritory method - Logic");
			}
		world.territory.get(territory).setOwner(id);
		world.territory.get(territory).setArmy_size(1);
		
	}
	
	//160 
	public void setUnsetArmies(String msg){
		String temp;
		int armies=-1;
		try{
			temp = msg.substring(4,6).trim();
			 armies = Integer.parseInt(temp);			
		}catch(Exception e){
			System.out.println("Problem in setUnsetArmies method - Logic");
			}
		world.reinforcement.setLabel(armies+"");
		world.setReinforcementVisibility();
		if(armies==0)
		world.reinforcement.setVisible(false);
		
		
	}
	
	//320 Increase armies
	public void increaseArmies(String msg){
		//int id=-1;
		int territory = -1;
		//String idString = msg.substring(4, 5);
		String ter = msg.substring(6);
		try{
			 //id = Integer.parseInt(idString);
			 territory = Integer.parseInt(ter);			
		}catch(Exception e){
			System.out.println("Problem in increaseArmies method - Logic");
			}
		world.territory.get(territory).increaseArmies();
		
	}
	
	
	
	//400 Attack
	public String processAttack(){
		String line = world.attack();
		if(line.equals("error"))line=world.attack();
		if(line.equals("430"))return "430 End of attack.";
		else		
		return "410 "+getId()+" "+line;
		
	}
	
	//415
	
	public void updateDices(String msg){
		world.attackWindow.update(msg);
	}
	
	//420
	public void processAttackResults(String msg){
		int id1=-1, id2=-1;
		int homeTerr=-1, defTerr=-1;
		int armies1=0,armies2=0;
		
		String[] nizTerr = msg.substring(4).split(":");
		try{
			id1=Integer.parseInt(nizTerr[0]);
			id2=Integer.parseInt(nizTerr[3]);
			homeTerr=Integer.parseInt(nizTerr[1]);
			defTerr=Integer.parseInt(nizTerr[4]);
			defTerr=Integer.parseInt(nizTerr[4]);
			armies1=Integer.parseInt(nizTerr[2]);
			armies2=Integer.parseInt(nizTerr[5]);
			
		}catch(Exception e){
			System.out.println("Problem in processAttackResults method - Logic");
		}
		
		world.territory.get(homeTerr).setOwner(id1);
		world.territory.get(homeTerr).setArmy_size(armies1);
		world.territory.get(defTerr).setOwner(id2);
		world.territory.get(defTerr).setArmy_size(armies2);
		
		
		if(id1==id2)world.playWinsBattleSound();
		
	}
	
	//600  SREDITI POVRATNU VREDNOST
	public int gameOver(String msg){
		int id=-1;
		String winner = msg.substring(22, 23);
		try{
			id = Integer.parseInt(winner);
		}catch(Exception e){}
		return id;
	}
	
	
	
	public int getId(){
		return world.getThisPlayerId();
		
	}
}
