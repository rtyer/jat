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

    public Class getClazz()
    {
        try
        {
            return Class.forName(getClassName(), true, ClassFile.class.getClassLoader());
        } catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
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
    public int hashCode(){
    	return getClassName().hashCode();
    }
    public boolean equals(Object o){
        if(this == o)
        {
            return true;
        }
        if(o == null || getClass() != o.getClass())
        {
            return false;
        }

        ClassFile classfile = (ClassFile) o;
        return this.getClassName() == classfile.getClassName();
    }
    
}
