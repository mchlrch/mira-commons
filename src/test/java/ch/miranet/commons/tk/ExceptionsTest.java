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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ExceptionsTest {

	private final Exceptions tk = new Exceptions();

	@Test
	public void testUnwrap() {
		final RuntimeException rt1 = new RuntimeException();
		final RuntimeException rt2 = new RuntimeException(new IOException());
		final RuntimeException rt3 = new RuntimeException(new IllegalStateException());

		final InvocationTargetException it1 = new InvocationTargetException(null);
		final InvocationTargetException it2 = new InvocationTargetException(new IOException());
		final InvocationTargetException it3 = new InvocationTargetException(new IllegalStateException());

		Assert.assertEquals(tk.unwrap(new RuntimeException(rt1)), rt1);
		Assert.assertEquals(tk.unwrap(new RuntimeException(rt2)), rt2.getCause());
		Assert.assertEquals(tk.unwrap(new RuntimeException(rt3)), rt3.getCause());

		Assert.assertEquals(tk.unwrap(new InvocationTargetException(it1)), it1);
		Assert.assertEquals(tk.unwrap(new InvocationTargetException(it2)), it2.getCause());
		Assert.assertEquals(tk.unwrap(new InvocationTargetException(it3)), it3.getCause());
	}

}
