//stream的collect用法
package ch06;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import ch04.Dish;
import ch04.Dish.caloriesLevel;

public class Test06 {
	public static void main(String[] args) {
		List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
				new Dish("beef", false, 700, Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT),
				new Dish("french fries", true, 530, Dish.Type.OTHER), new Dish("rice", true, 350, Dish.Type.OTHER),
				new Dish("season fruit", true, 120, Dish.Type.OTHER), new Dish("pizza", true, 550, Dish.Type.OTHER),
				new Dish("prawns", false, 400, Dish.Type.FISH), new Dish("salmon", false, 450, Dish.Type.FISH));

		// long howManyDishes = menu.stream().collect(Collectors.counting());
		long howManyDishes = menu.stream().count();
		System.out.println("多少道菜：" + howManyDishes);

		// 选出最大卡路里的菜
		Dish dish;
		Comparator<Dish> dishCaloriesComparator = Comparator.comparing(Dish::getCalories);
		menu.stream().collect(Collectors.maxBy(dishCaloriesComparator)).ifPresent(System.out::println);
		Optional<Dish> maxCaloriesDish = menu.stream().max(Comparator.comparing(Dish::getCalories));
		if (maxCaloriesDish.isPresent()) {
			dish = maxCaloriesDish.get();
			System.out.println(dish.getName());
		}
		int max = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i > j ? i : j));
		System.out.println("最大卡路里：" + max);

		// 求和，卡路里之和。
		int sumCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
		System.out.println("所有Dish共有：" + sumCalories + "卡路里。");
		int sum = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i + j));
		System.out.println("所有Dish共有：" + sum + "卡路里。");
		menu.stream().map(d -> d.getCalories()).reduce(Integer::sum).ifPresent(System.out::println);

		// 求平均值，平均卡路里
		double avgCalories = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
		System.out.println("平均卡路里为：" + avgCalories);

		// 一次性收集合计，平均值，最大值，最小值
		IntSummaryStatistics menuStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
		System.out.println("总卡路里：" + menuStatistics.getSum());
		System.out.println("平均卡路里：" + menuStatistics.getAverage());
		System.out.println("最大卡路里：" + menuStatistics.getMax());
		System.out.println("最小卡路里：" + menuStatistics.getMin());

		// joining方法的使用，它会把流中的字符串拼接起来。
		String dishNames = menu.stream().map(Dish::getName).collect(Collectors.joining());
		System.out.println("无分隔符：" + dishNames);
		String dishNamesWithSeparator = menu.stream().map(Dish::getName).collect(Collectors.joining(","));
		System.out.println("有分隔符：" + dishNamesWithSeparator);
		String dishNamesWithSeparatorAndPS = menu.stream().map(Dish::getName)
				.collect(Collectors.joining(",", "{", "}"));
		System.out.println("有分隔符和前后缀：" + dishNamesWithSeparatorAndPS);

		// 按照菜肴的类型进行分组:{MEAT=[pork, beef, chicken], OTHER=[french fries, rice,
		// season fruit, pizza], FISH=[prawns, salmon]}
		Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(Collectors.groupingBy(Dish::getType));
		System.out.println(dishesByType);

		// 按照卡路里分组：低热、普通、高热
		Map<Dish.caloriesLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(Collectors.groupingBy(d -> {
			if (d.getCalories() <= 400) {
				return Dish.caloriesLevel.DIET;
			} else if (d.getCalories() <= 700) {
				return Dish.caloriesLevel.NORMAL;
			} else {
				return Dish.caloriesLevel.FAT;
			}
		}));
		System.out.println(dishesByCaloricLevel);

		// groupingBy的用法：1、多级分组 一级类型，二级卡路里
		Map<Dish.Type, Map<Dish.caloriesLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy(Dish::getCaloricLevel)));
		System.out.print("以类型和卡路里进行分组：");
		System.out.println(dishesByTypeCaloricLevel);

		// groupingBy的用法：2、一级分组 然后取每一组的个数
		Map<Dish.Type, Long> typesCount = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
		System.out.print("以类型进行分组，每一组的个数：");
		System.out.println(typesCount);

		// groupingBy的用法：3、一级分组 然后取每一组的最大卡路里
		Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream().collect(
				Collectors.groupingBy(Dish::getType, Collectors.maxBy(Comparator.comparing(Dish::getCalories))));
		System.out.print("以类型进行分组，每一组卡路里最大的菜肴是：");
		System.out.println(mostCaloricByType);

		// groupingBy的用法：3、一级分组 然后取每一组的最大卡路里
		// 重要
		Map<Dish.Type, Dish> mostCaloricByType2 = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors
				.collectingAndThen(Collectors.maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)));
		System.out.print("以类型进行分组，每一组卡路里最大的菜肴是：");
		System.out.println(mostCaloricByType2);

		Map<Dish.Type, Integer> sumCaloricByType = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.summingInt(Dish::getCalories)));
		System.out.print("以类型进行分组，每一组卡路里总和是：");
		System.out.println(sumCaloricByType);

		// 重要 最后的构造函数引用
		Map<Dish.Type, Set<Dish.caloriesLevel>> caloricLevelByType = menu.stream().collect(Collectors.groupingBy(
				Dish::getType, Collectors.mapping(Dish::getCaloricLevel, Collectors.toCollection(HashSet::new))));
		System.out.println(caloricLevelByType);

		System.out.println("==================================================================");
		// 分区:Collectors.partitioningBy方法 返回bool值
		// 将menu分成素食和非素食两组（只有两组，一组true，一组false）
		Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
		System.out.println(partitionedMenu);

		// 取每一组的最大卡路里的菜肴
		Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = menu.stream()
				.collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors
						.collectingAndThen(Collectors.maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)));
		System.out.print("每组卡路里最大的菜肴是：");
		System.out.println(mostCaloricPartitionedByVegetarian);

		// 分成素食和非素食两组后，再按照菜肴类型分组
		Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishByType = menu.stream()
				.collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getType)));
		System.out.println(vegetarianDishByType);

	}
}
