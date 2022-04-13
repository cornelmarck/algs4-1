import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class WordNetTest {
    WordNet word;


    @BeforeEach
    public void init() throws FileNotFoundException {
        word = new WordNet(this.getClass().getResource("synsets.txt").getFile(),
                this.getClass().getResource("hypernyms.txt").getFile());
    }

    @Test
    public void isNoun() {
        Assertions.assertTrue(word.isNoun("zucchini"));
    }

    @Test
    public void farApartDistance() {
        Assertions.assertEquals(27, word.distance("American_water_spaniel", "histology"));
    }

    @Test
    public void farApartAncestor() {
        Assertions.assertEquals("entity", word.sap("American_water_spaniel", "histology"));
    }

    @Test
    public void path() {

    }


}
