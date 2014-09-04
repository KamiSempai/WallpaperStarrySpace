package ru.kamisempai.livewallpaperstars.sprite;

import android.graphics.Canvas;
import android.graphics.RectF;

public interface ILayout {

	public void draw(Canvas canvas, RectF vsibleRect);
	public void bind(Canvas canvas);
	public void unbind(Canvas canvas);
	
	public void setPosition(float x, float y);
	public void setScale(float scale);

	public float getX();
	public float getY();
	public float getScale();
	
	public void update(long timeDelta);

	public void addSprite(ISprite sprite);
	public void removeSprite(ISprite sprite);
}
