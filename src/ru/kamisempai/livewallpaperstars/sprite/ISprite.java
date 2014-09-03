package ru.kamisempai.livewallpaperstars.sprite;

import android.graphics.Canvas;
import android.graphics.RectF;

public interface ISprite {
	
	public void draw(Canvas canvas);
	public void aplyTransformation(Canvas canvas);
	public void undoTransformation(Canvas canvas);
	
	public void setPosition(float x, float y);
	public void setSize(float width, float height);
	public void setScale(float scale);

	public float getX();
	public float getY();
	public float getWidth();
	public float getHeight();
	public float getScale();
	
	public boolean isVisible(RectF visibleRect);
	
	public void update(long timeDelta);
}