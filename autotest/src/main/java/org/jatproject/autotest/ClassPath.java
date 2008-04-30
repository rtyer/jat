package org.jatproject.autotest;

import java.io.File;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;

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
        IOFileFilter filter = FileFilterUtils.ageFileFilter(time, false);

        ClassFiles files = new ClassFiles();

        for(File directory : pathDirectories)
        {
            Collection changes = FileUtils.listFiles(directory, filter, TrueFileFilter.INSTANCE);
            files.addAll(directory, FileUtils.convertFileCollectionToFileArray(changes));
        }

        return files;
    }
}
