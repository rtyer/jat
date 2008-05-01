package org.jatproject.autotest;

public interface Tester
{
    void addTestListener(TestListener listener);
    void runTests(Class... classes);
}
