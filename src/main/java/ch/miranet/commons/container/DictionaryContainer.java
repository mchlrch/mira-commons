/*********************************************************************
  This file is part of the 'mira-commons' library,
  see <http://www.miranet.ch/projects/mira-commons>

  Copyright (C) 2010 Michael Rauch
  
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

package ch.miranet.commons.container;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class DictionaryContainer<T> extends ListContainer<T> {
	
	private final Map<String, T> dictionary = new HashMap<String, T>();
	
	protected DictionaryContainer(List<T> items) {
		super(items);
		
		for (T item : items) {
			final String name = nameOfItem(item);
			
			if (dictionary.containsKey(name)) {
				throw new IllegalStateException("duplicate name: " + name);
			} else {
				dictionary.put(name, item);
			}
		}
	}
	
	public T getByName(String name) {
		return dictionary.get(name);
	}
	
	protected abstract String nameOfItem(T item);

}
