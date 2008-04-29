package org.jatproject.autotest;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class AutoTestTest
{
    private Mockery mockery;

    @BeforeMethod
    public void beforeEach()
    {
        mockery = new Mockery();
    }

    @AfterMethod
    public void afterEach()
    {
        mockery.assertIsSatisfied();
    }

    public void runsTestsWhenChangesAreDetected()
    {
        final Tester tester = mockery.mock(Tester.class);
        MockClassMonitor monitor = new MockClassMonitor();

        mockery.checking(new Expectations()
        {{
            one(tester).runTests(Tester.class);
        }});

        new AutoTest(monitor, tester);

        monitor.signalClassChange(Tester.class);
    }

    private class MockClassMonitor implements ClassMonitor
    {
        private ClassChangeListener listener;

        public void addListener(ClassChangeListener listener)
        {
            this.listener = listener;
        }

        public void signalClassChange(Class changed)
        {
            listener.changedClasses(changed);
        }
    }
}