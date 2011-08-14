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

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class ListContainerTest {
	
	@Test
	public void testContainer() {
		final List<String> names = new ArrayList<String>();
		names.add("homer");
		names.add("bart");
		
		final ListContainer<String> container = new ListContainer<String>(names);
		
		assert container.get().size() == 2;
		assert container.get().contains("homer");
		assert container.get().contains("bart");
		
		names.remove("homer");
		
		// container is a snapshot of the list at creation time
		assert container.get().size() == 2;
		assert container.get().contains("homer");
		assert container.get().contains("bart");		
	}
	
	@Test
	public void testContainerEmpty() {
		final ListContainer<String> c1 = new ListContainer<String>(null);
		assert c1.get().isEmpty();
	}	

}
