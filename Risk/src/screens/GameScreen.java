package screens;





import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import connection.ConnWatcher;
import entity.World;

public class GameScreen implements Screen{
	
	Game game;
	private SpriteBatch batch;
	private Sprite pozadina;	
	Sprite terSprite;
	Texture texture;
	OrthographicCamera camera;
	World world;
	Stage stage;
	float w,h;
	ConnWatcher conn;
	
	
	
	public GameScreen(Game game){
		this.game = game;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		//podesavanje pozadine
		Texture.setEnforcePotImages(false);
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("images/Pozadina.jpg"));
		pozadina = new Sprite(texture);
		pozadina.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera = new OrthographicCamera();				
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		camera = new OrthographicCamera();	
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();
		
		//Instanciranje World-a i teritorija
		world = new World(camera, game);
		makeContact();
		
		
	}
	
	
	
	 
	
	 private void makeContact(){
		 conn = new ConnWatcher(this,world);
	   new Thread(conn).start();    // start watching for server msgs
	    
	  
	  }  // end of makeContact()
	
	 

	 
	 
	
	//debug - test stampa
	public void stampaj(String line){
		System.out.println(line);
	}
	
	
	
	
	
	@Override
	public void show() {		
				
				}
	
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.begin();		
		pozadina.draw(batch);
		batch.end();
		world.render(delta);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		texture.dispose();
		batch.dispose();
		
		
	}

}
