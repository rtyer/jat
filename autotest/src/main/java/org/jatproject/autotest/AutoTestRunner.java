package org.jatproject.autotest;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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
        try
        {
            ClassLoader loader = new SystemClassPath().getIsolatedClassLoader();
            Thread.currentThread().setContextClassLoader(loader);
            
            Class<?> autotestClass = Class.forName("org.jatproject.autotest.AutoTest", true, loader);

            Constructor constructor = autotestClass.getConstructor(File[].class);
            Object object = constructor.newInstance(new Object[] {classDirs});
            Method method = autotestClass.getMethod("run", long.class);
            lastRunTime = (Long) method.invoke(object, lastRunTime);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        File classDir = new File(args[0]);
        File testDir = new File(args[1]);
        new Timer().schedule(new AutoTestRunner(new File[]{classDir, testDir}), 0, 10000);
    }
}
