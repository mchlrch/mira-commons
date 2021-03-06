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

package ch.miranet.commons.tk;

import java.lang.reflect.Proxy;

import ch.miranet.commons.reflect.DelegateFusionInvocationHandler;

public class Proxies {

	public <T> T createProxy(Class<T> iface, Object... delegates) {
		final Object proxy = createProxy(Objects.class.getClassLoader(), new Class<?>[] { iface }, delegates);

		// This is OK, as the return type equals the implemented interface
		@SuppressWarnings("unchecked")
		final T proxyResult = (T) proxy;

		return proxyResult;
	}

	public Object createProxy(ClassLoader loader, Class<?>[] ifaces, Object... delegates) {
		final DelegateFusionInvocationHandler handler = new DelegateFusionInvocationHandler(ifaces, delegates);
		final Object proxy = Proxy.newProxyInstance(loader, ifaces, handler);
		return proxy;
	}

}
