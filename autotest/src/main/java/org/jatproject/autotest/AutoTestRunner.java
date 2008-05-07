package org.jatproject.autotest;

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
        lastRunTime = new AutoTest(classDirs).run(lastRunTime);        
    }
    
    public static void main(String[] args)
    {
        File classDir = new File(args[0]);
        File testDir = new File(args[1]);
        new Timer().schedule(new AutoTestRunner(new File[]{classDir, testDir}), 0, 10000);
    }
}
