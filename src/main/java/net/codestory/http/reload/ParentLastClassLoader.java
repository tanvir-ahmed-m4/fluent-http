/**
 * Copyright (C) 2013 all@code-story.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package net.codestory.http.reload;

import static net.codestory.http.misc.Fluent.*;

import java.net.*;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class ParentLastClassLoader extends URLClassLoader {

  public ParentLastClassLoader(ClassLoader parent) throws MalformedURLException {
    super(getUrls(parent), parent);
  }

  private static URL[] getUrls(ClassLoader parent) throws MalformedURLException {
    if (!(parent instanceof URLClassLoader)) {
      return new URL[0];
    }

    return of(((URLClassLoader) parent).getURLs()).exclude(url -> url.toString().endsWith(".jar")).toArray(URL[]::new);
  }

  @Override
  protected synchronized Class<?> loadClass(String name, boolean resolve)
    throws ClassNotFoundException {
    Class<?> c = findLoadedClass(name);
    if (c == null) {
      try {
        c = findClass(name);
      } catch (ClassNotFoundException e) {
        c = super.loadClass(name, resolve);
      }
    }
    if (resolve) {
      resolveClass(c);
    }
    return c;
  }

  // TODO classpath resources

}
