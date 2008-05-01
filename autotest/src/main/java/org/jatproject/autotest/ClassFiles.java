package org.jatproject.autotest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ClassFiles implements Iterable<ClassFile>
{
    private List<ClassFile> files = new ArrayList<ClassFile>();

    public ClassFiles(ClassFile... files)
    {
        this.files.addAll(Arrays.asList(files));
    }

    public void addAll(File baseDirectory, File[] classFiles)
    {
        for(File classFile : classFiles)
        {
            files.add(new ClassFile(baseDirectory, classFile));
        }
    }

    public Class[] toClassArray(AutoTestClassLoader loader)
    {
        List<Class> classes = new ArrayList<Class>();

        for(ClassFile file : files)
        {
            try
            {
                classes.add(loader.loadClass(file.getClassName(), file));
            }
            catch(ClassNotFoundException e)
            {
            }
        }

        return classes.toArray(new Class[files.size()]);
    }

    public Iterator<ClassFile> iterator()
    {
        return files.iterator();
    }

    public ClassFile get(int index)
    {
        return files.get(index);
    }
}
