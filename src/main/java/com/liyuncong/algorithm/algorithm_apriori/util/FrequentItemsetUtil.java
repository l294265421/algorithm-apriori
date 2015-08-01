package com.liyuncong.algorithm.algorithm_apriori.util;

import java.util.ArrayList;
import java.util.List;

import com.liyuncong.algorithm.algorithm_apriori.entity.FrequentItemset;

public class FrequentItemsetUtil {
	/**
	 * 合并一对k-1频繁项目集为一个k项目集
	 * @param frequentItemset
	 * @param another
	 * @return
	 */
	public static List<String> mergePairFrequentItemset(FrequentItemset 
			frequentItemset, FrequentItemset another) {
		List<String> afterMerge = new ArrayList<>();
		List<String> itemList1 = frequentItemset.getFrequentItemset();
		List<String> itemList2 = another.getFrequentItemset();
		int itemListSize = itemList1.size();
		if (itemList1.get(itemListSize - 1).compareTo(itemList2.
				get(itemListSize - 1)) < 0) {
			afterMerge.addAll(itemList1);
			afterMerge.add(itemList2.get(itemListSize - 1));
		} else {
			afterMerge.addAll(itemList2);
			afterMerge.add(itemList1.get(itemListSize - 1));
		}
		
		return afterMerge;
	}
}
