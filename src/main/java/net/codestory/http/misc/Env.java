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
package net.codestory.http.misc;

public class Env {
  private static final String APP_PORT = "app.port";
  private static final String DISABLE_CLASSPATH = "http.disable.classpath";
  private static final String DISABLE_FILESYSTEM = "http.disable.filesystem";
  private static final String PROD_MODE = "PROD_MODE";

  private Env() {
    // Static class
  }

  public static boolean devMode() {
    return getBoolean(PROD_MODE, false);
  }

  public static int overriddenPort(int port) {
    return getInt(APP_PORT, port);
  }

  public static boolean disableClassPath() {
    return getBoolean(DISABLE_CLASSPATH, false);
  }

  public static boolean disableFilesystem() {
    return getBoolean(DISABLE_FILESYSTEM, false);
  }

  private static String get(String propertyName) {
    String env = System.getenv(propertyName);
    return (env != null) ? env : System.getProperty(propertyName);
  }

  private static boolean getBoolean(String propertyName, boolean defaultValue) {
    String value = get(propertyName);
    return (value == null) ? defaultValue : Boolean.parseBoolean(value);
  }

  private static int getInt(String propertyName, int defaultValue) {
    String value = get(propertyName);
    return (value == null) ? defaultValue : Integer.parseInt(value);
  }
}
