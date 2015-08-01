package com.liyuncong.algorithm.algorithm_apriori.entity;

import java.util.List;
/**
 * 频繁项目集
 * @author yuncong
 *
 */
public class FrequentItemset {
	private List<String> frequentItemset;
	private int supportCount;
	
	/**
	 * 判断这个频繁项目集合另一个频繁项目集是不是一对；
	 * 一对的意思就是，它们两长度一样，并且只有最后一
	 * 个元素不一样，可以用来组成一个比它们长度大1的
	 * 候选频繁项目集
	 * @param another
	 * @return
	 */
	public boolean isPairTo(FrequentItemset another) {
		List<String> anotherFrequentItemset = another.getFrequentItemset();
		int anotherFrequentItemsetSize = anotherFrequentItemset.size();
		// 比较长度
		if (anotherFrequentItemsetSize != this.frequentItemset.size()) {
			return false;
		}
		
		// 比较最后一项
		if (this.frequentItemset.get(anotherFrequentItemsetSize - 1).
				compareTo(anotherFrequentItemset.
						get(anotherFrequentItemsetSize - 1)) == 0) {
			return false;
		}
		
		// 比较除最后一项的所有项
		for(int i = 0; i < anotherFrequentItemsetSize - 1; i++) {
			if (this.frequentItemset.get(i).compareTo(
					anotherFrequentItemset.get(i)) != 0) {
					return false;
			}
		}
		
		return true;
	}

	public FrequentItemset(List<String> frequentItemset, int supportCount) {
		super();
		this.frequentItemset = frequentItemset;
		this.supportCount = supportCount;
	}

	public List<String> getFrequentItemset() {
		return frequentItemset;
	}

	public void setFrequentItemset(List<String> frequentItemset) {
		this.frequentItemset = frequentItemset;
	}

	public int getsupportCount() {
		return supportCount;
	}

	public void setsupportCount(int supportCount) {
		this.supportCount = supportCount;
	}

	@Override
	public String toString() {
		return "FrequentItemset [frequentItemset=" + frequentItemset
				+ ", supportCount=" + this.supportCount + "]";
	}

	
}
