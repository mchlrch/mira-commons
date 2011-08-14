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

public class StringsTest {

	private final Strings tk = new Strings();

	private static final String s1 = "X";
	private static final String s2 = "FreeSoftware";
	private static final String s3 = "GNU";

	@Test
	public void testIsEmpty() {
		assert tk.isEmpty(null);
		assert tk.isEmpty("");
		assert tk.isEmpty("  ");

		assert !tk.isEmpty("a");
	}

	@Test
	public void testEnforceNotEmptySuccess() {
		final String name = "homer";
		final String s = tk.assertNotEmpty(name, "name");
		assert s == name;
	}

	@Test(expectedExceptions = { Strings.EmptyStringException.class })
	public void testEnforceNotEmptyFailure() {
		tk.assertNotEmpty(null, "name");
	}

	@Test
	public void testDeCapitalize() {
		final String s1Decap = tk.decapitalize(s1);
		final String s2Decap = tk.decapitalize(s2);
		final String s3Decap = tk.decapitalize(s3);

		assert s1Decap.equals("x");
		assert s2Decap.equals("freeSoftware");
		assert s3Decap.equals("GNU");

		final String s1Cap = tk.capitalize(s1Decap);
		final String s2Cap = tk.capitalize(s2Decap);
		final String s3Cap = tk.capitalize(s3Decap);

		assert s1Cap.equals(s1);
		assert s2Cap.equals(s2);
		assert s3Cap.equals(s3);
	}

	@Test
	public void testHumanize() {
		assert tk.humanize(null) == null;
		assert tk.humanize("").equals("");

		assert tk.humanize("X").equals("X");
		assert tk.humanize("freeSoftware").equals("Free Software");
		assert tk.humanize("GNU").equals("GNU");
		assert tk.humanize("nooGNU").equals("Noo GNU");
		assert tk.humanize("Me1st").equals("Me 1st");
		assert tk.humanize("go1234").equals("Go 1234");
	}

}
