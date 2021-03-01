package com.bromleyoil.binomialstats.model;

import org.apache.commons.math3.distribution.PascalDistribution;

public class PascalStats {
	public static final double TWO_SIGMA = 0.9545;
	public static final double THREE_SIGMA = 0.9973;

	private PascalDistribution dist;

	public PascalStats(PascalDistribution dist) {
		this.dist = dist;
	}

	public String getMean() {
		// Pascal represents the number of failures before the desired successes are
		// achieved, so add the successes
		return String.format("%.2f", dist.getNumericalMean() + dist.getNumberOfSuccesses());
	}

	public String getStdDev() {
		return String.format("%.2f", Math.sqrt(dist.getNumericalVariance()));
	}

	public Range getRange(double confidence) {
		confidence = Math.min(1, confidence);
		int x1 = (int) Math.floor(dist.getNumericalMean());
		int x2 = (int) Math.ceil(dist.getNumericalMean());
		for (int x = 0; true; x++) {
			// Distribution uses an exclusive lower bound
			if (dist.cumulativeProbability(Math.max(-1, x1 - x - 1), x2 + x) >= confidence) {
				// Add the successes
				return new Range(Math.max(0, x1 - x) + dist.getNumberOfSuccesses(),
						x2 + x + dist.getNumberOfSuccesses());
			}
		}
	}
}
