package org.jatproject.autotest;

import org.jatproject.autotest.ClassFile;
import org.jatproject.autotest.DependencyIndex;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked"})
@Test
public class DependencyIndexTest
{
    private Mockery mockery;

    @BeforeMethod
    protected void beforeEach()
    {
        mockery = new Mockery()
        {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
            }
        };
    }

    @AfterMethod
    protected void afterEach()
    {
        mockery.assertIsSatisfied();
    }

    public void shouldReturnModifiedFile()
    {
        final ClassFile modified = mockery.mock(ClassFile.class);
        DependencyIndex index = new DependencyIndex(Collections.<ClassFile>emptySet());

        assertTrue(index.findReferencesTo(modified).contains(modified));
    }

    public void shouldReturnReferencesToTheModifiedFile()
    {
        final ClassFile inPath = mockery.mock(ClassFile.class, "inPath");
        final ClassFile modified = mockery.mock(ClassFile.class, "modified");

        mockery.checking(new Expectations()
        {{
            one(inPath).getDependencies(); will(returnValue(Collections.singleton(modified)));
        }});

        DependencyIndex index = new DependencyIndex(Collections.singleton(inPath));
        Set<ClassFile> references = index.findReferencesTo(modified);

        assertTrue(references.contains(modified));
        assertTrue(references.contains(inPath));
    }

    public void shouldReturnMultipleReferencesIfTheyAreFound()
    {
        final ClassFile first = mockery.mock(ClassFile.class, "first");
        final ClassFile second = mockery.mock(ClassFile.class, "second");
        final ClassFile modified = mockery.mock(ClassFile.class, "modified");

        mockery.checking(new Expectations()
        {{
            one(first).getDependencies(); will(returnValue(Collections.singleton(modified)));
            one(second).getDependencies(); will(returnValue(Collections.singleton(modified)));
        }});

        HashSet<ClassFile> classpath = new HashSet<ClassFile>();
        Collections.addAll(classpath, first, second);

        DependencyIndex index = new DependencyIndex(classpath);
        Set<ClassFile> references = index.findReferencesTo(modified);

        assertTrue(references.contains(modified));
        assertTrue(references.containsAll(classpath));
    }
}
