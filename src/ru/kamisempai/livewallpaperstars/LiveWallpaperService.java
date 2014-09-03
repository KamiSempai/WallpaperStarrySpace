package ru.kamisempai.livewallpaperstars;

import ru.kamisempai.livewallpaperstars.sprite.SpriteLayout;
import ru.kamisempai.livewallpaperstars.sprite.StarsLayout;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class LiveWallpaperService extends WallpaperService {
	private static final int LAYOUT_COUNT = 20;
	private static final int STARS_COUNT = 300;
	private static final float DEPTH_MIN = 0.7f;
	private static final float DEPTH_MAX = 1.2f;
	private static final int FRAME_RATE = 30;

	public Engine onCreateEngine() {
		return new MyWallpaperEngine();
	}

	class MyWallpaperEngine extends Engine {

		private final Handler handler = new Handler();
		private final Runnable drawRunner = new Runnable() {
			@Override
			public void run() {
				draw();
			}
		};
		private boolean visible = true;
		
		private long lastTimeUpdate;
		private StarsLayout[] mLayouts;
		private int surfaceWidth; 
		private RectF mVisibleRect;

		MyWallpaperEngine() {
			
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			this.visible = visible;
			// if screen wallpaper is visible then draw the image otherwise do
			// not draw
			if (visible) {
				handler.post(drawRunner);
			} else {
				handler.removeCallbacks(drawRunner);
			}
		}
		
		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			
			surfaceWidth = width;
			
			mVisibleRect = new RectF(0, 0, width, height);

			float layoutWidth = width * 3;
			float layoutHeight = height;
			
			int starsPerLayout = STARS_COUNT / LAYOUT_COUNT;
			float depthStep = (DEPTH_MAX - DEPTH_MIN) / (LAYOUT_COUNT - 1);
			mLayouts = new StarsLayout[LAYOUT_COUNT];
			for(int i = 0; i < mLayouts.length; i++) {
				float depth = DEPTH_MIN + depthStep * i;
				mLayouts[i] = new StarsLayout(layoutWidth * depth, layoutHeight, starsPerLayout , depth);
			}
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			this.visible = false;
			handler.removeCallbacks(drawRunner);
		}

		public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
				float yStep, int xPixels, int yPixels) {
			for(StarsLayout layout: mLayouts)
				layout.setPosition((surfaceWidth - layout.getWidth()) * xOffset, 0);
			draw();
		}

		void draw() {
			long newTime = System.currentTimeMillis();
			if(lastTimeUpdate > 0) {
				long timeDelta = newTime - lastTimeUpdate;
				for(SpriteLayout layout: mLayouts)
					layout.update(timeDelta);
			}
			lastTimeUpdate = newTime;
			
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas canvas = null;
			try {
				canvas = holder.lockCanvas();
				if (canvas != null) {
					canvas.drawColor(Color.BLACK);// clear the canvas
					for(SpriteLayout layout: mLayouts)
						layout.draw(canvas, mVisibleRect);
				}
			} finally {
				if (canvas != null)
					holder.unlockCanvasAndPost(canvas);
			}

			handler.removeCallbacks(drawRunner);
			if (visible) {
				handler.postDelayed(drawRunner, 1000 / FRAME_RATE); // delay 10 mileseconds
			}

		}
	}
}