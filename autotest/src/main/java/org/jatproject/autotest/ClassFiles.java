package org.jatproject.autotest;

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

    public void add(ClassFile file)
    {
        files.add(file);
    }

    public void addAll(ClassFiles classFiles)
    {
        this.files.addAll(classFiles.files);
    }

    public Class[] toClassArray(AutoTestClassLoader loader)
    {
        List<Class> classes = new ArrayList<Class>();

        for(ClassFile file : files)
        {
            file.appendClass(classes, loader);
        }

        return classes.toArray(new Class[classes.size()]);
    }

    public Iterator<ClassFile> iterator()
    {
        return files.iterator();
    }

    public ClassFile get(int index)
    {
        return files.get(index);
    }

    public boolean isEmpty()
    {
        return files.isEmpty();
    }
}
