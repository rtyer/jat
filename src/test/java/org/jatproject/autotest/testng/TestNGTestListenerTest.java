package org.jatproject.autotest.testng;

import org.jatproject.autotest.TestListener;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.IClass;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class TestNGTestListenerTest
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

    public void shouldCallTestsStartedWhenOnStartIsCalled()
    {
        final TestListener listener = mockery.mock(TestListener.class);

        mockery.checking(new Expectations()
        {{
            one(listener).testsStarted();
        }});

        new TestNGTestListener(listener).onStart(null);
    }

    public void shouldCallTestsEndedWhenOnFinishIsCalled()
    {
        final TestListener listener = mockery.mock(TestListener.class);

        mockery.checking(new Expectations()
        {{
            one(listener).testsEnded();
        }});

        new TestNGTestListener(listener).onFinish(null);        
    }

    public void shouldCallTestPassedWhenOnTestSuccessIsCalled()
    {
        final TestListener listener = mockery.mock(TestListener.class);
        final ITestResult result = mockery.mock(ITestResult.class);
        final IClass clazz = mockery.mock(IClass.class);
        final ITestNGMethod method = mockery.mock(ITestNGMethod.class);

        final String className = "className";
        final String methodName = "methodName";

        mockery.checking(new Expectations()
        {{
            one(result).getTestClass();will(returnValue(clazz));
            one(clazz).getName();will(returnValue(className));

            one(result).getMethod();will(returnValue(method));
            one(method).getMethodName();will(returnValue(methodName));

            one(listener).testPassed(className + "." + methodName);
        }});

        new TestNGTestListener(listener).onTestSuccess(result);
    }

    public void shouldCallTestFailedWhenOnTestFailureIsCalled()
    {
        final TestListener listener = mockery.mock(TestListener.class);
        final ITestResult result = mockery.mock(ITestResult.class);
        final IClass clazz = mockery.mock(IClass.class);
        final ITestNGMethod method = mockery.mock(ITestNGMethod.class);

        final String className = "className";
        final String methodName = "methodName";

        final Throwable throwable = new Exception();
        mockery.checking(new Expectations()
        {{
            one(result).getTestClass();will(returnValue(clazz));
            one(clazz).getName();will(returnValue(className));

            one(result).getMethod();will(returnValue(method));
            one(method).getMethodName();will(returnValue(methodName));
            one(result).getThrowable();will(returnValue(throwable));

            one(listener).testFailed(className + "." + methodName, throwable);
        }});

        new TestNGTestListener(listener).onTestFailure(result);
    }
}
