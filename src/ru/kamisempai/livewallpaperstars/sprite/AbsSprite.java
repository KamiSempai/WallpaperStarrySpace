package ru.kamisempai.livewallpaperstars.sprite;

import ru.kamisempai.livewallpaperstars.sprite.modifier.ISpriteModifier;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;


public class AbsSprite extends AbsSpriteLayout implements ISprite {
	protected RectF mSpriteRect;
	
	private ILayout mParent;
	private ISpriteModifier mModifier;
	private Paint mBackGroundPaint;
	
	public AbsSprite(float x, float y, float width, float height) {
		this(x, y, width, height, 1);
	}
	
	public AbsSprite(float x, float y, float width, float height, float scale) {
		super(x, y, scale);
		mSpriteRect = new RectF(x, x, x + width, x + height);
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
		return true;//RectF.intersects(visibleRect, mSpriteRect);
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
	
	public void setSpriteModifier(ISpriteModifier modifier) {
		mModifier = modifier;
	}
	
	public ISpriteModifier getSpriteModifier() {
		return mModifier;
	}
	
	public void setParrent(ILayout parent) {
		mParent = parent;
	}
	
	public ILayout getParent() {
		return mParent;
	}
	
	public void removeFromParent() {
		if(mParent != null)
			mParent.removeSprite(this);
	}
	
	@Override
	public void update(long timeDelta) {
		if(mModifier != null)
			mModifier.modify(this, timeDelta);
	}

	protected void updateRect(float x, float y, float width, float height) {
		mSpriteRect.set(x, x, x + width, x + height);
	}
}
