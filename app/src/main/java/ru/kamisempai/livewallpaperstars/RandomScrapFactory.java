package ru.kamisempai.livewallpaperstars;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import ru.kamisempai.livewallpaperstars.sprite.BitmapSprite;
import ru.kamisempai.livewallpaperstars.sprite.ISprite;

public class RandomScrapFactory {
	private static final String SCRAP_FOLDER = "scrap";
	private static final String ASTERODS_FOLDER = "asteroids";

    private static final float SCRAP_DEPTH_FAR = 0.2f;
    private static final float SCRAP_DEPTH_NORMAL = 0.5f;
    private static final float SCRAP_DEPTH_NEAR = 0.7f;
    private static final float SCRAP_DEPTH_CLOSE = 1f;

    private static final float[] SCRAP_DEPTH = {SCRAP_DEPTH_FAR,
                                                SCRAP_DEPTH_NORMAL,
                                                SCRAP_DEPTH_NEAR,
                                                SCRAP_DEPTH_CLOSE};
	
	private static final float SCRAP_CHANCE = 0.05f;
	
	private AssetManager mAssetManager;
	private String[] asteroidsList;
	private String[][] scrapList;
	private Random mRandom;
    private float mMinDepth;
    private float mMaxDepth;
    private float mDeltaDepth;

    private float mScrapCount;
	
	public RandomScrapFactory(Context context, float minDepth, float maxDepth) {
		mRandom = new Random();

        mMinDepth = minDepth;
        mMaxDepth = maxDepth;
        mDeltaDepth = mMaxDepth - mMinDepth;
		
		mAssetManager = context.getAssets();
        scrapList = new String[SCRAP_DEPTH.length][];
        try {
            for (int i = 1; i <= SCRAP_DEPTH.length; i++) {
                scrapList[i - 1] = mAssetManager.list(SCRAP_FOLDER + i);
                mScrapCount =+ scrapList[i - 1].length;
            }
		} catch (IOException e) {
			e.printStackTrace();
            for (int i = 1; i < SCRAP_DEPTH.length; i++) {
                scrapList[i] = new String[]{"asteroid1.png"};
            }
		}
        try {
        	asteroidsList = mAssetManager.list(ASTERODS_FOLDER);
		} catch (IOException e) {
			e.printStackTrace();
			asteroidsList = new String[]{"asteroid1.png"};
		}
	}

	public ISprite newSprite(float depth) {
		InputStream inputStream = null;
		try {
			inputStream = mAssetManager.open(getRandomFilePath(depth));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new BitmapSprite(BitmapFactory.decodeStream(inputStream), 0, 0);
	}
	
	private String getRandomFilePath(float depth) {
		if(mRandom.nextFloat() <= SCRAP_CHANCE) {
            float depthKoef = (depth - mMinDepth) / (mDeltaDepth);
            int depthIndex = 1;
            while (depthIndex < SCRAP_DEPTH.length && depthKoef > SCRAP_DEPTH[depthIndex - 1])
                depthIndex++;
            return SCRAP_FOLDER + depthIndex + "/" + scrapList[depthIndex - 1][mRandom.nextInt(scrapList[depthIndex - 1].length)];
        }
		return ASTERODS_FOLDER + "/" + asteroidsList[mRandom.nextInt(asteroidsList.length)];
	}
}
