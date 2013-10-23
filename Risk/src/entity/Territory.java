package entity;

import screens.WelcomeScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Territory extends Actor{
	
	
	protected String name;
	protected Vector2 position;
	float width, height,x,y;
	protected int owner=6;
	protected int army_size=0;
	private int id;
	protected int[] canAttack;
	private Sprite terSprite;
	private Texture texture;
	private Label label;
	private float alp=1;
	
	
	public Territory(String name, Vector2 position,int id, int[] canAttack, Texture texture){
		this.name = name;
		this.position = position;
		this.id = id;
		this.canAttack = canAttack;
		this.texture = texture;
		width = texture.getWidth();
		height = texture.getHeight();
		x=position.x/1000*Gdx.graphics.getWidth()-width/2;
		y=position.y/600*Gdx.graphics.getHeight()-height/2;
		setTouchable(Touchable.enabled);
		
		
		
		//podesavanje texture i lokacije
		terSprite = new Sprite(texture);
		terSprite.setPosition(position.x/1000*Gdx.graphics.getWidth()-width/2,position.y/600*Gdx.graphics.getHeight()-height/2);
		//Podesavanje teksta teritorije
		label = new Label(getArmy_size()+"", WelcomeScreen.labelStyle);
		label.setPosition(x+width/4, y);
		
				
		
		setWidth(terSprite.getWidth());
		setHeight(terSprite.getHeight());
		setBounds(0, 0, getWidth(), getHeight());
		
				
		
    }
	
	
	
	//crtanje
	/*
	public void draw(SpriteBatch batch){
		
		terSprite.setTexture(texture);
		terSprite.draw(batch);
		label.draw(batch, 1);
	}
	*/
	
	
	public void draw(SpriteBatch batch,float alpha){
		terSprite.setTexture(texture);
		terSprite.draw(batch, alp);
		label.setText(getArmy_size()+"");
		label.draw(batch, 1);
	}
	
	
	public boolean hits(float X, float Y){
		if(X>=x && X<=x+width && Y>=y && Y<=y+height){
			return true;
		}
		return false;
	}
	
	public Territory hitsTerritory(float X, float Y){
		if(X>=x && X<=x+width && Y>=y && Y<=y+height){
			return this;
		}
		return null;
	}
	
	public boolean containsId(int id){
		for(int i=0;i<canAttack.length;i++){
			if (id==canAttack[i])return true;
		}return false;
	}
	
	public void restoreDefaults(){
		alp=1;
		
	}
	public void increaseArmies(){
		++army_size;
	}
	public void decreaseArmies(){
		--army_size;
	}
	
	
	
	
	//Getters and Setters 
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		if(owner>=0 && owner <=6){
		this.owner = owner;
		texture = World.texture[owner];}
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

	public Label getLabel() {
		return label;
	}

	public void setLabelName(String label) {
		this.label.setText(label);
	}



	public float getAlp() {
		return alp;
	}



	public void setAlp(float alp) {
		this.alp = alp;
	}

	

	
}
