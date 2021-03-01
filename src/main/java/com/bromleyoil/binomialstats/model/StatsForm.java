package com.bromleyoil.binomialstats.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.PascalDistribution;
import org.apache.commons.math3.stat.interval.AgrestiCoullInterval;
import org.apache.commons.math3.stat.interval.BinomialConfidenceInterval;
import org.apache.commons.math3.stat.interval.ClopperPearsonInterval;
import org.apache.commons.math3.stat.interval.NormalApproximationInterval;
import org.apache.commons.math3.stat.interval.WilsonScoreInterval;

public class StatsForm {
	private static final Map<String, BinomialConfidenceInterval> ALGORITHMS = Map.of(
			"Wilson Score", new WilsonScoreInterval(),
			"Agresti-Coull", new AgrestiCoullInterval(),
			"Clopper-Pearson", new ClopperPearsonInterval(),
			"Normal Approximation", new NormalApproximationInterval());

	private Integer n;
	private Integer x;
	private Double p;

	public boolean hasConfidence() {
		return n != null && x != null;
	}

	public boolean hasDistribution() {
		return n != null && p != null;
	}

	public boolean hasNegativeDistribution() {
		return x != null && x > 0 && p != null;
	}

	public boolean hasStats() {
		return n != null && x != null && p != null;
	}

	public List<TieredConfidenceInterval> getIntervals() {
		List<Double> confidenceLevels = Arrays.asList(0.9545, 0.9973);
		List<TieredConfidenceInterval> tieredIntervals = new ArrayList<>();

		for (Entry<String, BinomialConfidenceInterval> algorithm : ALGORITHMS.entrySet()) {
			try {
				tieredIntervals.add(new TieredConfidenceInterval(algorithm.getKey(), 1d * x / n,
						confidenceLevels.stream()
								.map(c -> algorithm.getValue().createInterval(n, x, c))
								.collect(Collectors.toList())));
			} catch (Exception e) {
				// Forget this algorithm
			}
		}

		return tieredIntervals;
	}

	public BinomialStats getStats() {
		return hasDistribution() ? new BinomialStats(new BinomialDistribution(n, p)) : null;
	}

	public PascalStats getNegativeStats() {
		return hasNegativeDistribution() ? new PascalStats(new PascalDistribution(x, p)) : null;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Double getP() {
		return p;
	}

	public void setP(Double p) {
		this.p = p;
	}

}
