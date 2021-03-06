/*
 * Copyright 2000-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.structuralsearch.impl.matcher.handlers;

import com.intellij.psi.*;
import com.intellij.structuralsearch.impl.matcher.MatchContext;

/**
 * Handler for statement search
 */
public class StatementHandler extends MatchingHandler {

  @Override
  public boolean match(PsiElement patternNode, PsiElement matchedNode, MatchContext context) {
    // filtering is done on SubstitutionHandler level
    if (patternNode == null) return false;

    if ((!(matchedNode instanceof PsiStatement) && !(matchedNode instanceof PsiComment) /* comments are matched as statements */) ||
        (matchedNode instanceof PsiBlockStatement &&
         !(matchedNode.getParent() instanceof PsiBlockStatement) &&
         !(matchedNode.getParent().getParent() instanceof PsiSwitchStatement))) {
      // typed statement does not match these things
      // (BlockStatement could be non-top level in if, etc)
      return false;
    }

    patternNode = ((PsiExpressionStatement)patternNode).getExpression();
    return context.getMatcher().match(patternNode, matchedNode);
  }
}
