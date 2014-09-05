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
	private static final String[] FILES_LIST = {"asteroid1.png", "asteroid2.png", "asteroid3.png", "asteroid4.png", "asteroid5.png", "asteroid6.png"};
	
	private Context mContext;
	private Random mRandom;
	
	public RandomSpriteFactory(Context context) {
		mContext = context;
		mRandom = new Random();
	}

	public ISprite newSprite() {
		AssetManager assets = mContext.getAssets();
		InputStream inputStream = null;
		try {
			inputStream = assets.open(FILES_LIST[mRandom.nextInt(FILES_LIST.length)]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new BitmapSprite(BitmapFactory.decodeStream(inputStream), 300, 300);
	}
}
