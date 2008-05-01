package org.jatproject.autotest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import org.jatproject.autotest.listeners.ConsoleTestListener;
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

        TestMapper mapper = new SimpleTestMapper(classpath);
        ArrayList<Class> testClasses = new ArrayList<Class>();
        for(ClassFile clazz : classpathChanges)
        {
            Class[] tests = mapper.findTestsFor(clazz).toClassArray(loader);
            testClasses.addAll(Arrays.asList(tests));
        }


        TestNGTester tester = new TestNGTester();
        tester.addTestListener(new ConsoleTestListener());
        tester.runTests(testClasses.toArray(new Class[testClasses.size()]));
    }

    public static void main(String[] args)
    {
        File classDir = new File("/Users/aesterline/Projects/jat/autotest/target/classes/");
        File testDir = new File("/Users/aesterline/Projects/jat/autotest/target/test/classes/");
        new Timer().schedule(new AutoTestRunner(new File[]{classDir, testDir}), 0, 10000);
    }
}
