package ru.kamisempai.livewallpaperstars.sprite.modifier;

import ru.kamisempai.livewallpaperstars.sprite.ISprite;

public class AbsTimeLimitedModifier implements ISpriteModifier {

	private float mTime;
	private float mTimeElapsed;
	
	private CompletionListener mCompletionListener;
	

	public AbsTimeLimitedModifier(float time) {
		this(time, null);
	}
	
	public AbsTimeLimitedModifier(float time, CompletionListener completionListener) {
		mTime = time;
		mTimeElapsed = 0;
		mCompletionListener = completionListener;
	}

	@Override
	public void modify(ISprite sprite, long timeDelta) {
		if(mTimeElapsed <= mTime) {
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
	
	public void setProgress(float progress) {
		if(progress >= 1)
			mTimeElapsed = mTime;
		else if(progress <= 0)
			mTimeElapsed = 0;
		else
			mTimeElapsed = mTime * progress;
	}

	public CompletionListener getCompletionListener() {
		return mCompletionListener;
	}

	public void setCompletionListener(CompletionListener mCompletionListener) {
		this.mCompletionListener = mCompletionListener;
	}

	public interface CompletionListener {
		public void onComplete(AbsTimeLimitedModifier modifier);
	}
}
