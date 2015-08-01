package com.liyuncong.algorithm.algorithm_apriori.apriori;

import java.util.ArrayList;
import java.util.List;

import com.liyuncong.algorithm.algorithm_apriori.entity.ConfidentAssociationRule;
import com.liyuncong.algorithm.algorithm_apriori.entity.FrequentItemset;
import com.liyuncong.algorithm.algorithm_apriori.entity.Transactions;
import com.liyuncong.algorithm.algorithm_apriori.util.AprioriUtil;
import com.liyuncong.algorithm.algorithm_apriori.util.FrequentItemsetUtil;
/**
 * Apriori算法
 * @author yuncong
 *
 */
public class Apriori {
	/**
	 * 生成所有频繁项目集
	 * @param transactions
	 * @param minsup
	 * @return
	 */
	public List<FrequentItemset> generateFrequentItemsets(Transactions transactions,
			float minsup) {
		// 已经得到的所有频繁项目集
		List<FrequentItemset> frequentItemsets = new ArrayList<FrequentItemset>();
		// first pass
		List<FrequentItemset> oneFrequentItemsets = this.firstPass(transactions,
				minsup);
		// k-1频繁项目集，通过它获得k频繁项目集;初始时，是单频繁项目集
		List<FrequentItemset> foreFrequentItemsets = new ArrayList<FrequentItemset>(
				oneFrequentItemsets);
		do {
			frequentItemsets.addAll(foreFrequentItemsets);
			// 得到新的k-1频繁项目集
			foreFrequentItemsets = this.kpass(transactions, foreFrequentItemsets,
					minsup);
		} while (foreFrequentItemsets != null);
		return frequentItemsets;
	}
	
	/**
	 * 获得单项频繁项目集集合
	 * @param transactions 事务集
	 * @param minsup 最小支持度
	 * @return
	 */
	private List<FrequentItemset> firstPass(Transactions transactions, float minsup) {
		List<FrequentItemset> oneFrequentItemsets = new ArrayList<FrequentItemset>();
		// 遍历事务集，获得所有元素
		List<String> itemList = new ArrayList<String>();
		List<List<String>> transactionList = transactions.getTransactions();
		for(List<String> transaction : transactionList) {
			for(String item : transaction) {
				if (!itemList.contains(item)) {
					itemList.add(item);
				}
			}
		}
		// 计算每个元素的支持度，获得所有单项目频繁项目集
		int transactionsSize = transactions.getTransactionsSize();
		for(String item : itemList) {
			List<String> itemListCandidate = new ArrayList<String>();
			itemListCandidate.add(item);
			int supportCount = this.getSupportCount(transactions, itemListCandidate);
			if ((float)supportCount / transactionsSize > minsup) {
				FrequentItemset frequentItemset = new FrequentItemset(
						itemListCandidate, supportCount);
				oneFrequentItemsets.add(frequentItemset);
			}
		}
		
		return oneFrequentItemsets;
	}
	
	/**
	 * 获得一个候选频繁项目集在事务集中的支持度计数
	 * @param transactions 事务集
	 * @param itemListCandidate 候选频繁项目集
	 * @return
	 */
	private int getSupportCount(Transactions transactions, List<String> itemListCandidate) {
		int supportCount = 0;
		List<List<String>> transactionList = transactions.getTransactions();
		for(List<String> transaction : transactionList) {
			if (transaction.containsAll(itemListCandidate)) {
				supportCount++;
			}
		}
		return supportCount;
	}
	
	/**
	 * 获得k频繁项目集
	 * @param transactions
	 * @param foreFrequentItemsets
	 * @param minsup
	 * @return
	 */
	private List<FrequentItemset> kpass(Transactions transactions, 
			List<FrequentItemset> foreFrequentItemsets,
			float minsup) {
		List<FrequentItemset> kfrequentItemsets = new ArrayList<FrequentItemset>();
		List<List<String>> c1 = this.merge(foreFrequentItemsets);
		if (c1.size() == 0) {
			return null;
		}
		List<List<String>> c2 = this.tailor(foreFrequentItemsets, c1);
		if (c2.size() == 0) {
			return null;
		}
		
		int transactionsSize = transactions.getTransactionsSize();
		for(List<String> itemList : c2) {
			int supportCount = this.getSupportCount(transactions, itemList);
			if ((float) supportCount / transactionsSize > minsup) {
				FrequentItemset frequentItemset = new 
						FrequentItemset(itemList, supportCount);
				kfrequentItemsets.add(frequentItemset);
			}
		}
		if (kfrequentItemsets.size() == 0) {
			return null;
		}
		return kfrequentItemsets;
	}
	
