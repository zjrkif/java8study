package ch03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


public class Test1 {
	public static <T> List<T> filter(List<T> list,Predicate<T> p){
		List<T> result = new ArrayList<T>();
		for (T t : list) {
			if (p.test(t)) {
				result.add(t);
			}
		}
		return result;
	}
	
	public static <T> void forEach(List<T> list,Consumer<T> c){
		for (T t : list) {
			c.accept(t);
		}
	}
	
	public static <T,R> List<R> map(List<T> list,Function<T, R> f){
		List<R> result = new ArrayList<R>();
		for (T t : list) {
			result.add(f.apply(t));
		}
		return result;
	}
	
	public static List<String> map2(List<String> list,BiFunction<String, String,Integer> f){
		List<String> result = new ArrayList<String>();
		for (String t : list) {
			if (f.apply(t,"e") != -1) {
				result.add(t);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		List<Integer> nums = Arrays.asList(1,2,3,4,5);
		forEach(nums, i->System.out.print(i));
		System.out.println();
		
		List<String> words = Arrays.asList("hello","zjrkif","you","are","handsome");
		List<String> hasE = filter(words, s->s.indexOf("e")!=-1);
		System.out.println(hasE);
		
		//BiFunction接受两个参数，返回一个参数
		BiFunction<String,String,Integer> aa = String::indexOf;
		List<String> hasE2 = map2(words,aa);
		System.out.println(hasE2);
		
		List<Integer> wordLength = map(words, String::length);
		System.out.println(wordLength);
		
		Thread t1 = new Thread(()->System.out.println("1"));
		t1.start();
		
		List<String> str = Arrays.asList("a","b","A","B");
		str.sort(String::compareToIgnoreCase);
		System.out.println(str);
		
		BiPredicate<List<String>, String> saa = List::contains;
	}
}
