package enigma;

import enigma.Exceptions.EnigmaException;

abstract class Enigma {

    protected enum RotorNames {
        I {
            @Override
            public String toString() {
                return "I";
            }
        },
        II {
            @Override
            public String toString() {
                return "II";
            }
        },
        III {
            @Override
            public String toString() {
                return "III";
            }
        },
        IV {
            @Override
            public String toString() {
                return "IV";
            }
        },
        V {
            @Override
            public String toString() {
                return "V";
            }
        },
        VI {
            @Override
            public String toString() {
                return "VI";
            }
        },
        VII {
            @Override
            public String toString() {
                return "VII";
            }
        },
        VIII {
            @Override
            public String toString() {
                return "VIII";
            }
        }
    }

    abstract void rotate();

    abstract int encrypt(int input);

    abstract void checkNoOfRotors(String[] rotorNames) throws EnigmaException;
    abstract void checkValidityOfRotorNames(String[] rotorNames) throws EnigmaException;
    abstract void checkValidityOfReflector(String reflector) throws EnigmaException;
    abstract void checkNoOfRotorOffsets(int[] rotorOffsets) throws EnigmaException;
    abstract void checkValidityOfRotorOffsets(int[] rotorOffsets) throws EnigmaException;
    abstract void checkNoOfRingSettings(int[] ringSettings) throws EnigmaException;
    abstract void checkValidityOfRingSettings(int[] ringSettings) throws EnigmaException;

    public char encrypt(char input) {
        return (char) (this.encrypt(input - 65) + 65);
    }

    public String encrypt(String input) {
        input = input.toUpperCase(); // converts everything to uppercase so that the program will not break
        char[] inputArray = input.toCharArray();
        char[] output = new char[inputArray.length];

        for (int i = 0; i < inputArray.length; i++) {
            output[i] = this.encrypt(inputArray[i]);
        }

        return new String(output);
    }

}
