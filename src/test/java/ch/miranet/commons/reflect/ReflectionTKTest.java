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

package ch.miranet.commons.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

public class ReflectionTKTest {
	
	private static final String s1 = "X";
	private static final String s2 = "FreeSoftware";
	private static final String s3 = "GNU";
	
	@Test
	public void testDeCapitalize() {
		final String s1Decap = ReflectionTK.decapitalize(s1);
		final String s2Decap = ReflectionTK.decapitalize(s2);
		final String s3Decap = ReflectionTK.decapitalize(s3);
		
		assert s1Decap.equals("x");
		assert s2Decap.equals("freeSoftware");
		assert s3Decap.equals("GNU");		
		
		final String s1Cap = ReflectionTK.capitalize(s1Decap);
		final String s2Cap = ReflectionTK.capitalize(s2Decap);
		final String s3Cap = ReflectionTK.capitalize(s3Decap);
		
		assert s1Cap.equals(s1);
		assert s2Cap.equals(s2);
		assert s3Cap.equals(s3);
	}
	
	@Test
	public void testHumanize() {		
		assert ReflectionTK.humanize(null) == null;
		assert ReflectionTK.humanize("").equals("");
		
		assert ReflectionTK.humanize("X").equals("X");
		assert ReflectionTK.humanize("freeSoftware").equals("Free Software");
		assert ReflectionTK.humanize("GNU").equals("GNU");
		assert ReflectionTK.humanize("nooGNU").equals("Noo GNU");
		assert ReflectionTK.humanize("Me1st").equals("Me 1st");
		assert ReflectionTK.humanize("go1234").equals("Go 1234");
	}	
	
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
		
		assert ReflectionTK.getItemType(fa) == int.class;
		assert ReflectionTK.getItemType(ma) == int.class;
		
		assert ReflectionTK.getItemType(fb) == int.class;
		assert ReflectionTK.getItemType(mb) == int.class;
		
		assert ReflectionTK.getItemType(fc) == Integer.class;
		assert ReflectionTK.getItemType(mc) == Integer.class;
		
		assert ReflectionTK.getItemType(fd) == String.class;
		assert ReflectionTK.getItemType(md) == String.class;
	}
	
	
	public static class TypeProvider {
		public int a;
		public int getA() { return a; }
		
		public int[] b;
		public int[] getB() { return b; }
		
		public List<Integer> c;
		public List<Integer> getC() { return c; }
		
		public Set<? extends String> d;
		public Set<? extends String> getD() { return d; }
		
	}

}
