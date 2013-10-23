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

public class Button extends Actor {

	protected Vector2 position;
	float width, height,x,y;
	private Sprite buttonSprite;
	private Texture texture;
	private Label label;
	float alp = 1;
	
	public Button(String name,Vector2 position, Texture texture){
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
		label.setPosition(x+width/4, y);
		//label.setAlignment(Align.center);
		
		setWidth(buttonSprite.getWidth());
		setHeight(buttonSprite.getHeight());
		setBounds(0, 0, getWidth(), getHeight());
	}
	
	
	public void draw(SpriteBatch batch,float alpha){
		buttonSprite.setTexture(texture);
		buttonSprite.draw(batch, alp);
		label.draw(batch, 1);
	}
	
	public boolean hits(float X, float Y){
		if(X>=x && X<=x+width && Y>=y && Y<=y+height){
			return true;
		}
		return false;
	}
	
	public Button hitsButton(float X, float Y){
		if(X>=x && X<=x+width && Y>=y && Y<=y+height){
			return this;
		}
		return null;
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

}
