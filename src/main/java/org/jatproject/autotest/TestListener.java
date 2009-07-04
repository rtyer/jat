package org.jatproject.autotest;

public interface TestListener
{
    void testsStarted();
    void testPassed(String testName);
    void testFailed(String testName, Throwable failure);
    void testsEnded();
}
