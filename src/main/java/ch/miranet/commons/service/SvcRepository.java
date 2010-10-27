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

package ch.miranet.commons.service;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import ch.miranet.commons.ObjectTK;

public class SvcRepository implements SvcProvider {

	private final Map<Class<?>, Object> implMap = new HashMap<Class<?>, Object>();
	private final Map<Class<?>, Konstruktor> implConstructorMap = new HashMap<Class<?>, Konstruktor>();
	
	@Override
	public <T> T getService(Class<T> svcDef) {
		
		@SuppressWarnings("unchecked")
		T impl = (T) implMap.get(svcDef);
		
		if (impl == null) impl = createByImplConstructor(svcDef);
		
		return impl;
	}
	
	@Override
	public <T> T requireService(Class<T> svcDef) {
		final T svc = getService(svcDef);
		if (svc == null) {
			throw new IllegalStateException("Required Service not available: " + svcDef);
		} else {
			return svc;
		}
	}

	public <T, I extends T> void setSingletonService(Class<T> svcDef, I svcImpl) {
		verifyServiceUndefined(svcDef);
		
		implMap.put(svcDef, svcImpl);
	}

	public <T> void setSingletonService(Class<T> svcDef, Class<? extends T> svcImpl) {
		verifyServiceUndefined(svcDef);
		
		Konstruktor konstruktor = null;
		
		// prefer no-arg constructor over constructor with SvcRepository as argument
		try {
			 final Constructor<? extends T> constructor = svcImpl.getConstructor();
			 konstruktor = new Konstruktor(Konstruktor.ArgType.NoArg, constructor);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			// try constructor with SvcProvider argument
		}
		
		// try constructor with SvcProvider as argument
		if (konstruktor == null) {
			try {
				final Constructor<? extends T> constructor = svcImpl.getConstructor(SvcProvider.class);
				konstruktor = new Konstruktor(Konstruktor.ArgType.SvcProviderArg, constructor);
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException("No appropriate constructor found in " + svcImpl + " : init() or init(" + SvcProvider.class.getName() + ")");
			}	
		}
		
		implConstructorMap.put(svcDef, konstruktor);
	}
	
	private <T> T createByImplConstructor(Class<T> svcDef) {
		final Konstruktor konstruktor = implConstructorMap.get(svcDef);
		if (konstruktor != null) {
			
			@SuppressWarnings("unchecked")
			final T svc = (T) konstruktor.newInstance(this);
			return svc;
		}
		
		return null;
	}	
	
	private void verifyServiceUndefined(Class<?> svcDef) {
		if (implMap.containsKey(svcDef)
				|| implConstructorMap.containsKey(svcDef)) {
			
			throw new IllegalStateException("Service implementation already defined.");
		}
	}
	
	// --------------------------------------------------------------
	
	static class Konstruktor {
		private static enum ArgType {NoArg, SvcProviderArg}
		
		private final ArgType argType;
		private final Constructor<?> constructor;
		
		private Konstruktor(ArgType argType, Constructor<?> constructor) {
			this.argType = ObjectTK.enforceNotNull(argType, "argType");
			this.constructor = ObjectTK.enforceNotNull(constructor, "constructor");
		}
		
		@SuppressWarnings("unchecked")
		private <T> T newInstance(SvcProvider repo) {
			try { 
				if (argType == ArgType.NoArg) {
					return (T) constructor.newInstance();
				} else {
					return (T) constructor.newInstance(repo);
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		
	}
	
}
