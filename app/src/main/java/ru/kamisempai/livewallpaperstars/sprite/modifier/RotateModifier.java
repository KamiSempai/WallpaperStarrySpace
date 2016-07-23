package ru.kamisempai.livewallpaperstars.sprite.modifier;

import ru.kamisempai.livewallpaperstars.sprite.ISprite;

public class RotateModifier implements ISpriteModifier {
	
	private float mRotationSpeed;
	private float mCenterX;
	private float mCenterY;
	
	public RotateModifier(float rotationPerSecond, float centerX, float centerY) {
		mRotationSpeed = rotationPerSecond / 1000f;
		mCenterX = centerX;
		mCenterY = centerY;
	}

	@Override
	public void modify(ISprite sprite, long timeDelta) {
		sprite.setRotation(sprite.getRotation() + mRotationSpeed * (float) timeDelta, sprite.getWidth() * mCenterX, sprite.getHeight() * mCenterY);
	}

}
