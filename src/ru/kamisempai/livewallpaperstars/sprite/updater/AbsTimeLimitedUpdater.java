package ru.kamisempai.livewallpaperstars.sprite.updater;

import ru.kamisempai.livewallpaperstars.sprite.ISprite;

public class AbsTimeLimitedUpdater implements ISpriteUpdater {

	private float mTime;
	private float mTimeElapsed;
	
	private CompletionListener mCompletionListener;
	

	public AbsTimeLimitedUpdater(float time) {
		this(time, null);
	}
	
	public AbsTimeLimitedUpdater(float time, CompletionListener completionListener) {
		mTime = time;
		mTimeElapsed = 0;
		mCompletionListener = completionListener;
	}

	@Override
	public void update(ISprite sprite, long timeDelta) {
		if(mTimeElapsed < mTime) {
			mTimeElapsed += timeDelta;
			if(mCompletionListener != null && mTimeElapsed >= mTime)
				mCompletionListener.onComplete(this);
		}
	}
	
	public float getProgress() {
		if(mTimeElapsed < mTime)
			return mTimeElapsed / mTime;
		return 1;
	}

	public CompletionListener getCompletionListener() {
		return mCompletionListener;
	}

	public void setCompletionListener(CompletionListener mCompletionListener) {
		this.mCompletionListener = mCompletionListener;
	}

	public interface CompletionListener {
		public void onComplete(AbsTimeLimitedUpdater updater);
	}
}
