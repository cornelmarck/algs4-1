import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public class SynonymDictionaryTest {
    SynonymDictionary syn;

    @BeforeEach
    public void init() {
        syn = new SynonymDictionary();

        syn.addSynonymSet(0, List.of("cat", "dog"));
        syn.addSynonymSet(3, List.of("lizard", "cat"));
        syn.addSynonymSet(1, List.of("horse", "dog"));
    }

    @Test
    public void getNumberOfSynonyms() {
        Assertions.assertEquals(3, syn.getNumberOfSynonyms());
    }

    @Test
    public void getNumberOfNouns() {
        Assertions.assertEquals(4, syn.getNumberOfNouns());
    }

    @Test
    public void getSynonymSet() {
        Assertions.assertEquals(Set.of("cat", "lizard"), syn.getSynonym(3));
    }

    @Test
    public void getId() {
        Assertions.assertEquals(Set.of(0, 1), syn.getId("dog"));
    }

    @Nested
    public class Synsets {
        SynonymDictionary syn;

        @BeforeEach
        public void init() throws FileNotFoundException {
            File input = new File(this.getClass().getResource("synsets.txt").getFile());
            syn = new SynonymDictionary(input);
        }

        @Test
        public void getNumberOfNouns() {
            Assertions.assertEquals(119188, syn.getNumberOfNouns());
        }

        @Test
        public void getNumberOfSynonyms() {
            Assertions.assertEquals(82192, syn.getNumberOfSynonyms());
        }

        @Test
        public void getSynonymSet() {
            Assertions.assertEquals(Set.of("zoology", "zoological_science"), syn.getSynonym(82164));
        }

        @Test
        public void getId() {
            Assertions.assertEquals(Set.of(24306, 24307, 25293, 33764, 70067), syn.getId("bird"));
        }
    }


}