	/**
	 * 合并k-1频繁项目集，得到k候选频繁项目集；
	 * 方法是寻找所有只有最后一个元素不同的k-1
	 * 频繁项目集对，然后合并它们，这样做，之
	 * 所以可行，是因为，如果一个项目集是k频繁
	 * 项目集，那么它的所有k-1个元素的子集也
	 * 必然是频繁项目集，自然存在只有最后一个元素
	 * 不同的两个k-1项子集，所以不会漏掉任何一个
	 * 候选k频繁项目集
	 * @param foreFrequentItemsets
	 * @return
	 */
	private List<List<String>> merge(List<FrequentItemset> foreFrequentItemsets) {
		List<List<String>> c1 = new ArrayList<List<String>>();
		int foreFrequentItemsetsSize = foreFrequentItemsets.size();
		// 遍历每一个k-1频繁项目集，让它们与排在它们后面的频繁项目集连线,
		// 从而让所有k-1频繁项目集两两配对
		for(int i = 0; i < foreFrequentItemsetsSize; i++) {
			FrequentItemset frequentItemset = foreFrequentItemsets.get(i);
			for(int j = i + 1; j < foreFrequentItemsetsSize; j++) {
				FrequentItemset another = foreFrequentItemsets.get(j);
				if (frequentItemset.isPairTo(another)) {
					List<String> frequentItemsetAfterMerge = FrequentItemsetUtil.mergePairFrequentItemset
					(frequentItemset, another);
					if (!c1.contains(frequentItemsetAfterMerge)) {
						c1.add(frequentItemsetAfterMerge);
					}
				}
			}
		}
		return c1;
	}
	
	/**
	 * 如果一个k项目集的任何k-1项目子集不是频繁项目集，
	 * 那么这个k项目集也不是频繁项目集
	 * @param transactions
	 * @param c1
	 * @return
	 */
	private List<List<String>> tailor(List<FrequentItemset> foreFrequentItemsets, 
			List<List<String>> c1) {
		List<List<String>> c2 = new ArrayList<List<String>>();
		for(List<String> itemList : c1) {
			if (AprioriUtil.isRealCandidateFrequentItemset(
					foreFrequentItemsets, itemList)) {
				c2.add(itemList);
			}
		}
		return c2;
	}
	
	/**
	 * 从频繁项目集上生成所有置信度大于等于最小置信度的可信关联规则
	 * @param frequentItemsets
	 * @param minconf
	 * @return
	 */
	public List<ConfidentAssociationRule> generateConfidentAssociationRules(
			List<FrequentItemset> frequentItemsets, float minconf) {
		List<ConfidentAssociationRule> confidentAssociationRules =
				new ArrayList<ConfidentAssociationRule>();
		// 对每一个元素个数大于等于2的频繁项目集生成可信关联规则
		for(FrequentItemset frequentItemset : frequentItemsets) {
			List<String> frequentItemList = frequentItemset.getFrequentItemset();
			int frequentItemListCount = frequentItemset.getsupportCount();
			int frequentItemListSize = frequentItemList.size();
			if (frequentItemListSize >= 2) {
				confidentAssociationRules.addAll(this.generateConfidentAssociationRules(
						frequentItemsets, frequentItemList, minconf,
						frequentItemListCount));
			}
		}
		return confidentAssociationRules;
	}
	
