package com.broxus.solidity.ide.folding

import com.broxus.solidity.utils.SolTestBase

class SolidityFoldingBuilderTest  : SolTestBase() {
  override fun getTestDataPath() = "src/test/resources/fixtures/folding/"

  fun testComments() = doTest()
  // TODO: fix natspec folding
  // fun testNatSpec() = doTest()

  private fun doTest() {
    myFixture.testFolding("${testDataPath}/$fileName")
  }
}