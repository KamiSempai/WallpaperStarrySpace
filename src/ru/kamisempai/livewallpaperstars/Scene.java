package ru.kamisempai.livewallpaperstars;

import java.security.SecureRandom;

import ru.kamisempai.livewallpaperstars.sprite.ILayout;
import ru.kamisempai.livewallpaperstars.sprite.ISprite;
import ru.kamisempai.livewallpaperstars.sprite.StarsLayout;
import ru.kamisempai.livewallpaperstars.sprite.updater.AbsTimeLimitedUpdater;
import ru.kamisempai.livewallpaperstars.sprite.updater.RotateUpdater;
import ru.kamisempai.livewallpaperstars.sprite.updater.SpriteUpdatersSet;
import ru.kamisempai.livewallpaperstars.sprite.updater.TranslateUpdater;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.SurfaceHolder;

public class Scene {
	private static final float STAR_SIZE = 18;
	private static final int LAYOUT_COUNT = 2;
	private static final int UFO_COUNT = 20;
	private static final int UFO_DELAY = 200;
	private static final float UFO_ROTATE_MAX_SPEED = 30;
	private static final float UFO_ROTATE_MIN_SPEED = 10;
	private static final float UFO_MAX_SPEED = 30;
	private static final float UFO_MIN_SPEED = 10;
	private static final int STARS_COUNT = 600;
	private static final float DEPTH_MIN = 0.7f;
	private static final float DEPTH_MAX = 1.2f;
	
	private StarsLayout[] mLayouts;
	private int surfaceWidth; 
	private RandomSpriteFactory mSpriteFactory;

	private SecureRandom mRandom;
	private long mUfoReleseTime;
	private int mUfoCount;
	
	@SuppressLint("TrulyRandom")
	public Scene(Context context, SurfaceHolder holder, int format,
			int width, int height) {
		mRandom = new SecureRandom(new byte[] {(byte) width, (byte) height, (byte) System.currentTimeMillis()});
		mSpriteFactory = new RandomSpriteFactory(context);
		
		surfaceWidth = width;
		mUfoReleseTime = 0;
		mUfoCount = 0;
		
		float layoutWidth = width * 3;
		float layoutHeight = height;
		
		int starsPerLayout = STARS_COUNT / LAYOUT_COUNT;
		mLayouts = new StarsLayout[LAYOUT_COUNT];
		for(int i = 0; i < mLayouts.length; i++) {
			float depth = getLayoutDepth(i);
			mLayouts[i] = new StarsLayout(layoutWidth * depth, layoutHeight, starsPerLayout , STAR_SIZE * depth);
		}
	}
	
	public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
			float yStep, int xPixels, int yPixels) {
		for(StarsLayout layout: mLayouts)
			layout.setPosition((surfaceWidth - layout.getWidth()) * xOffset, 0);
	}
	
	public void update(long timeDelta) {
		mUfoReleseTime += timeDelta;
		if(mUfoCount < UFO_COUNT && mUfoReleseTime >= UFO_DELAY) {
			mUfoReleseTime = 0;
			int layoutIndex = mRandom.nextInt(mLayouts.length);
			
			final ISprite ufoSprite = mSpriteFactory.newSprite();
			float depth = getLayoutDepth(layoutIndex);
			ufoSprite.setSize(ufoSprite.getWidth() * depth, ufoSprite.getHeight() * depth);

			boolean vertical = mRandom.nextBoolean();
			boolean leftToRight = mRandom.nextBoolean();
			float rotationSpeed = (leftToRight ? 1 : -1) * UFO_ROTATE_MIN_SPEED + mRandom.nextInt((int) (UFO_ROTATE_MAX_SPEED - UFO_ROTATE_MIN_SPEED));
			float speed = UFO_MIN_SPEED + mRandom.nextInt((int) (UFO_MAX_SPEED - UFO_MIN_SPEED));
			
			float fromX;
			float fromY;
			float toX;
			float toY;
			
			if(vertical) {
				fromY = - ufoSprite.getHeight() * 2;
				toY = mLayouts[layoutIndex].getHeight() + ufoSprite.getHeight() * 2;
				
				if(!leftToRight) {
					float y = fromY;
					fromY = toY;
					toY = y;
				}

				fromX = mRandom.nextInt((int) mLayouts[layoutIndex].getWidth());
				toX = mRandom.nextInt((int) mLayouts[layoutIndex].getWidth());
			}
			else {
				fromX = - ufoSprite.getWidth() * 2;
				toX = mLayouts[layoutIndex].getWidth() + ufoSprite.getWidth() * 2;
				
				if(!leftToRight) {
					float x = fromX;
					fromX = toX;
					toX = x;
				}

				fromY = mRandom.nextInt((int) mLayouts[layoutIndex].getHeight());
				toY = mRandom.nextInt((int) mLayouts[layoutIndex].getHeight());
			}
			
			float deltaX = fromX - toX;
			float deltaY = fromY - toY;
			float delta = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
			
			float time = delta / speed * 1000;
			SpriteUpdatersSet updatersSet = new SpriteUpdatersSet();
			updatersSet.addUpdater(new RotateUpdater(rotationSpeed, 0.5f, 0.5f));
			updatersSet.addUpdater(new TranslateUpdater(fromX, fromY, toX, toY, time, new AbsTimeLimitedUpdater.CompletionListener() {
				@Override
				public void onComplete(AbsTimeLimitedUpdater updater) {
					ufoSprite.removeFromParent();
					mUfoCount--;
				}
			}));
			ufoSprite.setSpriteUpdater(updatersSet);
			mLayouts[layoutIndex].addSprite(ufoSprite);
			mUfoCount++;
		}
		for(ILayout layout: mLayouts)
			layout.update(timeDelta);
	}
	
	public void draw(Canvas canvas, RectF vsibleRect) {
		canvas.drawColor(Color.BLACK);
		for(ILayout layout: mLayouts)
			layout.draw(canvas, vsibleRect);
	}
	
	private float getLayoutDepth(int index) {
		float depthStep = (DEPTH_MAX - DEPTH_MIN) / (LAYOUT_COUNT - 1);
		return DEPTH_MIN + depthStep * index;
	}
}
