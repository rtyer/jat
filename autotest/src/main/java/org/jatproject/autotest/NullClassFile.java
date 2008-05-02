package org.jatproject.autotest;

import java.util.List;

public class NullClassFile extends ClassFile
{
    private Classname classname;

    public NullClassFile(Classname classname)
    {
        super(null, null);
        this.classname = classname;
    }

    public String getClassName()
    {
        return classname.getFullyQulifiedClassName();
    }

    public void appendClass(List<Class> classes, AutoTestClassLoader loader)
    {
    }

    public byte[] getContents()
    {
        return new byte[0];
    }
}
