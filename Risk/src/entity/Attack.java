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
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class Attack extends Actor{
	World world;
	protected Vector2 position;
	float width, height,x,y;
	private Sprite buttonSprite, terr1,terr2,names1,names2,d1,d2,d3,d4,d5;
	private Texture texture;
	private Texture ter1, ter2,n1,n2,x1,x2,x3,x4,x5;
	private String name1,name2,army1,army2;
	private int t1,t2; 	
	private Label l1,l2,a1,a2;
	String msg;
	int w1=0,w2=0,w3=0,w4=0,w5=0;
		
	public Attack(Texture texture, World world){
		this.world = world;
		this.texture = texture;
		width = texture.getWidth();
		position = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		height = texture.getHeight();
		x=position.x/1000*Gdx.graphics.getWidth()-width/2;
		y=position.y/600*Gdx.graphics.getHeight()-height/2;
		setTouchable(Touchable.enabled);
		this.setVisible(false);
		
		ter1 = World.texture[0];
		ter2 = World.texture[0];
		n1= World.namesTexture[0];
		n2= World.namesTexture[0];
		x1=World.redD[0];
		x2=World.redD[0];
		x3=World.redD[0];
		x4=World.blackD[0];
		x5=World.blackD[0];
		
		
		//podesavanje texture i lokacije
		buttonSprite = new Sprite(this.texture);
		buttonSprite.setTexture(texture);
		terr1 = new Sprite(ter1);
		terr2 = new Sprite(ter2);	
		names1 = new Sprite(n1);
		names2 = new Sprite(n2);
		d1=new Sprite(x1);
		d2=new Sprite(x2);
		d3=new Sprite(x3);
		d4=new Sprite(x4);
		d5=new Sprite(x5);
		terr1.scale(0.35f);
		terr2.scale(0.35f);		
		buttonSprite.setPosition(position.x/1000*Gdx.graphics.getWidth()-width/2,position.y/600*Gdx.graphics.getHeight()-height/2);
		terr1.setPosition(310-terr1.getWidth()/2,320-terr1.getHeight()/2);
		terr2.setPosition(690-terr2.getWidth()/2,320-terr2.getHeight()/2);
		names1.setPosition(310-names1.getWidth()/2,400-names2.getHeight()/2);
		names2.setPosition(690-names2.getWidth()/2,400-names2.getHeight()/2);
		d1.setPosition(444-x1.getWidth()/2, 400-x1.getHeight()/2);
		d2.setPosition(444-x1.getWidth()/2, 340-x1.getHeight()/2);
		d3.setPosition(444-x1.getWidth()/2, 280-x1.getHeight()/2);
		d4.setPosition(556-x1.getWidth()/2, 400-x1.getHeight()/2);
		d5.setPosition(556-x1.getWidth()/2, 340-x1.getHeight()/2);
		
		
		//Podesavanje labela
		l1 = new Label("", WelcomeScreen.labelStyle);
		l1.setAlignment(Align.center);
		l1.setBounds(250, 385, 120, 30);
		
		l2 = new Label("", WelcomeScreen.labelStyle);
		l2.setAlignment(Align.center);
		l2.setBounds(630, 385, 120, 30);
		
		a1 = new Label("", WelcomeScreen.labelStyle);
		a1.setAlignment(Align.center);
		a1.setBounds(310-terr1.getWidth()/2, 320-terr1.getHeight()/2, terr1.getWidth(), terr1.getHeight());
	
		a2 = new Label("", WelcomeScreen.labelStyle);
		a2.setAlignment(Align.center);
		a2.setBounds(690-terr2.getWidth()/2, 320-terr2.getHeight()/2, terr2.getWidth(), terr2.getHeight());
		
		setWidth(buttonSprite.getWidth());
		setHeight(buttonSprite.getHeight());
		setBounds(0, 0, getWidth(), getHeight());
		
	}
	
	public void draw(SpriteBatch batch,float alpha){
		
		buttonSprite.draw(batch, 1);
		
		terr1.draw(batch,1);
		terr2.draw(batch,1);
		names1.draw(batch,1);
		names2.draw(batch,1);
		l1.draw(batch, 1);
		l2.draw(batch, 1);
		a1.draw(batch, 1);
		a2.draw(batch, 1);
		if(w1==0)d1.draw(batch, 0);else d1.draw(batch, 1);
		if(w2==0)d2.draw(batch, 0);else d2.draw(batch, 1);
		if(w3==0)d3.draw(batch, 0);else d3.draw(batch, 1);
		if(w4==0)d4.draw(batch, 0);else d4.draw(batch, 1);
		if(w5==0)d5.draw(batch, 0);else d5.draw(batch, 1);
		
	}
	
	
	public void setValues(String msg){
		this.msg = msg;
		try{
			String[] teritorije = msg.split(":");
			t1 = Integer.parseInt(teritorije[0]);
			t2 = Integer.parseInt(teritorije[1]);
			
		}catch(Exception e){
			System.out.println("Greska u parsiranju - attack klasa");
		}
		
		name1 = world.getTerritory(t1).getName();
		name2 = world.getTerritory(t2).getName();
		army1 = world.getTerritory(t1).getArmy_size()+"";
		army2 = world.getTerritory(t2).getArmy_size()+"";
		l1.setText(name1);
		l2.setText(name2);
		a1.setText(army1);
		a2.setText(army2);
		
		ter1=World.texture[world.getTerritory(t1).getOwner()];
		ter2=World.texture[world.getTerritory(t2).getOwner()];
		n1=World.namesTexture[world.getTerritory(t1).getOwner()];
		n2=World.namesTexture[world.getTerritory(t2).getOwner()];
		
		
		terr1.setTexture(ter1);
		terr2.setTexture(ter2);
		names1.setTexture(n1);
		names2.setTexture(n2);
	}
	
	
	
	
	public void update(String msg){
		int homeTerr = -1;
		int defTerr = -1;	
		String terr=msg.substring(14);        
		String[] niz = terr.split(":");
		try{
			
			w1=Integer.parseInt(msg.substring(4, 5));
			w2=Integer.parseInt(msg.substring(6, 7));
			w3=Integer.parseInt(msg.substring(8, 9));
			w4=Integer.parseInt(msg.substring(10, 11));
			w5=Integer.parseInt(msg.substring(12, 13));
			homeTerr = Integer.parseInt(niz[0]);			
			defTerr = Integer.parseInt(niz[1]);
				
		}catch(Exception e){
			System.out.println("Problem in update method - Attack");
		}
		d1.setTexture(World.redD[w1]);
		d2.setTexture(World.redD[w2]);
		d3.setTexture(World.redD[w3]);
		d4.setTexture(World.blackD[w4]);
		d5.setTexture(World.blackD[w5]);
		setValues(homeTerr+":"+defTerr);
		
	}
	
	
	
}
