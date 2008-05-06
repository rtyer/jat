package org.jatproject.autotest;

import org.jatproject.autotest.junit4.JUnit4TestEngine;
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
    private File[] classDirs;

    public AutoTestRunner(File[] classDirs)
    {
        this.classDirs = classDirs;
        lastRunTime = System.currentTimeMillis();
    }

    public void run()
    {
        ClassPath classpath = new ClassPath(classDirs);
        ReferenceRepository repository = new SimpleReferenceRepository(classpath);

        Set<ClassFile> classpathChanges = classpath.findChangesSince(lastRunTime);
        lastRunTime = System.currentTimeMillis();

        Set<ClassFile> dependencies = new HashSet<ClassFile>();
        for(ClassFile clazz : classpathChanges)
        {
            dependencies.addAll(repository.findReferencesTo(clazz));
        }
        TestEngine[] engines = {new TestNGTestEngine(), new JUnit4TestEngine()};
        Tester tester = new Tester(engines);
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
