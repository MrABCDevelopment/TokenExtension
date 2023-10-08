package me.dreamdevs.tokenextension;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.extensions.Extension;
import me.dreamdevs.randomlootchest.api.utils.Util;
import me.dreamdevs.tokenextension.listeners.ChestOpenListener;
import me.dreamdevs.tokenextension.managers.TokensManager;
import me.realized.tokenmanager.api.TokenManager;
import org.bukkit.Bukkit;

public class TokenExtensionMain extends Extension {

	public static TokenManager tokenManager;
	public static TokenExtensionMain instance;
	private @Getter TokensManager tokensManager;

	@Override
	public void onExtensionEnable() {
		instance = this;
		if (!setupTokens()) {
			Util.sendPluginMessage("&cTokenExtension could not hook with TokenManager, so there might be a problem...");
			setState(State.DISABLED);
			this.onExtensionDisable();
			return;
		}
		saveDefaultConfig();
		setup();
	}

	@Override
	public void onExtensionDisable() {
		Util.sendPluginMessage("&cDisabling TokenExtension...");
	}

	@Override
	public void reloadConfig() {
		super.reloadConfig();

		setup();
	}

	private void setup() {
		tokensManager = new TokensManager(this);
		registerListener(new ChestOpenListener());
		Util.sendPluginMessage("&aLoaded all variables and setup TokenExtension!");
	}

	private boolean setupTokens() {
		tokenManager = (TokenManager) Bukkit.getServer().getPluginManager().getPlugin("TokenManager");
		return tokenManager != null;
	}

}