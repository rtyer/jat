package org.jatproject.autotest.repositories;

import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class InMemoryReferenceRepositoryTest {
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


}
