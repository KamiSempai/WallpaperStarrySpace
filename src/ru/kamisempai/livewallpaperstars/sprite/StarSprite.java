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
	private static final float CORE_SIZE = 0.5f;
	private static final float GLOW_SIZE = 3.5f;
	private static final float FLASH_STEP_PER_SEC = 0.5f;
	private static final float MIN_FLASH_STATE = 0.01f;
	private static final float MAX_FLASH_STATE = 3f;
	private static final float FLASH_STATE_LIMIT = 1f;

	private int mColor;
	private Paint mCorePaint;
	private Paint mGlowPaint;
	
	private float mFlashState;
	private float mFlashSign;
	
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
		mGlowPaint.setShader(new RadialGradient(0, 0,
				Math.min(getHeight(), getWidth()) * GLOW_SIZE, mColor, Color.TRANSPARENT, TileMode.MIRROR));
		mGlowPaint.setAlpha(70);
		
		mFlashState = flashState * MAX_FLASH_STATE;
		mFlashSign = 1;
	}

	@Override
	public void draw(Canvas canvas) {
		aplyTransformation(canvas);

		mGlowPaint.setStrokeWidth(Math.min(getHeight(), getWidth()) * GLOW_SIZE);
		canvas.drawPoint(0, 0, mGlowPaint);
		canvas.drawCircle(0, 0, Math.min(getHeight(), getWidth()) * CORE_SIZE, mCorePaint);
		
		undoTransformation(canvas);
	}

	@Override
	public void update(long timeElapsed) {
		float delta = FLASH_STEP_PER_SEC * (float) timeElapsed / 1000f;
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
			setScale(FLASH_STATE_LIMIT);
		else
			setScale(mFlashState);
	}

	@Override
	public boolean isVisible(RectF visibleRect) {
		return visibleRect.intersects(getX() - getWidth() / 2, getY() - getHeight() / 2, getX() + getWidth() / 2, getY() + getHeight() / 2);
	}
}
