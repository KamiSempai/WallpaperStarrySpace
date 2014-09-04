package ru.kamisempai.livewallpaperstars.sprite.updater;

import ru.kamisempai.livewallpaperstars.sprite.ISprite;

public class RotateUpdater implements ISpriteUpdater {
	
	private float mRotationSpeed;
	private float mCenterX;
	private float mCenterY;
	
	public RotateUpdater(float rotationPerSecond, float centerX, float centerY) {
		mRotationSpeed = rotationPerSecond / 1000f;
		mCenterX = centerX;
		mCenterY = centerY;
	}

	@Override
	public void update(ISprite sprite, long timeDelta) {
		sprite.setRotation(sprite.getRotation() + mRotationSpeed * (float) timeDelta, sprite.getWidth() * mCenterX, sprite.getHeight() * mCenterY);
	}

}
