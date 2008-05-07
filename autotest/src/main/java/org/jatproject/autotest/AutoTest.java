package org.jatproject.autotest;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.jatproject.autotest.junit4.JUnit4TestEngine;
import org.jatproject.autotest.listeners.ConsoleTestListener;
import org.jatproject.autotest.listeners.GrowlTestListener;
import org.jatproject.autotest.repositories.SimpleReferenceRepository;
import org.jatproject.autotest.testng.TestNGTestEngine;

public class AutoTest 
{
	private File[] classDirectories;

	public AutoTest(File[] classDirectories)
	{
		this.classDirectories = classDirectories;
	}
	
    public long run(long lastRunTime)
    {
        ClassPath classpath = new ClassPath(classDirectories);
        ReferenceRepository repository = new SimpleReferenceRepository(classpath);

        Set<ClassFile> classpathChanges = classpath.findChangesSince(lastRunTime);
        long nextRunTime = System.currentTimeMillis();

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
        
        return nextRunTime;
    }
}
