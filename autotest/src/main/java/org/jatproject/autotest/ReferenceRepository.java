package org.jatproject.autotest;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ReferenceRepository
{
    private Map<ClassFile, Set<ClassFile>> index = new HashMap<ClassFile, Set<ClassFile>>();

    public ReferenceRepository(Iterable<ClassFile> iterable)
    {
        for(ClassFile file : iterable)
        {
            for(ClassFile dependecy : file.getDependencies())
            {
                Set<ClassFile> references = index.get(dependecy);
                if(references == null)
                {
                    references = new HashSet<ClassFile>();
                    index.put(dependecy, references);
                }

                references.add(file);
            }
        }
    }

    public Set<ClassFile> findReferencesTo(ClassFile modified)
    {
        Set<ClassFile> references = new HashSet<ClassFile>();
        references.add(modified);

        Set<ClassFile> indexed = index.containsKey(modified) ? index.get(modified) : Collections.<ClassFile>emptySet();
        references.addAll(indexed);

        return references;
    }
}
