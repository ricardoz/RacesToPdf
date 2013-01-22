package onetime;

import java.util.ArrayList;
import java.util.List;

import neo.wrappers.HorseNode;

import com.google.common.base.Function;
import com.google.common.collect.Multimap;

public class TestMaps {

	/**
	 * @param args
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		Function<HorseNode, String> function = new Function<HorseNode, String>(){
			public String apply(HorseNode hn){
				return "";
			}
		};
		
		String s = "";
		
		List<? extends String> list = getAllRecords(s.getClass());

	}
	
	static <T> List<T> getAllRecords(Class<T> cl) throws InstantiationException, IllegalAccessException 
    {
        T inst=cl.newInstance();
        List<T> list=new ArrayList<T>();
       
        return list;
    }


}
