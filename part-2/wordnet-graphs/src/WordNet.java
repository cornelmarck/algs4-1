import edu.princeton.cs.algs4.Digraph;

import java.io.File;
import java.io.FileNotFoundException;

public class WordNet {
    SynonymBimap synonyms;
    HypernymGraph hypernyms;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) throws FileNotFoundException {
        this.synonyms = new SynonymBimap(new File(synsets));
        this.hypernyms = new HypernymGraph(synonyms.size(), new File(hypernyms));
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synonyms.getNouns();
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
        SAP shortestAncestralPath = new SAP(hypernyms.getGraph());
        return shortestAncestralPath.length(synonyms.getId(nounA), synonyms.getId(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Noun does not exist");
        }
        SAP shortestAncestralPath = new SAP(hypernyms.getGraph());

        int ancestor = shortestAncestralPath.ancestor(synonyms.getId(nounA), synonyms.getId(nounB));
        return synonyms.getSynonym(ancestor).toString();
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}