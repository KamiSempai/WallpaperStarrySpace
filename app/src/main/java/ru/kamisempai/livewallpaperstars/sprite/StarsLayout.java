package ru.kamisempai.livewallpaperstars.sprite;

import java.security.SecureRandom;

import android.annotation.SuppressLint;

public class StarsLayout extends AbsSprite {

	private float mStarSize;
	private int mStarsCount;
	private SecureRandom mRandom;
	private int mStarsColor = 0xff84846a;

	@SuppressLint("TrulyRandom")
	public StarsLayout(float width, float height, int starsCount, float starSize) {
		super(0, 0, width, height, 1);
		mStarsCount = starsCount;
		mRandom = new SecureRandom(new byte[] {(byte) width, (byte) height, (byte) starsCount, (byte) (starSize * 10)});
		mStarSize = starSize;
	}
	
	@Override
	public void update(long timeDelta) {
		if(mSprites == null || mSprites.size() < mStarsCount) {
			float random = mRandom.nextFloat();
			StarSprite sprite = new StarSprite( getWidth() * random, mRandom.nextFloat() * getHeight(), mStarSize, mStarSize, mStarsColor, mRandom.nextFloat());
			addSprite(sprite);
		}
		super.update(timeDelta);
		moveStars();
	}
	
	private void moveStars() {
		if(mSprites != null)
			for(ISprite sprite: mSprites) {
				if(sprite.getScale() <= 0.05) {
					float random = mRandom.nextFloat();
					sprite.setPosition(getWidth()  * random, mRandom.nextFloat() * getHeight());
				}
			}
	}
}
