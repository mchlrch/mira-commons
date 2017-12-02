/*********************************************************************
  This file is part of the 'mira-commons' library,
  see <http://www.miranet.ch/projects/mira-commons>

  Copyright (C) 2010, 2011 Michael Rauch
  
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

import java.beans.Introspector;

import ch.miranet.commons.TK;

public class Strings {

	public boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}

	public String assertNotEmpty(String s, String name) {
		if (isEmpty(s))
			throw new EmptyStringException(name);
		return s;
	}

	public String decapitalize(String s) {
		return Introspector.decapitalize(s);
	}

	public String capitalize(String s) {
		if (s == null || s.length() < 1)
			return null;

		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public String humanize(String s) {
		if (TK.Strings.isEmpty(s))
			return s;

		final StringBuilder result = new StringBuilder(capitalize(s));
		for (int i = 1, n = result.length(); i < n; i++) {
			final char ch0 = result.charAt(i - 1);
			final char ch1 = result.charAt(i);
			if (!Character.isUpperCase(ch0) && Character.isUpperCase(ch1) || !Character.isDigit(ch0)
					&& Character.isDigit(ch1)) {
				result.insert(i++, " ");
			}
		}
		return result.toString();
	}
	
	public int compareNullSafe(String s1, String s2) {
		return ("" + s1).compareTo("" + s2);
	}

	public static class EmptyStringException extends IllegalArgumentException {
		private static final long serialVersionUID = 1L;

		public EmptyStringException(String name) {
			super(String.format("String %s is null or empty", name));
		}
	}

}
