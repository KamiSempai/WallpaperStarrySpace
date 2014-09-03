package ru.kamisempai.livewallpaperstars.sprite;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.RectF;

public class SpriteLayout {
	private float mX = 0;
	private float mY = 0;
	
	private float mScale = 1;

	protected ArrayList<ISprite> mSprites = new ArrayList<ISprite>();
	
	public void draw(Canvas canvas, RectF vsibleRect) {
		RectF rect = new RectF(vsibleRect);
		rect.offsetTo(-mX, 0);

		canvas.translate(mX, mY);
		canvas.scale(mScale, mScale);
		for(ISprite sprite: mSprites) {
			if(sprite.isVisible(rect)) {
				sprite.draw(canvas);
			}
		}
		canvas.scale(1/mScale, 1/mScale);
		canvas.translate(-mX, -mY);
	}
	
	public void addSprite(ISprite sprite) {
		mSprites.add(sprite);
	}
	
	public void setPosition(float x, float y) {
		mX = x;
		mY = y;
	}

	public void setScale(float scale) {
		mScale = scale;
	}

	public float getX() {
		return mX;
	}

	public float getY() {
		return mY;
	}

	public float getScale() {
		return mScale;
	}
	
	public void update(long timeElapsed) {
		for(ISprite sprite: mSprites) {
			sprite.update(timeElapsed);
		}
	}
}
