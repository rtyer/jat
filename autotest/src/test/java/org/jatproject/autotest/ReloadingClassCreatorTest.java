package org.jatproject.autotest;

import java.io.File;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import static org.testng.AssertJUnit.assertSame;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class ReloadingClassCreatorTest
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

    public void classNotFoundInSearchPathThenFindSystemClass() throws Exception
    {
        final SearchPath path = mockery.mock(SearchPath.class);
        final AutoTestClassLoader loader = mockery.mock(AutoTestClassLoader.class);

        mockery.checking(new Expectations()
        {{
            one(path).find("org/project/Project.class"); will(returnValue(null));
            one(loader).loadClass("org.project.Project"); will(returnValue(Tester.class));
        }});

        ClassCreator reloadingClassCreator = new ReloadingClassCreator(path, loader);
        Class result = reloadingClassCreator.loadClass("org.project.Project");
        assertSame(Tester.class, result);
    }

    public void classFoundInSearchPathThenDefineClass() throws Exception
    {
        final SearchPath path = mockery.mock(SearchPath.class);
        final AutoTestClassLoader loader = mockery.mock(AutoTestClassLoader.class);
        final File file = new File("org/project/Project.class");

        mockery.checking(new Expectations()
        {{
            one(path).find("org/project/Project.class"); will(returnValue(file));
            one(loader).defineClass("org.project.Project", file); will(returnValue(Tester.class));
        }});

        ClassCreator reloadingClassCreator = new ReloadingClassCreator(path, loader);
        Class result = reloadingClassCreator.loadClass("org.project.Project");
        assertSame(Tester.class, result);
    }
}
