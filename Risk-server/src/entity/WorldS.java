package entity;

import java.util.ArrayList;
import java.util.Iterator;

import logic.Board;


public class WorldS {
	
	public ArrayList<TerritoryS> territoryS;
 	private Iterator< TerritoryS > territoryIterator;
	private TerritoryS t;
	public TerritoryS terInFocus;
	boolean running=true;
	private int[] neutral;								//brisati ukoliko ostane fokus
	Player[] players;
	Board board;

		
	public WorldS(Player[] players, boolean randomT){
		this.players = players;
		
		neutral = new int[42];
		for(int i=0;i<neutral.length;i++)neutral[i]=i;   // Izbrisati ukoliko ostane podesavanje da sve teritorije koje nisu u fokusu bude transparentne 
		System.out.println("In WorldS method.");							 					//	isto i ovaj red
		
		//Postavljanje broja armija u zavisnosti od broja igraca
		setArmiesNumber();
	
		
		//Definisanje i insanciranje Teritorija
		territoryS = new ArrayList<TerritoryS>();
		territoryS.add(new TerritoryS(0,new int[]{1,2,38}));
		territoryS.add(new TerritoryS(1,new int[]{0,2,7,8}));
		territoryS.add(new TerritoryS(2,new int[]{0,1,3,7,8}));
		territoryS.add(new TerritoryS(3,new int[]{2,4,5,7}));
		territoryS.add(new TerritoryS(4,new int[]{3,5,10}));
		territoryS.add(new TerritoryS(5,new int[]{3,4,6,7}));
		territoryS.add(new TerritoryS(6,new int[]{5,7,9}));
		territoryS.add(new TerritoryS(7,new int[]{1,2,3,5,6,8}));
		territoryS.add(new TerritoryS(8,new int[]{1,7,9}));
		territoryS.add(new TerritoryS(9,new int[]{6,8,14}));
		territoryS.add(new TerritoryS(10,new int[]{4,11,12}));
		territoryS.add(new TerritoryS(11,new int[]{10,12,13,21}));
		territoryS.add(new TerritoryS(12,new int[]{10,11,13}));
		territoryS.add(new TerritoryS(13,new int[]{11,12}));
		territoryS.add(new TerritoryS(14,new int[]{9,15,18}));
		territoryS.add(new TerritoryS(15,new int[]{14,16,19}));
		territoryS.add(new TerritoryS(16,new int[]{15,17,19}));
		territoryS.add(new TerritoryS(17,new int[]{16,19,20,22,31}));
		territoryS.add(new TerritoryS(18,new int[]{14,19,20}));
		territoryS.add(new TerritoryS(19,new int[]{15,16,17,18,20}));
		territoryS.add(new TerritoryS(20,new int[]{17,18,19,30,31,35}));
		territoryS.add(new TerritoryS(21,new int[]{11,22,23,24}));
		territoryS.add(new TerritoryS(22,new int[]{17,21,23,31}));
		territoryS.add(new TerritoryS(23,new int[]{21,22,24,25}));
		territoryS.add(new TerritoryS(24,new int[]{21,23,25}));
		territoryS.add(new TerritoryS(25,new int[]{23,24}));
		territoryS.add(new TerritoryS(26,new int[]{27,28}));
		territoryS.add(new TerritoryS(27,new int[]{26,28,29}));
		territoryS.add(new TerritoryS(28,new int[]{26,27,29,34}));
		territoryS.add(new TerritoryS(29,new int[]{27,28}));
		territoryS.add(new TerritoryS(30,new int[]{20,31,32,33,35}));
		territoryS.add(new TerritoryS(31,new int[]{17,20,22,30,32}));
		territoryS.add(new TerritoryS(32,new int[]{30,31,33,34}));
		territoryS.add(new TerritoryS(33,new int[]{30,32,34,35,36,40}));
		territoryS.add(new TerritoryS(34,new int[]{28,32,33}));
		territoryS.add(new TerritoryS(35,new int[]{20,30,33,36}));
		territoryS.add(new TerritoryS(36,new int[]{33,35,37,39,40}));
		territoryS.add(new TerritoryS(37,new int[]{36,38,39}));
		territoryS.add(new TerritoryS(38,new int[]{0,37,39,40}));
		territoryS.add(new TerritoryS(39,new int[]{36,37,38,40}));
		territoryS.add(new TerritoryS(40,new int[]{33,36,38,39}));
		//terInFocus = new TerritoryS( -1, new int[]{});
		//territoryS.add(terInFocus);
		
		
		
		
		
		
		//Dodaj sve teritorije na stage
		for (territoryIterator = territoryS.iterator(); territoryIterator.hasNext();) {
			// Get the next territory in line
			t = territoryIterator.next();			
			
			
		}
		board = new Board(this,players, randomT);
	}
	
