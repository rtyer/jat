package org.jatproject.autotest;

import java.io.File;
import java.util.TimerTask;
import org.jatproject.autotest.testng.TestNGTestAsserter;
import org.jatproject.autotest.testng.TestNGTester;

public class AutoTestRunner extends TimerTask
{
    private long lastRunTime;
    private File[] classDirs;

    public AutoTestRunner(File[] classDirs)
    {
        this.classDirs = classDirs;
        lastRunTime = System.currentTimeMillis();
    }

    public void run()
    {
        ClassPath classpath = new ClassPath(classDirs);
        AutoTestClassLoader loader = new AutoTestClassLoader(classpath);
        ClassFiles classpathChanges = classpath.findChangesSince(lastRunTime);
        lastRunTime = System.currentTimeMillis();


        TestMapper mapper = new SimpleTestMapper(new TestNGTestAsserter(), loader);
        new TestNGTester(mapper).runTests(classpathChanges);
    }
}
