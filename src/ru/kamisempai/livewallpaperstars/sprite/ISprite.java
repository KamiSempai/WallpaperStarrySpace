package ru.kamisempai.livewallpaperstars.sprite;

import android.graphics.RectF;

public interface ISprite extends ILayout {
	
	public void setSize(float width, float height);
	
	public float getWidth();
	public float getHeight();
	
	public boolean isVisible(RectF visibleRect);
}