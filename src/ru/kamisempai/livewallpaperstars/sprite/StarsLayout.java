package ru.kamisempai.livewallpaperstars.sprite;

import android.annotation.SuppressLint;
import java.security.SecureRandom;

public class StarsLayout extends SpriteLayout {
	private static final float STAR_SIZE = 7;
	
	private float mWidth;
	private float mHeight;
	private float mStarSize;
	private int mStarsCount;
	private SecureRandom mRandom;
	private int mStarsColor = 0x77ffffcc;
//	private int mStarsColor = 0x77ff00ff;

	@SuppressLint("TrulyRandom")
	public StarsLayout(float width, float height, int starsCount, float depth) {
		super();
		mWidth = width * depth;
		mHeight = height;
		mStarsCount = starsCount;
		mRandom = new SecureRandom(new byte[] {(byte) width, (byte) height, (byte) starsCount, (byte) (depth * 10)});
		mStarSize = STAR_SIZE * depth;
	}
	
	@Override
	public void update(long timeElapsed) {
		if(mSprites.size() < mStarsCount) {
			StarSprite sprite = new StarSprite(mRandom.nextFloat() * mWidth, mRandom.nextFloat() * mHeight, mStarSize, mStarSize, mStarsColor, mRandom.nextFloat());
			addSprite(sprite);
		}
		super.update(timeElapsed);
		moveStars();
	}
	
	public float getWidth() {
		return mWidth;
	}
	
	private void moveStars() {
		for(ISprite sprite: mSprites) {
			if(sprite.getScale() <= 0.05)
				sprite.setPosition(mRandom.nextFloat() * mWidth, mRandom.nextFloat() * mHeight);
		}
	}
}
