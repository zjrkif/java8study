package ch05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch04.Dish;

public class Test05 {
	
	public static void main(String[] args) {
		List<String> words = Arrays.asList("Hello", "world");

		List<String> uniqueCharacters = words.stream().map(w -> w.split("")).flatMap(Arrays::stream).distinct()
				.collect(Collectors.toList());
		System.out.println(uniqueCharacters);

		// 重要  flatMap的例子
		List<Integer> numbers1 = Arrays.asList(1, 2, 3);
		List<Integer> numbers2 = Arrays.asList(4, 5, 6);
		List<int[]> numbers = numbers1.stream().flatMap(i -> numbers2.stream().map(j -> new int[] { i, j }))
				.collect(Collectors.toList());

		numbers.stream().forEach(i -> {
			for (int ks : i) {
				System.out.print(ks);
			}
			System.out.println();
		});

		//和能被三整除
		System.out.println("和能被三整除");
		List<int[]> pairs = numbers1.stream()
				.flatMap(i -> numbers2.stream().filter(j -> ((i + j) % 3) == 0).map(j -> new int[] { i, j }))
				.collect(Collectors.toList());
		pairs.stream().forEach(i -> {
			for (int ks : i) {
				System.out.print(ks);
			}
			System.out.println();
		});
		
		Dish.menu.stream().filter(Dish::isVegetarian).findAny().ifPresent(System.out::println);
		
		Stream<int[]> intStream = IntStream.rangeClosed(1,100).boxed().flatMap(a->IntStream.rangeClosed(a, 100).filter(b->Math.sqrt(a*a+b*b)%1 == 0).mapToObj(b->new int[]{a,b,(int)Math.sqrt(a*a+b*b)}));
		intStream.forEach(t->System.out.println("{"+t[0]+","+t[1]+","+t[2]+"}"));
	}
}
