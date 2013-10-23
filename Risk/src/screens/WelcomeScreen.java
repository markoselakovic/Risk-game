package screens;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class WelcomeScreen implements Screen{

	public Game game;
	private SpriteBatch batch;
	private Sprite pozadina;
	private Stage stage;
	private Table table;
	Texture texture;
	private TextButton buttonExit, buttonConnect;
	private Label heading,IPandPort;
	public static Skin skin;
	public static BitmapFont white, black;
	private TextureAtlas atlas;
	private TextField name,port, IPaddress;
	TextFieldStyle textFieldStyle;
	public static TextButtonStyle textButtonStyle;
	public static LabelStyle labelStyle, labelStyleRED; 
	public static String nameString;
	public static String ADDRESS;
	public static int PORT;
	
	public WelcomeScreen(Game game){
		this.game = game;
	}
	
	@Override
	public void show() {	
		
		
		//podesavanje pozadine
		Texture.setEnforcePotImages(false);
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal("images/Pozadina.jpg"));
		pozadina = new Sprite(texture);
		pozadina.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//Podesavanje parametara
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		
		//podesavanje fonta		
		white = new BitmapFont(Gdx.files.internal("font/White.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("font/black.fnt"), false);
		
		//podesavanje Headinga
		labelStyle = new LabelStyle(white, Color.DARK_GRAY);
		labelStyleRED = new LabelStyle(white, Color.RED);
		heading = new Label("Meni", labelStyle);
		heading.setFontScale(2);
		
		
		//ucitavanje slicice dugmeta i definisanje tabele
		atlas = new TextureAtlas("images/button.9");
		skin = new Skin(atlas);
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		
		//TextButtonStyle - definisanje stila dugmeta
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		textButtonStyle.font = black;
		
		//TextFieldStyle
		textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = black;
		textFieldStyle.fontColor = Color.DARK_GRAY;
		textFieldStyle.background = skin.getDrawable("button.up");	
		
		
		//IPandPort 
		IPandPort = new Label("IP address and port:", labelStyle);
		
		
		//IP address
		IPaddress = new TextField("127.0.0.1", textFieldStyle);
		IPaddress.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			Gdx.input.setOnscreenKeyboardVisible(true);
			}
		});
		
		//Port
		port = new TextField("2222", textFieldStyle);
		port.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			Gdx.input.setOnscreenKeyboardVisible(true);
			}
		});
		
		//Name
		name = new TextField("Name", textFieldStyle);
		name.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			name.setText("");
			Gdx.input.setOnscreenKeyboardVisible(true);
			}
		});
		
		//ButtonExit
		buttonExit = new TextButton("Exit", textButtonStyle);
		buttonExit.pad(15);
		buttonExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {				
			Gdx.app.exit();
			}
		});
		
		
		//ButtonConnect
		buttonConnect = new TextButton("Connect", textButtonStyle);
		buttonConnect.pad(15);	
		buttonConnect.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {				
			nameString = name.getText();
			ADDRESS=IPaddress.getText();
			try{
				PORT= Integer.parseInt(port.getText());
			}catch(Exception e){
				PORT=2222;
			}
			game.setScreen(new GameScreen(game));
			}
		});
	
		//Dodavanje elemenata na Ekran
		table.align(Align.center);
		table.add(heading);
		table.getCell(heading).spaceBottom(50);
		table.row();
		table.add(IPandPort);
		table.row();
		table.add(IPaddress);
		table.row();
		table.add(port);
		table.getCell(port).spaceBottom(15);
		table.row();
		table.add(name);
		table.getCell(name).spaceBottom(10);
		table.row();
		table.add(buttonConnect);
		table.getCell(buttonConnect).spaceBottom(20);
		table.row();
		table.add(buttonExit);
		table.debug();
		stage.addActor(table);
	}
	
	@Override
	public void render(float delta) {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.begin();		
		pozadina.draw(batch, 0.45f);		
		batch.end();		
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {		
		
	}

	@Override
	public void hide() {
	
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		pozadina.getTexture().dispose();
		white.dispose();
		black.dispose();
		atlas.dispose();
		stage.dispose();
		skin.dispose();
		
	}
	
	

}
