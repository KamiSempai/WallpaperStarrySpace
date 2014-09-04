package ru.kamisempai.livewallpaperstars.sprite.updater;

import java.util.ArrayList;

import ru.kamisempai.livewallpaperstars.sprite.ISprite;

public class SpriteUpdatersSet implements ISpriteUpdater {
	private ArrayList<ISpriteUpdater> mUpdaters = new ArrayList<ISpriteUpdater>();

	@Override
	public void update(ISprite sprite, long timeDelta) {
		for(ISpriteUpdater updater: mUpdaters)
			updater.update(sprite, timeDelta);
	}
	
	public void addUpdater(ISpriteUpdater updater) {
		mUpdaters.add(updater);
	}

}
