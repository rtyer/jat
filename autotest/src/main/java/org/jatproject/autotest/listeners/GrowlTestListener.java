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
			runTime.exec("/usr/local/bin/growlnotify -s \"Tests Passed\" -m \"All Tests Run\"");
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void testsStarted() 
	{
		totalPassed = totalFailed = 0;
	}

}
