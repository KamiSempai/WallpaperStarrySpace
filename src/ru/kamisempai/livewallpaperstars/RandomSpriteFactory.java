package ru.kamisempai.livewallpaperstars;

import android.content.Context;
import android.graphics.BitmapFactory;
import ru.kamisempai.livewallpaperstars.sprite.BitmapSprite;
import ru.kamisempai.livewallpaperstars.sprite.ISprite;

public class RandomSpriteFactory {
	
	private Context mContext;
	
	public RandomSpriteFactory(Context context) {
		mContext = context;
	}

	public ISprite newSprite() {
		return new BitmapSprite(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher), 300, 300);
	}
}
