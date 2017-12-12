//stream��collect�÷�
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
		System.out.println("���ٵ��ˣ�" + howManyDishes);

		// ѡ�����·��Ĳ�
		Dish dish;
		Comparator<Dish> dishCaloriesComparator = Comparator.comparing(Dish::getCalories);
		menu.stream().collect(Collectors.maxBy(dishCaloriesComparator)).ifPresent(System.out::println);
		Optional<Dish> maxCaloriesDish = menu.stream().max(Comparator.comparing(Dish::getCalories));
		if (maxCaloriesDish.isPresent()) {
			dish = maxCaloriesDish.get();
			System.out.println(dish.getName());
		}
		int max = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i > j ? i : j));
		System.out.println("���·�" + max);

		// ��ͣ���·��֮�͡�
		int sumCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
		System.out.println("����Dish���У�" + sumCalories + "��·�");
		int sum = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i + j));
		System.out.println("����Dish���У�" + sum + "��·�");
		menu.stream().map(d -> d.getCalories()).reduce(Integer::sum).ifPresent(System.out::println);

		// ��ƽ��ֵ��ƽ����·��
		double avgCalories = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
		System.out.println("ƽ����·��Ϊ��" + avgCalories);

		// һ�����ռ��ϼƣ�ƽ��ֵ�����ֵ����Сֵ
		IntSummaryStatistics menuStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
		System.out.println("�ܿ�·�" + menuStatistics.getSum());
		System.out.println("ƽ����·�" + menuStatistics.getAverage());
		System.out.println("���·�" + menuStatistics.getMax());
		System.out.println("��С��·�" + menuStatistics.getMin());

		// joining������ʹ�ã���������е��ַ���ƴ��������
		String dishNames = menu.stream().map(Dish::getName).collect(Collectors.joining());
		System.out.println("�޷ָ�����" + dishNames);
		String dishNamesWithSeparator = menu.stream().map(Dish::getName).collect(Collectors.joining(","));
		System.out.println("�зָ�����" + dishNamesWithSeparator);
		String dishNamesWithSeparatorAndPS = menu.stream().map(Dish::getName)
				.collect(Collectors.joining(",", "{", "}"));
		System.out.println("�зָ�����ǰ��׺��" + dishNamesWithSeparatorAndPS);

		// ���ղ��ȵ����ͽ��з���:{MEAT=[pork, beef, chicken], OTHER=[french fries, rice,
		// season fruit, pizza], FISH=[prawns, salmon]}
		Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(Collectors.groupingBy(Dish::getType));
		System.out.println(dishesByType);

		// ���տ�·����飺���ȡ���ͨ������
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

		// groupingBy���÷���1���༶���� һ�����ͣ�������·��
		Map<Dish.Type, Map<Dish.caloriesLevel, List<Dish>>> dishesByTypeCaloricLevel = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy(d -> {
					if (d.getCalories() <= 400) {
						return Dish.caloriesLevel.DIET;
					} else if (d.getCalories() <= 700) {
						return Dish.caloriesLevel.NORMAL;
					} else {
						return Dish.caloriesLevel.FAT;
					}
				})));
		System.out.print("�����ͺͿ�·����з��飺");
		System.out.println(dishesByTypeCaloricLevel);

		// groupingBy���÷���2��һ������ Ȼ��ȡÿһ��ĸ���
		Map<Dish.Type, Long> typesCount = menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
		System.out.print("�����ͽ��з��飬ÿһ��ĸ�����");
		System.out.println(typesCount);

		// groupingBy���÷���3��һ������ Ȼ��ȡÿһ������·��
		Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream().collect(
				Collectors.groupingBy(Dish::getType, Collectors.maxBy(Comparator.comparing(Dish::getCalories))));
		System.out.print("�����ͽ��з��飬ÿһ�鿨·�����Ĳ����ǣ�");
		System.out.println(mostCaloricByType);

		// groupingBy���÷���3��һ������ Ȼ��ȡÿһ������·��
		// ��Ҫ
		Map<Dish.Type, Dish> mostCaloricByType2 = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors
				.collectingAndThen(Collectors.maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)));
		System.out.print("�����ͽ��з��飬ÿһ�鿨·�����Ĳ����ǣ�");
		System.out.println(mostCaloricByType2);
		
		Map<Dish.Type, Integer> sumCaloricByType = menu.stream().collect(Collectors.groupingBy(Dish::getType,Collectors.summingInt(Dish::getCalories)));
		System.out.print("�����ͽ��з��飬ÿһ�鿨·���ܺ��ǣ�");
		System.out.println(sumCaloricByType);
		
		//��Ҫ ���Ĺ��캯������
		Map<Dish.Type, Set<Dish.caloriesLevel>> caloricLevelByType = menu.stream().collect(Collectors.groupingBy(Dish::getType,Collectors.mapping(d->{
			if (d.getCalories() <= 400) {
				return Dish.caloriesLevel.DIET;
			} else if (d.getCalories() <= 700) {
				return Dish.caloriesLevel.NORMAL;
			} else {
				return Dish.caloriesLevel.FAT;
			}
		}, Collectors.toCollection(HashSet::new))));
		System.out.println(caloricLevelByType);
		
		System.out.println("==================================================================");
		//����:Collectors.partitioningBy���� ����boolֵ
		//��menu�ֳ���ʳ�ͷ���ʳ���飨ֻ�����飬һ��true��һ��false��
		Map<Boolean, List<Dish>> partitionedMenu = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
		System.out.println(partitionedMenu);
		
		//ȡÿһ������·��Ĳ���
		Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)));
		System.out.print("ÿ�鿨·�����Ĳ����ǣ�");
		System.out.println(mostCaloricPartitionedByVegetarian);
		
		//�ֳ���ʳ�ͷ���ʳ������ٰ��ղ������ͷ���
		Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishByType = menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getType)));
		System.out.println(vegetarianDishByType);
		
	}
}
