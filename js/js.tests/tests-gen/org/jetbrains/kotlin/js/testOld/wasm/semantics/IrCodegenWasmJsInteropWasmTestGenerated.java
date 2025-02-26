/*
 * Copyright 2010-2022 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.js.testOld.wasm.semantics;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.util.KtTestUtil;
import org.jetbrains.kotlin.test.TargetBackend;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.GenerateJsTestsKt}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("compiler/testData/codegen/boxWasmJsInterop")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class IrCodegenWasmJsInteropWasmTestGenerated extends AbstractIrCodegenWasmJsInteropWasmTest {
    private void runTest(String testDataFilePath) throws Exception {
        KotlinTestUtils.runTest0(this::doTest, TargetBackend.WASM, testDataFilePath);
    }

    public void testAllFilesPresentInBoxWasmJsInterop() throws Exception {
        KtTestUtil.assertAllTestsPresentByMetadataWithExcluded(this.getClass(), new File("compiler/testData/codegen/boxWasmJsInterop"), Pattern.compile("^(.+)\\.kt$"), null, TargetBackend.WASM, true);
    }

    @TestMetadata("callingWasmDirectly.kt")
    public void testCallingWasmDirectly() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/callingWasmDirectly.kt");
    }

    @TestMetadata("defaultValues.kt")
    public void testDefaultValues() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/defaultValues.kt");
    }

    @TestMetadata("externalTypeOperators.kt")
    public void testExternalTypeOperators() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/externalTypeOperators.kt");
    }

    @TestMetadata("externals.kt")
    public void testExternals() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/externals.kt");
    }

    @TestMetadata("functionTypes.kt")
    public void testFunctionTypes() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/functionTypes.kt");
    }

    @TestMetadata("jsExport.kt")
    public void testJsExport() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/jsExport.kt");
    }

    @TestMetadata("jsToKotlinAdapters.kt")
    public void testJsToKotlinAdapters() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/jsToKotlinAdapters.kt");
    }

    @TestMetadata("kotlinToJsAdapters.kt")
    public void testKotlinToJsAdapters() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/kotlinToJsAdapters.kt");
    }

    @TestMetadata("longStrings.kt")
    public void testLongStrings() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/longStrings.kt");
    }

    @TestMetadata("nullableExternRefs.kt")
    public void testNullableExternRefs() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/nullableExternRefs.kt");
    }

    @TestMetadata("types.kt")
    public void testTypes() throws Exception {
        runTest("compiler/testData/codegen/boxWasmJsInterop/types.kt");
    }
}
