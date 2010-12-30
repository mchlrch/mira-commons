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

package ch.miranet.commons;

import java.util.ArrayList;
import java.util.List;

import ch.miranet.commons.filter.AcceptAllFilter;
import ch.miranet.commons.filter.Filter;
import ch.miranet.commons.transformer.Transformer;

public class ListTK {

	public static <T> List<T> filter(List<T> baseList, Filter<? super T> filter) {
		final ArrayList<T> result = new ArrayList<T>();
		for (T element : baseList) {
			if (filter.accept(element)) {
				result.add(element);
			}
		}
		return result;
	}
	
	public static <I, O> List<O> transform(List<I> baseList, Transformer<? super I, O> transformer) {
		return filterAndTransform(baseList, AcceptAllFilter.getInstance(), transformer);
	}
	
	public static <I, O> List<O> filterAndTransform(List<I> baseList, Filter<? super I> filter, Transformer<? super I, O> transformer) {
		final ArrayList<O> result = new ArrayList<O>();
		for (I input : baseList) {
			if (filter.accept(input)) {
				result.add(transformer.transform(input));
			}
		}
		return result;
	}
	
	public static boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	public static <T> List<T> enforceNotEmpty(List<T> list, String name) {
		if (isEmpty(list)) throw new EmptyListException(name);
		return list;
	}

	public static class EmptyListException extends IllegalArgumentException {
		private static final long serialVersionUID = 1L;

		public EmptyListException(String name) {
			super(String.format("List %s is null or empty", name));
		}
	}

}
