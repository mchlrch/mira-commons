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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import ch.miranet.commons.filter.Filter;

public class ListTKTest {
	
	@Test
	public void testIsEmpty() {
		assert ListTK.isEmpty(null);
		assert ListTK.isEmpty(Collections.emptyList());
		
		assert ! ListTK.isEmpty(Arrays.asList("a"));
	}
	
	@Test
	public void testEnforceNotEmptySuccess() {
		final List<String> names = Arrays.asList("homer"); 
		final List<String> s = ListTK.enforceNotEmpty(names, "names");
		assert s == names;
	}
	
	@Test(expectedExceptions = {ListTK.EmptyListException.class})
	public void testEnforceNotEmptyFailure() {
		 ListTK.enforceNotEmpty(null, "names");
	}
	
	@Test
	public void testFilter() {
		final String homer = "homer", bart = "bart";
		final Filter<String> filter = new Filter<String>() {
			public boolean accept(String element) {
				return homer.equals(element);
			}
		};
		
		final List<String> result = ListTK.filter(Arrays.asList(homer, bart), filter); 
		
		assert result.contains(homer);
		assert ! result.contains(bart);
	}

}
