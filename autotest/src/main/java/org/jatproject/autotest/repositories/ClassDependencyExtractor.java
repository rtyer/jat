package org.jatproject.autotest.repositories;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassDependencyExtractor
{
    private static Pattern REGEX = Pattern.compile("L?((\\w+/)+\\w+)");
    private String contents;

    public ClassDependencyExtractor(byte[] contents)
    {
        this(new String(contents));
    }

    public ClassDependencyExtractor(String contents)
    {
        this.contents = contents;
    }

    public Set<String> getDependencies()
    {
        Matcher matcher = REGEX.matcher(contents);
        Set<String> dependencies = new HashSet<String>();

        while(matcher.find())
        {
            dependencies.add(matcher.group(1).replace('/', '.'));
        }

        return dependencies;
    }
}
