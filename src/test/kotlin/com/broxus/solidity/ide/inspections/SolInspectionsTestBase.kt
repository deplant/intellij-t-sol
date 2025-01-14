package com.broxus.solidity.ide.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.broxus.solidity.utils.SolTestBase
import org.intellij.lang.annotations.Language

abstract class SolInspectionsTestBase(private val inspection: LocalInspectionTool) : SolTestBase() {

  protected fun enableInspection() = myFixture.enableInspections(inspection.javaClass)

  protected fun checkByText(
    @Language("T-Sol") text: String,
    checkWarn: Boolean = true,
    checkInfo: Boolean = false,
    checkWeakWarn: Boolean = false
  ) {
    myFixture.configureByText("main.tsol", prepare(text))
    enableInspection()
    myFixture.checkHighlighting(checkWarn, checkInfo, checkWeakWarn)
  }

  protected fun checkFixByText(
    fixName: String,
    before: String,
    after: String,
    checkWarn: Boolean = true,
    checkInfo: Boolean = false,
    checkWeakWarn: Boolean = false
  ) {
    myFixture.configureByText("main.tsol", before)
    enableInspection()
    myFixture.checkHighlighting(checkWarn, checkInfo, checkWeakWarn)
    applyQuickFix(fixName)
    myFixture.checkResult(after)
  }

  protected fun applyQuickFix(name: String) {
    val action = myFixture.findSingleIntention(name)
    myFixture.launchAction(action)
  }

  private fun prepare(text: String): String {
    return text.replace("/*@", "<").replace("@*/", ">")
  }
}
