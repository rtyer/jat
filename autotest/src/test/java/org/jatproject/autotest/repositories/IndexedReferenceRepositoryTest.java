package org.jatproject.autotest.repositories;

import org.jatproject.autotest.ClassFile;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

@Test
public class IndexedReferenceRepositoryTest
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


    public void shouldReturnTheModifiedFileIfNoReferencesAreFound()
    {
        final DependencyIndex index = mockery.mock(DependencyIndex.class);
        final ClassFile modified = mockery.mock(ClassFile.class);
        final Set<ClassFile> dependencies = new HashSet<ClassFile>();

        mockery.checking(new Expectations()
        {{
            one(index).findReferences(modified); will(returnValue(dependencies));    
        }});

        Set<ClassFile> references = new IndexedReferenceRepository(index).findReferencesTo(modified);
        assertTrue(references.contains(modified));
    }

    public void shouldReturnTheModifiedFileAndItsReferences()
    {
        final DependencyIndex index = mockery.mock(DependencyIndex.class);
        final ClassFile modified = mockery.mock(ClassFile.class);
        final Set<ClassFile> dependencies = new HashSet<ClassFile>();
        dependencies.add(mockery.mock(ClassFile.class, "dependency"));

        mockery.checking(new Expectations()
        {{
            one(index).findReferences(modified); will(returnValue(dependencies));
        }});

        Set<ClassFile> references = new IndexedReferenceRepository(index).findReferencesTo(modified);
        assertTrue(references.contains(modified));
        assertTrue(references.containsAll(dependencies));
    }
}
