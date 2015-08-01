package com.liyuncong.algorithm.algorithm_apriori.entity;

import java.util.List;

public class ConfidentAssociationRule {
	private List<String> condition;
	private List<String> consequent;
	private float confidence;

	public ConfidentAssociationRule(List<String> condition,
			List<String> consequent, float confidence) {
		super();
		this.condition = condition;
		this.consequent = consequent;
		this.confidence = confidence;
	}

	public List<String> getCondition() {
		return condition;
	}

	public List<String> getConsequent() {
		return consequent;
	}

	public float getConfidence() {
		return confidence;
	}

	@Override
	public String toString() {
		return "ConfidentAssociationRule [condition=" + condition
				+ ", consequent=" + consequent + ", confidence=" + confidence
				+ "]";
	}

	
}
