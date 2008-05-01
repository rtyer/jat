package org.jatproject.autotest.testng;

import org.jatproject.autotest.TestListener;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.ITestListener;
import org.testng.TestNG;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class TestNGTesterTest
{
    private Mockery mockery;

    @BeforeMethod
    public void beforeEach()
    {
        mockery = new Mockery()
        {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
            }
        };
    }

    @AfterMethod
    public void afterEach()
    {
        mockery.assertIsSatisfied();
    }

    public void shouldSetTheCorrectTestNGDefaults()
    {
        final TestNG testng = mockery.mock(TestNG.class);

        mockery.checking(new Expectations()
        {{
            one(testng).setVerbose(0);
            one(testng).setUseDefaultListeners(false);
        }});

        new TestNGTester(testng);
    }

    public void shouldSetCorrectTestsToRunWhenRunTestsCalled()
    {
        final TestNG testng = mockery.mock(TestNG.class);

        final Class testClass = Mockery.class;

        mockery.checking(new Expectations()
        {{
            one(testng).setVerbose(0);
            one(testng).setUseDefaultListeners(false);

            one(testng).setTestClasses(new Class[] {testClass});
            one(testng).run();
        }});

        new TestNGTester(testng).runTests(testClass);
    }

    public void shouldNotRunWhenTestClassesIsEmpty()
    {
        final TestNG testng = mockery.mock(TestNG.class);

        mockery.checking(new Expectations()
        {{
            one(testng).setVerbose(0);
            one(testng).setUseDefaultListeners(false);
            never(testng).run();
        }});

        new TestNGTester(testng).runTests();
    }

    public void shouldAddTestNGListenerWhenTestListenerAdded()
    {
        final TestNG testng = mockery.mock(TestNG.class);
        final TestListener listener = mockery.mock(TestListener.class);

        mockery.checking(new Expectations()
        {{
            one(testng).setVerbose(0);
            one(testng).setUseDefaultListeners(false);
            one(testng).addListener((ITestListener)with(a(TestNGTestListener.class)));
        }});

        new TestNGTester(testng).addTestListener(listener);

    }
}
