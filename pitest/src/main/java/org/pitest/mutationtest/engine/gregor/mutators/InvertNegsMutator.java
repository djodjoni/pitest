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
package org.pitest.mutationtest.engine.gregor.mutators;

import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.pitest.mutationtest.engine.gregor.AbstractInsnMutator;
import org.pitest.mutationtest.engine.gregor.Context;
import org.pitest.mutationtest.engine.gregor.InsnSubstitution;
import org.pitest.mutationtest.engine.gregor.MethodInfo;
import org.pitest.mutationtest.engine.gregor.MethodMutatorFactory;
import org.pitest.mutationtest.engine.gregor.ZeroOperandMutation;

public enum InvertNegsMutator implements MethodMutatorFactory {

  INVERT_NEGS_MUTATOR;

  public MethodVisitor create(final Context context,
      final MethodInfo methodInfo, final MethodVisitor methodVisitor) {
    return new InvertNegsMethodVisitor(this, methodInfo, context, methodVisitor);
  }

  public String getGloballyUniqueId() {
    return this.getClass().getName();
  }

  public String getName() {
    return name();
  }

}

class InvertNegsMethodVisitor extends AbstractInsnMutator {

  private static final String                            MESSAGE   = "removed negation";
  private final static Map<Integer, ZeroOperandMutation> mutations = new HashMap<Integer, ZeroOperandMutation>();

  static {
    mutations.put(Opcodes.INEG, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    mutations.put(Opcodes.DNEG, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    mutations.put(Opcodes.FNEG, new InsnSubstitution(Opcodes.NOP, MESSAGE));
    mutations.put(Opcodes.LNEG, new InsnSubstitution(Opcodes.NOP, MESSAGE));
  }

  public InvertNegsMethodVisitor(final MethodMutatorFactory factory,
      final MethodInfo methodInfo, final Context context,
      final MethodVisitor writer) {
    super(factory, methodInfo, context, writer);
  }

  @Override
  protected Map<Integer, ZeroOperandMutation> getMutations() {
    return mutations;
  }

}
