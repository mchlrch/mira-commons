/*********************************************************************
  This file is part of the 'mira-commons' library,
  see <http://www.miranet.ch/projects/mira-commons>

  Copyright (C) 2011 Michael Rauch
  
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

package ch.miranet.commons;

import ch.miranet.commons.tk.Arrays;
import ch.miranet.commons.tk.Exceptions;
import ch.miranet.commons.tk.Lists;
import ch.miranet.commons.tk.Objects;
import ch.miranet.commons.tk.Proxies;
import ch.miranet.commons.tk.Reflection;
import ch.miranet.commons.tk.Strings;

public class TK {

	public static final Objects Objects = new Objects();
	public static final Strings Strings = new Strings();
	public static final Arrays Arrays = new Arrays();
	public static final Lists Lists = new Lists();
	public static final Exceptions Exceptions = new Exceptions();
	public static final Reflection Reflection = new Reflection();
	public static final Proxies Proxies = new Proxies();

}
