package ru.kamisempai.livewallpaperstars.sprite;

import ru.kamisempai.livewallpaperstars.sprite.modifier.ISpriteModifier;
import android.graphics.RectF;

public interface ISprite extends ILayout {
	
	public void setSize(float width, float height);
	
	public float getWidth();
	public float getHeight();
	
	public boolean isVisible(RectF visibleRect);
	
	public void setSpriteModifier(ISpriteModifier modifier);
	public ISpriteModifier getSpriteModifier();
	
	public void setParent(ILayout parent);
	public ILayout getParent();
	public void removeFromParent();
}