package org.openyu.commons.util;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import org.openyu.commons.junit.supporter.BaseTestSupporter;

public class SerializeHelperTest extends BaseTestSupporter {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	public static LinkedList<String> mockLinkedList() {
		LinkedList<String> result = new LinkedList<String>();
		result.add("TEST_STRING");
		result.add("測試字串");
		result.add(new String(new byte[307200]));// 300k
		return result;
	}

	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	@Test
	public void getInstance() {
		// SerializeHelper.getInstance();
		List<String> value = mockLinkedList();
		SerializeHelper.serialize(value);
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// round: 1.29, GC: 80
	public void serialize() {
		List<String> value = mockLinkedList();
		byte[] result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.serialize(value);
		}
		//
		System.out.println(result.length + " ," + result);// 614,486 bytes
		System.out.println(new String(result));
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test(expected = IllegalArgumentException.class)
	public void serializeException() {
		List<String> value = mockLinkedList();
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			SerializeHelper.serialize(value, null);
		}
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// round: 1.14, GC: 77
	public void deserialize() {
		List<String> list = mockLinkedList();
		byte[] value = SerializeHelper.serialize(list);

		List<String> result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.deserialize(value);
		}
		//
		System.out.println(result);
		assertCollectionEquals(list, result);
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// fstConfiguration.getObjectOutput
	// round: 0.29, GC: 17
	public void ___fst2() {
		LinkedList<String> value = mockLinkedList();
		byte[] result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.___fst2(value);
		}
		//
		System.out.println(result.length + " ," + result);// 307,239 bytes
		System.out.println(new String(result));
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// fstConfiguration.getObjectInput
	// round: 0.39, GC: 17
	public void ___defst2() {
		LinkedList<String> list = mockLinkedList();
		byte[] value = SerializeHelper.___fst2(list);

		List<String> result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.___defst2(value);
		}
		//
		System.out.println(result);
		assertCollectionEquals(list, result);
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// fstObjectOutputCacheFactory
	// round: 0.25, GC: 17
	public void fst() {
		LinkedList<String> value = mockLinkedList();
		byte[] result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.fst(value);
		}
		//
		System.out.println(result.length + " ," + result);// 307,239 bytes
		System.out.println(new String(result));
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// fstObjectInputCacheFactory
	// round: 0.53, GC: 17
	public void defst() {
		LinkedList<String> list = mockLinkedList();
		byte[] value = SerializeHelper.fst(list);

		List<String> result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.defst(value);
		}
		//
		System.out.println(result);
		assertCollectionEquals(list, result);
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// round: 1.28, GC: 80
	public void jgroup() {
		LinkedList<String> value = mockLinkedList();
		byte[] result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.jgroup(value);
		}
		//
		System.out.println(result.length + " ," + result);// 614,487 bytes
		System.out.println(new String(result));
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// round: 1.11, GC: 77
	public void dejgroup() {
		LinkedList<String> list = mockLinkedList();
		byte[] value = SerializeHelper.jgroup(list);

		List<String> result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.dejgroup(value);
		}
		//
		System.out.println(result);
		assertCollectionEquals(list, result);
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// kryoCacheFactory
	// round: 0.70, GC: 38
	public void kryo() {
		List<String> value = mockLinkedList();
		byte[] result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.kryo(value);
		}
		//
		System.out.println(result.length + " ," + result);// 307,235 bytes
		System.out.println(new String(result));
	}

	@SuppressWarnings("unchecked")
	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// kryoCacheFactory
	// round: 0.55, GC: 35
	public void dekryo() {
		List<String> list = mockLinkedList();
		byte[] value = SerializeHelper.kryo(list);

		List<String> result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.dekryo(value, LinkedList.class);
		}
		//
		System.out.println(result);
		assertCollectionEquals(list, result);
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// round: 10.91, GC: 205
	public void jackson() {
		LinkedList<String> value = mockLinkedList();
		byte[] result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.jackson(value);
		}
		//
		System.out.println(result.length + " ," + result);// 1,843,233 bytes
		System.out.println(new String(result));
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// round: 3.19, GC: 50
	public void dejackson() {
		LinkedList<String> list = mockLinkedList();
		byte[] value = SerializeHelper.jackson(list);

		List<String> result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.dejackson(value, LinkedList.class);
		}
		//
		System.out.println(result);
		assertCollectionEquals(list, result);
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// round: 0.44, GC: 33
	public void smile() {
		LinkedList<String> value = mockLinkedList();
		byte[] result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.smile(value);
		}
		//
		System.out.println(result.length + " ," + result);// 307,233 bytes
		System.out.println(new String(result));
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// round: 0.52, GC: 49
	public void desmile() {
		LinkedList<String> list = mockLinkedList();
		byte[] value = SerializeHelper.smile(list);

		List<String> result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.desmile(value, LinkedList.class);
		}
		//
		System.out.println(result);
		assertCollectionEquals(list, result);
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// round: 0.40, GC: 37
	public void smileJaxrs() {
		LinkedList<String> value = mockLinkedList();
		byte[] result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.smileJaxrs(value);
		}
		//
		System.out.println(result.length + " ," + result);// 307,233 bytes
		System.out.println(new String(result));
	}

	@BenchmarkOptions(benchmarkRounds = 3, warmupRounds = 2, concurrency = 1)
	@Test
	// round: 0.54, GC: 57
	public void desmileJaxrs() {
		LinkedList<String> list = mockLinkedList();
		byte[] value = SerializeHelper.smileJaxrs(list);

		List<String> result = null;
		//
		int count = 500;
		for (int i = 0; i < count; i++) {
			result = SerializeHelper.desmileJaxrs(value, LinkedList.class);
		}
		//
		System.out.println(result);
		assertCollectionEquals(list, result);
	}

	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 1, concurrency = 100)
	@Test
	// round: 0.59 [+- 0.07], round.block: 0.38 [+- 0.05], round.gc: 0.00 [+-
	// 0.00], GC.calls: 3, GC.time: 0.03, time.total: 0.64, time.warmup: 0.00,
	// time.bench: 0.64
	public void serializeWithProcessor() {
		LinkedList<String> value = mockLinkedList();
		byte[] result = null;
		//
		result = SerializeHelper.serializeWithProcessor(value);
		//
		System.out.println(result.length + " ," + result);// 307235
		assertEquals(307235, result.length);
	}

	@BenchmarkOptions(benchmarkRounds = 100, warmupRounds = 1, concurrency = 100)
	@Test
	// round: 0.67 [+- 0.07], round.block: 0.46 [+- 0.06], round.gc: 0.00 [+-
	// 0.00], GC.calls: 4, GC.time: 0.04, time.total: 0.74, time.warmup: 0.00,
	// time.bench: 0.74
	public void deserializeWithProcessor() {
		LinkedList<String> value = mockLinkedList();
		byte[] serialize = SerializeHelper.serializeWithProcessor(value);

		List<String> result = null;
		//
		result = SerializeHelper.deserializeWithProcessor(serialize, LinkedList.class);
		//
		System.out.println(result.size());
		assertCollectionEquals(value, result);
	}
}
