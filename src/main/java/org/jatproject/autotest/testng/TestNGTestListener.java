package org.jatproject.autotest.testng;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;
import org.jatproject.autotest.TestListener;

public class TestNGTestListener implements ITestListener
{
    private TestListener listener;

    public TestNGTestListener(TestListener listener)
    {
        this.listener = listener;
    }

    public void onTestStart(ITestResult iTestResult)
    {
    }

    public void onTestSuccess(ITestResult iTestResult)
    {
        listener.testPassed(iTestResult.getTestClass().getName() + "." + iTestResult.getMethod().getMethodName());
    }

    public void onTestFailure(ITestResult iTestResult)
    {
        listener.testFailed(iTestResult.getTestClass().getName() + "." + iTestResult.getMethod().getMethodName(), iTestResult.getThrowable());
    }

    public void onTestSkipped(ITestResult iTestResult)
    {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult)
    {

    }

    public void onStart(ITestContext iTestContext)
    {
        listener.testsStarted();
    }

    public void onFinish(ITestContext iTestContext)
    {
        listener.testsEnded();
    }
}
