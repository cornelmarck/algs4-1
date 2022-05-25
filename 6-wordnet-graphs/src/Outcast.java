import edu.princeton.cs.algs4.In;

import java.io.FileNotFoundException;

public class Outcast {
    private WordNet wordNet;
    private int[][] distance;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        distance = new int[nouns.length][nouns.length];

        for (int i = 0; i < nouns.length; i++) {
            for (int j = i + 1; j < nouns.length; j++) {
                int combination = wordNet.distance(nouns[i], nouns[j]);
                distance[i][j] = combination;
                distance[j][i] = combination;
            }
        }

        return nouns[computeMaxDistance()];
    }

    private int computeMaxDistance() {
        int maxDistance = Integer.MIN_VALUE;
        int maxDistanceIdx = -1;

        for (int i = 0; i < distance.length; i++) {
            int sum = 0;
            for (int j = 0; j < distance.length; j++) {
                sum += distance[i][j];
            }
            if (sum > maxDistance) {
                maxDistance = sum;
                maxDistanceIdx = i;
            }
        }

        return maxDistanceIdx;
    }



    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);

        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            System.out.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}