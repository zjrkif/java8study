package ch05;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Test05_2 {
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(21,4526,4526,123,1123,73,5);
		
		//求最大值。
		int max = numbers.stream().reduce(0,Integer::max);
		System.out.println(max);
		
		//求最大值（无初始值变种）
		Optional<Integer> oMax = numbers.stream().reduce(Integer::max);
		oMax.ifPresent(System.out::println);

		
		Trader raoul = new Trader("Raoul", "Cambriage");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambriage");
		Trader brian = new Trader("Brian", "Cambriage");
		
		List<Transaction> transactions = Arrays.asList(
				new Transaction(brian, 2011, 300),
				new Transaction(raoul, 2012, 1000),
				new Transaction(raoul, 2011, 400),
				new Transaction(mario, 2012, 710),
				new Transaction(raoul, 2012, 700),
				new Transaction(alan, 2012, 950)
				);
		//2011年发生的所有交易，按交易额排序
		List<Transaction> traderIn2011 = transactions.stream().filter(t->t.getYear()==2011).sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
		System.out.println(traderIn2011);
		//交易员都在哪些不同城市工作过
		List<String> cities = transactions.stream().map(t->t.getTrader().getCity()).distinct().collect(Collectors.toList());
		System.out.println(cities);
		//查找来自所有来自于剑桥的交易员，并按姓名排序
		List<Trader> traders = transactions.stream().map(t->t.getTrader()).filter(t->t.getCity().equalsIgnoreCase("Cambriage")).distinct().sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
		System.out.println(traders);
		//返回所有交易员的姓名字符串，并按字母顺序排序
		List<String> tradersName = transactions.stream().map(t->t.getTrader()).filter(t->t.getCity().equalsIgnoreCase("Cambriage")).distinct().sorted(Comparator.comparing(Trader::getName)).map(Trader::getName).collect(Collectors.toList());
		System.out.println(tradersName);
		//有没有交易员是在米兰工作的
		Boolean traderInMilan = transactions.stream().anyMatch(t->t.getTrader().getCity().equalsIgnoreCase("milan"));
		System.out.println(traderInMilan?"有":"没有");
		//打印生活在剑桥的交易员的所有交易额
		transactions.stream().filter(t->t.getTrader().getCity().equalsIgnoreCase("milan")).map(Transaction::getValue).forEach(System.out::println);
		//所有交易额中，最高的交易额是多少？
		int hightestValue = transactions.stream().map(Transaction::getValue).reduce(0,Integer::max);
		System.out.println(hightestValue);
		//找到交易额最小的交易
		transactions.stream().map(Transaction::getValue).reduce(Integer::min).ifPresent(System.out::println);
	}
}
