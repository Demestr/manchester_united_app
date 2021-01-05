/*
 * Created by Dmitry Lipski on 05.01.21 12:38
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 05.01.21 12:38
 */

package com.lipssoftware.manchester.united

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.lipssoftware.manchester.united", appContext.packageName)
    }
}