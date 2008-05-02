package org.jatproject.autotest;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

@Test
public class ClassnameTest
{
    public void shouldKnowFileNameFromFullyQualifiedClassName()
    {
        Classname classname = new Classname("org.name.Myname");
        assertEquals("org/name/Myname.class", classname.getClassFileName());
    }
}
