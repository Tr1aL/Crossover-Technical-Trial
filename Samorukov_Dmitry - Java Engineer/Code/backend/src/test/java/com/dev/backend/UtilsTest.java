package com.dev.backend;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UtilsTest {

    @Test
    public void testParseInteger() throws Exception {
        assertNull(Utils.parseInteger("1.1"));
        assertNotNull(Utils.parseInteger("1"));
    }
}