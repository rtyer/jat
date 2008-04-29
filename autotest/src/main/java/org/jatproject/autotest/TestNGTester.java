package org.jatproject.autotest;

import org.testng.TestNG;

public class TestNGTester implements Tester
{
    private TestMapper mapper;
    private TestNG testng;

    public TestNGTester(TestNG testng, TestMapper mapper)
    {
        this.mapper = mapper;
        this.testng = testng;

        testng.setVerbose(0);
        testng.setUseDefaultListeners(false);
    }

    public void runTests(Class... classes)
    {
        testng.setTestClasses(mapper.findTestsFor(classes));
    }
}
