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

		// �����ֵ��
		int max = numbers.stream().reduce(0, Integer::max);
		System.out.println(max);

		// �����ֵ���޳�ʼֵ���֣�
		Optional<Integer> oMax = numbers.stream().reduce(Integer::max);
		oMax.ifPresent(System.out::println);

		Trader raoul = new Trader("Raoul", "Cambriage");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambriage");
		Trader brian = new Trader("Brian", "Cambriage");

		List<Transaction> transactions = Arrays.asList(new Transaction(brian, 2011, 300),
				new Transaction(raoul, 2012, 1000), new Transaction(raoul, 2011, 400),
				new Transaction(mario, 2012, 710), new Transaction(raoul, 2012, 700), new Transaction(alan, 2012, 950));
		// 2011�귢�������н��ף������׶�����
		List<Transaction> traderIn2011 = transactions.stream().filter(t -> t.getYear() == 2011)
				.sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
		System.out.println(traderIn2011);
		// ����Ա������Щ��ͬ���й�����
		List<String> cities = transactions.stream().map(t -> t.getTrader().getCity()).distinct()
				.collect(Collectors.toList());
		System.out.println(cities);
		// �����������������ڽ��ŵĽ���Ա��������������
		List<Trader> traders = transactions.stream().map(t -> t.getTrader())
				.filter(t -> t.getCity().equalsIgnoreCase("Cambriage")).distinct()
				.sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
		System.out.println(traders);
		// �������н���Ա�������ַ�����������ĸ˳������
		List<String> tradersName = transactions.stream().map(t -> t.getTrader())
				.filter(t -> t.getCity().equalsIgnoreCase("Cambriage")).distinct()
				.sorted(Comparator.comparing(Trader::getName)).map(Trader::getName).collect(Collectors.toList());
		System.out.println(tradersName);
		// ��û�н���Ա��������������
		Boolean traderInMilan = transactions.stream().anyMatch(t -> t.getTrader().getCity().equalsIgnoreCase("milan"));
		System.out.println(traderInMilan ? "��" : "û��");
		// ��ӡ�����ڽ��ŵĽ���Ա�����н��׶�
		transactions.stream().filter(t -> t.getTrader().getCity().equalsIgnoreCase("milan")).map(Transaction::getValue)
				.forEach(System.out::println);
		// ���н��׶��У���ߵĽ��׶��Ƕ��٣�
		transactions.stream().mapToInt(Transaction::getValue).max().ifPresent(System.out::println);
		// �ҵ����׶���С�Ľ���
		transactions.stream().mapToInt(Transaction::getValue).min().ifPresent(System.out::println);

		// IntStream��LongStream������range��rangeclosed������������һ����ֵ��Χ��range������������rangeclosed����������ֵ��
		IntStream.rangeClosed(1, 100).filter(i -> i % 2 == 0).forEach(System.out::println);

		//�ҹ�����
		Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100).boxed()
				.flatMap(a -> IntStream.rangeClosed(a, 100).filter(b -> Math.sqrt(a * a + b * b) % 1 == 0).boxed()
						.map(b -> new int[] { a, b, (int) Math.sqrt(a * a + b * b) }));
		pythagoreanTriples.forEach(t -> System.out.println("{" + t[0] + "," + t[1] + "," + t[2] + "}"));

		// ͨ��stream.of��ʾ�Ĵ���һ��stream
		Stream<String> stringStream = Stream.of("hello", "zjrkif", "you", "are", "handsome");
		stringStream.map(s -> s.toUpperCase()).forEach(System.out::println);
		
		int[] intNumbers = {1,2,3,4,5};
		int sum = Arrays.stream(intNumbers).sum();
		System.out.println(sum);

	}
}
