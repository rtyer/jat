package org.jatproject.autotest;

import java.io.File;

public class SearchPath
{
    private File[] pathDirectories;

    public SearchPath(File[] pathDirectories)
    {
        this.pathDirectories = pathDirectories;
    }

    public File find(String fileName)
    {
        for(File directory : pathDirectories)
        {
            File file = new File(directory, fileName);
            if(file.exists()) return file;
        }
        
        return null;
    }
}
