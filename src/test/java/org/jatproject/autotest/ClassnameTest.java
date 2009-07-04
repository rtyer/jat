package org.jatproject.autotest;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

import java.io.File;

@Test
public class ClassnameTest
{
    public void shouldKnowFileNameFromFullyQualifiedClassName()
    {
        Classname classname = new Classname("org.name.Myname");
        assertEquals("org/name/Myname.class", classname.getClassFileName());
    }

    public void shouldBeAbleToCalculateClassNameFromBaseDirectoryAndFile()
    {
        File baseDirectory = new File("/Users/whocares/Projects/jat/");
        File file = new File("/Users/whocares/Projects/jat/org/myproject/MyClass.class");

        Classname name = new Classname(baseDirectory, file);

        assertEquals("org.myproject.MyClass", name.getFullyQulifiedClassName());
    }

    public void shouldCalculateCorrectClassNameEvenIfClassIsInTheDirectory()
    {
        File baseDirectory = new File("/Users/whocares/Projects/jat/");
        File file = new File("/Users/whocares/Projects/jat/org/class/MyClass.class");

        Classname name = new Classname(baseDirectory, file);

        assertEquals("org.class.MyClass", name.getFullyQulifiedClassName());
    }

    public void shouldWorkEvenIfTheBaseDirectoryDoesNotHaveTrailingSlash()
    {
        File baseDirectory = new File("/Users/whocares/Projects/jat");
        File file = new File("/Users/whocares/Projects/jat/org/myproject/MyClass.class");

        Classname name = new Classname(baseDirectory, file);

        assertEquals("org.myproject.MyClass", name.getFullyQulifiedClassName());
    }

    public void shouldWorkInWindows()
    {
        File baseDirectory = new File("c:\\Projects\\jat");
        File file = new File("c:\\Projects\\jat\\org\\myproject\\MyClass.class");

        Classname name = new Classname(baseDirectory, file);

        assertEquals("org.myproject.MyClass", name.getFullyQulifiedClassName());
    }
}
