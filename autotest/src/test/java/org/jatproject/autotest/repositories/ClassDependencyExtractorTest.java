package org.jatproject.autotest.repositories;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;

import java.util.Set;

@Test
public class ClassDependencyExtractorTest
{
    private final String BYTE_CODES = "Êþº¾\u0000\u0000\u00001\u0000\u000B\u0007\u0000\t\u0007\u0000\n" +
            "\u0001\u0000\u0006isTest\u0001\u0000\u0014(Ljava/lang/Class;)Z\u0001\u0000\tSignature\u0001\u0000\u0017(Ljava/lang/Class<*>;)Z\u0001\u0000\n" +
            "SourceFile\u0001\u0000\u0011TestAsserter.java\u0001\u0000$org/jatproject/autotest/TestAsserter\u0001\u0000\u0010java/lang/Object\u0006\u0001\u0000\u0001\u0000\u0002\u0000\u0000\u0000\u0000\u0000\u0001\u0004\u0001\u0000\u0003\u0000\u0004\u0000\u0001\u0000\u0005\u0000\u0000\u0000\u0002\u0000\u0006\u0000\u0001\u0000\u0007\u0000\u0000\u0000\u0002\u0000\b";

    public void fullyQualifiedClassNamesShouldBeExtracted()
    {
        Set<String> dependencies = new ClassDependencyExtractor(BYTE_CODES).getDependencies();

        assertEquals(3, dependencies.size());
        assertTrue(dependencies.contains("java.lang.Class"));
        assertTrue(dependencies.contains("java.lang.Object"));
        assertTrue(dependencies.contains("org.jatproject.autotest.TestAsserter"));
    }
}
