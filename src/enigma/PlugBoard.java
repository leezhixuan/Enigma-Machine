package enigma;

import java.util.HashSet;
import java.util.Set;

public class PlugBoard {
    private int[] wiring;

    public PlugBoard(String connections) {
        this.wiring = decodeConnections(connections);
    }

    public static int[] decodeConnections(String connections) {
        if (connections == null || connections.equals("")) {
            return identityMapping();
        }

        String[] pairings = connections.split("[^a-zA-Z]");
        Set<Integer> connectedCharacters = new HashSet<>();
        int[] mappings = identityMapping();

        for (String pair : pairings) {
            if (pair.length() != 2) {
                // invalid pairing, 1 to none connection
                return identityMapping();
            }

            int char1 = pair.charAt(0) - 65;
            int char2 = pair.charAt(1) - 65;

            if (connectedCharacters.contains(char1) || connectedCharacters.contains(char2)) {
                // connections are uniquely 1 to 1, so it is impossible to have paired characters to be
                // paired with another character
                return identityMapping();
            }

            // add paired characters to the set
            connectedCharacters.add(char1);
            connectedCharacters.add(char2);

            // create mutual pairing
            mappings[char1] = char2;
            mappings[char2] = char1;
        }

        return mappings;
    }

    public static int[] identityMapping() {
        int[] result = new int[26];
        for (int i = 0; i < 26; i++) {
            result[i] = i;
        }

        return result;
    }

    public int goForward(int input) {
        return this.wiring[input];
    }
}
