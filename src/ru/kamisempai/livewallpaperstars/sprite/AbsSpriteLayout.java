package ru.kamisempai.livewallpaperstars.sprite;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;

import android.graphics.Canvas;
import android.graphics.RectF;

public class AbsSpriteLayout implements ILayout {
	protected float mX;
	protected float mY;
	
	protected float mScale;

	protected ArrayList<ISprite> mSprites;
	protected LinkedBlockingDeque<ISprite> mSpritesToRemove = new LinkedBlockingDeque<ISprite>();
	
	public AbsSpriteLayout() {
		this(0, 0);
	}
	
	public AbsSpriteLayout(float x, float y) {
		this(x, y, 1);
	}
	
	public AbsSpriteLayout(float x, float y, float scale) {
		mX = x;
		mY = y;
		mScale = scale;
	}
	
	public void draw(Canvas canvas, RectF vsibleRect) {
		bind(canvas);
		drawLayout(canvas, vsibleRect);
		drawChilds(canvas, vsibleRect);
		unbind(canvas);
	}
	
	protected void drawLayout(Canvas canvas, RectF vsibleRect) {
		
	}
	
	protected void drawChilds(Canvas canvas, RectF vsibleRect) {
		if(mSprites != null) {
			RectF rect = new RectF(vsibleRect);
			rect.offsetTo(-mX, -mY);
			
			for(ISprite sprite: mSprites) {
				if(vsibleRect == null || sprite.isVisible(rect)) {
					sprite.draw(canvas, vsibleRect);
				}
			}
		}
	}

	@Override
	public void bind(Canvas canvas) {
		canvas.translate(mX, mY);
		canvas.scale(mScale, mScale);
	}

	@Override
	public void unbind(Canvas canvas) {
		canvas.scale(1/mScale, 1/mScale);
		canvas.translate(-mX, -mY);
	}
	
	public void addSprite(ISprite sprite) {
		if(mSprites == null)
			mSprites = new ArrayList<ISprite>();
		mSprites.add(sprite);
	}

	@Override
	public void removeSprite(ISprite sprite) {
		mSpritesToRemove.add(sprite);
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
	
	public void update(long timeDelta) {
		if(mSprites != null) {
			while (mSpritesToRemove.size() > 0) {
				mSprites.remove(mSpritesToRemove.peek());
			}
			for(ISprite sprite: mSprites) {
				sprite.update(timeDelta);
			}
		}
		mSpritesToRemove.clear();
	}
}
