package net.drakkcore;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.drakkcore.Screen.SplashScreen;
import net.drakkcore.States.GameStateManager;
import net.drakkcore.States.PlayState;
import net.drakkcore.loader.ResourseLoader;

public class BubbleBoom extends Game {

	public static final String TITLE = "Bubble Boom";

	private SplashScreen splashscreen;
	
	@Override
	public void create () {
		ResourseLoader.load();
		splashscreen = new SplashScreen(this);
		setScreen(splashscreen);
	}



	public void dispose(){

		super.dispose();
		ResourseLoader.dispose();
		splashscreen.dispose();

	}
}
