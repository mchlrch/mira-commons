package ch.miranet.commons.collection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import ch.miranet.commons.collection.OneToManyMap;
import ch.miranet.commons.collection.TransientOneToManyMap;

public class TransientOneToManyMapTest {

	@Test
	public void shouldReturnEmptySetIfNotMapped() {
		final OneToManyMap<String, String> map = new TransientOneToManyMap<String, String>();
		final String key = "someKey";

		Assert.assertFalse(map.containsKey(key));
		Assert.assertTrue(map.get(key).isEmpty());
	}

	@Test
	public void shouldPutAndRemoveValues() {
		final OneToManyMap<String, String> map = new TransientOneToManyMap<String, String>();
		final String key = "someKey";

		final Set<String> setA = stringSet("foo", "bar", "baz");
		map.put(key, setA);
		Assert.assertEquals(map.get(key), setA);

		final Set<String> setB = stringSet("free", "libre", "open");
		map.put(key, setB);
		Assert.assertEquals(map.get(key), setB);

		map.remove(key);
		Assert.assertFalse(map.containsKey(key));
		Assert.assertTrue(map.get(key).isEmpty());
	}

	@Test(expectedExceptions = { NullPointerException.class })
	public void shouldRejectNullAsParamToPut() {
		final OneToManyMap<String, String> map = new TransientOneToManyMap<String, String>();
		final String key = "someKey";
		map.put(key, null);
	}

	@Test(expectedExceptions = { NullPointerException.class })
	public void shouldRejectNullInParamToPut() {
		final OneToManyMap<String, String> map = new TransientOneToManyMap<String, String>();
		final String key = "someKey";
		map.put(key, stringSet("foo", null, "baz"));
	}

	@Test
	public void shouldAddValuesForSingleKey() {
		final OneToManyMap<String, String> map = new TransientOneToManyMap<String, String>();
		final String keyA = "someKey";

		map.add(keyA, "foo");
		Assert.assertTrue(map.containsKey(keyA));
		Assert.assertEquals(map.get(keyA), stringSet("foo"));

		map.add(keyA, "bar");
		Assert.assertEquals(map.get(keyA), stringSet("foo", "bar"));

		final String keyB = "otherKey";
		map.add(keyB, stringSet("apple", "mango"));
		map.add(keyB, stringSet("cherry", "plum"));
		Assert.assertEquals(map.get(keyB),
				stringSet("apple", "mango", "cherry", "plum"));
	}

	@Test
	public void shouldAddValuesFromAnotherMap() {
		final OneToManyMap<String, String> mapA = new TransientOneToManyMap<String, String>();
		final String keyA = "someKey";
		final String keyB = "otherKey";

		mapA.add(keyA, "foo");
		mapA.add(keyA, "bar");
		mapA.add(keyB, "raz");
		mapA.add(keyB, "faz");

		final OneToManyMap<String, String> mapB = new TransientOneToManyMap<String, String>();
		final String keyC = "yaKey";
		mapB.add(keyC, stringSet("apple", "mango"));

		mapB.addAll(mapA);

		Assert.assertTrue(mapB.containsKey(keyA));
		Assert.assertTrue(mapB.containsKey(keyB));
		Assert.assertTrue(mapB.containsKey(keyC));

		Assert.assertEquals(mapB.get(keyA), stringSet("foo", "bar"));
		Assert.assertEquals(mapB.get(keyB), stringSet("raz", "faz"));
		Assert.assertEquals(mapB.get(keyC), stringSet("apple", "mango"));
	}

	@Test(expectedExceptions = { NullPointerException.class })
	public void shouldRejectNullAsParamToAdd() {
		final OneToManyMap<String, String> map = new TransientOneToManyMap<String, String>();
		final String key = "someKey";
		map.add(key, (String) null);
	}

	@Test(expectedExceptions = { NullPointerException.class })
	public void shouldRejectNullInParamToAdd() {
		final OneToManyMap<String, String> map = new TransientOneToManyMap<String, String>();
		final String key = "someKey";
		map.add(key, stringSet("foo", null, "baz"));
	}

	private Set<String> stringSet(String... strings) {
		return new HashSet<String>(Arrays.asList(strings));
	}
}
