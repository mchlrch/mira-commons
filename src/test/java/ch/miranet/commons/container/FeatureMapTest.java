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

import org.testng.annotations.Test;

import ch.miranet.commons.container.FeatureKey;
import ch.miranet.commons.container.FeatureMap;

public class FeatureMapTest {
	
	@Test
	public void testMap() {
		final FeatureMap map = new FeatureMap();
		
		map.putFeature(Name.First, "Homer");
		map.putFeature(Name.Last,  "Simpson");		
		
		assert map.getFeature(Name.First).equals("Homer");
		assert map.getFeature(Name.Last).equals("Simpson");
		
		assert map.getFeatureKeys().size() == 2;
		assert map.getFeatureKeys().contains(Name.First);
		assert map.getFeatureKeys().contains(Name.Last);
		
		
		assert map.removeFeature(Name.First).equals("Homer");
		assert map.getFeature(Name.First) == null;
		assert map.getFeatureKeys().size() == 1;
		assert map.getFeatureKeys().contains(Name.Last);
	}
	
	
	enum Name implements FeatureKey<String> {
	  First, Last;

	  public Class<String> getFeatureType() {
	    return String.class;
	  }
	}

}
