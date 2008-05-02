package org.jatproject.autotest.listeners;

import java.io.IOException;

import org.jatproject.autotest.TestListener;

public class GrowlTestListener implements TestListener 
{
	private int totalPassed, totalFailed;

	public void testFailed(String testName, Throwable failure) 
	{
		totalFailed++;
	}

	public void testPassed(String testName) 
	{
		totalPassed++;
	}

	public void testsEnded() 
	{
		Runtime runTime = Runtime.getRuntime();
		try 
		{
			if (totalFailed == 0)
			{
				
				//runTime.exec(new String[]{"/usr/local/bin/growlnotify", "-s", "-t", "Tests Finished", "-m", "Tests Passing: " + totalPassed + ". No Failures", "--image", "/Users/cthiel/projects/jat/autotest/images/pass.png"});
				runTime.exec(new String[]{"/usr/local/bin/growlnotify", "-t", "Tests Finished", "-m", "Tests Passing: " + totalPassed + ". No Failures."});
			}
			else
			{
				//runTime.exec(new String[]{"/usr/local/bin/growlnotify", "-s", "-t", "Tests Finished", "-m", "Tests Passing: " + totalPassed + ". Total Failures: " + totalFailed + ".", "--image", "/Users/cthiel/projects/jat/autotest/images/fail.png"});
				runTime.exec(new String[]{"/usr/local/bin/growlnotify", "-t", "Tests Finished", "-m", "Tests Passing: " + totalPassed + " Total Failures: " + totalFailed});
			}
			
		} catch (IOException e) 
		{
			System.out.println("growlnotify not installed on this machine.");
		}
	}

	public void testsStarted() 
	{
		totalPassed = totalFailed = 0;
	}

}
