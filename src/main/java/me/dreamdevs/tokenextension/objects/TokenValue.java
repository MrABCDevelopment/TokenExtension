package me.dreamdevs.tokenextension.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor @Getter
public class TokenValue {

	private final double chance;
	private final double min;
	private final double max;

	public double getRandomValue() {
		return ThreadLocalRandom.current().nextDouble(min, max);
	}

}