package my.guava.collect;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
/**
 * A bimap (or "bidirectional map") is a map that preserves the uniqueness of its values as well as that of its keys. 
 * This constraint enables bimaps to support an "inverse view", which is another bimap containing the same entries as 
 * this bimap but with reversed keys and values. 
 * 
 * @Description:  
 * @author: Binke Zhang
 * @date:   2017年11月15日 下午6:49:22   
 *     
 * @Copyright: 2017 www.cdsunrise.net Inc. All rights reserved.
 */
public class BiMapTest {
	public static void main(String[] args) {
		BiMap<Integer, String> empIDNameMap = HashBiMap.create();

		empIDNameMap.put(new Integer(101), "Mahesh");
		empIDNameMap.put(new Integer(102), "Sohan");
		empIDNameMap.put(new Integer(103), "Ramesh");

		for (BiMap.Entry<Integer, String>  e : empIDNameMap.entrySet()) {
			System.out.println(e.getKey());
			System.out.println(e.getValue());
		}
		// Emp Id of Employee "Mahesh"
		System.out.println(empIDNameMap.inverse().get("Mahesh"));
	}
}
