package ru.kamisempai.livewallpaperstars.sprite;

import ru.kamisempai.livewallpaperstars.sprite.modifier.ISpriteModifier;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;


public class AbsSprite extends AbsSpriteLayout implements ISprite {

	private float mWidth;
	private float mHeight;
	
	private ILayout mParent;
	private ISpriteModifier mModifier;
	private Paint mBackGroundPaint;
	
	public AbsSprite(float x, float y, float width, float height) {
		this(x, y, width, height, 1);
	}
	
	public AbsSprite(float x, float y, float width, float height, float scale) {
		super(x, y, scale);
		mWidth = width;
		mHeight = height;
	}

	@Override
	public void setSize(float width, float height) {
		mWidth = width;
		mHeight = height;
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
	public boolean isVisible(RectF visibleRect) {
		return mX + mWidth > visibleRect.left & mX < visibleRect.right
				& mY + mHeight > visibleRect.top & mY < visibleRect.bottom;
	}
	
	@Override
	protected void drawLayout(Canvas canvas, RectF vsibleRect) {
		super.drawLayout(canvas, vsibleRect);
		if(mBackGroundPaint != null)
			canvas.drawRect(0, 0, mWidth, mHeight, mBackGroundPaint);
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
		super.update(timeDelta);
		if(mModifier != null)
			mModifier.modify(this, timeDelta);
	}
}
