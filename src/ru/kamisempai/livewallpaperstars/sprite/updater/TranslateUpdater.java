package ru.kamisempai.livewallpaperstars.sprite.updater;

import ru.kamisempai.livewallpaperstars.sprite.ISprite;

public class TranslateUpdater extends AbsTimeLimitedUpdater {
	
	private float mFromX;
	private float mFromY;
	private float mDeltaX;
	private float mDeltaY;
	
	public TranslateUpdater(float fromX, float fromY, float toX, float toY, float time) {
		this(fromX, fromY, toX, toY, time, null);
	}
	
	public TranslateUpdater(float fromX, float fromY, float toX, float toY, float time, CompletionListener completionListene) {
		super(time, completionListene);
		mFromX = fromX;
		mFromY = fromY;
		mDeltaX = toX - fromX;
		mDeltaY = toY - fromY;
	}

	@Override
	public void update(ISprite sprite, long timeDelta) {
		super.update(sprite, timeDelta);
		float progress = getProgress();
		sprite.setPosition(mFromX + mDeltaX * progress, mFromY + mDeltaY * progress);
	}

}
