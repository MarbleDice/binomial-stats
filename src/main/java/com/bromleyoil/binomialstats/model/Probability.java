package com.bromleyoil.binomialstats.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Probability {
	private static final Logger log = LoggerFactory.getLogger(Probability.class);

	private double p;
	private String prefix = "";

	public Probability(double p) {
		this.p = p;
	}

	public Probability(double p, String prefix) {
		this.p = p;
		this.prefix = prefix;
	}

	public double getP() {
		return p;
	}

	public String getFrequency() {
		return String.format("%s%.4f%%", prefix, p);
	}

	public String getPercent() {
		double percent;
		if (p >= 1) {
			percent = 100;
		} else if (p >= 0.99995) {
			percent = 99.99;
		} else {
			percent = 100d * p;
		}
		return String.format("%s%.2f%%", prefix, percent);
	}

	public String getOdds() {
		long odds = (long) (1f / p);
		log.info("Odds {} {}", p, odds);
		return odds > 1000000000
				? String.format("%s1 in %.1e", prefix, 1d / p)
				: String.format("%s1 in %,d", prefix, (long) (1d / p));
	}

	public String getProbability() {
		if(p <= 0 || p >= 1) {
			return getPercent();
		} else if (p < 0.0001) {
			return getOdds();
		} else {
			return getPercent();
		}
	}
	
	@Override
	public String toString() {
		return getProbability();
	}
}
