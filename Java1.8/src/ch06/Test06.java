//stream的collect用法
package ch06;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import ch04.Dish;

public class Test06 {
	public static void main(String[] args) {
		List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT), 
				new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER), 
				new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("season fruit", true, 120, Dish.Type.OTHER), 
				new Dish("pizza", true, 550, Dish.Type.OTHER),
				new Dish("prawns", false, 400, Dish.Type.FISH), 
				new Dish("salmon", false, 450, Dish.Type.FISH));
		
		//long howManyDishes = menu.stream().collect(Collectors.counting());
		long howManyDishes = menu.stream().count();
		System.out.println("多少道菜："+howManyDishes);
		
		//选出最大卡路里的菜
		Dish dish; 
		Comparator<Dish> dishCaloriesComparator = Comparator.comparing(Dish::getCalories);
		menu.stream().collect(Collectors.maxBy(dishCaloriesComparator)).ifPresent(System.out::println);
		Optional<Dish> maxCaloriesDish = menu.stream().max(Comparator.comparing(Dish::getCalories));
		if (maxCaloriesDish.isPresent()) {
			dish=maxCaloriesDish.get();
			System.out.println(dish.getName());
		}
		int max = menu.stream().collect(Collectors.reducing(0,Dish::getCalories,(i,j)->i>j?i:j));
		System.out.println("最大卡路里："+max);
		
		//求和，卡路里之和。
		int sumCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
		System.out.println("所有Dish共有："+sumCalories+"卡路里。");
		int sum = menu.stream().collect(Collectors.reducing(0,Dish::getCalories,(i,j)->i+j));
		System.out.println("所有Dish共有："+sum+"卡路里。");
		menu.stream().map(d->d.getCalories()).reduce(Integer::sum).ifPresent(System.out::println);
		
		//求平均值，平均卡路里
		double avgCalories = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
		System.out.println("平均卡路里为："+avgCalories);
		
		//一次性收集合计，平均值，最大值，最小值
		IntSummaryStatistics menuStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
		System.out.println("总卡路里："+menuStatistics.getSum());
		System.out.println("平均卡路里："+menuStatistics.getAverage());
		System.out.println("最大卡路里："+menuStatistics.getMax());
		System.out.println("最小卡路里："+menuStatistics.getMin());
		
		//joining方法的使用，它会把流中的字符串拼接起来。
		String dishNames = menu.stream().map(Dish::getName).collect(Collectors.joining());
		System.out.println("无分隔符："+dishNames);
		String dishNamesWithSeparator = menu.stream().map(Dish::getName).collect(Collectors.joining(","));
		System.out.println("有分隔符："+dishNamesWithSeparator);
		String dishNamesWithSeparatorAndPS = menu.stream().map(Dish::getName).collect(Collectors.joining(",","{","}"));
		System.out.println("有分隔符和前后缀："+dishNamesWithSeparatorAndPS);
	}
}
