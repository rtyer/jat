package org.jatproject.autotest;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClassFile
{
    private Classname classname;
    private File classFile;

    public ClassFile(Classname classname, File classFile)
    {
        this.classname = classname;
        this.classFile = classFile;
    }

    public String getClassName()
    {
        return classname.getFullyQulifiedClassName();
    }

    public void appendClass(List<Class> classes, AutoTestClassLoader loader)
    {
        classes.add(loader.defineClass(getClassName(), getContents()));
    }

    public byte[] getContents()
    {
        try
        {
            return FileUtils.readFileToByteArray(classFile);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
