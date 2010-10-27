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

import org.testng.annotations.Test;

import ch.miranet.commons.reflect.ProxyTK;

public class ProxyTKTest {
	
	@Test
	public void testCreateProxy() {
		final Reader reader = new Reader() {
			public String read() {
				return "a";
			}			
		};
		
		final Writer writer = new Writer() {
			public boolean write(String s) {
				return "a".equals(s);				
			}
		};
		
		
		final ReadWrite rw = ProxyTK.createProxy(ReadWrite.class, reader, writer);
		assert rw.read().equals("a");
		assert rw.write("a");		 
	}
	
	
	public interface Reader { public String read(); }
	public interface Writer { public boolean write(String s); }
	public interface ReadWrite extends Reader, Writer {} ;

}
