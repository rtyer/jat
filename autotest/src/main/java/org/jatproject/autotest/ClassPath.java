package org.jatproject.autotest;

import java.io.File;

public class ClassPath
{
    private File[] pathDirectories;

    public ClassPath(File[] pathDirectories)
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
