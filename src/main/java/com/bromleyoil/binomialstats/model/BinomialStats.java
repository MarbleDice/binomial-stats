package com.bromleyoil.binomialstats.model;

import org.apache.commons.math3.distribution.BinomialDistribution;

public class BinomialStats {
	public static final double TWO_SIGMA = 0.9545;
	public static final double THREE_SIGMA = 0.9973;

	private BinomialDistribution binDist;
	private int minX;
	private double minProb;
	private int maxX;
	private double maxProb;

	public BinomialStats(BinomialDistribution binDist) {
		this.binDist = binDist;
		double q;
		// Find the minimum cumulative probability
		for (int i = 0; i < binDist.getNumberOfTrials(); i++) {
			q = binDist.cumulativeProbability(i);
			if (q > 0) {
				minX = i;
				minProb = q;
				break;
			}
		}
		// Find the maximum cumulative probability
		for (int i = binDist.getNumberOfTrials(); i >= 0; i--) {
			q = binDist.cumulativeProbability(i);
			if (q < 1) {
				maxX = i;
				maxProb = q;
				break;
			}
		}
	}

	public String getMean() {
		return String.format("%.2f", binDist.getNumericalMean());
	}

	public String getStdDev() {
		return String.format("%.2f", Math.sqrt(binDist.getNumericalVariance()));
	}

	public Probability getPLteX(int x) {
		double q = binDist.cumulativeProbability(x);
		if (x < 0) {
			return new Probability(0);
		} else if (x >= binDist.getNumberOfTrials()) {
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
		double q = 1d - binDist.cumulativeProbability(x - 1);
		if (x <= 0) {
			return new Probability(1);
		} else if (x > binDist.getNumberOfTrials()) {
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
		int x1 = (int) Math.floor(binDist.getNumericalMean());
		int x2 = (int) Math.ceil(2 * binDist.getNumericalMean() - x1);
		for (int x = 0; x <= x1; x++) {
			if (binDist.cumulativeProbability(x1 - 1 - x, x2 + x) >= confidence) {
				return new Range(x1 - x, x2 + x);
			}
		}
		return new Range(0, Math.round(binDist.getNumericalMean() * 2));
	}
}
