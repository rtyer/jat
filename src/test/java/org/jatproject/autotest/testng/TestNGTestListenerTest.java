package org.jatproject.autotest.testng;

import org.jatproject.autotest.TestListener;
import org.testng.IClass;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Test
public class TestNGTestListenerTest
{
    public void shouldCallTestsStartedWhenOnStartIsCalled()
    {
        TestListener listener = mock(TestListener.class);
        new TestNGTestListener(listener).onStart(null);

        verify(listener).testsStarted();
    }

    public void shouldCallTestsEndedWhenOnFinishIsCalled()
    {
        TestListener listener = mock(TestListener.class);
        new TestNGTestListener(listener).onFinish(null);

        verify(listener).testsEnded();
    }

    public void shouldCallTestPassedWhenOnTestSuccessIsCalled()
    {
        TestListener listener = mock(TestListener.class);
        ITestResult result = mock(ITestResult.class);
        IClass clazz = mock(IClass.class);
        ITestNGMethod method = mock(ITestNGMethod.class);

        String className = "className";
        String methodName = "methodName";

        when(result.getTestClass()).thenReturn(clazz);
        when(clazz.getName()).thenReturn(className);
        when(result.getMethod()).thenReturn(method);
        when(method.getMethodName()).thenReturn(methodName);

        new TestNGTestListener(listener).onTestSuccess(result);

        verify(listener).testPassed(className + "." + methodName);
    }

    public void shouldCallTestFailedWhenOnTestFailureIsCalled()
    {
        TestListener listener = mock(TestListener.class);
        ITestResult result = mock(ITestResult.class);
        IClass clazz = mock(IClass.class);
        ITestNGMethod method = mock(ITestNGMethod.class);

        String className = "className";
        String methodName = "methodName";

        Throwable throwable = new Exception();

        when(result.getTestClass()).thenReturn(clazz);
        when(clazz.getName()).thenReturn(className);
        when(result.getMethod()).thenReturn(method);
        when(method.getMethodName()).thenReturn(methodName);
        when(result.getThrowable()).thenReturn(throwable);

        new TestNGTestListener(listener).onTestFailure(result);

        verify(listener).testFailed(className + "." + methodName, throwable);
    }
}
