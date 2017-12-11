package ch05;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Test05_2 {
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(21,4526,4526,123,1123,73,5);
		
		//�����ֵ��
		int max = numbers.stream().reduce(0,Integer::max);
		System.out.println(max);
		
		//�����ֵ���޳�ʼֵ���֣�
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
		//2011�귢�������н��ף������׶�����
		List<Transaction> traderIn2011 = transactions.stream().filter(t->t.getYear()==2011).sorted(Comparator.comparing(Transaction::getValue)).collect(Collectors.toList());
		System.out.println(traderIn2011);
		//����Ա������Щ��ͬ���й�����
		List<String> cities = transactions.stream().map(t->t.getTrader().getCity()).distinct().collect(Collectors.toList());
		System.out.println(cities);
		//�����������������ڽ��ŵĽ���Ա��������������
		List<Trader> traders = transactions.stream().map(t->t.getTrader()).filter(t->t.getCity().equalsIgnoreCase("Cambriage")).distinct().sorted(Comparator.comparing(Trader::getName)).collect(Collectors.toList());
		System.out.println(traders);
		//�������н���Ա�������ַ�����������ĸ˳������
		List<String> tradersName = transactions.stream().map(t->t.getTrader()).filter(t->t.getCity().equalsIgnoreCase("Cambriage")).distinct().sorted(Comparator.comparing(Trader::getName)).map(Trader::getName).collect(Collectors.toList());
		System.out.println(tradersName);
		//��û�н���Ա��������������
		Boolean traderInMilan = transactions.stream().anyMatch(t->t.getTrader().getCity().equalsIgnoreCase("milan"));
		System.out.println(traderInMilan?"��":"û��");
		//��ӡ�����ڽ��ŵĽ���Ա�����н��׶�
		transactions.stream().filter(t->t.getTrader().getCity().equalsIgnoreCase("milan")).map(Transaction::getValue).forEach(System.out::println);
		//���н��׶��У���ߵĽ��׶��Ƕ��٣�
		int hightestValue = transactions.stream().map(Transaction::getValue).reduce(0,Integer::max);
		System.out.println(hightestValue);
		//�ҵ����׶���С�Ľ���
		transactions.stream().map(Transaction::getValue).reduce(Integer::min).ifPresent(System.out::println);
	}
}
