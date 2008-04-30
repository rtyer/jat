package org.jatproject.autotest;

import java.io.File;
import java.io.FileFilter;
import org.apache.commons.io.filefilter.AgeFileFilter;

public class ClassPath
{
    private File[] pathDirectories;

    public ClassPath(File[] pathDirectories)
    {
        this.pathDirectories = pathDirectories;
    }

    public ClassFile find(String classname)
    {
        String filename = classname.replace('.', '/') + ".class";

        for(File directory : pathDirectories)
        {
            File file = new File(directory, filename);
            if(file.exists()) return new ClassFile(directory, file);
        }
        
        return null;
    }

    public ClassFiles findChangesSince(long time)
    {
        ClassFiles files = new ClassFiles();

        for(File directory : pathDirectories)
        {
            File[] changes = directory.listFiles((FileFilter)new AgeFileFilter(time, false));
            files.addAll(directory, changes);
        }

        return files;
    }
}
