/*
		Ova igra nastala je kao rezultat seminarskog rada iz predmeta Racunarske mreze i telekomunikacije
	 	na Fakultetu organizacionih nauka. Slobodno mozete koristiti delove koda za svoje potrebe.
	 	
	 
*
*
*
*/

package com.Marko.Risk;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
       
       
        initialize(new RiskGame(), cfg);
    }
}