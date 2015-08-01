package com.liyuncong.algorithm.algorithm_apriori.util;

import java.util.ArrayList;
import java.util.List;

import com.liyuncong.algorithm.algorithm_apriori.entity.FrequentItemset;

public class AprioriUtil {
	/**
	 * 获得itemList的所有比其元素个数少一的子集;
	 * itemList的元素个数必须大于等于2
	 * @param itemList
	 * @return
	 */
	public static List<List<String>> getSubsetList(List<String> itemList) {
		List<List<String>> subsetList = new ArrayList<List<String>>();
		int itemListSize = itemList.size();
		for(int i = 0; i < itemListSize; i++) {
			List<String> subset = new ArrayList<String>();
			for(int j = 0; j < itemListSize; j++) {
				if (j != i) {
					subset.add(itemList.get(j));
				}
			}
			subsetList.add(subset);
		}
		return subsetList;
		
	}
	
	/**
	 * 判断一个itemList是不是真的候选频繁项目集，
	 * 只要有一个比它少一个元素的子集不是k-1频繁项
	 * 目集集合的元素，就被排除；因为，如果一个项目
	 * 集是频繁项目集的话，它的任何非空子集也都是
	 * 频繁项目集
	 * @param foreFrequentItemsets
	 * @param itemList
	 * @return
	 */
	public static  boolean isRealCandidateFrequentItemset(List<FrequentItemset>
	foreFrequentItemsets, List<String> itemList) {
		List<List<String>> subsetList = AprioriUtil.getSubsetList(itemList);
		List<List<String>> foreFrequentItemsetList = new ArrayList<List<String>>();
		for(FrequentItemset frequentItemset : foreFrequentItemsets) {
			foreFrequentItemsetList.add(frequentItemset.getFrequentItemset());
		}
		for(List<String> subset : subsetList) {
			if (!foreFrequentItemsetList.contains(subset)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 再次确认一个元素集是否可能为后件
	 * @param foreConsequents
	 * @param subsetList
	 * @return
	 */
	public static boolean isRealCandidateConsequent(List<List<String>> foreConsequents,
			List<String> itemList) {
		List<List<String>> subsetList = AprioriUtil.getSubsetList(itemList);
		for(List<String> subset : subsetList) {
			if (!foreConsequents.contains(subset)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 得到一个后件的前件
	 * @param frequentItemList
	 * @param consequent
	 * @return
	 */
	public static List<String> getCondition(List<String> frequentItemList, 
			List<String> consequent) {
		List<String> condition = new ArrayList<String>();
		for(String item : frequentItemList) {
			if (!consequent.contains(item)) {
				condition.add(item);
			}
		}
		if (condition.size() == 0) {
			return null;
		}
		return condition;
	}
	
	/**
	 * 一对的意思就是，它们两长度一样，并且只有最后一
	 * 个元素不一样
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static boolean isPair(List<String> list1, List<String> list2) {
		// 比较长度
		if (list1.size() != list2.size()) {
			return false;
		}
		
		// 比较最后一项
		int listSize = list1.size();
		if (list1.get(listSize - 1).compareTo(list2.get(list2.size() - 1)) == 0) {
			return false;
		}
		
		// 比较除最后一项的所有项
		for(int i = 0; i < listSize - 1; i++) {
			if (list1.get(i).compareTo(list2.get(i)) != 0) {
					return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 合并一对k-1频繁项目集为一个k项目集
	 * @param frequentItemset
	 * @param another
	 * @return
	 */
	public static List<String> mergePairList(List<String> list1, 
			List<String> list2) {
		List<String> afterMerge = new ArrayList<>();
		int listSize = list1.size();
		if (list1.get(listSize - 1).compareTo(list2.
				get(listSize - 1)) < 0) {
			afterMerge.addAll(list1);
			afterMerge.add(list2.get(listSize - 1));
		} else {
			afterMerge.addAll(list2);
			afterMerge.add(list1.get(listSize - 1));
		}
		
		return afterMerge;
	}
}
