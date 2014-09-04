package ru.kamisempai.livewallpaperstars;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import ru.kamisempai.livewallpaperstars.sprite.BitmapSprite;
import ru.kamisempai.livewallpaperstars.sprite.ILayout;
import ru.kamisempai.livewallpaperstars.sprite.StarsLayout;
import ru.kamisempai.livewallpaperstars.sprite.updater.RotateUpdater;

public class Scene {
	private static final int LAYOUT_COUNT = 20;
	private static final int STARS_COUNT = 300;
	private static final float DEPTH_MIN = 0.7f;
	private static final float DEPTH_MAX = 1.2f;
	
	private Context mContext;
	private StarsLayout[] mLayouts;
	private int surfaceWidth; 
	
	public Scene(Context context, SurfaceHolder holder, int format,
			int width, int height) {
		mContext = context;
		
		surfaceWidth = width;
		
		float layoutWidth = width * 3;
		float layoutHeight = height;
		
		int starsPerLayout = STARS_COUNT / LAYOUT_COUNT;
		float depthStep = (DEPTH_MAX - DEPTH_MIN) / (LAYOUT_COUNT - 1);
		mLayouts = new StarsLayout[LAYOUT_COUNT];
		for(int i = 0; i < mLayouts.length; i++) {
			float depth = DEPTH_MIN + depthStep * i;
			mLayouts[i] = new StarsLayout(layoutWidth * depth, layoutHeight, starsPerLayout , depth);
		}
		BitmapSprite sprite = new BitmapSprite(300, 300, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher));
		sprite.setSpriteUpdater(new RotateUpdater(30, 0.5f, 0.5f));
		mLayouts[LAYOUT_COUNT/2].addSprite(sprite);
	}
	
	public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
			float yStep, int xPixels, int yPixels) {
		for(StarsLayout layout: mLayouts)
			layout.setPosition((surfaceWidth - layout.getWidth()) * xOffset, 0);
	}
	
	public void update(long timeDelta) {
		for(ILayout layout: mLayouts)
			layout.update(timeDelta);
	}
	
	public void draw(Canvas canvas, RectF vsibleRect) {
		canvas.drawColor(Color.BLACK);
		for(ILayout layout: mLayouts)
			layout.draw(canvas, vsibleRect);
	}
}
