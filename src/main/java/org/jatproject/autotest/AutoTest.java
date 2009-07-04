package org.jatproject.autotest;

import org.jatproject.autotest.junit4.JUnit4TestEngine;
import org.jatproject.autotest.listeners.ConsoleTestListener;
import org.jatproject.autotest.listeners.GrowlTestListener;
import org.jatproject.autotest.testng.TestNGTestEngine;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class AutoTest 
{
	private Set<File> classDirectories;

	public AutoTest(Set<File> classDirectories)
	{
		this.classDirectories = classDirectories;
	}
	
    public long run(long lastRunTime)
    {
        ClassPath classpath = new ClassPath(classDirectories);
        ReferenceRepository repository = new ReferenceRepository(classpath);

        Set<ClassFile> classpathChanges = classpath.findChangesSince(lastRunTime);
        long nextRunTime = System.currentTimeMillis();

        Set<ClassFile> dependencies = new HashSet<ClassFile>();
        for(ClassFile clazz : classpathChanges)
        {
            dependencies.addAll(repository.findReferencesTo(clazz));
        }

        System.out.print(".");

        Tester tester = new Tester(new TestNGTestEngine(), new JUnit4TestEngine());
        tester.addListener(new ConsoleTestListener());
        tester.addListener(new GrowlTestListener());
        tester.runTests(dependencies);
        
        return nextRunTime;
    }
}
