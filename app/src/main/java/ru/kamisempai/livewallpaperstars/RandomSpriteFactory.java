package ru.kamisempai.livewallpaperstars;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import ru.kamisempai.livewallpaperstars.sprite.BitmapSprite;
import ru.kamisempai.livewallpaperstars.sprite.ISprite;

public class RandomSpriteFactory {
	private static final String SCRAP_FOLDER = "scrap";
	private static final String ASTERODS_FOLDER = "asteroids";
	
	private static final float SCRAP_CHANCE = 0.05f;
	
	private AssetManager mAssetManager;
	private String[] asteroidsList;
	private String[] scrapList;
	private Random mRandom;
	
	public RandomSpriteFactory(Context context) {
		mRandom = new Random();
		
		mAssetManager = context.getAssets();
        try {
			scrapList = mAssetManager.list(SCRAP_FOLDER);
		} catch (IOException e) {
			e.printStackTrace();
			scrapList = new String[]{"scrap/asteroid1.png"};
		}
        try {
        	asteroidsList = mAssetManager.list(ASTERODS_FOLDER);
		} catch (IOException e) {
			e.printStackTrace();
			asteroidsList = new String[]{"scrap/asteroid1.png"};
		}
	}

	public ISprite newSprite() {
		InputStream inputStream = null;
		try {
			inputStream = mAssetManager.open(getRandomFilePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new BitmapSprite(BitmapFactory.decodeStream(inputStream), 0, 0);
	}
	
	private String getRandomFilePath() {
		if(mRandom.nextFloat() <= SCRAP_CHANCE)
			return SCRAP_FOLDER + "/" + scrapList[mRandom.nextInt(scrapList.length)];
		return ASTERODS_FOLDER + "/" + asteroidsList[mRandom.nextInt(asteroidsList.length)];
	}
}
