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

package ch.miranet.commons.reflect;

import java.beans.Introspector;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import ch.miranet.commons.StringTK;
import ch.miranet.commons.filter.Filter;

public class ReflectionTK {

	public static String decapitalize(String s) {
		return Introspector.decapitalize(s);
	}

	public static String capitalize(String s) {
		if (s == null || s.length() < 1) return null;

		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static String humanize(String s) {
		if (StringTK.isEmpty(s)) return s;

		final StringBuilder result = new StringBuilder(capitalize(s));
		for (int i = 1, n = result.length(); i < n; i++) {
			final char ch0 = result.charAt(i-1);
			final char ch1 = result.charAt(i);
			if (   ! Character.isUpperCase(ch0) && Character.isUpperCase(ch1)
					|| ! Character.isDigit(ch0)     && Character.isDigit(ch1)) {
				result.insert(i++, " ");
			}
		}
		return result.toString();
	}

	public static List<Method> getMethods(Class<?> cls, Filter<Method> filter) {
		final List<Method> result = new ArrayList<Method>();
		for (Method m : getAllMethods(cls)) {
			if (filter.accept(m)) result.add(m);
		}
		return result;
	}

	public static List<Method> getAllMethods(Class<?> cls) {
		final List<Method> allMethods = new ArrayList<Method>();
		allMethods.addAll(Arrays.asList(cls.getMethods()));
		for (Method m : cls.getDeclaredMethods()) {
			if ( ! allMethods.contains(m)) allMethods.add(m);
		}
		return allMethods;
	}

	public static List<Field> getAllFields(Class<?> cls) {
		final List<Field> allFields = new ArrayList<Field>();
		allFields.addAll(Arrays.asList(cls.getFields()));
		for (Field f : cls.getDeclaredFields()) {
			if ( ! allFields.contains(f)) allFields.add(f);
		}
		return allFields;
	}

	public static Class<?> getItemType(AccessibleObject ao) {
		
		// handle arrays
		Class<?> plainType = null;
		if (ao instanceof Field) {
			plainType = ((Field) ao).getType();
		} else if (ao instanceof Method) {
			plainType = ((Method) ao).getReturnType();
		}		
		if (plainType.isArray()) {
			final Class<?> type = plainType.getComponentType();
			return type;
		}
		
		// handle non-arrays
		Type genType = null;
		if (ao instanceof Field) {
			genType = ((Field) ao).getGenericType();
		} else if (ao instanceof Method) {
			genType = ((Method) ao).getGenericReturnType();
		}

		// get parameterized type
		if (genType instanceof ParameterizedType) {
			final ParameterizedType type = (ParameterizedType) genType;
			final Type[] typeArgs = type.getActualTypeArguments();
			if (typeArgs.length == 1) {
				if (typeArgs[0] instanceof Class<?>) {
					return (Class<?>) typeArgs[0];
				} else if (typeArgs[0] instanceof ParameterizedType) {
					final ParameterizedType paramTypeArg = (ParameterizedType) typeArgs[0];
					final Type rawType = paramTypeArg.getRawType();
					if (rawType instanceof Class<?>) {
						return (Class<?>) rawType;
					}
				} else if (typeArgs[0] instanceof WildcardType) {
					final WildcardType wildcardTypeArg = (WildcardType) typeArgs[0];
					final Type[] upperBoundsTypes = wildcardTypeArg.getUpperBounds();
					if (upperBoundsTypes.length == 1 && upperBoundsTypes[0] instanceof Class<?>) {
						return (Class<?>) upperBoundsTypes[0];
					}
				}
			}
			
			// Without generic type declaration, genType = type
		} else if (genType instanceof Class<?>) {
			final Class<?> type = (Class<?>) genType;
			if (Collection.class.isAssignableFrom(type)) {
				return Object.class;
			} else {
				return type;
			}
		}

		return null;
	}

}
