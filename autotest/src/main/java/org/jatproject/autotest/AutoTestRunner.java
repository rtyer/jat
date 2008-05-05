package org.jatproject.autotest;

import org.jatproject.autotest.listeners.ConsoleTestListener;
import org.jatproject.autotest.listeners.GrowlTestListener;
import org.jatproject.autotest.repositories.SimpleReferenceRepository;
import org.jatproject.autotest.testng.TestNGTestEngine;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class AutoTestRunner extends TimerTask
{
    private long lastRunTime;
    private ClassPath classpath;
    private ReferenceRepository repository;

    public AutoTestRunner(File[] classDirs)
    {
        lastRunTime = System.currentTimeMillis();
        classpath = new ClassPath(classDirs);
        repository = new SimpleReferenceRepository(classpath);
    }

    public void run()
    {
        Set<ClassFile> classpathChanges = classpath.findChangesSince(lastRunTime);
        lastRunTime = System.currentTimeMillis();

        Set<ClassFile> dependencies = new HashSet<ClassFile>();
        for(ClassFile clazz : classpathChanges)
        {
            dependencies.addAll(repository.findReferencesTo(clazz));
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
