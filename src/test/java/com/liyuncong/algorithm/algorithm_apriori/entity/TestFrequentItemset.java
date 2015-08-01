package com.liyuncong.algorithm.algorithm_apriori.entity;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestFrequentItemset {
	@Test
	public void testIsPairTo() {
		List<String> f1 = new ArrayList<>();
		f1.add("lierwang");
		f1.add("liyuncong");
		FrequentItemset frequentItemset = new FrequentItemset(f1, 0);
		
		List<String> f2 = new ArrayList<>();
		f2.add("wangfang");
		f2.add("lierwang");
		FrequentItemset frequentItemset2 = new FrequentItemset(f2, 0);
		
		System.out.println(frequentItemset.isPairTo(frequentItemset2));
	}

}
