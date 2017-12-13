package ch07;

import java.util.function.Function;

public class Test07 {
	// 测量对前n个自然数求和的函数的性能
	
	//这个方法接受一个函数和一个 long 作为参数。它会对传给方法的 long 应用函数10次，记录
	//每次执行的时间（以毫秒为单位），并返回最短的一次执行时间。
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
