package org.jatproject.autotest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ClassPath implements Iterable<ClassFile>
{
    private static final IOFileFilter CLASS_FILE_FILTER = FileFilterUtils.suffixFileFilter(".class");

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

    public Set<ClassFile> findChangesSince(long time)
    {
        IOFileFilter filter = new AndFileFilter(FileFilterUtils.ageFileFilter(time, false), CLASS_FILE_FILTER);
        return findClassFiles(filter);
    }

    public Iterator<ClassFile> iterator()
    {
        return findClassFiles(CLASS_FILE_FILTER).iterator();
    }

    private Set<ClassFile> findClassFiles(IOFileFilter filter)
    {
        Set<ClassFile> files = new HashSet<ClassFile>();

        for(File directory : pathDirectories)
        {
            Collection classFiles = FileUtils.listFiles(directory, filter, TrueFileFilter.INSTANCE);
            for(File classFile : FileUtils.convertFileCollectionToFileArray(classFiles))
            {
                files.add(new ClassFile(new Classname(directory, classFile), classFile));
            }
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
