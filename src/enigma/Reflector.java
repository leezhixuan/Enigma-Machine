package enigma;

public class Reflector {
    protected String name;
    protected int[] forwardWires;

    public Reflector(String name, String mapping) {
        this.name = name;
        this.forwardWires = decodeWiring(mapping);
    }

    protected static Reflector createReflector(String name) {
        switch (name) {
            case "A":
                return new Reflector("A", "EJMZALYXVBWFCRQUONTSPIKHGD");
            case "B":
                return new Reflector("B", "YRUHQSLDPXNGOKMIEBFZCWVJAT");
            case "C":
                return new Reflector("C", "FVPJIAOYEDRZXWGCTKUQSBNMHL");
            case "B Thin":
                return new Reflector("B Thin", "ENKQAUYWJICOPBLMDXZVFTHRGS");
            case "C Thin":
                return new Reflector("C Thin", "RDOBJNTKVEHMLFCWZAXGYIPSUQ");
        }
        return null;
    }

    public int[] decodeWiring(String mapping) {
        char[] charArray = mapping.toCharArray();
        int[] wiring = new int[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            wiring[i] = charArray[i] - 65; // 65 is the ascii value of 'a'
        }
        return wiring;
    }

    public int goForward(int input) {
        return this.forwardWires[input];
    }
}
