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
import java.util.Collections;
import java.util.List;

public class ListContainer<T> {
	
	private final List<T> elements;
	
	public ListContainer(List<T> items) {
		if (items == null || items.isEmpty()) {
			this.elements = Collections.emptyList();
		} else {
			this.elements = new ArrayList<T>(items);
		}
	}
	
	public List<T> get() {
		return elements;
	}

}
