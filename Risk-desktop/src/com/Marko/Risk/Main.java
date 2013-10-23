/*
		Ova igra nastala je kao rezultat seminarskog rada iz predmeta Racunarske mreze i telekomunikacije
	 	na Fakultetu organizacionih nauka. Slobodno mozete koristiti delove koda za svoje potrebe.
	 	
	 
*
*
*
*/
package com.Marko.Risk;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Risk Game";
		cfg.useGL20 = true;
		cfg.width = 1000;
		cfg.height = 600;
		cfg.resizable = true;
		
		new LwjglApplication(new RiskGame(), cfg);
	}
}
 