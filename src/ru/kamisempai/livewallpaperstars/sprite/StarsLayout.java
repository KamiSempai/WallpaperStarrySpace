package ru.kamisempai.livewallpaperstars.sprite;

import java.security.SecureRandom;

import android.annotation.SuppressLint;

public class StarsLayout extends AbsSpriteLayout {
	
	private float mWidth;
	private float mHeight;
	private float mStarSize;
	private int mStarsCount;
	private SecureRandom mRandom;
	private int mStarsColor = 0xff84846a;

	@SuppressLint("TrulyRandom")
	public StarsLayout(float width, float height, int starsCount, float starSize) {
		super(0, 0, 1);
		mWidth = width;
		mHeight = height;
		mStarsCount = starsCount;
		mRandom = new SecureRandom(new byte[] {(byte) width, (byte) height, (byte) starsCount, (byte) (starSize * 10)});
		mStarSize = starSize;
	}
	
	@Override
	public void update(long timeDelta) {
		if(mSprites == null || mSprites.size() < mStarsCount) {
			float random = mRandom.nextFloat();
			StarSprite sprite = new StarSprite( mWidth * random, mRandom.nextFloat() * mHeight, mStarSize, mStarSize, mStarsColor, mRandom.nextFloat());
			addSprite(sprite);
		}
		super.update(timeDelta);
		moveStars();
	}
	
	public float getWidth() {
		return mWidth;
	}
	
	public float getHeight() {
		return mHeight;
	}
	
	private void moveStars() {
		if(mSprites != null)
			for(ISprite sprite: mSprites) {
				if(sprite.getScale() <= 0.05) {
					float random = mRandom.nextFloat();
					sprite.setPosition(mWidth  * random, mRandom.nextFloat() * mHeight);
				}
			}
	}
}
