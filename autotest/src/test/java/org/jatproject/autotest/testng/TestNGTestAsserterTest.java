package org.jatproject.autotest.testng;

import org.jatproject.autotest.ClassnameTest;
import org.jatproject.autotest.TestAsserter;
import org.jatproject.autotest.Tester;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTestAsserterTest
{
    private TestAsserter asserter;

    @BeforeMethod
    protected void beforeEach() throws Exception
    {
        asserter = new TestNGTestAsserter();
    }

    @Test
    public void nonTestNGClassesShouldReturnFalse()
    {
        assertFalse(asserter.isTest(Tester.class));
    }

    @Test
    public void testAnnotationOnClassShouldReturnTrue()
    {
        assertTrue(asserter.isTest(ClassnameTest.class));
    }

    @Test
    public void testAnnotationOnMethodsShouldReturnTrue()
    {
        assertTrue(asserter.isTest(TestNGTestAsserterTest.class));        
    }
}
