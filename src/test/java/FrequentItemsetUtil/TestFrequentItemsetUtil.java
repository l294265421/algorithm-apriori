package FrequentItemsetUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.liyuncong.algorithm.algorithm_apriori.entity.FrequentItemset;
import com.liyuncong.algorithm.algorithm_apriori.util.FrequentItemsetUtil;

public class TestFrequentItemsetUtil {
	@Test
	public void testMerge() {
		List<String> f1 = new ArrayList<>();
		f1.add("lierwang");
		f1.add("liyuncong");
		FrequentItemset frequentItemset = new FrequentItemset(f1, 0);
		
		List<String> f2 = new ArrayList<>();
		f2.add("lierwang");
		f2.add("wangfang");
		FrequentItemset frequentItemset2 = new FrequentItemset(f2, 0);
		System.out.println(FrequentItemsetUtil.mergePairFrequentItemset(frequentItemset, frequentItemset2));
	}
}
