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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FeatureMap implements FeatureProvider {
	
	private final Map<FeatureKey<?>, Object> featureMap = new HashMap<FeatureKey<?>, Object>();

	public <T extends Object> void putFeature(FeatureKey<T> key, T feature) {
		featureMap.put(key, feature);
	}
	
	public <T extends Object> T removeFeature(FeatureKey<T> key) {
		
		// casting is OK, as putFeature() is typesafe
		@SuppressWarnings("unchecked")
		final T result = (T) featureMap.remove(key); 
		return result;
	}

	public <T> T getFeature(FeatureKey<T> key) {
		
		// casting is OK, as putFeature() is typesafe
		@SuppressWarnings("unchecked")
		final T result = (T) featureMap.get(key);
		return result;
	}

	public Set<FeatureKey<?>> getFeatureKeys() {
		return Collections.unmodifiableSet(featureMap.keySet());
	}

}
