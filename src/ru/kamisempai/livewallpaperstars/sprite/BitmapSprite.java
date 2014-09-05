package ru.kamisempai.livewallpaperstars.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class BitmapSprite extends AbsSprite {
	private Bitmap mBitmap;
	private Paint mPaint;
	private RectF mBitmapRect;
	

	public BitmapSprite(Bitmap bitmap, float x, float y) {
		this(bitmap, x, y, bitmap.getWidth(), bitmap.getHeight());
	}

	public BitmapSprite(Bitmap bitmap, float x, float y, float width, float height) {
		super(x, y, width, height);
		mBitmap = bitmap;
		mBitmapRect = new RectF(0, 0, width, height);
		
		mPaint = new Paint();
		mPaint.setStyle(Style.FILL);
	}

	@Override
	protected void drawLayout(Canvas canvas, RectF vsibleRect) {
		super.drawLayout(canvas, vsibleRect);
		canvas.drawBitmap(mBitmap, null, mBitmapRect, mPaint);
	}
	
	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		mBitmapRect.set(0, 0, width, height);
	}
}
