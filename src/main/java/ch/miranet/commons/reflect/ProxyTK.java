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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ch.miranet.commons.ArrayTK;
import ch.miranet.commons.ObjectTK;



public class ProxyTK {

	public static <T> T createProxy(Class<T> iface, Object... delegates) {
		final Object proxy = createProxy(ObjectTK.class.getClassLoader(), new Class<?>[] { iface }, delegates);

		// This is OK, as the return type equals the implemented interface
		@SuppressWarnings("unchecked")
		final T proxyResult = (T) proxy;

		return proxyResult;
	}

	public static Object createProxy(ClassLoader loader, Class<?>[] ifaces, Object... delegates) {
		final DelegateFusionInvocationHandler handler = new DelegateFusionInvocationHandler(ifaces, delegates);
		final Object proxy = Proxy.newProxyInstance(loader, ifaces, handler);
		return proxy;
	}
	
	
	// ===========================================
	public static class DelegateFusionInvocationHandler implements InvocationHandler {

		private final Map<Method, Object> dispatchMap = new HashMap<Method, Object>();

		DelegateFusionInvocationHandler(Class<?>[] ifaces, Object... delegates) {
			ArrayTK.enforceNotEmpty(ifaces, "ifaces");
			ArrayTK.enforceNotEmpty(delegates, "delegates");

			final List<Object> delegateList = Arrays.asList(delegates);
			final Map<Class<?>, List<Method>> delegateMethodMap = new HashMap<Class<?>, List<Method>>();
			for (Object delegate : delegateList) {
				final Class<?> delegateClass = delegate.getClass();
				if ( ! delegateMethodMap.containsKey(delegateClass)) {
					final List<Method> methodList = Arrays.asList(delegateClass.getMethods());
					delegateMethodMap.put(delegateClass, methodList);
				}
			}

			for (Class<?> iface : ifaces) {
				for (Method ifaceMethod : iface.getMethods()) {
					boolean methodMatched = false;
					for (Iterator<Object> delegateIterator = delegateList.iterator(); ! methodMatched && delegateIterator.hasNext();) {
						final Object delegate = delegateIterator.next();
						final Class<?> delegateClass = delegate.getClass();
						for (Iterator<Method> delegateMethodIterator = delegateMethodMap.get(delegateClass).iterator(); ! methodMatched && delegateMethodIterator.hasNext();) {
							final Method delegateMethod = delegateMethodIterator.next();
							if (isImplementationOf(delegateMethod, ifaceMethod)) {
								dispatchMap.put(ifaceMethod, delegate);
								methodMatched = true;
							}
						}
					}

					if ( ! methodMatched) throw new IllegalStateException("no matching delegate for method: " + ifaceMethod);
				}
			}
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			final Object delegate = dispatchMap.get(method);
			if (delegate == null) throw new IllegalStateException("no matching delegate for method: " + method);
			if ( ! method.isAccessible()) method.setAccessible(true);
			return method.invoke(delegate, args);
		}

		private boolean isImplementationOf(Method implementedMethod, Method declaredMethod) {
			final Class<?> implClass = implementedMethod.getDeclaringClass();
			final Class<?> declaringClass = implementedMethod.getDeclaringClass();

			if ( ! declaringClass.isAssignableFrom(implClass)) return false;
			if ( ! declaredMethod.getName().equals(implementedMethod.getName())) return false;
			if ( ! declaredMethod.getReturnType().equals(implementedMethod.getReturnType())) return false;

			/* Avoid unnecessary cloning */
			Class<?>[] params1 = declaredMethod.getParameterTypes();
			Class<?>[] params2 = implementedMethod.getParameterTypes();
			if (params1.length != params2.length) return false;
			for (int i = 0; i < params1.length; i++) {
				if (params1[i] != params2[i]) return false;
			}
			return true;
		}
	}

}
