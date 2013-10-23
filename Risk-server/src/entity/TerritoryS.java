package entity;


public class TerritoryS {
	
	
	
	protected int owner=6;
	protected int army_size=0;
	private int id;
	private int[] canAttack;	
	
	
	
	public TerritoryS(int id, int[] canAttack){
		this.id = id;
		this.canAttack = canAttack;		
		
	
	}
	public boolean containsId(int id){
		for(int i=0;i<canAttack.length;i++){
			if (id==canAttack[i])return true;
		}return false;
	}
		
	//Getters and Setters 
	
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		if(owner>=0 && owner <=6)
		this.owner = owner;		
		else this.owner=6;
	}
	public int getArmy_size() {
		return army_size;
	}
	public void setArmy_size(int army_size) {
		this.army_size = army_size;
	}
	
	public int getId(){
		return id;
	}
	
	public void increaseArmies(){
		++army_size;
	}
	public void decreaseArmies(){
		--army_size;
	}
	
	public boolean canAttack(int terr){
		for(int i=0;i<canAttack.length;i++)
			if(canAttack[i]==terr)return true;
		return false;
	}
}
