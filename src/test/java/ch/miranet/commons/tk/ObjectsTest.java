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

import org.testng.annotations.Test;

public class ObjectsTest {

	private final Objects tk = new Objects();

	@Test
	public void testEqual() {
		final String a = "a";
		final String b = "b";

		assert tk.equal(a, a);
		assert tk.equal(null, null);

		assert !tk.equal(a, b);
		assert !tk.equal(a, null);
		assert !tk.equal(null, a);
	}

	@Test
	public void testEnforceNotNullSuccess() {
		final String name = "homer";
		final String s = tk.assertNotNull(name, "name");
		assert s == name;
	}

	@Test(expectedExceptions = { NullPointerException.class })
	public void testEnforceNotNullFailure() {
		tk.assertNotNull(null, "name");
	}

}