	//Set armies number in constructor in correlation with a number of players
		private void setArmiesNumber() {
			int number=0;
			int numOfPlayers = players.length;
			if(numOfPlayers==2)number=28;
			if(numOfPlayers==3)number=25;
			if(numOfPlayers==4)number=30;
			if(numOfPlayers==5)number=25;
			if(numOfPlayers==6)number=20;
			for(int i=0;i<players.length;i++){
				players[i].setUnusedArmy(number);				
			}
		}
	
	//ChooseTerritory
		public boolean chooseTerritory(String msg){
			int id=-1;
			int territory = -1;
			String idString = msg.substring(4, 5);
			String ter = msg.substring(6);
			try{
				 id = Integer.parseInt(idString);
				 territory = Integer.parseInt(ter);			
			}catch(Exception e){
				System.out.println("Problem in chooseTerritory method - WorldS");		}
			for (territoryIterator = territoryS.iterator(); territoryIterator.hasNext();) {
				// Get the next territory in line
				t = territoryIterator.next();				
				if(t.getId()==territory && t.getOwner()==6){
					players[id].decreaseUnusedArmies();
					System.out.println("player("+id+") unusedArmies ="+players[id].getUnusedArmy());
					t.setOwner(id);
					t.increaseArmies();
					return true;			
				}
			}
			return false;
		}
		
		public boolean unSetTerritories(){
			for (territoryIterator = territoryS.iterator(); territoryIterator.hasNext();) {
				// Get the next territory in line
				t = territoryIterator.next();			
				if(t.getOwner()==6)return true;			
			}
			return false;
		}
		
	
	
	
	//unSetArmies - if players have some unSet armies, they must set them to some territory 
	public boolean unSetArmies(){
		for(int i=0;i<players.length;i++){
			if(players[i].getUnusedArmy()>0)return true;
		}
		return false;
	}

	public boolean increaseArmies(String msg){
		int id=-1;
		int territory = -1;
		String idString = msg.substring(4, 5);
		String ter = msg.substring(6);
		try{
			 id = Integer.parseInt(idString);
			 territory = Integer.parseInt(ter);			
		}catch(Exception e){
			System.out.println("Problem in increaseArmies method - WorldS");		}
		for (territoryIterator = territoryS.iterator(); territoryIterator.hasNext();) {
			// Get the next territory in line
			t = territoryIterator.next();				
			if(t.getId()==territory && t.getOwner()==id){
				t.increaseArmies();
				players[id].decreaseUnusedArmies();
				System.out.println("player("+id+") unusedArmies ="+players[id].getUnusedArmy());
				return true;			
			}
		}
		return false;
		
	}

	
public boolean isAttackOk(String msg){
		int id=-1;
		int homeTerr = -1;
		int defTerr = -1;
		String idString = msg.substring(4,5);
		String terr=msg.substring(6);        
		String[] niz = terr.split(":");
		try{
			 id = Integer.parseInt(idString);
			 homeTerr = Integer.parseInt(niz[0]);			
			 defTerr = Integer.parseInt(niz[1]);			
		}catch(Exception e){
			System.out.println("Problem in attack method - WorldS");		
			}
		
		if(homeTerr>=0&& homeTerr<=40 && defTerr>=0&& defTerr<=40 && territoryS.get(homeTerr).getOwner()==id && territoryS.get(homeTerr).canAttack(defTerr)
				&& moreThen1Armies(homeTerr))return true;
		else return false;
	}
	
