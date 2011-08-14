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

public class DictionaryContainerTest {
	
	@Test
	public void testContainer() {
		final List<String> names = new ArrayList<String>();
		names.add("homer");
		names.add("bart");
		
		final MyDictionary dict = new MyDictionary(names);
		
		assert dict.get().size() == 2;
		assert dict.get().contains("homer");
		assert dict.get().contains("bart");
		
		assert dict.getByName("dad").equals("homer");
		assert dict.getByName("son").equals("bart");
	}
	
	
	
	private static class MyDictionary extends DictionaryContainer<String> {
		
		protected MyDictionary(List<String> items) {
			super(items);
		}

		@Override
		protected String nameOfItem(String item) {
			if ("homer".equals(item)) return "dad";
			if ("bart".equals(item))  return "son";
			return null;
		}
		
	}

}
