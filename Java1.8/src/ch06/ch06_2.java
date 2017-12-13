package ch06;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ch06_2 {
	public Boolean isPrime(int number){
		int numberRoot = (int)Math.sqrt((double)number);
		return IntStream.range(2, numberRoot).noneMatch(i->number%i==0);
	}
	public Map<Boolean, List<Integer>> numbersGroupByPrime(int number){
		//parallel并行
		return IntStream.rangeClosed(2, number).boxed().parallel().collect(Collectors.partitioningBy(n->isPrime(n)));
	}
	public static void main(String[] args) {
		long time1 = System.currentTimeMillis();
		Map<Boolean, List<Integer>> numbers = new ch06_2().numbersGroupByPrime(9000000);
		long time2 = System.currentTimeMillis();
		System.out.println("work is over!" + (time2 - time1) / 60000 + "minutes"
				+ ((time2 - time1) % 60000) / 1000 + "seconds");
//		System.out.println("20内的非质数："+numbers.get(false));
//		System.out.println("20内的质数："+numbers.get(true));
	}
}
