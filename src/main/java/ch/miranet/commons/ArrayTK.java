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

import java.lang.reflect.Array;

public class ArrayTK {

	public static boolean isEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

	public static <T> T[] enforceNotEmpty(T[] array, String name) {
		if (isEmpty(array)) throw new EmptyArrayException(name);
		return array;
	}
	
	public static <A> A[] append(A[] base, A postfix) {

		@SuppressWarnings("unchecked")
		final A[] result = (A[]) Array.newInstance(base.getClass().getComponentType(), base.length + 1);

		System.arraycopy(base, 0, result, 0, base.length);
		result[base.length] = postfix;
		return result;
	}


	public static class EmptyArrayException extends IllegalArgumentException {
		private static final long serialVersionUID = 1L;

		public EmptyArrayException(String name) {
			super(String.format("Array %s is null or empty", name));
		}
	}

}
