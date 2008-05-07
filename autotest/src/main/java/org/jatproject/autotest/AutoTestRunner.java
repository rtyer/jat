package org.jatproject.autotest;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
            ClassLoader loader = new ParentLastUrlClassLoader(getClassPathAsURLs());
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

    private static URL[] getClassPathAsURLs() throws Exception
    {
        List<File> files = new ArrayList<File>();
        for(String path : System.getProperty("java.class.path").split(File.pathSeparator))
        {
            files.add(new File(path));        
        }

        return FileUtils.toURLs(FileUtils.convertFileCollectionToFileArray(files));
    }
}
