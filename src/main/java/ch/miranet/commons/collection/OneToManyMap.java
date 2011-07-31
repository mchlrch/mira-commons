package ch.miranet.commons.collection;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface OneToManyMap<K, V> extends Map<K, Set<V>> {

	// PEND: unklar ob overloading die bessere wahl ist 
	boolean add(K key, V value);
	boolean add(K key, Collection<? extends V> vals);
//	boolean addOne(K key, V value);
//	boolean addMany(K key, Collection<? extends V> vals);

	boolean addAll(OneToManyMap<? extends K, ? extends V> map);

}
