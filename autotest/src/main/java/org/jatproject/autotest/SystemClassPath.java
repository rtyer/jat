package org.jatproject.autotest;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.net.URL;

public class SystemClassPath
{
    private final Set<File> files;
    private final Set<File> classDirectories;

    public SystemClassPath()
    {
        files = new HashSet<File>();
        classDirectories = new HashSet<File>();

        for(String path : System.getProperty("java.class.path").split(File.pathSeparator))
        {
            File file = new File(path);
            if(file.exists() && file.canRead())
            {
                if(file.isDirectory()) classDirectories.add(file);
                
                files.add(file);
            }
        }
    }

    public IsolatedClassLoader getIsolatedClassLoader()
    {
        try
        {
            URL[] urls = FileUtils.toURLs(FileUtils.convertFileCollectionToFileArray(files));
            return new IsolatedClassLoader(urls);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public File[] getClassDirectories()
    {
        return classDirectories.toArray(new File[classDirectories.size()]);
    }
}
