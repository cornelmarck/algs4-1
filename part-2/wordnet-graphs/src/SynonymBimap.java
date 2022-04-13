import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SynonymBimap {
    private final Map<String, List<Integer>> nounToId;
    private final Map<Integer, List<String>> idToNoun;

    public SynonymBimap(File txt) {
        nounToId = new HashMap<>();
        idToNoun = new HashMap<>();

        try (Scanner scanner = new Scanner(txt)) {
            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(",");
                int id = Integer.parseInt(fields[0]);
                String[] synonyms = fields[1].split(" ");

                addSynonymSet(id, Arrays.asList(synonyms));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Synonym file not found: " + txt.getName() );
        }
    }

    public void addSynonymSet(int id, Collection<String> synonyms) {
        idToNoun.put(id, new ArrayList<>(synonyms));

        for (String word : synonyms) {
            if (!nounToId.containsKey(word)) {
                nounToId.put(word, new ArrayList<>());
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

    public int size() {
        return idToNoun.size();
    }

    public Iterable<String> getNouns() {
        return nounToId.keySet();
    }

    public Iterable<Integer> getIds() {
        return idToNoun.keySet();
    }




}
