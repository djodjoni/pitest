/*
 * Copyright 2010 Henry Coles
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package org.pitest.mutationtest.engine.gregor.mutators.experimental;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.MutationIdentifier;
import org.pitest.mutationtest.engine.gregor.Context;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;


public enum RemoveIncrementsMutator implements MethodMutatorFactory {

  REMOVE_INCREMENTS_MUTATOR;

  public MethodVisitor create(final Context context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new RemoveIncrementsMethodVisitor(this, context, methodVisitor);
  }

  public String getGloballyUniqueId() {
    return this.getClass().getName();
  }

  public String getName() {
    return name();
  }
}

class RemoveIncrementsMethodVisitor extends MethodVisitor {

  private final MethodMutatorFactory factory;
  private final Context              context;

  public RemoveIncrementsMethodVisitor(final MethodMutatorFactory factory,
      final Context context, final MethodVisitor delegateMethodVisitor) {
    super(Opcodes.ASM4, delegateMethodVisitor);
    this.factory = factory;
    this.context = context;
  }

  @Override
  public void visitIincInsn(final int var, final int increment) {
    final MutationIdentifier newId = this.context.registerMutation(
    		this.factory, "Removed increment " + increment);
    if (this.context.shouldMutate(newId)) {
      this.mv.visitInsn(Opcodes.NOP);
    } else {
      this.mv.visitIincInsn(var, increment);
    }
  }

}