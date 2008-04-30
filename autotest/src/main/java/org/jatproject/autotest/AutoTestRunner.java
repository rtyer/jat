package org.jatproject.autotest;

import java.io.File;
import java.util.Timer;
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
        ClassFiles classpathChanges = classpath.findChangesSince(lastRunTime);
        lastRunTime = System.currentTimeMillis();

        AutoTestClassLoader loader = new AutoTestClassLoader(classpath);
        TestMapper mapper = new SimpleTestMapper(new TestNGTestAsserter(), loader);
        new TestNGTester(mapper).runTests(classpathChanges);
    }

    public static void main(String[] args)
    {
        File classDir = new File("/Users/aesterline/Projects/jat/autotest/target/classes/");
        File testDir = new File("/Users/aesterline/Projects/jat/autotest/target/test/classes/");
        new Timer().schedule(new AutoTestRunner(new File[] {classDir, testDir}), 0, 10000);
    }
}
