package com.bromleyoil.binomialstats.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.math3.stat.interval.ConfidenceInterval;

public class TieredConfidenceInterval {
	private String algorithmName;
	private double mean;
	private List<Double> confidenceLevels = new ArrayList<>();
	private List<Double> lowerBounds = new ArrayList<>();
	private List<Double> upperBounds = new ArrayList<>();

	public TieredConfidenceInterval(String algorithmName, double mean, Collection<ConfidenceInterval> intervals) {
		this.algorithmName  = algorithmName;
		this.mean = mean;
		intervals.stream().forEach(this::addInterval);
	}

	private void addInterval(ConfidenceInterval interval) {
		confidenceLevels.add(interval.getConfidenceLevel());
		lowerBounds.add(interval.getLowerBound());
		upperBounds.add(interval.getUpperBound());
		Collections.sort(confidenceLevels);
		Collections.sort(lowerBounds);
		Collections.sort(upperBounds);
	}

	public String getAlgorithmName() {
		return algorithmName;
	}

	public double getConfidenceLevel(int i) {
		return confidenceLevels.get(confidenceLevels.size() - i - 1);
	}

	public Probability getLowerBound(int i) {
		return new Probability(lowerBounds.get(i));
	}

	public Probability getUpperBound(int i) {
		return new Probability(upperBounds.get(upperBounds.size() - i - 1));
	}

	public Probability getMean() {
		return new Probability(mean);
	}

	public List<Double> getConfidenceLevels() {
		return confidenceLevels;
	}

	public List<Double> getLowerBounds() {
		return lowerBounds;
	}

	public List<Double> getUpperBounds() {
		return upperBounds;
	}
}
