package ru.kamisempai.livewallpaperstars;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class LiveWallpaperService extends WallpaperService {
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
		
		private Scene mScene;
		private long lastTimeUpdate;
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
			mVisibleRect = new RectF(0, 0, width, height);
			mScene = new Scene(getApplicationContext(), holder, format, width, height);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			this.visible = false;
			handler.removeCallbacks(drawRunner);
		}

		public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
				float yStep, int xPixels, int yPixels) {
			mScene.onOffsetsChanged(xOffset, yOffset, xStep, yStep, xPixels, yPixels);
			draw();
		}

		void draw() {
			long newTime = System.currentTimeMillis();
			if(lastTimeUpdate > 0) {
				long timeDelta = newTime - lastTimeUpdate;
				mScene.update(timeDelta);
			}
			lastTimeUpdate = newTime;
			
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas canvas = null;
			try {
				canvas = holder.lockCanvas();
				if (canvas != null) {
					mScene.draw(canvas, mVisibleRect);
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