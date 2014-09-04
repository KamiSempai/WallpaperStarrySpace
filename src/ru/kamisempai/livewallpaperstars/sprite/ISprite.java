package ru.kamisempai.livewallpaperstars.sprite;

import ru.kamisempai.livewallpaperstars.sprite.updater.ISpriteUpdater;
import android.graphics.RectF;

public interface ISprite extends ILayout {
	
	public void setSize(float width, float height);
	
	public float getWidth();
	public float getHeight();
	
	public boolean isVisible(RectF visibleRect);
	
	public void setSpriteUpdater(ISpriteUpdater updater);
	public ISpriteUpdater getSpriteUpdater();
	
	public void setParrent(ILayout parent);
	public ILayout getParent();
	public void removeFromParent();
}