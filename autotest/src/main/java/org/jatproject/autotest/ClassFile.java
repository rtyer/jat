package org.jatproject.autotest;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClassFile
{
    private Classname classname;
    private File classFile;
    private AutoTestClassLoader loader;

    public ClassFile(Classname classname, File classFile, AutoTestClassLoader loader)
    {
        this.classname = classname;
        this.classFile = classFile;
        this.loader = loader;
    }

    public String getClassName()
    {
        return classname.getFullyQulifiedClassName();
    }

    public Class getClazz()
    {
        return loader.defineClass(getClassName(), getContents());    
    }

    public void appendClass(List<Class> classes)
    {
        classes.add(getClazz());
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
