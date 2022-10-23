package hu.herpaipeter;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TransitiveDependencyTest {

    @Test
    void empty_has_no_dependency() {
        TransitiveDependency transDep = new TransitiveDependency(Collections.emptyList());
        String deps = transDep.getDependencies("");
        assertEquals("", deps);
    }

    @Test
    void single_dependency_is_not_dependency() {
        TransitiveDependency transDep = new TransitiveDependency(Arrays.asList("A"));
        String deps = transDep.getDependencies("A");
        assertEquals("", deps);
    }

    @Test
    void simple_dependency() {
        TransitiveDependency transDep = new TransitiveDependency(Arrays.asList("AB"));
        String deps = transDep.getDependencies("A");
        assertEquals("B", deps);
    }

    @Test
    void transitive_dependency_with_one_definition() {
        TransitiveDependency transDep = new TransitiveDependency(Arrays.asList("ABC"));
        String deps = transDep.getDependencies("A");
        assertEquals("BC", deps);
    }

    @Test
    void transitive_dependency_not_first_from_definition() {
        TransitiveDependency transDep = new TransitiveDependency(Arrays.asList("ABC"));
        String deps = transDep.getDependencies("B");
        assertEquals("C", deps);
    }

    @Test
    void transitive_dependency_last_from_definition_gives_empty() {
        TransitiveDependency transDep = new TransitiveDependency(Arrays.asList("ABC"));
        String deps = transDep.getDependencies("C");
        assertEquals("", deps);
    }

    @Test
    void independent_dependency_elements_first_from_second() {
        TransitiveDependency transDep = new TransitiveDependency(Arrays.asList("ABC", "DEF"));
        String deps = transDep.getDependencies("D");
        assertEquals("EF", deps);
    }

    @Test
    void independent_dependency_elements_second_from_second() {
        TransitiveDependency transDep = new TransitiveDependency(Arrays.asList("ABC", "DEF"));
        String deps = transDep.getDependencies("E");
        assertEquals("F", deps);
    }

    @Test
    void two_dependency_list_common_element() {
        TransitiveDependency transDep = new TransitiveDependency(Arrays.asList("ABC", "BEF"));
        String deps = transDep.getDependencies("B");
        assertEquals("CEF", deps);
    }

    @Test
    void two_dependency_list_transitive_elements() {
        TransitiveDependency transDep = new TransitiveDependency(Arrays.asList("ABC", "CEF"));
        String deps = transDep.getDependencies("B");
        assertEquals("CEF", deps);
    }

    @Test
    void more_dependency_list_transitive_elements() {
        TransitiveDependency transDep = new TransitiveDependency(Arrays.asList("ABC", "BCE", "CG"));
        String deps = transDep.getDependencies("A");
        assertEquals("BCEG", deps);
    }

    @Test
    void more_dependency_backwards() {
        TransitiveDependency transDep = new TransitiveDependency(Arrays.asList("ABC", "BCE", "CG", "DAF", "EF", "FH"));
        String deps = transDep.getDependencies("D");
        assertEquals("ABCEFGH", deps);
    }

    @Test
    void all_dependency() {
        TransitiveDependency transDep = new TransitiveDependency(Arrays.asList("ABC", "BCE", "CG", "DAF", "EF", "FH"));
        String aDeps = transDep.getDependencies("A");
        assertEquals("BCEFGH", aDeps);
        String bDeps = transDep.getDependencies("B");
        assertEquals("CEFGH", bDeps);
        String cDeps = transDep.getDependencies("C");
        assertEquals("EFGH", cDeps);
        String dDeps = transDep.getDependencies("D");
        assertEquals("ABCEFGH", dDeps);
        String eDeps = transDep.getDependencies("E");
        assertEquals("FH", eDeps);
        String fDeps = transDep.getDependencies("F");
        assertEquals("H", fDeps);
        String gDeps = transDep.getDependencies("G");
        assertEquals("", gDeps);
        String hDeps = transDep.getDependencies("H");
        assertEquals("", hDeps);
    }

}
