package com.bromleyoil.binomialstats.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.bromleyoil.binomialstats.model.StatsForm;

@Controller
public class StatsController {
	private static final Logger log = LoggerFactory.getLogger(StatsController.class);

	public static final String VIEW = "stats";

	public static final String MODEL_BINOMIAL = "bin";
	public static final String MODEL_CONFIDENCE = "ci";
	public static final String MODEL_NBINOMIAL = "neg";

	@GetMapping("/")
	public String initialRequest(StatsForm statsForm, Model model) {
		return VIEW;
	}

	@PostMapping("/")
	public String post(StatsForm statsForm, Model model) {
		log.info("n {} x {} p {}", statsForm.getN(), statsForm.getX(), statsForm.getP());
		model.addAttribute(MODEL_BINOMIAL, statsForm.getStats());
		model.addAttribute(MODEL_NBINOMIAL, statsForm.getNegativeStats());
		return VIEW;
	}
}
