package org.jatproject.autotest;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class SystemClassPath
{
    public ParentLastUrlClassLoader getIsolatedClassLoader()
    {
        List<File> files = new ArrayList<File>();
        for(String path : System.getProperty("java.class.path").split(File.pathSeparator))
        {
            files.add(new File(path));
        }


        try
        {
            return new ParentLastUrlClassLoader(FileUtils.toURLs(FileUtils.convertFileCollectionToFileArray(files)));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
