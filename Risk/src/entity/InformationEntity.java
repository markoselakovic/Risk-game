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


public class InformationEntity extends Actor{

	protected Vector2 position;
	float width, height,x,y;
	private Sprite buttonSprite;
	private Texture texture;
	public Label label;
	float alp = 1;
	
	
	public InformationEntity(String name,Vector2 position, Texture texture){
		this.texture = texture;
		this.position = position;
		width = texture.getWidth();
		height = texture.getHeight();
		x=position.x/1000*Gdx.graphics.getWidth()-width/2;
		y=position.y/600*Gdx.graphics.getHeight()-height/2;
		setTouchable(Touchable.enabled);
		
		
		
		//podesavanje texture i lokacije
		buttonSprite = new Sprite(texture);
		buttonSprite.setPosition(position.x/1000*Gdx.graphics.getWidth()-width/2,position.y/600*Gdx.graphics.getHeight()-height/2);
		//Podesavanje teksta dugmeta
		label = new Label(name+"", WelcomeScreen.labelStyle);
		label.setAlignment(Align.center);
		label.setPosition(x+width/4, y);
		label.setBounds(x, y, width, height);
		
		
		
		setWidth(buttonSprite.getWidth());
		setHeight(buttonSprite.getHeight());
		setBounds(0, 0, getWidth(), getHeight());
	}
	
	
	public void draw(SpriteBatch batch,float alpha){
		buttonSprite.setTexture(texture);
		buttonSprite.draw(batch, alp);
		label.draw(batch, 1);
	}
	
	public float getAlp() {
		return alp;
	}

	public void restoreDefaults(){
		alp=1;
	}

	public void setAlp(float alp) {
		this.alp = alp;
	}
	
	public void setLabel(String label){
		this.label.setText(label);
		
	}
	
	public String getLabel(){
		String  temp= "Reinforcement label Error";
		try{
		temp =label.getText().toString();
		}catch(Exception e){
			System.out.println("Problem in parsing Label name");
		}
		return temp;
	}

}
