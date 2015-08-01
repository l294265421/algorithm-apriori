package com.liyuncong.algorithm.algorithm_apriori.entity;

import java.util.List;

import org.junit.Test;

public class TestTransactions {
	@Test
	public void test1() {
		String fileName = "transactions.txt";
		Transactions transactions = new Transactions(fileName);
		List<List<String>> t = transactions.getTransactions();
		for(List<String> itemList : t) {
			for(String item : itemList) {
				System.out.println(item);
			}
			System.out.println("........");
		}
	}

}
