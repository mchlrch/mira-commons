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

package ch.miranet.commons.filter;

import org.testng.annotations.Test;

public class FilterTest {
	
	@Test
	public void testAcceptAllFilter() {
		final AcceptAllFilter<Object> filter = AcceptAllFilter.getInstance();
		
		assert filter.accept(null);
		assert filter.accept("a");
		assert filter.accept(1);
	}
	
	@Test
	public void testCompositeFilter() {
		final Filter<String> aFilter = new Filter<String>() {	public boolean accept(String element) { return element == null || element.equals("a"); }	};
		final Filter<String> bFilter = new Filter<String>() {	public boolean accept(String element) { return element == null || element.equals("b"); }	};
		
		@SuppressWarnings("unchecked")
		final CompositeFilter<String> filter = new CompositeFilter<String>(aFilter, bFilter);
		
		assert filter.accept(null);
		assert ! filter.accept("a");
		assert ! filter.accept("b");
		assert ! filter.accept("c");
	}

}
