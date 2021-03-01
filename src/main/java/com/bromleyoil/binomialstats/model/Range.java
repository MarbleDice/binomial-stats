package com.bromleyoil.binomialstats.model;

public class Range {
	private int x1;
	private int x2;

	public Range(int x1, int x2) {
		this.x1 = x1;
		this.x2 = x2;
	}

	@Override
	public String toString() {
		return String.format("%d-%d", x1, x2);
	}
}
