package com.bromleyoil.binomialstats;

import org.apache.commons.math3.stat.inference.AlternativeHypothesis;
import org.apache.commons.math3.stat.inference.BinomialTest;
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
	CommandLineRunner runIt() {
		return args -> {
			int n;
			int x;
			double p;

			n = 177536;
			x = 1481;
			p = 0.00834;

			pValue(n, x, p);
		};
	}

	private static void pValue(int n, int x, double p) {
		// p-value test on x with n p
		BinomialTest test = new BinomialTest();
		log.info("p> {}", test.binomialTest(n, x, p, AlternativeHypothesis.GREATER_THAN));
		log.info("p< {}", test.binomialTest(n, x, p, AlternativeHypothesis.LESS_THAN));
		log.info("pX {}", test.binomialTest(n, x, p, AlternativeHypothesis.TWO_SIDED));
	}
}
