package enigma;

import enigma.Exceptions.EnigmaException;

abstract class Enigma {

    protected Rotor firstRotor; // counted from the left
    protected Rotor secondRotor;
    protected Rotor thirdRotor;

    protected Reflector reflector;

    protected PlugBoard plugBoard;

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

    protected Enigma(String[] rotorNames, String reflector, int[] rotorOffsets, int[] ringSettings, String plugBoardConnections) {
        try {
            checkNoOfRotors(rotorNames);
            checkValidityOfRotorNames(rotorNames);
            checkValidityOfReflector(reflector);
            checkNoOfRotorOffsets(rotorOffsets);
            checkValidityOfRotorOffsets(rotorOffsets);
            checkNoOfRingSettings(ringSettings);
            checkValidityOfRingSettings(ringSettings);
        } catch (EnigmaException e) {
            System.out.println(e.toString());
        }
        this.firstRotor = Rotor.createRotor(rotorNames[0], rotorOffsets[0], ringSettings[0]);
        this.secondRotor = Rotor.createRotor(rotorNames[1], rotorOffsets[1], ringSettings[1]);
        this.thirdRotor = Rotor.createRotor(rotorNames[2], rotorOffsets[2], ringSettings[2]);
        this.reflector = Reflector.createReflector(reflector);
        this.plugBoard = new PlugBoard(plugBoardConnections);
    }

    protected abstract int encrypt(int input);

    protected abstract void checkNoOfRotors(String[] rotorNames) throws EnigmaException;
    protected abstract void checkValidityOfRotorNames(String[] rotorNames) throws EnigmaException;
    protected abstract void checkValidityOfReflector(String reflector) throws EnigmaException;
    protected abstract void checkNoOfRotorOffsets(int[] rotorOffsets) throws EnigmaException;
    protected abstract void checkValidityOfRotorOffsets(int[] rotorOffsets) throws EnigmaException;
    protected abstract void checkNoOfRingSettings(int[] ringSettings) throws EnigmaException;
    protected abstract void checkValidityOfRingSettings(int[] ringSettings) throws EnigmaException;

    public void rotate() {
        if (this.secondRotor.isAtTurnoverNotchPosition()) {
            // double stepping
            this.secondRotor.executeTurnover();
            this.thirdRotor.executeTurnover();

        } else if (firstRotor.isAtTurnoverNotchPosition()) {
            this.secondRotor.executeTurnover();
        }

        // will always turn
        firstRotor.executeTurnover();
    }

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
