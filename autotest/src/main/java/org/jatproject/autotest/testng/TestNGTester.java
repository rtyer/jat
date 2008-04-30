package org.jatproject.autotest.testng;

import org.testng.TestNG;
import org.jatproject.autotest.Tester;
import org.jatproject.autotest.TestMapper;
import org.jatproject.autotest.SimpleTestMapper;

public class TestNGTester implements Tester
{
    private TestMapper mapper;
    private TestNG testng;

    public TestNGTester()
    {
        this(new TestNG(), new SimpleTestMapper(new TestNGTestAsserter(), ClassLoader.getSystemClassLoader()));
    }

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
        testng.run();
    }
}