	/**
	 * 生成某一个频繁项目集的所有可信关联规则;
	 * frequentItemList元素个数大于等于2；
	 * 后件元素个数保证比最多frequentItemList
	 * 元素个数少1
	 * @param frequentItemsets
	 * @param frequentItemList
	 * @param minconf
	 * @param frequentItemListCount
	 * @return
	 */
	private List<ConfidentAssociationRule> generateConfidentAssociationRules(
			List<FrequentItemset> frequentItemsets, List<String> frequentItemList,
			float minconf, int frequentItemListCount) {
		List<ConfidentAssociationRule> confidentAssociationRules =
				new ArrayList<ConfidentAssociationRule>();
		int frequentItemListSize = frequentItemList.size();
		// 生成后件只有一个元素的可信关联规则
		List<List<String>> oneConsequents = this.getOneConsequents(frequentItemList);
		// 包含k-1个元素的可信关联规则的后件的集合
		List<List<String>> foreConsequents = new ArrayList<List<String>>();
		for(List<String> consequent : oneConsequents) {
			float confidence = this.getConfidence(frequentItemsets, 
					frequentItemList, frequentItemListCount, consequent);
			if (confidence >= minconf) {
				foreConsequents.add(consequent);
				List<String> condition = AprioriUtil.getCondition(
						frequentItemList, consequent);
				ConfidentAssociationRule confidentAssociationRule = 
						new ConfidentAssociationRule(condition, 
								consequent, confidence);
				confidentAssociationRules.add(confidentAssociationRule);
			}
		}
		
		// i表示后件的元素个数，必须比frequentItemListSize小;
		// 保存i-1个元素的后件的foreConsequents也必须有元素，
		// 因为i个元素的后件必须由它们合并而来
		for(int i = 2; i < frequentItemListSize && 
				foreConsequents.size() > 0; i++) {
			// k个元素的后件，是由k-1个元素的后件合并而来的
			List<List<String>> c1 = this.mergeConsequents(foreConsequents);
			List<List<String>> c2 =  this.tailorConsequents(foreConsequents, c1);
			foreConsequents.clear();
			for(List<String> consequent : c2) {
				float confidence = this.getConfidence(frequentItemsets, 
						frequentItemList, frequentItemListCount, consequent);
				if (confidence >= minconf) {
					foreConsequents.add(consequent);
					List<String> condition = AprioriUtil.getCondition(
							frequentItemList, consequent);
					ConfidentAssociationRule confidentAssociationRule = 
							new ConfidentAssociationRule(condition, 
									consequent, confidence);
					confidentAssociationRules.add(confidentAssociationRule);
				}
			}
		}
		
		return confidentAssociationRules;
	}
	
	/**
	 * 获得只包含一个元素的后件的集合
	 * @param frequentItemList
	 * @return
	 */
	private List<List<String>> getOneConsequents(List<String> frequentItemList) {
		List<List<String>> oneConsequentList = new ArrayList<List<String>>();
		for(String item : frequentItemList) {
			List<String> oneConsequent = new ArrayList<>();
			oneConsequent.add(item);
			oneConsequentList.add(oneConsequent);
		}
		return oneConsequentList;
	}
	
	/**
	 * 获得一个后件的对应的关联规则的支持度
	 * @param frequentItemsets
	 * @param frequentItemList
	 * @param frequentItemListCount
	 * @param consequent
	 * @return
	 */
	private float getConfidence(List<FrequentItemset> frequentItemsets,
			List<String> frequentItemList, int frequentItemListCount,
			List<String> consequent) {
		List<String> condition = AprioriUtil.getCondition(
				frequentItemList, consequent);
		int conditionCount = 0;
		for(FrequentItemset frequentItemset : frequentItemsets) {
			if (frequentItemset.getFrequentItemset().equals(condition)) {
				conditionCount = frequentItemset.getsupportCount();
			}
		}
		float confidence = (float) frequentItemListCount / conditionCount;
		return confidence;		
	}
	
	/**
	 * 组合含k-1个元素的后件为k个元素的后件
	 * @param foreConsequents
	 * @return
	 */
	private List<List<String>> mergeConsequents(List<List<String>> foreConsequents) {
		List<List<String>> c1 = new ArrayList<List<String>>();
		int foreConsequentsSize = foreConsequents.size();
		// 遍历每一个k-1后件项目集，让它们与排在它们后面的后件项目集连线,
		// 从而让所有k-1后件项目集两两配对
		for(int i = 0; i < foreConsequentsSize; i++) {
			List<String> consequent = foreConsequents.get(i);
			for(int j = i + 1; j < foreConsequentsSize; j++) {
				List<String> another = foreConsequents.get(j);
				if (AprioriUtil.isPair(consequent, another)) {
					List<String> consequentAfterMerge = 
							AprioriUtil.mergePairList(consequent, another);
					if (!c1.contains(consequentAfterMerge)) {
						c1.add(consequentAfterMerge);
					}
				}
			}
		}
		return c1;
	}
	
	/**
	 * k项候选后件的任何k-1项子集都必须在
	 * k-1项可信关联规则后件集合里面
	 * @param foreConsequents
	 * @param c1
	 * @return
	 */
	private List<List<String>> tailorConsequents(List<List<String>> foreConsequents,
			List<List<String>> c1) {
		List<List<String>> c2 = new ArrayList<List<String>>();
		for(List<String> itemList : c1) {
			if (AprioriUtil.isRealCandidateConsequent(foreConsequents, itemList)) {
				c2.add(itemList);
			}
		}
		return c2;
	}
	
}
