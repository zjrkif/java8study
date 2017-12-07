package ch04;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

	public static void main(String[] args) {
		List<Dish> menu1 = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT), 
				new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER), 
				new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("season fruit", true, 120, Dish.Type.OTHER), 
				new Dish("pizza", true, 550, Dish.Type.OTHER),
				new Dish("prawns", false, 400, Dish.Type.FISH), 
				new Dish("salmon", false, 450, Dish.Type.FISH));

		
		long time1 = System.currentTimeMillis();
		List<String> threeHighCaloricDishNames = menu1.stream().filter(d -> d.getCalories() > 400)
				.sorted((Comparator.comparing(Dish::getCalories)).reversed()).limit(3).map(Dish::getName)
				.filter(s->s.indexOf("p")!=-1).collect(Collectors.toList());
		System.out.println(threeHighCaloricDishNames);
		long time2 = System.currentTimeMillis();
		System.out.println("work is over!" + (time2 - time1) / 60000 + "minutes"
				+ ((time2 - time1) % 60000) / 1000 + "seconds");
	}
}
