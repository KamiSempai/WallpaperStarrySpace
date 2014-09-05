package ru.kamisempai.livewallpaperstars;

import java.security.SecureRandom;

import ru.kamisempai.livewallpaperstars.sprite.AbsSprite;
import ru.kamisempai.livewallpaperstars.sprite.ILayout;
import ru.kamisempai.livewallpaperstars.sprite.ISprite;
import ru.kamisempai.livewallpaperstars.sprite.StarsLayout;
import ru.kamisempai.livewallpaperstars.sprite.modifier.AbsTimeLimitedModifier;
import ru.kamisempai.livewallpaperstars.sprite.modifier.RotateModifier;
import ru.kamisempai.livewallpaperstars.sprite.modifier.SpriteModifiersSet;
import ru.kamisempai.livewallpaperstars.sprite.modifier.TranslateModifier;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.SurfaceHolder;

public class Scene {
	private static final float STAR_SIZE = 18;
	private static final int STARS_LAYOUT_COUNT = 15;
	private static final int SCRAP_LAYOUT_COUNT = 5;
	private static final int SCRAP_COUNT = 10;
	private static final int SCRAP_RELEASE_DELAY = 2000;
	private static final float SCRAP_ROTATE_MAX_SPEED = 30;
	private static final float SCRAP_ROTATE_MIN_SPEED = 10;
	private static final float SCRAP_MAX_SPEED = 30;
	private static final float SCRAP_MIN_SPEED = 10;
	private static final int STARS_COUNT = 300;
	private static final float STAR_DEPTH_MIN = 0.7f;
	private static final float STAR_DEPTH_MAX = 0.9f;
	private static final float SCRAP_DEPTH_MIN = 0.95f;
	private static final float SCRAP_DEPTH_MAX = 1.5f;

	private StarsLayout[] mStarsLayouts;
	private ISprite[] mScrapLayouts;
	private int surfaceWidth; 
	private RandomSpriteFactory mScrapFactory;

	private SecureRandom mRandom;
	private long mScrapReleseTime;
	private int mScrapCount;
	
	@SuppressLint("TrulyRandom")
	public Scene(Context context, SurfaceHolder holder, int format,
			int width, int height) {
		mRandom = new SecureRandom(new byte[] {(byte) width, (byte) height, (byte) System.currentTimeMillis()});
		mScrapFactory = new RandomSpriteFactory(context);
		
		surfaceWidth = width;
		mScrapReleseTime = 0;
		mScrapCount = 0;
		
		float layoutWidth = width * 3;
		float layoutHeight = height;
		
		int starsPerLayout = STARS_COUNT / STARS_LAYOUT_COUNT;
		mStarsLayouts = new StarsLayout[STARS_LAYOUT_COUNT];
		for(int i = 0; i < mStarsLayouts.length; i++) {
			float depth = STAR_DEPTH_MIN + ((STAR_DEPTH_MAX - STAR_DEPTH_MIN) / (STARS_LAYOUT_COUNT - 1)) * i;
			mStarsLayouts[i] = new StarsLayout(layoutWidth * depth, layoutHeight, starsPerLayout , STAR_SIZE * depth);
		}

		mScrapLayouts = new ISprite[SCRAP_LAYOUT_COUNT];
		for(int i = 0; i < mScrapLayouts.length; i++) {
			float depth = getScrapDepth(i);
			mScrapLayouts[i] = new AbsSprite(0, 0, layoutWidth * depth, layoutHeight);
		}
	}
	
	public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
			float yStep, int xPixels, int yPixels) {
		for(StarsLayout layout: mStarsLayouts) {
			layout.setPosition((surfaceWidth - layout.getWidth()) * xOffset, 0);
		}
		for(ISprite layout: mScrapLayouts) {
			layout.setPosition((surfaceWidth - layout.getWidth()) * xOffset, 0);
		}
	}
	
	public void update(long timeDelta) {
		mScrapReleseTime += timeDelta;
		if(mScrapCount < SCRAP_COUNT && mScrapReleseTime >= SCRAP_RELEASE_DELAY) {
			mScrapReleseTime = 0;
			int layoutIndex = mRandom.nextInt(mScrapLayouts.length);
			
			final ISprite scrapSprite = mScrapFactory.newSprite();
			float depth = getScrapDepth(layoutIndex);
			scrapSprite.setSize(scrapSprite.getWidth() * depth, scrapSprite.getHeight() * depth);

			boolean vertical = mRandom.nextBoolean();
			boolean leftToRight = mRandom.nextBoolean();
			float rotationSpeed = (leftToRight ? 1 : -1) * SCRAP_ROTATE_MIN_SPEED + mRandom.nextInt((int) (SCRAP_ROTATE_MAX_SPEED - SCRAP_ROTATE_MIN_SPEED));
			float speed = SCRAP_MIN_SPEED + mRandom.nextInt((int) (SCRAP_MAX_SPEED - SCRAP_MIN_SPEED));
			
			float fromX;
			float fromY;
			float toX;
			float toY;
			
			if(vertical) {
				fromY = - scrapSprite.getHeight() * 2;
				toY = mScrapLayouts[layoutIndex].getHeight() + scrapSprite.getHeight() * 2;
				
				if(!leftToRight) {
					float y = fromY;
					fromY = toY;
					toY = y;
				}

				fromX = mRandom.nextInt((int) mScrapLayouts[layoutIndex].getWidth());
				toX = mRandom.nextInt((int) mScrapLayouts[layoutIndex].getWidth());
			}
			else {
				fromX = - scrapSprite.getWidth() * 2;
				toX = mScrapLayouts[layoutIndex].getWidth() + scrapSprite.getWidth() * 2;
				
				if(!leftToRight) {
					float x = fromX;
					fromX = toX;
					toX = x;
				}

				fromY = mRandom.nextInt((int) mScrapLayouts[layoutIndex].getHeight());
				toY = mRandom.nextInt((int) mScrapLayouts[layoutIndex].getHeight());
			}
			
			float deltaX = fromX - toX;
			float deltaY = fromY - toY;
			float delta = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
			
			float time = delta / speed * 1000;
			SpriteModifiersSet modifiersSet = new SpriteModifiersSet();
			modifiersSet.add(new RotateModifier(rotationSpeed, 0.5f, 0.5f));
			modifiersSet.add(new TranslateModifier(fromX, fromY, toX, toY, time, new AbsTimeLimitedModifier.CompletionListener() {
				@Override
				public void onComplete(AbsTimeLimitedModifier modifier) {
					scrapSprite.removeFromParent();
					mScrapCount--;
				}
			}));
			scrapSprite.setSpriteModifier(modifiersSet);
			mScrapLayouts[layoutIndex].addSprite(scrapSprite);
			mScrapCount++;
		}
		for(ILayout layout: mStarsLayouts)
			layout.update(timeDelta);
		for(ILayout layout: mScrapLayouts)
			layout.update(timeDelta);
	}
	
	public void draw(Canvas canvas, RectF vsibleRect) {
		canvas.drawColor(Color.BLACK);
		for(ILayout layout: mStarsLayouts)
			layout.draw(canvas, vsibleRect);
		for(ILayout layout: mScrapLayouts)
			layout.draw(canvas, vsibleRect);
	}
	
	private float getScrapDepth(int index) {
		float depthStep = (SCRAP_DEPTH_MAX - SCRAP_DEPTH_MIN) / (SCRAP_LAYOUT_COUNT - 1);
		return SCRAP_DEPTH_MIN + depthStep * index;
	}
}
