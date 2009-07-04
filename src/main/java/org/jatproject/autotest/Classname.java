package org.jatproject.autotest;

import java.io.File;

public class Classname
{
    private String fullyQulifiedClassName;

    public Classname(String fullyQulifiedClassName)
    {
        this.fullyQulifiedClassName = fullyQulifiedClassName;
    }

    public Classname(File baseDirectory, File classFile)
    {
        String classFilePath = classFile.getAbsolutePath();
        String baseDirectoryPath = baseDirectory.getAbsolutePath();

        String fullyQualifiedClassName = classFilePath.replace(baseDirectoryPath, "");
        fullyQualifiedClassName = fullyQualifiedClassName.replace(".class", "");
        fullyQualifiedClassName = fullyQualifiedClassName.replaceAll("/|\\\\", ".");
        fullyQualifiedClassName = fullyQualifiedClassName.replaceFirst("^\\.", "");

        this.fullyQulifiedClassName = fullyQualifiedClassName;
    }

    public String getFullyQulifiedClassName()
    {
        return fullyQulifiedClassName;
    }

    public String getClassFileName()
    {
        return fullyQulifiedClassName.replace('.', '/') + ".class";
    }

    public boolean equals(Object o)
    {
        if(this == o)
        {
            return true;
        }
        if(o == null || getClass() != o.getClass())
        {
            return false;
        }

        Classname classname = (Classname) o;

        if(fullyQulifiedClassName != null ? !fullyQulifiedClassName.equals(classname.fullyQulifiedClassName) : classname.fullyQulifiedClassName != null)
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        return (fullyQulifiedClassName != null ? fullyQulifiedClassName.hashCode() : 0);
    }
}
