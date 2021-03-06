package enigma;

public class Rotor {
    protected String name;
    protected int rotorOffset;
    protected int turnoverNotchPosition;
    protected int ringSetting;

    protected int[] forwardWires;
    protected int[] backwardWires;

    public Rotor(String name, String mapping, int rotorOffset, int turnoverNotchPosition, int ringSetting) {
        this.name = name;
        this.rotorOffset = rotorOffset;
        this.turnoverNotchPosition = turnoverNotchPosition;
        this.ringSetting = ringSetting;
        this.forwardWires = decodeWiring(mapping);
        this.backwardWires = inverseWiring(this.forwardWires);
    }

    public static Rotor createRotor(String name, int rotorOffset, int ringSetting) {
        switch (name) {
            case "I":
                return new Rotor("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ", rotorOffset,
                        'q' - 'a', ringSetting);
            case "II":
                return new Rotor("II", "AJDKSIRUXBLHWTMCQGZNPYFVOE", rotorOffset,
                        'e' - 'a', ringSetting);
            case "III":
                return new Rotor("III", "BDFHJLCPRTXVZNYEIWGAKMUSQO", rotorOffset,
                        'v' - 'a', ringSetting);
            case "IV":
                return new Rotor("IV", "ESOVPZJAYQUIRHXLNFTGKDCMWB", rotorOffset,
                        'j' - 'a', ringSetting);
            case "V":
                return new Rotor("V", "VZBRGITYUPSDNHLXAWMJQOFECK", rotorOffset,
                        'z' - 'a', ringSetting);
            case "VI":
                return new Rotor("VI","JPGVOUMFYQBENHZRDKASXLICTW", rotorOffset, 0, ringSetting) {
                    @Override
                    public boolean isAtTurnoverNotchPosition() {
                        return this.turnoverNotchPosition == 12 || this.turnoverNotchPosition == 25;
                    }
                };
            case "VII":
                return new Rotor("VI","NZJHGRCXMYSWBOUFAIVLPEKQDT", rotorOffset, 0, ringSetting) {
                    @Override
                    public boolean isAtTurnoverNotchPosition() {
                        return this.turnoverNotchPosition == 12 || this.turnoverNotchPosition == 25;
                    }
                };
            case "VIII":
                return new Rotor("VIII","FKQHTLXOCBJSPDZRAMEWNIUYGV", rotorOffset, 0, ringSetting) {
                    @Override
                    public boolean isAtTurnoverNotchPosition() {
                        return this.turnoverNotchPosition == 12 || this.turnoverNotchPosition == 25;
                    }
                };
            case "Beta":
                return new Rotor("Beta", "LEYJVCNIXWPBQMDRTAKZGFUHOS", rotorOffset, 0, ringSetting) {
                    @Override
                    public boolean isAtTurnoverNotchPosition() {
                        return false;
                    }

                    @Override
                    public void executeTurnover() {
                        this.rotorOffset = this.rotorOffset;
                    }
                };
            case "Gamma":
                return new Rotor("Gamma", "FSOKANUERHMBTIYCWLQPZXVGJD", rotorOffset, 0, ringSetting) {
                    @Override
                    public boolean isAtTurnoverNotchPosition() {
                        return false;
                    }

                    @Override
                    public void executeTurnover() {
                        this.rotorOffset = this.rotorOffset;
                    }
                };
            default:
                return new Rotor("IdentityRotor","ABCDEFGHIJKLMNOPQRSTUVWXYZ", rotorOffset, 0, ringSetting);
        }
    }

    public boolean isAtTurnoverNotchPosition() {
        return this.turnoverNotchPosition == this.rotorOffset;
    }

    public static int[] decodeWiring(String mapping) {
        char[] charArray = mapping.toCharArray();
        int[] wiring = new int[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            wiring[i] = charArray[i] - 65; // 65 is the ascii value of 'a'
        }
        return wiring;
    }

    public static int[] inverseWiring(int[] wiring) {
        int[] inverseWires = new int[wiring.length];
        for (int i = 0; i < wiring.length; i++) {
            int forwardMapping = wiring[i];
            inverseWires[forwardMapping] = i;
        }
        return inverseWires;
    }

    public String getName() {
        return this.name;
    }

    public int getRotorOffset() {
        return this.rotorOffset;
    }

    public static int encrypt(int input, int offset, int setting, int[] mapping) {
        int amountToShift = offset - setting;
        return (mapping[(input + amountToShift + 26) % 26] - amountToShift + 26) % 26;
    }

    public int goForward(int input) {
        return encrypt(input, this.rotorOffset, this.ringSetting, this.forwardWires);
    }

    public int goBackward(int input) {
        return encrypt(input, this.rotorOffset, this.ringSetting, this.backwardWires);
    }

    public void executeTurnover() {
        this.rotorOffset = (this.rotorOffset + 1) % 26;
    }
}
