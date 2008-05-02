package org.jatproject.autotest;

public class Classname
{
    private String fullyQulifiedClassName;

    public Classname(String fullyQulifiedClassName)
    {
        this.fullyQulifiedClassName = fullyQulifiedClassName;
    }

    public String getFullyQulifiedClassName()
    {
        return fullyQulifiedClassName;
    }

    public String getClassFileName()
    {
        return fullyQulifiedClassName.replace('.', '/') + ".class";
    }
}
