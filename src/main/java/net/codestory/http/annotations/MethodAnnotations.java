/**
 * Copyright (C) 2013-2014 all@code-story.net
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
package net.codestory.http.annotations;

import net.codestory.http.Context;
import net.codestory.http.payload.Payload;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class MethodAnnotations {
  private final List<Function<Context, Payload>> byPassOperations;
  private final List<BiFunction<Context, Payload, Payload>> afterOperations;

  MethodAnnotations() {
    this.byPassOperations = new ArrayList<>();
    this.afterOperations = new ArrayList<>();
  }

  void addByPassOperation(Function<Context, Payload> operation) {
    byPassOperations.add(operation);
  }

  void addAfterOperation(BiFunction<Context, Payload, Payload> operation) {
    afterOperations.add(operation);
  }

  public Payload byPass(Context context) {
    for (Function<Context, Payload> operation : byPassOperations) {
      Payload payload = operation.apply(context);
      if (payload != null) {
        return payload;
      }
    }

    return null;
  }

  public Payload after(Context context, Payload payload) {
    for (BiFunction<Context, Payload, Payload> operation : afterOperations) {
      payload = operation.apply(context, payload);
    }

    return payload;
  }
}
