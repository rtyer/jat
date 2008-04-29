package org.jatproject.autotest;

public class AutoTest implements ClassChangeListener
{
    private Tester tester;

    public AutoTest(ClassMonitor monitor, Tester tester)
    {
        this.tester = tester;
        monitor.addListener(this);
    }

    public void changedClasses(Class... changed)
    {
        tester.runTests(changed);
    }
}