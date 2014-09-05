package ru.kamisempai.livewallpaperstars.sprite.modifier;

import java.util.ArrayList;

import ru.kamisempai.livewallpaperstars.sprite.ISprite;

public class SpriteModifiersSet implements ISpriteModifier {
	private ArrayList<ISpriteModifier> mUpdaters = new ArrayList<ISpriteModifier>();

	@Override
	public void modify(ISprite sprite, long timeDelta) {
		for(ISpriteModifier updater: mUpdaters)
			updater.modify(sprite, timeDelta);
	}
	
	public void add(ISpriteModifier modifier) {
		mUpdaters.add(modifier);
	}

}
