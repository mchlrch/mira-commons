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

public class Option<T> {
	
	private final T cargo;

    public static <T> Option<T> createNone() {
        return new Option<T>(null);
    }

    public Option(T cargo) {
        this.cargo = cargo;
    }

    public boolean isSet() {
        return cargo != null;
    }

    public boolean isNone() {
        return !isSet();
    }

    public T get() {
        if (!isSet()) {
            throw new IllegalStateException("Option not set.");
        }
        return cargo;
    }

    public Option<T> assertNotNull() {
        return this;
    }

    public Option<T> assertNotEmpty() {
        if (!isSet()) {
            throw new IllegalStateException("Option not set.");
        }
        return this;
    }

    @Override
    public String toString() {
        return isSet() ? cargo.toString() : "*none*";
    }

}
