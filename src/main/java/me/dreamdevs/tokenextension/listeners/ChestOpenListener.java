package me.dreamdevs.tokenextension.listeners;

import me.dreamdevs.randomlootchest.api.events.ChestOpenEvent;
import me.dreamdevs.randomlootchest.api.utils.Util;
import me.dreamdevs.tokenextension.TokenExtensionMain;
import me.dreamdevs.tokenextension.managers.TokensManager;
import me.dreamdevs.tokenextension.objects.TokenValue;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ChestOpenListener implements Listener {

	@EventHandler
	public void openChestEvent(ChestOpenEvent event) {
		if (!TokenExtensionMain.instance.getTokensManager().getTokenValues().containsKey(event.getChestGame().getId()))
			return;
		TokenValue tokenValue = TokenExtensionMain.instance.getTokensManager().getTokenValues().get(event.getChestGame().getId());
		if (Util.chance(tokenValue.getChance())) {
			double tokens = tokenValue.getRandomValue();
			TokenExtensionMain.tokenManager.addTokens(event.getPlayer(), round(tokens));
			if (tokens == 0) {
				event.getPlayer().sendMessage(TokensManager.NOTHING_FOUND_MESSAGE);
			} else if (tokens > 0) {
				event.getPlayer().sendMessage(TokensManager.FOUND_TOKENS_MESSAGE.replace("%TOKENS%", String.valueOf(round(tokens))));
			} else {
				event.getPlayer().sendMessage(TokensManager.LOST_TOKENS_MESSAGE.replace("%TOKENS%", String.valueOf(round(tokens))));
			}
		}
	}

	private long round(double money) {
		BigDecimal bd = BigDecimal.valueOf(money);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.longValue();
	}
}