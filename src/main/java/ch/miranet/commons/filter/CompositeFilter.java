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


import ch.miranet.commons.ArrayTK;

/**
 * All filters must accept.
 */
public class CompositeFilter<T> implements Filter<T> {

    private final Filter<? super T>[] delegates;
  
    public CompositeFilter(Filter<? super T>... delegates) {
      this.delegates = ArrayTK.enforceNotEmpty(delegates, "delegates");      
    }
  
	public boolean accept(T element) {
	  boolean accepted = true;
	  for (int i=0, n=delegates.length; accepted && i<n; i++) {
	    accepted = delegates[i].accept(element);
	  }
	  return accepted;
	}

}
