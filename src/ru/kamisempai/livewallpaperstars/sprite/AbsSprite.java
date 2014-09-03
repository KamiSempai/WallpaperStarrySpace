package ru.kamisempai.livewallpaperstars.sprite;

import android.graphics.Canvas;
import android.graphics.RectF;


public abstract class AbsSprite implements ISprite {
	private float mX;
	private float mY;
	
	private float mWidth;
	private float mHeight;
	
	private float mScale;
	
	private ISpriteUpdater mUpdater;
	
	public AbsSprite(float x, float y, float width, float height) {
		this(x, y, width, height, 1);
	}
	
	public AbsSprite(float x, float y, float width, float height, float scale) {
		mX = x;
		mY = y;
		mWidth = width;
		mHeight = height;
		mScale = scale;
	}
	
	@Override
	public void aplyTransformation(Canvas canvas) {
		canvas.translate(mX, mY);
		canvas.scale(mScale, mScale);
	}
	
	@Override
	public void undoTransformation(Canvas canvas) {
		canvas.scale(1/mScale, 1/mScale);
		canvas.translate(-mX, -mY);
	}

	@Override
	public void setPosition(float x, float y) {
		mX = x;
		mY = y;
	}

	@Override
	public void setSize(float width, float height) {
		mWidth = width;
		mHeight = height;
	}

	@Override
	public void setScale(float scale) {
		mScale = scale;
	}

	@Override
	public float getX() {
		return mX;
	}

	@Override
	public float getY() {
		return mY;
	}

	@Override
	public float getWidth() {
		return mWidth;
	}

	@Override
	public float getHeight() {
		return mHeight;
	}

	@Override
	public float getScale() {
		return mScale;
	}

	@Override
	public boolean isVisible(RectF visibleRect) {
		return visibleRect.intersects(mX, mY, mX + mWidth, mY + mHeight);
	}
	
	public void setSpriteUpdater(ISpriteUpdater updater) {
		mUpdater = updater;
	}
	
	@Override
	public void update(long timeDelta) {
		if(mUpdater != null)
			mUpdater.update(this, timeDelta);
	}

}
