package org.jatproject.autotest.repositories;

import org.jatproject.autotest.ClassFile;
import org.jatproject.autotest.ReferenceRepository;

import java.util.HashSet;
import java.util.Set;

public class IndexedReferenceRepository implements ReferenceRepository
{
    private DependencyIndex index;

    public IndexedReferenceRepository(DependencyIndex index)
    {
        this.index = index;
    }

    public Set<ClassFile> findReferencesTo(ClassFile changeClass)
    {
        Set<ClassFile> references = new HashSet<ClassFile>();
        references.add(changeClass);
        references.addAll(index.findReferences(changeClass));
        return references;
    }
}
