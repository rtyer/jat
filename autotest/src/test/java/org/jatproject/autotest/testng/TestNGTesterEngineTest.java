package org.jatproject.autotest.testng;

import org.jatproject.autotest.TestListener;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.ITestListener;
import org.testng.TestNG;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.assertFalse;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class TestNGTesterEngineTest
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

        new TestNGTestEngine(testng, null);
    }

    public void shouldAddClassWhenAsserterReturnsTrue()
    {
        final TestNGTestAsserter asserter = mockery.mock(TestNGTestAsserter.class);
        final TestNG testng = mockery.mock(TestNG.class);

        mockery.checking(new Expectations()
        {{
            one(testng).setVerbose(0);
            one(testng).setUseDefaultListeners(false);
            one(asserter).isTest(TestNG.class); will(returnValue(true));
        }});

        assertTrue(new TestNGTestEngine(testng, asserter).add(TestNG.class));
    }

    public void shouldNotAddClassWhenAsserterReturnsFalse()
    {
        final TestNGTestAsserter asserter = mockery.mock(TestNGTestAsserter.class);
        final TestNG testng = mockery.mock(TestNG.class);

        mockery.checking(new Expectations()
        {{
            one(testng).setVerbose(0);
            one(testng).setUseDefaultListeners(false);
            one(asserter).isTest(TestNG.class); will(returnValue(false));
        }});

        assertFalse(new TestNGTestEngine(testng, asserter).add(TestNG.class));
    }

    public void shouldRunTestsThatAreSuccessfullyAddedToTheEngine()
    {
        final TestNG testng = mockery.mock(TestNG.class);
        final TestNGTestAsserter asserter = mockery.mock(TestNGTestAsserter.class);

        final Class testClass = Mockery.class;

        mockery.checking(new Expectations()
        {{
            one(testng).setVerbose(0);
            one(testng).setUseDefaultListeners(false);
            one(asserter).isTest(testClass); will(returnValue(true));

            one(testng).setTestClasses(new Class[] {testClass});
            one(testng).run();
        }});

        TestNGTestEngine testEngine = new TestNGTestEngine(testng, asserter);
        testEngine.add(testClass);
        testEngine.run();
    }

    public void shouldNotRunTestsThatAreNotSuccessfullyAddedToTheEngine()
    {
        final TestNG testng = mockery.mock(TestNG.class);
        final TestNGTestAsserter asserter = mockery.mock(TestNGTestAsserter.class);

        final Class testClass = Mockery.class;
        final Class nonTest = TestNG.class;

        mockery.checking(new Expectations()
        {{
            one(testng).setVerbose(0);
            one(testng).setUseDefaultListeners(false);
            one(asserter).isTest(testClass); will(returnValue(true));
            one(asserter).isTest(nonTest); will(returnValue(false));

            one(testng).setTestClasses(new Class[] {testClass});
            one(testng).run();
        }});

        TestNGTestEngine testEngine = new TestNGTestEngine(testng, asserter);
        testEngine.add(testClass);
        testEngine.add(nonTest);
        testEngine.run();
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

        new TestNGTestEngine(testng, null).run();
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

        new TestNGTestEngine(testng, null).addListener(listener);

    }
}
