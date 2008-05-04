package org.jatproject.autotest;

import org.jatproject.autotest.listeners.ConsoleTestListener;
import org.jatproject.autotest.listeners.GrowlTestListener;
import org.jatproject.autotest.repositories.SimpleDependencyRepository;
import org.jatproject.autotest.testng.TestNGTestEngine;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

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

        DependencyRepository repository = new SimpleDependencyRepository(classpath);
        ClassFiles dependencies = new ClassFiles();
        for(ClassFile clazz : classpathChanges)
        {
            dependencies.addAll(repository.findDependenciesFor(clazz));
        }

        Tester tester = new Tester(new TestNGTestEngine());
        tester.addListener(new ConsoleTestListener());
        tester.addListener(new GrowlTestListener());
        tester.runTests(dependencies);        
    }

    public static void main(String[] args)
    {
        File classDir = new File(args[0]);
        File testDir = new File(args[1]);
        new Timer().schedule(new AutoTestRunner(new File[]{classDir, testDir}), 0, 10000);
    }
}
