 package com.liyuncong.algorithm.algorithm_apriori.apriori;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.liyuncong.algorithm.algorithm_apriori.entity.ConfidentAssociationRule;
import com.liyuncong.algorithm.algorithm_apriori.entity.FrequentItemset;
import com.liyuncong.algorithm.algorithm_apriori.entity.Transactions;

public class TestApriori {
	@SuppressWarnings("unchecked")
	@Test
	public void testFirstPass() {
		String fileName = "transactions.txt";
		Transactions transactions = new Transactions(fileName);
		// 打印出事务集
		for(List<String> itemList : transactions.getTransactions()) {
			for(String item : itemList) {
				System.out.print(item + "	");
			}
			System.out.println();
		}
		
		float minsup = 0.3f;
		Apriori apriori = new Apriori();
		// 利用反射访问私有方法
		Method firstPassMethod = null;
		try {
			firstPassMethod = Apriori.class.getDeclaredMethod("firstPass", 
					Transactions.class, float.class);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		firstPassMethod.setAccessible(true);
		
		try {
			List<FrequentItemset> oneFrequentItemsets = (List<FrequentItemset>) 
					firstPassMethod.invoke(apriori, transactions, minsup);
			for(FrequentItemset frequentItemset : oneFrequentItemsets) {
				System.out.println("................");
//				List<String> itemList = frequentItemset.getFrequentItemset();
//				for(String item : itemList) {
//					System.out.print(item + "	");
//				}
//				System.out.println(frequentItemset.getsupportCount());
				System.out.println(frequentItemset);
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void testGenerateFrequentItemsets() {
		String fileName = "transactions.txt";
		Transactions transactions = new Transactions(fileName);
		float minsup = 0.3f;
		Apriori apriori = new Apriori();
		List<FrequentItemset> frequentItemsets = apriori.
				generateFrequentItemsets(transactions, minsup);
		for(FrequentItemset frequentItemset : frequentItemsets) {
			System.out.println("....................");
			System.out.println(frequentItemset);
		}
	}
	
	@Test
	public void testGenerateConfidentAssociationRules() {
		String fileName = "transactions.txt";
		Transactions transactions = new Transactions(fileName);
		float minsup = 0.3f;
		Apriori apriori = new Apriori();
		List<FrequentItemset> frequentItemsets = apriori.
				generateFrequentItemsets(transactions, minsup);
		List<ConfidentAssociationRule> confidentAssociationRules = 
				apriori.generateConfidentAssociationRules(frequentItemsets, 0.8f);
		for(ConfidentAssociationRule confidentAssociationRule : 
			confidentAssociationRules) {
			System.out.println("--------------------");
			System.out.println(confidentAssociationRule);
		}
	}
}
