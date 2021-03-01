package com.bromleyoil.binomialstats.model;

public class Range {
	private long x1;
	private long x2;

	public Range(long x1, long x2) {
		this.x1 = x1;
		this.x2 = x2;
	}

	@Override
	public String toString() {
		return String.format("%d-%d", x1, x2);
	}
}
