//stream��collect�÷�
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
		System.out.println("���ٵ��ˣ�"+howManyDishes);
		
		//ѡ�����·��Ĳ�
		Dish dish; 
		Comparator<Dish> dishCaloriesComparator = Comparator.comparing(Dish::getCalories);
		menu.stream().collect(Collectors.maxBy(dishCaloriesComparator)).ifPresent(System.out::println);
		Optional<Dish> maxCaloriesDish = menu.stream().max(Comparator.comparing(Dish::getCalories));
		if (maxCaloriesDish.isPresent()) {
			dish=maxCaloriesDish.get();
			System.out.println(dish.getName());
		}
		int max = menu.stream().collect(Collectors.reducing(0,Dish::getCalories,(i,j)->i>j?i:j));
		System.out.println("���·�"+max);
		
		//��ͣ���·��֮�͡�
		int sumCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
		System.out.println("����Dish���У�"+sumCalories+"��·�");
		int sum = menu.stream().collect(Collectors.reducing(0,Dish::getCalories,(i,j)->i+j));
		System.out.println("����Dish���У�"+sum+"��·�");
		menu.stream().map(d->d.getCalories()).reduce(Integer::sum).ifPresent(System.out::println);
		
		//��ƽ��ֵ��ƽ����·��
		double avgCalories = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
		System.out.println("ƽ����·��Ϊ��"+avgCalories);
		
		//һ�����ռ��ϼƣ�ƽ��ֵ�����ֵ����Сֵ
		IntSummaryStatistics menuStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
		System.out.println("�ܿ�·�"+menuStatistics.getSum());
		System.out.println("ƽ����·�"+menuStatistics.getAverage());
		System.out.println("���·�"+menuStatistics.getMax());
		System.out.println("��С��·�"+menuStatistics.getMin());
		
		//joining������ʹ�ã���������е��ַ���ƴ��������
		String dishNames = menu.stream().map(Dish::getName).collect(Collectors.joining());
		System.out.println("�޷ָ�����"+dishNames);
		String dishNamesWithSeparator = menu.stream().map(Dish::getName).collect(Collectors.joining(","));
		System.out.println("�зָ�����"+dishNamesWithSeparator);
		String dishNamesWithSeparatorAndPS = menu.stream().map(Dish::getName).collect(Collectors.joining(",","{","}"));
		System.out.println("�зָ�����ǰ��׺��"+dishNamesWithSeparatorAndPS);
	}
}
