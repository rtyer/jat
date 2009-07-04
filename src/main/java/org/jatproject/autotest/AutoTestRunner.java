package org.jatproject.autotest;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class AutoTestRunner extends TimerTask
{
    private long lastRunTime;
    private SystemClassPath classpath;

    public AutoTestRunner(Set<File> classpathElements)
    {
        lastRunTime = System.currentTimeMillis();
        classpath = new SystemClassPath(classpathElements);
    }

    public void run()
    {
        try
        {
            ClassLoader loader = classpath.getIsolatedClassLoader();

            Thread.currentThread().setContextClassLoader(loader);
            Class<?> autotestClass = Class.forName("org.jatproject.autotest.AutoTest", true, loader);

            Constructor constructor = autotestClass.getConstructor(Set.class);
            Object object = constructor.newInstance(classpath.getClassDirectories());
            Method method = autotestClass.getMethod("run", long.class);
            lastRunTime = (Long) method.invoke(object, lastRunTime);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new Timer().schedule(new AutoTestRunner(SystemClassPath.convertClassPathToFiles()), 0, 10000);
    }
}
