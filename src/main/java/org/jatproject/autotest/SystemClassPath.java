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

    public static Set<File> convertClassPathToFiles()
    {
        Set<File> files = new HashSet<File>();

        for(String path : System.getProperty("java.class.path").split(File.pathSeparator))
        {
            files.add(new File(path));
        }

        return files;
    }

    public SystemClassPath(Set<File> pathElements)
    {
        files = new HashSet<File>();
        classDirectories = new HashSet<File>();

        for(File file : pathElements)
        {
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

    public Set<File> getClassDirectories()
    {
        return classDirectories;
    }
}
