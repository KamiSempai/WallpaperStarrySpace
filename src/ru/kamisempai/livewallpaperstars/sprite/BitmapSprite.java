package ru.kamisempai.livewallpaperstars.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class BitmapSprite extends AbsSprite {
	private Bitmap mBitmap;
	private Paint mPaint;

	public BitmapSprite(float x, float y, Bitmap bitmap) {
		super(x, y, bitmap.getWidth(), bitmap.getHeight());
		mBitmap = bitmap;
		mPaint = new Paint();
		mPaint.setStyle(Style.FILL);
	}

	@Override
	protected void drawLayout(Canvas canvas, RectF vsibleRect) {
		super.drawLayout(canvas, vsibleRect);
		canvas.drawBitmap(mBitmap, 0, 0, mPaint);
	}
}
