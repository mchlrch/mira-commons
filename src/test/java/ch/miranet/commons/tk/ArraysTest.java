/*********************************************************************
  This file is part of the 'mira-commons' library,
  see <http://www.miranet.ch/projects/mira-commons>

  Copyright (C) 2010, 2011 Michael Rauch
  
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

import org.testng.annotations.Test;

public class ArraysTest {

	private final Arrays tk = new Arrays();

	@Test
	public void testIsEmpty() {
		assert tk.isEmpty(null);
		assert tk.isEmpty(new String[0]);

		assert !tk.isEmpty(new String[] { "a" });
	}

	@Test
	public void testEnforceNotEmptySuccess() {
		final String[] names = { "homer" };
		final String[] s = tk.assertNotEmpty(names, "names");
		assert s == names;
	}

	@Test(expectedExceptions = { Arrays.EmptyArrayException.class })
	public void testEnforceNotEmptyFailure() {
		tk.assertNotEmpty(null, "names");
	}

	@Test
	public void testAppend() {
		assert java.util.Arrays.equals(tk.append(new Object[0], null), new Object[] { null });
		assert java.util.Arrays.equals(tk.append(new Object[] { 'a' }, null), new Object[] { 'a', null });
		assert java.util.Arrays.equals(tk.append(new Object[] { 'a' }, 'b'), new Object[] { 'a', 'b' });
	}

}
