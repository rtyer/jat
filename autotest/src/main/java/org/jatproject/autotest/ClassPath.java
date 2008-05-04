package org.jatproject.autotest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.util.Collection;

public class ClassPath
{
    private File[] pathDirectories;

    public ClassPath(File[] pathDirectories)
    {
        this.pathDirectories = pathDirectories;
    }

    public boolean isOnPath(Classname classname)
    {
        return findBaseDirectoryForClass(classname) != null;
    }

    public ClassFile find(Classname classname)
    {
        File baseDirectory = findBaseDirectoryForClass(classname);
        if(baseDirectory == null) return null;

        return new ClassFile(classname, new File(baseDirectory, classname.getClassFileName()));        
    }

    public ClassFiles findChangesSince(long time)
    {
        IOFileFilter filter = FileFilterUtils.ageFileFilter(time, false);

        ClassFiles files = new ClassFiles();

        for(File directory : pathDirectories)
        {
            Collection changes = FileUtils.listFiles(directory, filter, TrueFileFilter.INSTANCE);
            files.addAll(directory, FileUtils.convertFileCollectionToFileArray(changes));
        }

        return files;
    }

    private File findBaseDirectoryForClass(Classname classname)
    {
        for(File directory : pathDirectories)
        {
            File file = new File(directory, classname.getClassFileName());
            if(file.exists()) return directory;
        }

        return null;
    }
}
