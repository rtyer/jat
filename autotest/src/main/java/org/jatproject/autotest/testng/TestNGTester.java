package org.jatproject.autotest.testng;

import org.jatproject.autotest.ClassFiles;
import org.jatproject.autotest.TestMapper;
import org.jatproject.autotest.Tester;
import org.testng.TestNG;

public class TestNGTester implements Tester
{
    private TestMapper mapper;
    private TestNG testng;

    public TestNGTester(TestMapper mapper)
    {
        this(new TestNG(), mapper);
    }

    public TestNGTester(TestNG testng, TestMapper mapper)
    {
        this.mapper = mapper;
        this.testng = testng;

        testng.setVerbose(0);
        testng.setUseDefaultListeners(false);
    }

    public void runTests(ClassFiles classes)
    {
        testng.setTestClasses(mapper.findTestsFor(classes));
        testng.run();
    }
}