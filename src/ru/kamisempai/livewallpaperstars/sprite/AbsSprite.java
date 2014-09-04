package ru.kamisempai.livewallpaperstars.sprite;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;


public abstract class AbsSprite extends AbsSpriteLayout implements ISprite {
	protected RectF mSpriteRect;
	
	private ISpriteUpdater mUpdater;
	private Paint mBackGroundPaint;
	
	public AbsSprite(float x, float y, float width, float height) {
		this(x, y, width, height, 1);
	}
	
	public AbsSprite(float x, float y, float width, float height, float scale) {
		super(x, y, scale);
		mSpriteRect = new RectF();
		updateRect(x, y, width, height);
	}

	@Override
	public void setSize(float width, float height) {
		updateRect(mX, mY, width, height);
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		updateRect(x, y, mSpriteRect.width(), mSpriteRect.height());
	}
	
	@Override
	public float getWidth() {
		return mSpriteRect.width();
	}

	@Override
	public float getHeight() {
		return mSpriteRect.height();
	}

	@Override
	public boolean isVisible(RectF visibleRect) {
		return RectF.intersects(visibleRect, mSpriteRect);
	}
	
	@Override
	protected void drawLayout(Canvas canvas, RectF vsibleRect) {
		super.drawLayout(canvas, vsibleRect);
		if(mBackGroundPaint != null)
			canvas.drawRect(0, 0, mSpriteRect.width(), mSpriteRect.height(), mBackGroundPaint);
	}
	
	public void setBackgroundColor(int color) {
		if(mBackGroundPaint == null) {
			mBackGroundPaint = new Paint();
			mBackGroundPaint.setStyle(Style.FILL);
		}
		mBackGroundPaint.setColor(color);
	}
	
	public void setBackgroundTransparent() {
		mBackGroundPaint = null;
	}
	
	public void setSpriteUpdater(ISpriteUpdater updater) {
		mUpdater = updater;
	}
	
	@Override
	public void update(long timeDelta) {
		if(mUpdater != null)
			mUpdater.update(this, timeDelta);
	}

	protected void updateRect(float x, float y, float width, float height) {
		mSpriteRect.set(x, x, x + width, x + height);
	}
}
