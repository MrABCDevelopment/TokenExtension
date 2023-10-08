package me.dreamdevs.tokenextension.managers;

import lombok.Getter;
import me.dreamdevs.randomlootchest.api.extensions.Extension;
import me.dreamdevs.randomlootchest.api.utils.ColourUtil;
import me.dreamdevs.tokenextension.objects.TokenValue;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class TokensManager {

	public static String FOUND_TOKENS_MESSAGE;
	public static String NOTHING_FOUND_MESSAGE;
	public static String LOST_TOKENS_MESSAGE;

	private @Getter final Map<String, TokenValue> tokenValues;
	private final FileConfiguration config;

	public TokensManager(Extension extension) {
		tokenValues = new HashMap<>();

		config = extension.getConfig();
		load();
	}

	public void load() {
		tokenValues.clear();

		FOUND_TOKENS_MESSAGE = ColourUtil.colorize(Objects.requireNonNull(config.getString("Messages.Tokens-Found")));
		NOTHING_FOUND_MESSAGE = ColourUtil.colorize(Objects.requireNonNull(config.getString("Messages.Nothing-Found")));
		LOST_TOKENS_MESSAGE = ColourUtil.colorize(Objects.requireNonNull(config.getString("Messages.Tokens-Lost")));

		Optional.ofNullable(config.getConfigurationSection("Chests")).ifPresent(section -> section.getKeys(false).forEach(s -> {
			String[] strings = Objects.requireNonNull(section.getString(s)).split(";");
			tokenValues.putIfAbsent(s, new TokenValue(Double.parseDouble(strings[0]), Double.parseDouble(strings[1]), Double.parseDouble(strings[2])));
		}));
	}

}