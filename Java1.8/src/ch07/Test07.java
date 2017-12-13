package ch07;

import java.util.function.Function;

public class Test07 {
	// ������ǰn����Ȼ����͵ĺ���������
	
	//�����������һ��������һ�� long ��Ϊ����������Դ��������� long Ӧ�ú���10�Σ���¼
	//ÿ��ִ�е�ʱ�䣨�Ժ���Ϊ��λ������������̵�һ��ִ��ʱ�䡣
	public static long measureSumPerf(Function<Long, Long> adder, long n) {
		long fastest = Long.MAX_VALUE;
		for (int i = 0; i < 10; i++) {
			long start = System.nanoTime();
			long sum = adder.apply(n);
			long duration = (System.nanoTime() - start) / 1_000_000;
			System.out.println("Result: " + sum);
			if (duration < fastest)
				fastest = duration;
		}
		return fastest;
	}

	public static void main(String[] args) {
//		
//		System.out.println("Sequential sum done in:" + measureSumPerf(ParallelStreams::sequentialSum, 10_000_000) + " msecs");
//		System.out.println("===========================================");
//		System.out.println("Iterative sum done in:" + measureSumPerf(ParallelStreams::iterativeSum, 10_000_000) + " msecs");
//		System.out.println("===========================================");
//		System.out.println("Parallel sum done in: " + measureSumPerf(ParallelStreams::parallelSum, 10_000_000) + " msecs" );
	
		
		System.out.println("SideEffect parallel sum done in: " + Test07.measureSumPerf(ParallelStreams::sideEffectSum, 10_000_000L) +"msecs" );
	}
}
