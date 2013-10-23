package entity;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class World {
	
	public Game game;
	public ArrayList<Territory> territory;
 	private Iterator< Territory > territoryIterator;
	private Territory t;
	public Territory terInFocus;
	public static Texture[] texture;
	public static Texture[] namesTexture,redD,blackD;
	public static Texture EndOfAttack,reinforcementTexture,attackTexture,retreat;
	private Stage stage;
	private OrthographicCamera camera;
	public Player[] players;
	Vector3 mousePosition;
	private int[] neutral;								//brisati ukoliko ostane fokus
	int unusedArmy=0;
	private int thisPlayerID=-1;
	boolean clickFocus = false;
	boolean isAttack=false;
	Button buttonEndOfAttack,buttonRetreat;
	public InformationEntity[] namesEntity;
	public InformationEntity reinforcement;
	Sound winsBattle;
	public Attack attackWindow;
	
	
		
	public World(OrthographicCamera camera, Game game){
		this.game=game;
		this.camera = camera;
		mousePosition = new Vector3();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		neutral = new int[42];
		for(int i=0;i<neutral.length;i++)neutral[i]=i;   // Izbrisati ukoliko ostane podesavanje da sve teritorije koje nisu u fokusu bude transparentne 
									 					//	isto i ovaj red
		
		//ucitavanje textura teritorija
		texture = new Texture[7];
		texture[0] = new Texture("images/red-small.png");
		texture[1] = new Texture("images/green-small.png");
		texture[2] = new Texture("images/blue-small.png");
		texture[3] = new Texture("images/yellow-small.png");
		texture[4] = new Texture("images/purple-small.png");
		texture[5] = new Texture("images/orange-small.png");
		texture[6] = new Texture("images/Gray-small.png");

		
		//ucitavanje textura imena
		namesTexture = new Texture[6];
		namesTexture[0] = new Texture("images/NamesRed.png");
		namesTexture[1] = new Texture("images/NamesGreen.png");
		namesTexture[2] = new Texture("images/namesBlue.png");
		namesTexture[3] = new Texture("images/NamesYellow.png");
		namesTexture[4] = new Texture("images/NamesPurple.png");
		namesTexture[5] = new Texture("images/NamesOrange.png");
		
		//ucitavanje textura za imena
		namesEntity  = new InformationEntity[6];
		namesEntity[0] = new InformationEntity("", new Vector2(140,572 ), namesTexture[0]);
		namesEntity[1] = new InformationEntity("", new Vector2(284,572), namesTexture[1]);
		namesEntity[2] = new InformationEntity("", new Vector2(428,572), namesTexture[2]);
		namesEntity[3] = new InformationEntity("", new Vector2(572,572), namesTexture[3]);
		namesEntity[4] = new InformationEntity("", new Vector2(716,572), namesTexture[4]);
		namesEntity[5] = new InformationEntity("", new Vector2(860,572), namesTexture[5]);
		
		//ucitavanje textura crnih kockica
		blackD = new Texture[7];
		blackD[0]= new Texture("dices/b1.png");
		blackD[1]= new Texture("dices/b1.png");
		blackD[2]= new Texture("dices/b2.png");
		blackD[3]= new Texture("dices/b3.png");
		blackD[4]= new Texture("dices/b4.png");
		blackD[5]= new Texture("dices/b5.png");
		blackD[6]= new Texture("dices/b6.png");
		
		//ucitavanje textura crvenih kockica
		redD = new Texture[7];
		redD[0]= new Texture("dices/r1.png");
		redD[1]= new Texture("dices/r1.png");
		redD[2]= new Texture("dices/r2.png");
		redD[3]= new Texture("dices/r3.png");
		redD[4]= new Texture("dices/r4.png");
		redD[5]= new Texture("dices/r5.png");
		redD[6]= new Texture("dices/r6.png");
		
		
		
		//ucitavanje dugmica
		EndOfAttack = new Texture("images/buttonEnd.png");
		retreat = new Texture("images/retreat.png");
		reinforcementTexture= new Texture("images/silhouette.png");
		attackTexture = new Texture("images/attack.png");
		
		
		//ucitavanje zvukova
		winsBattle = Gdx.audio.newSound(Gdx.files.internal("audio/RPG_Plus_Shrapnel.mp3")); 
		
		//Definisanje i insanciranje Teritorija
		territory = new ArrayList<Territory>();
		territory.add(new Territory("Alaska", new Vector2(70,528),0,new int[]{1,2,38},texture[6]));
		territory.add(new Territory("NorthWest Territory", new Vector2(159,525),1,new int[]{0,2,7,8},texture[6]));
		territory.add(new Territory("Alberta", new Vector2(165,600-125),2,new int[]{0,1,3,7,8},texture[6]));
		territory.add(new Territory("Western US", new Vector2(172,600-185),3,new int[]{2,4,5,7},texture[6]));
		territory.add(new Territory("Central America", new Vector2(181,350),4,new int[]{3,5,10},texture[6]));
		territory.add(new Territory("Eastern US", new Vector2(242,400),5,new int[]{3,4,6,7},texture[6]));
		territory.add(new Territory("Quebec", new Vector2(310,600-134),6,new int[]{5,7,9},texture[6]));
		territory.add(new Territory("Ontario", new Vector2(235,600-135),7,new int[]{1,2,3,5,6,8},texture[6]));
		territory.add(new Territory("North US", new Vector2(242,600-77),8,new int[]{1,7,9},texture[6]));
		territory.add(new Territory("Greenland", new Vector2(369,600-73),9,new int[]{6,8,14},texture[6]));
		territory.add(new Territory("Venezuela", new Vector2(222,600-325),10,new int[]{4,11,12},texture[6]));
		territory.add(new Territory("Brazil", new Vector2(300,600-383),11,new int[]{10,12,13,21},texture[6]));
		territory.add(new Territory("Peru", new Vector2(240,600-411),12,new int[]{10,11,13},texture[6]));
		territory.add(new Territory("Argentina", new Vector2(250,600-477),13,new int[]{11,12},texture[6]));
		territory.add(new Territory("Iceland", new Vector2(420,600-100),14,new int[]{9,15,18},texture[6]));
		territory.add(new Territory("Great Britain", new Vector2(420,600-165),15,new int[]{14,16,19},texture[6]));
		territory.add(new Territory("Western Europe", new Vector2(421,600-230),16,new int[]{15,17,19},texture[6]));
		territory.add(new Territory("Southern Europe", new Vector2(505,600-217),17,new int[]{16,19,20,22,31},texture[6]));
		territory.add(new Territory("Scandinavia", new Vector2(488,600-81),18,new int[]{14,19,20},texture[6]));
		territory.add(new Territory("Northern Europe", new Vector2(479,600-167),19,new int[]{15,16,17,18,20},texture[6]));
		territory.add(new Territory("Ukraine", new Vector2(569,600-140),20,new int[]{17,18,19,30,31,35},texture[6]));
		territory.add(new Territory("Northern Africa", new Vector2(428,600-336),21,new int[]{11,22,23,24},texture[6]));
		territory.add(new Territory("Egypt", new Vector2(503,600-300),22,new int[]{17,21,23,31},texture[6]));
		territory.add(new Territory("East Africa", new Vector2(545,600-372),23,new int[]{21,22,24,25},texture[6]));
		territory.add(new Territory("Congo", new Vector2(495,600-417),24,new int[]{21,23,25},texture[6]));
		territory.add(new Territory("South Africa", new Vector2(495,600-486),25,new int[]{23,24},texture[6]));
		territory.add(new Territory("Western Australia", new Vector2(789,600-484),26,new int[]{27,28},texture[6]));
		territory.add(new Territory("Eastern Australia", new Vector2(873,600-484),27,new int[]{26,28,29},texture[6]));
		territory.add(new Territory("Indonesia", new Vector2(789,600-387),28,new int[]{26,27,29,34},texture[6]));
		territory.add(new Territory("New Guinea", new Vector2(897,600-402),29,new int[]{27,28},texture[6]));
		territory.add(new Territory("Afghanistan", new Vector2(641,600-193),30,new int[]{20,31,32,33,35},texture[6]));
		territory.add(new Territory("Middle East", new Vector2(573,600-265),31,new int[]{17,20,22,30,32},texture[6]));
		territory.add(new Territory("India", new Vector2(689,600-300),32,new int[]{30,31,33,34},texture[6]));
		territory.add(new Territory("China", new Vector2(750,600-240),33,new int[]{30,32,34,35,36,40},texture[6]));
		territory.add(new Territory("Siam", new Vector2(762,600-310),34,new int[]{28,32,33},texture[6]));
		territory.add(new Territory("Ural", new Vector2(676,600-123),35,new int[]{20,30,33,36},texture[6]));
		territory.add(new Territory("Siberia", new Vector2(732,600-100),36,new int[]{33,35,37,39,40},texture[6]));
		territory.add(new Territory("Yakutsk", new Vector2(823,600-74),37,new int[]{36,38,39},texture[6]));
		territory.add(new Territory("Kamchatka", new Vector2(918,600-78),38,new int[]{0,37,39,40},texture[6]));
		territory.add(new Territory("Irkutsk", new Vector2(794,600-135),39,new int[]{36,37,38,40},texture[6]));
		territory.add(new Territory("Mongolia", new Vector2(815,600-185),40,new int[]{33,36,38,39},texture[6]));
		terInFocus = new Territory("Neutral", new Vector2(30,30), -1, new int[]{}, texture[6]);
		territory.add(terInFocus);
		
		
		
		buttonEndOfAttack = new Button("", new Vector2(680, 120), EndOfAttack);
		buttonRetreat = new Button("", new Vector2(500, 180), retreat);
		buttonRetreat.setVisible(false);
		
		
		reinforcement=new InformationEntity("", new Vector2(90,100), reinforcementTexture);
		reinforcement.label.setColor(1,0,0,1);
		reinforcement.label.setBounds(10, 67, 160, 100);
		setReinforcementVisibility();
		
		stage.addActor(reinforcement);
		terInFocus.setVisible(false);
		attackWindow = new Attack(attackTexture,this);
	
		
		for(int i=0;i<6;i++){
		stage.addActor(namesEntity[i]);
		namesEntity[i].setVisible(false);
		}
		
		//Dodaj sve teritorije na stage
		for (territoryIterator = territory.iterator(); territoryIterator.hasNext();) {
			// Get the next territory in line
			t = territoryIterator.next();			
			
			stage.addActor(t);	
			t.setOwner(6);
				}
	
		stage.addActor(attackWindow);
		stage.addActor(buttonEndOfAttack);
		stage.addActor(buttonRetreat);
	
		
	}

	
	
	
	
	public int getUnusedArmy() {
		return unusedArmy;
	}


	public void setUnusedArmy(int unusedArmy) {
		this.unusedArmy = unusedArmy;
	}
	
	public void setPlayers(Player[] players){
		this.players = players;
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public int getThisPlayerId(){
		return thisPlayerID;
	}
	
	public void setThisPlayerId(int id){
		thisPlayerID = id;
	}
	
	
	//Render 
	public void render(float delta){
		mousePosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mousePosition);
		
		for (territoryIterator = territory.iterator(); territoryIterator.hasNext();) {
			// Get the next territory in line
			  t = territoryIterator.next();			
			  t.restoreDefaults();
		}
		buttonRetreat.restoreDefaults();
		buttonEndOfAttack.restoreDefaults();
		buttonEndOfAttack.setVisible(isAttack);
		clickFocus(clickFocus);
		stage.act(delta);
		stage.draw();	
		
	}
	

	
	public void setNamesVisible(){
		for(int i=0;i<players.length;i++){
			 namesEntity[i].setLabel(players[i].getName());
			 namesEntity[i].setVisible(true); 
		 }
		
	}
	
	public void clickFocus(Boolean canAttack){
		for (territoryIterator = territory.iterator(); territoryIterator.hasNext();) {
			// Get the next territory in line
			t = territoryIterator.next();			
			if(Gdx.input.isTouched()&& t.hitsTerritory(mousePosition.x, mousePosition.y)!=null){
				terInFocus = t;
				terInFocus.setAlp(0.8f);
				}
		}
		if(Gdx.input.isTouched()&& buttonEndOfAttack.hitsButton(mousePosition.x, mousePosition.y)!=null)
			buttonEndOfAttack.setAlp(0.8f);
		if(Gdx.input.isTouched()&& buttonRetreat.hitsButton(mousePosition.x, mousePosition.y)!=null)
			buttonRetreat.setAlp(0.8f);
				if(canAttack){
			for(int i=0;i<territory.size();i++){
				if(terInFocus.containsId(territory.get(i).getId())){
					territory.get(i).setAlp(0.57f);
				}
			}
		}
		
	}
	
	public String chooseTerritory(boolean endOfAttack){
		while(!Gdx.input.isTouched()){continue;}
			for (territoryIterator = territory.iterator(); territoryIterator.hasNext();) {
				// Get the next territory in line
				t = territoryIterator.next();	
				if(Gdx.input.isTouched()&& t.hitsTerritory(mousePosition.x, mousePosition.y)!=null){
					terInFocus = t;
					return String.valueOf(terInFocus.getId());
					}
				if(endOfAttack && Gdx.input.isTouched()&& buttonEndOfAttack.hitsButton(mousePosition.x, mousePosition.y)!=null){
					attackWindow.setVisible(false);
					return "50";
				}
				if(endOfAttack && Gdx.input.isTouched()&& buttonRetreat.hitsButton(mousePosition.x, mousePosition.y)!=null){
					attackWindow.setVisible(false);
					buttonRetreat.setVisible(false);
					try{
						Thread.sleep(200);
					}catch(Exception e){}
				}
			}
		return "-1";
		}
	
	public String attack(){
		isAttack=true;
		try{
		clickFocus=true;
		String t1=null;
		String t2=null;
		int t1ID=-1;
		int t2ID=-1;
		boolean choosen1 = false;
		while(!choosen1){
			 t1 = chooseTerritory(true);
			try {Thread.sleep(300);	
				if(t1.equals("50")){
					isAttack=false;
					clickFocus=false;
					attackWindow.setVisible(false);
					return "430";
				}
				t1ID=Integer.parseInt(t1);
			} catch (Exception e) {			
				e.printStackTrace();
			}
			if(territory.get(t1ID).getOwner()!=thisPlayerID)continue;
			
			choosen1=true;
		}
		
		boolean choosen2 = false;
		while(!choosen2){
		 t2 = chooseTerritory(true);
			try {
				Thread.sleep(300);
				if(t1.equals("50")){
					isAttack=false;
					clickFocus=false;
					attackWindow.setVisible(false);
					return "430";
				}
				t1ID=Integer.parseInt(t1);
				t2ID=Integer.parseInt(t2);
			} catch (Exception e) {		
				
				e.printStackTrace();
			}
			if(t1.equals(t2))continue;
			if(!territory.get(t2ID).containsId(t1ID) || territory.get(t2ID).getOwner()==thisPlayerID){			
				t1=t2;
				continue;				
			}else {
					choosen2 = true;
					break;
			}
		}
		clickFocus=false;
		attackWindow.setVisible(true);
		attackWindow.setValues(t1+":"+t2);
		buttonRetreat.setVisible(true);
		return t1+":"+t2;
		}catch(Exception e){
			System.out.println(e.getMessage());
			
			
		}
		return "error";
	}
	
	
	public void setReinforcementVisibility(){
		if(reinforcement.getLabel()=="" || reinforcement.getLabel().trim()=="0")reinforcement.setVisible(false);
		else reinforcement.setVisible(true);
		
	}
	
	public void playWinsBattleSound(){
		winsBattle.play();
	}
	
	
	public Territory getTerritory(int id){
		return territory.get(id);
	}



	
}
	

		