	public String getDicesNumbers(String msg){
		
		//random dices
		int a1 = 1+(int)(Math.random()*6);
		int a2 = 1+(int)(Math.random()*6);
		int a3 = 1+(int)(Math.random()*6);
		int d1 = 1+(int)(Math.random()*6);
		int d2 = 1+(int)(Math.random()*6);
		
		int homeTerr = -1;
		int defTerr = -1;		
		String terr=msg.substring(6);        
		String[] niz = terr.split(":");
		try{			
			 homeTerr = Integer.parseInt(niz[0]);			
			 defTerr = Integer.parseInt(niz[1]);			
		}catch(Exception e){
			System.out.println("Problem in getDicesNumber method - WorldS");		
			}
		if(territoryS.get(homeTerr).getArmy_size()==2 && territoryS.get(defTerr).getArmy_size()==2)return a1+" 0 0 "+d1+" "+d2+" "+homeTerr+":"+defTerr;
		if(territoryS.get(homeTerr).getArmy_size()==2 && territoryS.get(defTerr).getArmy_size()==1)return a1+" 0 0 "+d1+" 0"+" "+homeTerr+":"+defTerr;
		if(territoryS.get(homeTerr).getArmy_size()==3 && territoryS.get(defTerr).getArmy_size()==1)return a1+" "+a2+" 0 "+d1+" 0"+" "+homeTerr+":"+defTerr;
		if(territoryS.get(homeTerr).getArmy_size()>3 && territoryS.get(defTerr).getArmy_size()==1)return a1+" "+a2+" "+a3+" "+d1+" 0"+" "+homeTerr+":"+defTerr;
		if(territoryS.get(homeTerr).getArmy_size()==2 && territoryS.get(defTerr).getArmy_size()>2)return a1+" 0 0 "+d1+" "+d2+" "+homeTerr+":"+defTerr;
		else return a1+" "+a2+" "+a3+" "+d1+" "+d2+" "+homeTerr+":"+defTerr;
		
	}
	
	public String processAttack(String line){
		int a1=0,a2=0,a3=0,d1=0,d2=0;
		int homeTerr = -1;
		int defTerr = -1;	
		String terr=line.substring(14);        
		String[] niz = terr.split(":");
		try{
			a1=Integer.parseInt(line.substring(4, 5));
			a2=Integer.parseInt(line.substring(6, 7));
			a3=Integer.parseInt(line.substring(8, 9));
			d1=Integer.parseInt(line.substring(10, 11));
			d2=Integer.parseInt(line.substring(12, 13));
			homeTerr = Integer.parseInt(niz[0]);			
			defTerr = Integer.parseInt(niz[1]);
				
		}catch(Exception e){
			System.out.println("Problem in processAttack method - WorldS");
		}
		//ordering the dices
		int w1=Math.max(a1, Math.max(a2, a3));
		int w2=middleW(a1,a2,a3);
		@SuppressWarnings("unused")
		int w3=Math.min(a1, Math.min(a2, a3));
		int def1=Math.max(d1, d2);
		int def2=Math.min(d1, d2);
		
		if(w1>def1)territoryS.get(defTerr).decreaseArmies();
			else territoryS.get(homeTerr).decreaseArmies();
		if(w2!=0 && def2!=0){
			if(w2>def2)territoryS.get(defTerr).decreaseArmies();
			else territoryS.get(homeTerr).decreaseArmies();
		}
		if(territoryS.get(defTerr).getArmy_size()==0){
			territoryS.get(defTerr).setOwner(territoryS.get(homeTerr).getOwner());
			territoryS.get(defTerr).increaseArmies();
			territoryS.get(homeTerr).decreaseArmies();
			
		}
			
			
			
		//return id1:homeTerr:homeTerrArmie_size:id2:defTerr:defTerrArmie_size
		return territoryS.get(homeTerr).getOwner()+":"+homeTerr+":"+territoryS.get(homeTerr).getArmy_size()+":"+
				territoryS.get(defTerr).getOwner()+":"+defTerr+":"+territoryS.get(defTerr).getArmy_size();
		
		
	}
	
	private int middleW(int a1, int a2, int a3){
		int[] niz = new int[3];
		niz[0]=a1; niz[1]=a2; niz[2]=a3;
		int min,temp,i=0;
		for(;i<niz.length-1;i++){
			min=i;
			for(int j=i+1;j<niz.length;j++){
				if (niz[j]<niz[min])min=j;
			}
			temp=niz[i];
			niz[i]=niz[min];
			niz[min]=temp;			
		}
		return niz[1];
	}
	
	private boolean moreThen1Armies(int TerrID){
		if(territoryS.get(TerrID).getArmy_size()>1)return true;
		else return false;
	}
	
}
	

		
