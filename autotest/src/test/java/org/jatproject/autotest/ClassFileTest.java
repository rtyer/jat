package org.jatproject.autotest;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;

import java.io.File;

@Test
public class ClassFileTest
{
    public void shouldBeAbleToCalculateClassNameFromBaseDirectoryAndFile()
    {
        File baseDirectory = new File("/Users/whocares/Projects/jat/");
        File file = new File("/Users/whocares/Projects/jat/org/myproject/MyClass.class");

        ClassFile classFile = new ClassFile(baseDirectory, file);

        assertEquals("org.myproject.MyClass", classFile.getClassName());
    }

    public void shouldCalculateCorrectClassNameEvenIfClassIsInTheDirectory()
    {
        File baseDirectory = new File("/Users/whocares/Projects/jat/");
        File file = new File("/Users/whocares/Projects/jat/org/class/MyClass.class");

        ClassFile classFile = new ClassFile(baseDirectory, file);

        assertEquals("org.class.MyClass", classFile.getClassName());        
    }

    public void shouldWorkEvenIfTheBaseDirectoryDoesNotHaveTrailingSlash()
    {
        File baseDirectory = new File("/Users/whocares/Projects/jat");
        File file = new File("/Users/whocares/Projects/jat/org/myproject/MyClass.class");

        ClassFile classFile = new ClassFile(baseDirectory, file);

        assertEquals("org.myproject.MyClass", classFile.getClassName());
    }

    public void shouldWorkInWindows()
    {
        File baseDirectory = new File("c:\\Projects\\jat");
        File file = new File("c:\\Projects\\jat\\org\\myproject\\MyClass.class");

        ClassFile classFile = new ClassFile(baseDirectory, file);

        assertEquals("org.myproject.MyClass", classFile.getClassName());
    }
}
