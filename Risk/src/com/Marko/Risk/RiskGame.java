package com.Marko.Risk;

import screens.WelcomeScreen;

import com.badlogic.gdx.Game;


public class RiskGame extends Game
{
	
	
	@Override
	public void create() {	
		this.setScreen(new WelcomeScreen(this));
	
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
		
		
		}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
