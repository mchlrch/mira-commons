/*********************************************************************
  This file is part of the 'mira-commons' library,
  see <http://www.miranet.ch/projects/mira-commons>

  Copyright (C) 2011 Michael Rauch
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
      http://www.apache.org/licenses/LICENSE-2.0
      
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. 
 *********************************************************************/

package ch.miranet.commons.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.miranet.commons.TK;


public class TransientOneToManyMap<K, V> implements OneToManyMap<K, V> {
	private final Map<K, Set<V>> delegate = new HashMap<K, Set<V>>();

	public Set<V> put(K key, Set<V> newValues) {
		assertCollectionNotNullAndNotContainsNull(newValues, "newValues",
				"newValues contains null");
		return delegate.put(key, newValues);
	}

	public boolean add(K key, V value) {
		TK.Objects.assertNotNull(value, "value");

		final Set<V> values = valuesFor(key);
		return values.add(value);
	}

	@Override
	public boolean add(K key, Collection<? extends V> newValues) {
		assertCollectionNotNullAndNotContainsNull(newValues, "newValues",
				"newValues contains null");

		final Set<V> values = valuesFor(key);
		return values.addAll(newValues);
	}
	
	@Override
	public boolean addAll(OneToManyMap<? extends K, ? extends V> newEntries) {
		for (K key : newEntries.keySet()) {
			final Set<? extends V> newValues = newEntries.get(key);
			assertCollectionNotNullAndNotContainsNull(newValues,
					String.format("newEntries[%s] is null", key),
					String.format("newEntries[%s] contains null", key));
		}

		boolean entriesModified = false;
		for (K key : newEntries.keySet()) {
			final Set<? extends V> newValues = newEntries.get(key);
			entriesModified |= add(key, newValues);
		}

		return entriesModified;
	}
	
	@Override
	public Set<V> remove(Object key) {
		return delegate.remove(key);
	}
	
	@Override
	public int size() {
		return delegate.size();
	}
	
	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}
	
	@Override
	public void clear() {
		delegate.clear();
	}

	@Override
	public boolean containsValue(Object value) {
		return delegate.containsValue(value);
	}
	
	@Override
	public Collection<Set<V>> values() {
		return delegate.values();
	}
	
	@Override
	public boolean containsKey(Object key) {
		return delegate.containsKey(key);
	}

	public Set<K> keySet() {
		return delegate.keySet();
	}
	
	// TODO: wrap returned set to protect from inserting null-values or set-containing-null-values
	@Override
	public Set<Entry<K, Set<V>>> entrySet() {
		return delegate.entrySet();
	}
	
	@Override
	public Set<V> get(Object key) {
		if (delegate.containsKey(key)) {
			return delegate.get(key);
		} else {
			return Collections.emptySet();
		}
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends Set<V>> newEntries) {
		for (K key : newEntries.keySet()) {
			final Set<? extends V> newValues = newEntries.get(key);
			assertCollectionNotNullAndNotContainsNull(newValues,
					String.format("newEntries[%s] is null", key),
					String.format("newEntries[%s] contains null", key));
		}

		for (K key : newEntries.keySet()) {
			final Set<V> newValues = newEntries.get(key);
			put(key, newValues);
		}
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	protected Set<V> valuesFor(K key) {
		Set<V> values = delegate.get(key);
		if (values == null) {
			values = new HashSet<V>();
			delegate.put(key, values);
		}
		return values;
	}

	protected void assertCollectionNotNullAndNotContainsNull(
			Collection<?> collection, String isNullFailMessage,
			String containsNullFailMessage) {
		// TODO: ObjectTK.assertNotNull && ObjectTK.assertContainsNotNull
		if (collection == null) {
			throw new NullPointerException(isNullFailMessage);
		}

		boolean containsNull = false;
		try {
			containsNull = collection.contains(null);
		} catch (NullPointerException ex) {
			// may happen if collection does not support null values
		}
		
		if (containsNull) {
			throw new NullPointerException(containsNullFailMessage);
		}
	}

}
