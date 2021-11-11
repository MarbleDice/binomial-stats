package com.bromleyoil.binomialstats.model;

import java.util.Optional;

import org.apache.commons.math3.distribution.IntegerDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatUtil {
	private static final Logger log = LoggerFactory.getLogger(StatUtil.class);

	public static final double TWO_SIGMA = 0.9545;
	public static final double THREE_SIGMA = 0.9973;

	private StatUtil() {
		// Static class
	}

	public static Range findConfidence(IntegerDistribution dist, double confidence) {
		return findConfidence(dist, confidence, 0, Optional.empty(), Optional.empty());
	}

	protected static Range findConfidence(IntegerDistribution dist, double confidence, int testRadius,
			Optional<Integer> lowerBound, Optional<Integer> upperBound) {
		if ((long) Math.ceil(dist.getNumericalMean()) + testRadius >= Integer.MAX_VALUE) {
			testRadius = Integer.MAX_VALUE - (int) Math.ceil(dist.getNumericalMean());
		}
		log.debug("MEAN {} VAR {} SD {}", dist.getNumericalMean(), dist.getNumericalVariance(), Math.sqrt(dist.getNumericalVariance()));
		log.debug("Testing {} with bounds {} {}", testRadius, lowerBound.orElse(-1), upperBound.orElse(-1));
		int x1 = (int) Math.max(0, Math.floor(dist.getNumericalMean()) - testRadius);
		int x2 = (int) Math.ceil(dist.getNumericalMean()) + testRadius;

		if (upperBound.isPresent() && (!lowerBound.isPresent() || upperBound.get() - lowerBound.get() == 1)) {
			// Confidence interval found, return the range
			return new Range(x1 - upperBound.get(), x2 + upperBound.get());
		}

		// Distribution has exclusive lower bound
		if (dist.cumulativeProbability(x1 - 1, x2) >= confidence) {
			// Confidence met, try a narrower radius
			upperBound = Optional.of(testRadius);
			testRadius = lowerBound.isPresent()
					? (lowerBound.get() + upperBound.get()) / 2
					: 0;
			log.debug("Met -> {}", testRadius);
		} else {
			// Confidence not met, try a wider radius
			lowerBound = Optional.of(testRadius);
			testRadius = upperBound.isPresent()
					? (lowerBound.get() + upperBound.get()) / 2
					: lowerBound.get() + (int) Math.ceil(Math.sqrt(dist.getNumericalVariance()));
			log.debug("Not met -> {}", testRadius);
		}

		return findConfidence(dist, confidence, testRadius, lowerBound, upperBound);
	}
}
