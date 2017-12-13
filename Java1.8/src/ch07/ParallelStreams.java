package ch07;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreams {
	public static long sequentialSum(long n) {
//		return Stream.iterate(1L, i -> i + 1).limit(n).reduce(0L, Long::sum);//Âý 787ms
		return LongStream.rangeClosed(1, n).sum();//¿ì 113ms
	}

	public static long iterativeSum(long n) {
		long result = 0;
		for (long i = 1L; i <= n; i++) {
			result += i;
		}
		return result;
	}

	public static long parallelSum(long n) {
//		return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(0L, Long::sum);//Âý2320ms
		return LongStream.rangeClosed(1, n).parallel().sum();//¿ì 29ms
	}
	
	public static long sideEffectSum(long n) {
		Accumulator accumulator = new Accumulator();
		LongStream.rangeClosed(1, n).forEach(accumulator::add);
		return accumulator.total;
	}

	public static long sideEffectParallelSum(long n) {
		Accumulator accumulator = new Accumulator();
		LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
		return accumulator.total;
	}
}

class Accumulator {
	public long total = 0;

	public void add(long value) {
		total += value;
	}
}
