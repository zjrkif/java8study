package ch03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Test {
	//Consumer��accept�޷���ֵ
	public static <T> void forEach(List<T> nums,Consumer<T> c){
		for (T t : nums) {
			c.accept(t);
		}
	}
	
	//Predicate��test����boolֵ
	public static <T> List<T> filter(List<T> words,Predicate<T> p){
		List<T> result = new ArrayList<T>();
		for (T s : words) {
			if (p.test(s)) {
				result.add(s);
			}
		}
		return  result;
	}
	
	//Function��apply����һ������R�Ķ���
	public static <T,R> List<R> map(List<T> list,Function<T,R> f){
		List<R> result = new ArrayList<R>();
		for (T t : list) {
			result.add(f.apply(t));
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		List<Integer> nums = Arrays.asList(1,2,3,4,5);
		forEach(nums, (Integer i)->System.out.println(i));
		
		//��ӡ[hello,are handsome]
		List<String> words = Arrays.asList("hello","zjrkif","you","are","handsome");
		List<String> ws = filter(words, (String i)->i.indexOf("e") != -1);
		System.out.println(ws);
		
		//��ӡ[5, 6, 3, 3, 8]
		List<Integer> lengths = map(words, (String i)->i.length());
		System.out.println(lengths);
	}
	
}
