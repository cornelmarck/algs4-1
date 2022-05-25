import java.io.File;
import java.io.FileNotFoundException;

public class WordNet {
    private SynonymDictionary synonyms;
    private HypernymGraph hypernyms;
    private SAP shortestAncestralPath;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        this.synonyms = new SynonymDictionary(new File(synsets));
        this.hypernyms = new HypernymGraph(synonyms.getNumberOfSynonyms(), new File(hypernyms));
        shortestAncestralPath = new SAP(this.hypernyms.getGraph());
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synonyms.nouns();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return synonyms.getId(word) != null;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Noun does not exist");
        }

        return shortestAncestralPath.length(synonyms.getId(nounA), synonyms.getId(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Noun does not exist");
        }

        int ancestor = shortestAncestralPath.ancestor(synonyms.getId(nounA), synonyms.getId(nounB));
        return String.join(" ", synonyms.getSynonym(ancestor));
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}