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

	public double getLowerBound(int i) {
		return lowerBounds.get(i);
	}

	public double getUpperBound(int i) {
		return upperBounds.get(upperBounds.size() - i - 1);
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public List<Double> getConfidenceLevels() {
		return confidenceLevels;
	}

	public void setConfidenceLevels(List<Double> confidenceLevels) {
		this.confidenceLevels = confidenceLevels;
	}

	public List<Double> getLowerBounds() {
		return lowerBounds;
	}

	public void setLowerBounds(List<Double> lowerBounds) {
		this.lowerBounds = lowerBounds;
	}

	public List<Double> getUpperBounds() {
		return upperBounds;
	}

	public void setUpperBounds(List<Double> upperBounds) {
		this.upperBounds = upperBounds;
	}
}
