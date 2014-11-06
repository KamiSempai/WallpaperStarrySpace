package ru.kamisempai.livewallpaperstars.sprite.modifier;

import ru.kamisempai.livewallpaperstars.sprite.ISprite;

public interface ISpriteModifier {
	public void modify(ISprite sprite, long timeDelta);
}
