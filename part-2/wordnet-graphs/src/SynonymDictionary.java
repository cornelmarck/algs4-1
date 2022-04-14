import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class SynonymDictionary {
    private Map<String, Set<Integer>> nounToId;
    private Map<Integer, Set<String>> idToNoun;

    public SynonymDictionary() {
        nounToId = new HashMap<>();
        idToNoun = new HashMap<>();
    }
    public SynonymDictionary(File txt){
        this();
        readFromFile(txt);
    }

    public void addSynonymSet(int id, Collection<String> synonyms) {
        idToNoun.put(id, new HashSet<>(synonyms));

        for (String word : synonyms) {
            if (!nounToId.containsKey(word)) {
                nounToId.put(word, new HashSet<>());
            }
            nounToId.get(word).add(id);
        }
    }

    public Collection<String> getSynonym(int id) {
        return idToNoun.get(id);
    }

    public Collection<Integer> getId(String word) {
        return nounToId.get(word);
    }

    public int getNumberOfSynonyms() {
        return idToNoun.size();
    }

    public int getNumberOfNouns() {
        return nounToId.size();
    }

    public Iterable<String> nouns() {
        return nounToId.keySet();
    }

    public Iterable<Integer> ids() {
        return idToNoun.keySet();
    }

    private void readFromFile(File txt){
        try (Scanner scanner = new Scanner(txt)) {
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                int id = Integer.parseInt(fields[0]);
                String[] synonyms = fields[1].split(" ");

                addSynonymSet(id, Set.of(synonyms));
            }
        }
        catch (FileNotFoundException ignored) {}

    }




}
