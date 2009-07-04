package org.jatproject.autotest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Test
public class ReferenceRepositoryTest
{
    public void shouldReturnModifiedFile()
    {
        ClassFile modified = mock(ClassFile.class);
        ReferenceRepository index = new ReferenceRepository(Collections.<ClassFile>emptySet());

        assertTrue(index.findReferencesTo(modified).contains(modified));
    }

    public void shouldReturnReferencesToTheModifiedFile()
    {
        ClassFile inPath = mock(ClassFile.class);
        ClassFile modified = mock(ClassFile.class);

        when(inPath.getDependencies()).thenReturn(Collections.singleton(modified));

        ReferenceRepository index = new ReferenceRepository(Collections.singleton(inPath));
        Set<ClassFile> references = index.findReferencesTo(modified);

        assertTrue(references.contains(modified));
        assertTrue(references.contains(inPath));
    }

    public void shouldReturnMultipleReferencesIfTheyAreFound()
    {
        ClassFile first = mock(ClassFile.class);
        ClassFile second = mock(ClassFile.class);
        ClassFile modified = mock(ClassFile.class);

        when(first.getDependencies()).thenReturn(Collections.singleton(modified));
        when(second.getDependencies()).thenReturn(Collections.singleton(modified));

        HashSet<ClassFile> classpath = new HashSet<ClassFile>();
        Collections.addAll(classpath, first, second);

        ReferenceRepository index = new ReferenceRepository(classpath);
        Set<ClassFile> references = index.findReferencesTo(modified);

        assertTrue(references.contains(modified));
        assertTrue(references.containsAll(classpath));
    }
}
