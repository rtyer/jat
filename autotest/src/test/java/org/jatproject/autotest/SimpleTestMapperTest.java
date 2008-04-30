package org.jatproject.autotest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import static org.testng.AssertJUnit.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.jatproject.autotest.testng.TestNGTester;
import org.jatproject.autotest.testng.TestNGTesterTest;

@Test
public class SimpleTestMapperTest
{
    private Mockery mockery;

    @BeforeMethod
    protected void beforeEach()
    {
        mockery = new Mockery()
        {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
            }
        };
    }

    @AfterMethod
    protected void afterEach()
    {
        mockery.assertIsSatisfied();
    }

    public void nonTestClassShouldAppendTestToClassName() throws Exception
    {
        final TestAsserter asserter = mockery.mock(TestAsserter.class);
        final ClassLoader loader = mockery.mock(ClassLoader.class);

        mockery.checking(new Expectations()
        {{
            one(asserter).isTest(AutoTest.class); will(returnValue(false));
            one(loader).loadClass(AutoTest.class.getName() + "Test"); will(returnValue(AutoTestTest.class));
        }});

        TestMapper mapper = new SimpleTestMapper(asserter, loader);
        Class[] tests = mapper.findTestsFor(AutoTest.class);

        assertSame(AutoTestTest.class, tests[0]);
    }

    public void testClassShouldJustReturnTheClass()
    {
        final TestAsserter asserter = mockery.mock(TestAsserter.class);
        final ClassLoader loader = mockery.mock(ClassLoader.class);

        mockery.checking(new Expectations()
        {{
            one(asserter).isTest(AutoTestTest.class); will(returnValue(true));
        }});

        TestMapper mapper = new SimpleTestMapper(asserter, loader);
        Class[] tests = mapper.findTestsFor(AutoTestTest.class);

        assertSame(AutoTestTest.class, tests[0]);
    }

    public void multipleClassesShouldGetMappedCorrectly() throws Exception
    {
        final TestAsserter asserter = mockery.mock(TestAsserter.class);
        final ClassLoader loader = mockery.mock(ClassLoader.class);

        mockery.checking(new Expectations()
        {{
            one(asserter).isTest(AutoTestTest.class); will(returnValue(true));
            one(asserter).isTest(TestNGTester.class); will(returnValue(false));

            one(loader).loadClass(TestNGTester.class.getName() + "Test"); will(returnValue(TestNGTesterTest.class));
        }});

        TestMapper mapper = new SimpleTestMapper(asserter, loader);
        Class[] tests = mapper.findTestsFor(AutoTestTest.class, TestNGTester.class);

        assertSame(AutoTestTest.class, tests[0]);
        assertSame(TestNGTesterTest.class, tests[1]);
    }

    public void unknownTestClassesShouldBeIgnored() throws Exception
    {
        final TestAsserter asserter = mockery.mock(TestAsserter.class);
        final ClassLoader loader = mockery.mock(ClassLoader.class);
        final String testName = Tester.class.getName() + "Test";

        mockery.checking(new Expectations()
        {{
            one(asserter).isTest(AutoTestTest.class); will(returnValue(true));
            one(asserter).isTest(Tester.class); will(returnValue(false));

            one(loader).loadClass(testName); will(throwException(new ClassNotFoundException(testName)));
        }});

        TestMapper mapper = new SimpleTestMapper(asserter, loader);
        Class[] tests = mapper.findTestsFor(AutoTestTest.class, Tester.class);

        assertSame(AutoTestTest.class, tests[0]);
        assertEquals(1, tests.length);
    }
}
