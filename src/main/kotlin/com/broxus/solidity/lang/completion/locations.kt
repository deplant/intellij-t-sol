package com.broxus.solidity.lang.completion

import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.StandardPatterns
import com.intellij.psi.PsiElement
import com.broxus.solidity.lang.core.SolElementType
import com.broxus.solidity.lang.core.SolidityFile
import com.broxus.solidity.lang.core.SolidityTokenTypes
import com.broxus.solidity.lang.psi.SolFunctionCallArguments
import com.broxus.solidity.lang.psi.SolFunctionCallExpression
import com.broxus.solidity.lang.psi.SolMapExpression
import com.broxus.solidity.lang.psi.SolPragmaDirective
import com.broxus.solidity.lang.psi.SolPrimaryExpression
import com.broxus.solidity.lang.psi.SolRevertStatement


fun emitStartStatement() =
  psiElement(SolidityTokenTypes.IDENTIFIER)
    .afterLeaf(psiElement(SolidityTokenTypes.EMIT))

fun revertStartStatement() =
  psiElement(SolidityTokenTypes.IDENTIFIER)
    .inside(SolRevertStatement::class.java)
    .afterLeaf("revert")

fun stateVarInsideContract() =
  psiElement(SolidityTokenTypes.IDENTIFIER)
    .inside(psiElement(SolPrimaryExpression::class.java))
    .inside(SolidityFile::class.java)

fun rootDeclaration(): ElementPattern<PsiElement> = psiElement()
  .withSuperParent(2, SolPrimaryExpression::class.java)
  .withSuperParent(3, SolidityFile::class.java)

fun expression(): ElementPattern<PsiElement> =
  StandardPatterns.or(
    functionCall(), primaryExpression(), functionCallArguments()
  )

fun functionCall(): ElementPattern<PsiElement> =
  psiElement(SolidityTokenTypes.IDENTIFIER).inside(SolFunctionCallExpression::class.java)

fun primaryExpression(): ElementPattern<PsiElement> =
  psiElement(SolidityTokenTypes.IDENTIFIER).inside(SolPrimaryExpression::class.java)

fun functionCallArguments(): ElementPattern<PsiElement> =
  psiElement(SolidityTokenTypes.IDENTIFIER).inside(SolFunctionCallArguments::class.java)

fun pragma(): ElementPattern<PsiElement> =
  psiElement(SolidityTokenTypes.IDENTIFIER).inside(SolPragmaDirective::class.java)

fun mapExpression(): ElementPattern<PsiElement> =
  psiElement(SolidityTokenTypes.IDENTIFIER).inside(SolMapExpression::class.java)

