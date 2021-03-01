package com.bromleyoil.binomialstats;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.PascalDistribution;
import org.apache.commons.math3.stat.inference.AlternativeHypothesis;
import org.apache.commons.math3.stat.inference.BinomialTest;
import org.apache.commons.math3.stat.interval.AgrestiCoullInterval;
import org.apache.commons.math3.stat.interval.BinomialConfidenceInterval;
import org.apache.commons.math3.stat.interval.ClopperPearsonInterval;
import org.apache.commons.math3.stat.interval.ConfidenceInterval;
import org.apache.commons.math3.stat.interval.NormalApproximationInterval;
import org.apache.commons.math3.stat.interval.WilsonScoreInterval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner runIt() {
		return (args) -> {
			int n;
			int x;
			double p;

			n = 177536;
			x = 1481;
			p = 0.00834;

			BinomialDistribution binDist = new BinomialDistribution(n, p);
			NormalDistribution norDist = new NormalDistribution(binDist.getNumericalMean(),
					Math.sqrt(binDist.getNumericalVariance()));

			double a1 = 0;
			double a2 = 0;
			for (int i = 0; i < n; i++) {
				double q1 = binDist.probability(i);
				double c1 = binDist.cumulativeProbability(i);
				double q2 = norDist.probability(i);
				double c2 = norDist.cumulativeProbability(i);
				a1 += q1;
				a2 += q2;
				if (i > 1481) {
					log.info("i={}", i);
					log.info("Bin prob: {} {} {}", c1, a1, q1);
					log.info("Nor prob: {}", c2);
					break;
				}
			}
		};
	}

	private static void pValue(int n, int x, double p) {
		// p-value test on x with n p
		BinomialTest test = new BinomialTest();
		log.info("p> %f", test.binomialTest(n, x, p, AlternativeHypothesis.GREATER_THAN));
		log.info("p< %f", test.binomialTest(n, x, p, AlternativeHypothesis.LESS_THAN));
		log.info("pX %f", test.binomialTest(n, x, p, AlternativeHypothesis.TWO_SIDED));
	}
}
