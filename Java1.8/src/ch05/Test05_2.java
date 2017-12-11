package ch05;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

public class Test05_2 {
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(21, 4526, 4526, 123, 1123, 73, 5);

		// 求最大值。
		int max = numbers.stream().reduce(0, Integer::max);
		System.out.println(max);

		// 求最大值（无初始值变种）
		Optional<Integer> oMax = numbers.stream().reduce(Integer::max);
		oMax.ifPresent(System.out::println);

		Trader raoul = new Trader("Raoul", "Cambriage");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambriage");
		Trader brian = new Trader("Brian", "Cambriage");

		List<Transaction> transactions = Arrays.asList(new Transaction(brian, 2011, 300),
				new Transaction(raoul, 2012, 1000), new Transaction(raoul, 2011, 400),
				new Transaction(mario, 2012, 710), new Transaction(raoul, 2012, 700), new Transaction(alan, 2012, 950));
		// 2011年发生的所有交易，按交易额排序
		List<Transaction> traderIn2011 = transactions.stream().filter(t -> t.getYear() == 2011)
				.sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
		System.out.println(traderIn2011);
		// 交易员都在哪些不同城市工作过
		List<String> cities = transactions.stream().map(t -> t.getTrader().getCity()).distinct()
				.collect(Collectors.toList());
		System.out.println(cities);
		// 查找来自所有来自于剑桥的交易员，并按姓名排序
		List<Trader> traders = transactions.stream().map(t -> t.getTrader())
				.filter(t -> t.getCity().equalsIgnoreCase("Cambriage")).distinct()
				.sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
		System.out.println(traders);
		// 返回所有交易员的姓名字符串，并按字母顺序排序
		List<String> tradersName = transactions.stream().map(t -> t.getTrader())
				.filter(t -> t.getCity().equalsIgnoreCase("Cambriage")).distinct()
				.sorted(Comparator.comparing(Trader::getName)).map(Trader::getName).collect(Collectors.toList());
		System.out.println(tradersName);
		// 有没有交易员是在米兰工作的
		Boolean traderInMilan = transactions.stream().anyMatch(t -> t.getTrader().getCity().equalsIgnoreCase("milan"));
		System.out.println(traderInMilan ? "有" : "没有");
		// 打印生活在剑桥的交易员的所有交易额
		transactions.stream().filter(t -> t.getTrader().getCity().equalsIgnoreCase("milan")).map(Transaction::getValue)
				.forEach(System.out::println);
		// 所有交易额中，最高的交易额是多少？
		transactions.stream().mapToInt(Transaction::getValue).max().ifPresent(System.out::println);
		// 找到交易额最小的交易
		transactions.stream().mapToInt(Transaction::getValue).min().ifPresent(System.out::println);

		// IntStream和LongStream都包含range和rangeclosed方法可以生成一个数值范围，range不包含结束，rangeclosed包含结束数值。
		IntStream.rangeClosed(1, 100).filter(i -> i % 2 == 0).forEach(System.out::println);

		//找勾股数
		Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
				.flatMap(a -> IntStream.rangeClosed(a, 100).filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed()
						.map(b -> new int[] { a, b, (int) Math.sqrt(a * a + b * b) }));
		pythagoreanTriples.forEach(t -> System.out.println("{" + t[0] + "," + t[1] + "," + t[2] + "}"));

		// 通过stream.of显示的创建一个stream
		Stream<String> stringStream = Stream.of("hello", "zjrkif", "you", "are", "handsome");
		stringStream.map(s -> s.toUpperCase()).forEach(System.out::println);
		
		int[] intNumbers = {1,2,3,4,5};
		int sum = Arrays.stream(intNumbers).sum();
		System.out.println(sum);

	}
}
