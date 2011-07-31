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

package ch.miranet.commons.tk;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

public class ReflectionTest {

	private final Reflection tk = new Reflection();

	@Test
	public void testGetItemType() throws Exception {
		final Field fa = TypeProvider.class.getField("a");
		final Field fb = TypeProvider.class.getField("b");
		final Field fc = TypeProvider.class.getField("c");
		final Field fd = TypeProvider.class.getField("d");

		final Method ma = TypeProvider.class.getMethod("getA");
		final Method mb = TypeProvider.class.getMethod("getB");
		final Method mc = TypeProvider.class.getMethod("getC");
		final Method md = TypeProvider.class.getMethod("getD");

		assert tk.getItemType(fa) == int.class;
		assert tk.getItemType(ma) == int.class;

		assert tk.getItemType(fb) == int.class;
		assert tk.getItemType(mb) == int.class;

		assert tk.getItemType(fc) == Integer.class;
		assert tk.getItemType(mc) == Integer.class;

		assert tk.getItemType(fd) == String.class;
		assert tk.getItemType(md) == String.class;
	}

	public static class TypeProvider {
		public int a;

		public int getA() {
			return a;
		}

		public int[] b;

		public int[] getB() {
			return b;
		}

		public List<Integer> c;

		public List<Integer> getC() {
			return c;
		}

		public Set<? extends String> d;

		public Set<? extends String> getD() {
			return d;
		}

	}

}
