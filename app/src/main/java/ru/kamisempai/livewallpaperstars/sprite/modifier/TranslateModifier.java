package ru.kamisempai.livewallpaperstars.sprite.modifier;

import ru.kamisempai.livewallpaperstars.sprite.ISprite;

public class TranslateModifier extends AbsTimeLimitedModifier {
	
	private float mFromX;
	private float mFromY;
	private float mDeltaX;
	private float mDeltaY;
	
	public TranslateModifier(float fromX, float fromY, float toX, float toY, float time) {
		this(fromX, fromY, toX, toY, time, null);
	}
	
	public TranslateModifier(float fromX, float fromY, float toX, float toY, float time, CompletionListener completionListene) {
		super(time, completionListene);
		mFromX = fromX;
		mFromY = fromY;
		mDeltaX = toX - fromX;
		mDeltaY = toY - fromY;
	}

	@Override
	public void modify(ISprite sprite, long timeDelta) {
		super.modify(sprite, timeDelta);
		float progress = getProgress();
		sprite.setPosition(mFromX + mDeltaX * progress, mFromY + mDeltaY * progress);
	}

}
