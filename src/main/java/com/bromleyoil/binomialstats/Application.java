package com.bromleyoil.binomialstats;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.distribution.PascalDistribution;
import org.apache.commons.math3.stat.inference.AlternativeHypothesis;
import org.apache.commons.math3.stat.inference.BinomialTest;
import org.apache.commons.math3.stat.interval.AgrestiCoullInterval;
import org.apache.commons.math3.stat.interval.BinomialConfidenceInterval;
import org.apache.commons.math3.stat.interval.ConfidenceInterval;
import org.apache.commons.math3.stat.interval.WilsonScoreInterval;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner runIt() {
		return (args) -> {
			int n, x; double p;

			n = 177536;
			x = 1481;
			p = 0.00834;

			n = 200;
			x = 1;
			p = 0.00834;

			n = 5;
			x = 5;
			p = 0.05;

			n = 10;
			x = 8;
			p = 0.5;
			
			log("N=%d x=%d p=%.4f", n, x, p);
			pValue(n, x, p);
			negativeBinomial(x, p);
			confInt(n, x);
		};
	}

	private static void pValue(int n, int x, double p) {
		// p-value test on x with n p
		BinomialTest test = new BinomialTest();
		log("p> %f", test.binomialTest(n, x, p, AlternativeHypothesis.GREATER_THAN));
		log("p< %f", test.binomialTest(n, x, p, AlternativeHypothesis.LESS_THAN));
		log("pX %f", test.binomialTest(n, x, p, AlternativeHypothesis.TWO_SIDED));
	}

	private static void negativeBinomial(int x, double p) {
		// Negative binom distro for x with p
		PascalDistribution pascal = new PascalDistribution(x, p);
		double mean = pascal.getNumericalMean();
		double sd = Math.sqrt(pascal.getNumericalVariance());
		log("Pascal mean %f sd %f", mean, sd);
		log("   95 %d %d", Double.valueOf(mean - sd * 2).intValue(), Double.valueOf(mean + sd * 2).intValue());
		log("   97 %d %d", Double.valueOf(mean - sd * 3).intValue(), Double.valueOf(mean + sd * 3).intValue());
	}

	private static void confInt(int n, int x) {
		// Confidence interval of x in n
		Map<String, BinomialConfidenceInterval> intervals = new HashMap<>();
		// intervals.put("Clopper-Pearson", new ClopperPearsonInterval());
		intervals.put("Wilson Score", new WilsonScoreInterval());
		intervals.put("Agresti-Coull", new AgrestiCoullInterval());
		// intervals.put("Normal Approximation", new NormalApproximationInterval());
		for (Entry<String, BinomialConfidenceInterval> entry : intervals.entrySet()) {
			ConfidenceInterval ci95 = entry.getValue().createInterval(n, x, 0.95);
			ConfidenceInterval ci997 = entry.getValue().createInterval(n, x, 0.997);
			log("%-20s P = %.8f - %.8f", entry.getKey() + " 95", ci95.getLowerBound(), ci95.getUpperBound());
			log("%-20s P = %.8f - %.8f", entry.getKey() + " 99", ci997.getLowerBound(), ci997.getUpperBound());
		}
	}

	private static void log(String format, Object... args) {
		System.out.println(String.format(format, args));
	}
}
