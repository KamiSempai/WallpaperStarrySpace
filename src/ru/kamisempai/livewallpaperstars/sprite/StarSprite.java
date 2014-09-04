package ru.kamisempai.livewallpaperstars.sprite;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;

public class StarSprite extends AbsSprite {
	private static final float CORE_SIZE = 0.2f;
	private static final float GLOW_SIZE = 1f;

	private int mColor;
	private Paint mCorePaint;
	private Paint mGlowPaint;
	private RadialGradient mGlowShader;
	
	public StarSprite(float x, float y, float width, float height, int color) {
		this(x, y, width, height, color, new Random(System.currentTimeMillis()).nextFloat());
	}
	
	public StarSprite(float x, float y, float width, float height, int color, float flashState) {
		super(x, y, width, height);
		mColor = color;
		mCorePaint = new Paint();
		mCorePaint.setStyle(Style.FILL);
		mCorePaint.setColor(mColor);
		
		mGlowPaint = new Paint();
		mGlowPaint.setStyle(Style.STROKE);
		mGlowPaint.setColor(mColor);
		mGlowShader = new RadialGradient(0, 0,
				Math.min(getHeight(), getWidth()) * GLOW_SIZE, mColor, Color.TRANSPARENT, TileMode.MIRROR);
		mGlowPaint.setShader(mGlowShader);
		mGlowPaint.setAlpha(70);
		
		setSpriteUpdater(new StarUpdater(flashState));
	}
	
	@Override
	protected void drawLayout(Canvas canvas, RectF vsibleRect) {
		super.drawLayout(canvas, vsibleRect);

		mGlowPaint.setStrokeWidth(Math.min(getHeight(), getWidth()) * GLOW_SIZE);
		canvas.drawPoint(0, 0, mGlowPaint);
		canvas.drawCircle(0, 0, Math.min(getHeight(), getWidth()) * CORE_SIZE, mCorePaint);
	}
	
	@Override
	public void bind(Canvas canvas) {
		canvas.translate(getWidth() / 2, getHeight() / 2);
		super.bind(canvas);
	}
	
	@Override
	public void unbind(Canvas canvas) {
		super.unbind(canvas);
		canvas.translate(-getWidth() / 2, -getHeight() / 2);
	}
	
//	@Override
//	protected void updateRect(float x, float y, float width, float height) {
//		mSpriteRect.set(x - width / 2, y - height / 2, x + width / 2, y + height / 2);
//	}
	
	private static class StarUpdater implements ISpriteUpdater {
		private static final float FLASH_STEP_PER_SEC = 0.5f;
		private static final float MIN_FLASH_STATE = 0.01f;
		private static final float MAX_FLASH_STATE = 3f;
		private static final float FLASH_STATE_LIMIT = 1f;
		
		private float mFlashState;
		private float mFlashSign;
		
		public StarUpdater(float flashState) {
			mFlashState = flashState * MAX_FLASH_STATE;
			mFlashSign = 1;
		}

		@Override
		public void update(ISprite sprite, long timeDelta) {
			float delta = FLASH_STEP_PER_SEC * (float) timeDelta / 1000f;
			if(delta > MAX_FLASH_STATE - MIN_FLASH_STATE) {
				if((delta / (MAX_FLASH_STATE - MIN_FLASH_STATE)) % 2 != 0)
					mFlashSign *= -1;
				delta = delta % (MAX_FLASH_STATE - MIN_FLASH_STATE);
			}
			mFlashState += delta * mFlashSign;
			if(mFlashState <= MIN_FLASH_STATE) {
				mFlashState = MIN_FLASH_STATE + MIN_FLASH_STATE - mFlashState;
				mFlashSign = 1;
			}
			if(mFlashState > MAX_FLASH_STATE) {
				mFlashSign = -1;
			}
			if(mFlashState > FLASH_STATE_LIMIT)
				sprite.setScale(FLASH_STATE_LIMIT);
			else
				sprite.setScale(mFlashState);
		}
	}
}
