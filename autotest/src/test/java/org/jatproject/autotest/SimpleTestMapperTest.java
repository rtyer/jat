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
        final AutoTestClassLoader loader = mockery.mock(AutoTestClassLoader.class);
        final ClassFiles classes = mockery.mock(ClassFiles.class);

        mockery.checking(new Expectations()
        {{
            one(classes).toClassArray(loader); will(returnValue(new Class[] {ClassFile.class}));
            one(asserter).isTest(ClassFile.class); will(returnValue(false));
            one(loader).loadClass(ClassFile.class.getName() + "Test"); will(returnValue(ClassFileTest.class));
        }});

        TestMapper mapper = new SimpleTestMapper(asserter, loader);
        Class[] tests = mapper.findTestsFor(classes);

        assertSame(ClassFileTest.class, tests[0]);
    }

    public void testClassShouldJustReturnTheClass()
    {
        final TestAsserter asserter = mockery.mock(TestAsserter.class);
        final AutoTestClassLoader loader = mockery.mock(AutoTestClassLoader.class);
        final ClassFiles classes = mockery.mock(ClassFiles.class);

        mockery.checking(new Expectations()
        {{
            one(classes).toClassArray(loader); will(returnValue(new Class[] {ClassFileTest.class}));
            one(asserter).isTest(ClassFileTest.class); will(returnValue(true));
        }});

        TestMapper mapper = new SimpleTestMapper(asserter, loader);
        Class[] tests = mapper.findTestsFor(classes);

        assertSame(ClassFileTest.class, tests[0]);
    }

    public void multipleClassesShouldGetMappedCorrectly() throws Exception
    {
        final TestAsserter asserter = mockery.mock(TestAsserter.class);
        final AutoTestClassLoader loader = mockery.mock(AutoTestClassLoader.class);
        final ClassFiles classes = mockery.mock(ClassFiles.class);

        mockery.checking(new Expectations()
        {{
            one(classes).toClassArray(loader); will(returnValue(new Class[] {ClassFileTest.class, TestNGTester.class}));
            one(asserter).isTest(ClassFileTest.class); will(returnValue(true));
            one(asserter).isTest(TestNGTester.class); will(returnValue(false));

            one(loader).loadClass(TestNGTester.class.getName() + "Test"); will(returnValue(TestNGTesterTest.class));
        }});

        TestMapper mapper = new SimpleTestMapper(asserter, loader);
        Class[] tests = mapper.findTestsFor(classes);

        assertSame(ClassFileTest.class, tests[0]);
        assertSame(TestNGTesterTest.class, tests[1]);
    }

    public void unknownTestClassesShouldBeIgnored() throws Exception
    {
        final TestAsserter asserter = mockery.mock(TestAsserter.class);
        final AutoTestClassLoader loader = mockery.mock(AutoTestClassLoader.class);
        final ClassFiles classes = mockery.mock(ClassFiles.class);
        final String testName = Tester.class.getName() + "Test";

        mockery.checking(new Expectations()
        {{
            one(classes).toClassArray(loader); will(returnValue(new Class[] {ClassFileTest.class, Tester.class}));
            one(asserter).isTest(ClassFileTest.class); will(returnValue(true));
            one(asserter).isTest(Tester.class); will(returnValue(false));

            one(loader).loadClass(testName); will(throwException(new ClassNotFoundException(testName)));
        }});

        TestMapper mapper = new SimpleTestMapper(asserter, loader);
        Class[] tests = mapper.findTestsFor(classes);

        assertSame(ClassFileTest.class, tests[0]);
        assertEquals(1, tests.length);
    }
}
