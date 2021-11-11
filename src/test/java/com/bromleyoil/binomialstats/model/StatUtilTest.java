package com.bromleyoil.binomialstats.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.IntegerDistribution;
import org.apache.commons.math3.distribution.PascalDistribution;
import org.junit.jupiter.api.Test;

class StatUtilTest {

	@Test
	void findConfidence_threeSigma_isNarrower() {
		IntegerDistribution dist = new BinomialDistribution(10000, 0.5);
		Range range = StatUtil.findConfidence(dist, StatUtil.TWO_SIGMA);
		assertThat(range.toString()).hasToString("4801-5199");
	}

	@Test
	void findConfidence_threeSigma_isWider() {
		IntegerDistribution dist = new BinomialDistribution(10000, 0.5);
		Range range = StatUtil.findConfidence(dist, StatUtil.THREE_SIGMA);
		assertThat(range.toString()).hasToString("4701-5299");
	}

	@Test
	void findConfidence_overflow_isApproximate() {
		IntegerDistribution dist = new PascalDistribution(100000000, 0.005);
		Range range = StatUtil.findConfidence(dist, StatUtil.THREE_SIGMA);
		assertThat(range.toString()).hasToString("4701-5299");
	}
}
