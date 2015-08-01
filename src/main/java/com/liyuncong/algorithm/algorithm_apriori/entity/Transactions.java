package com.liyuncong.algorithm.algorithm_apriori.entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.liyuncong.sort.sort_common.impl.QuickSort;
/**
 * 事务集
 * @author yuncong
 *
 */
public class Transactions {
	private List<List<String>> transactions = new ArrayList<>();
	
	public Transactions(List<List<String>> transactions) {
		for(List<String> items : transactions) {
			String[] itemArray = (String[]) items.toArray();
			new QuickSort().sort(itemArray);
			this.transactions.add(Arrays.asList(itemArray));
		}
	}
	
	/**
	 * 假设：在文件中，一行存储一个事物
	 * @param fileName 存有所有事务的文件的文件名
	 */
	public Transactions(String fileName) {
		// 默认保存事务的文件使用UTF-8字符集
		this(fileName, Charset.forName("UTF-8"));
	}
	
	public Transactions(String fileName, Charset charset) {
		try(InputStream inputStream = new FileInputStream(fileName);
				Reader reader = new InputStreamReader(
						inputStream, charset)) {
			StringBuffer transactaionsBuffer = new StringBuffer();
			char[] cbuf = new char[4096];
			int size = 0;
			while ((size = reader.read(cbuf)) != -1) {
				for(int i = 0; i < size; i++) {
					transactaionsBuffer.append(cbuf[i]);
				}
			}
			String transactaionsStr = transactaionsBuffer.toString();
			// 用换行符分割字符串，得到事务数组
			String[] transactaionsArr = transactaionsStr.split(
					System.lineSeparator());
			for(String itemsStr : transactaionsArr) {
				// 用英文或者中文逗号分隔字符串，得到项的数组
				String[] itemsArr = itemsStr.split("[,，]");
				new QuickSort().sort(itemsArr);
				this.transactions.add(Arrays.asList(itemsArr));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
	}

	public List<List<String>> getTransactions() {
		return transactions;
	}
	
	public int getTransactionsSize() {
		return this.transactions.size();
	}
	
}
