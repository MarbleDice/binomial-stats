package com.bromleyoil.binomialstats.model;

import org.apache.commons.math3.distribution.BinomialDistribution;

public class BinomialStats {
	public static final double TWO_SIGMA = 0.9545;
	public static final double THREE_SIGMA = 0.9973;

	private BinomialDistribution dist;
	private int minX;
	private double minProb;
	private int maxX;
	private double maxProb;

	public BinomialStats(BinomialDistribution dist) {
		this.dist = dist;
		double q;
		// Find the minimum cumulative probability
		for (int i = 0; i < dist.getNumberOfTrials(); i++) {
			q = dist.cumulativeProbability(i);
			if (q > 0) {
				minX = i;
				minProb = q;
				break;
			}
		}
		// Find the maximum cumulative probability
		for (int i = dist.getNumberOfTrials(); i >= 0; i--) {
			q = dist.cumulativeProbability(i);
			if (q < 1) {
				maxX = i;
				maxProb = q;
				break;
			}
		}
	}

	public String getMean() {
		return String.format("%.2f", dist.getNumericalMean());
	}

	public String getStdDev() {
		return String.format("%.2f", Math.sqrt(dist.getNumericalVariance()));
	}

	public Probability getPLteX(int x) {
		double q = dist.cumulativeProbability(x);
		if (x < 0) {
			return new Probability(0);
		} else if (x >= dist.getNumberOfTrials()) {
			return new Probability(1);
		} else if (x < minX) {
			return new Probability(minProb, "<");
		} else if(x > maxX) {
			return new Probability(maxProb, ">");
		} else {
			return new Probability(q);
		}
	}

	public Probability getPGteX(int x) {
		double q = 1d - dist.cumulativeProbability(x - 1);
		if (x <= 0) {
			return new Probability(1);
		} else if (x > dist.getNumberOfTrials()) {
			return new Probability(0);
		} else if (x - 1 < minX) {
			return new Probability(1d - minProb, ">");
		} else if(x - 1 > maxX) {
			return new Probability(1d - maxProb, "<");
		} else {
			return new Probability(q);
		}
	}

	public Range getRange(double confidence) {
		int x1 = (int) Math.floor(dist.getNumericalMean());
		int x2 = (int) Math.ceil(dist.getNumericalMean());
		for (int x = 0; x <= x1; x++) {
			// Distribution uses an exclusive lower bound
			if (dist.cumulativeProbability(x1 - 1 - x, x2 + x) >= confidence) {
				return new Range(x1 - x, x2 + x);
			}
		}
		return new Range(0, (int) Math.round(dist.getNumericalMean() * 2));
	}
}
