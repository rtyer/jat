package org.jatproject.autotest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
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

        new TestNGTester(testng, null);
    }

    public void shouldSetCorrectTestsToRunWhenRunTestsCalled()
    {
        final TestNG testng = mockery.mock(TestNG.class);
        final TestMapper mapper = mockery.mock(TestMapper.class);

        final Class[] changeClasses = {TestNG.class, TestMapper.class};
        final Class[] testClasses = {Mockery.class};

        mockery.checking(new Expectations()
        {{
            one(testng).setVerbose(0);
            one(testng).setUseDefaultListeners(false);

            one(mapper).findTestsFor(changeClasses);will(returnValue(testClasses));
            one(testng).setTestClasses(testClasses);
            one(testng).run();
        }});

        new TestNGTester(testng, mapper).runTests(changeClasses);
    }
}
