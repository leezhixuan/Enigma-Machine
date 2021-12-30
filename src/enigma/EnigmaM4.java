package enigma;

import enigma.Exceptions.EnigmaException;

import java.util.stream.Stream;

public class EnigmaM4 extends Enigma {

    private final int NO_OF_ROTORS = 4;

    private enum M4RotorNames {
        Beta {
            @Override
            public String toString() {
                return "Beta";
            }
        },
        Gamma {
            @Override
            public String toString() {
                return "Gamma";
            }
        }
    }

    private enum ReflectorNames {
        B_Thin {
            @Override
            public String toString() {
                return "B Thin";
            }
        },
        C_Thin {
            @Override
            public String toString() {
                return "C Thin";
            }
        }
    }

    private Rotor fourthRotor;

    public EnigmaM4(String[] rotorNames, String reflector, int[] rotorOffsets, int[] ringSettings, String plugBoardConnections) {
        super(rotorNames, reflector, rotorOffsets, ringSettings, plugBoardConnections);
        this.fourthRotor = Rotor.createRotor(rotorNames[3], rotorOffsets[3], ringSettings[3]);
    }

    @Override
    public int encrypt(int input) {
        rotate();

        int newInput = this.plugBoard.goForward(input);

        // newInput will go through a series of 9 mappings at the rotors

        int firstChange = firstRotor.goForward(newInput);
        int secondChange = secondRotor.goForward(firstChange);
        int thirdChange = thirdRotor.goForward(secondChange);
        int fourthChange = fourthRotor.goForward(thirdChange);

        int fifthChange = reflector.goForward(fourthChange);

        int sixthChange = fourthRotor.goBackward(fifthChange);
        int seventhChange = thirdRotor.goBackward(sixthChange);
        int eighthChange = secondRotor.goBackward(seventhChange);
        int ninthChange = firstRotor.goBackward(eighthChange);

        int finalResult = plugBoard.goForward(ninthChange);

        return finalResult;
    }

    @Override
    protected void checkNoOfRotors(String[] rotorNames) throws EnigmaException {
        if (rotorNames.length != this.NO_OF_ROTORS) {
            throw new EnigmaException("Number of rotors is not 4!");
        }
    }

    @Override
    protected void checkValidityOfRotorNames(String[] rotorNames) throws EnigmaException {
        String[] first3Rotors = new String[]{rotorNames[0], rotorNames[1], rotorNames[2]};
        for (String name : first3Rotors) {
            if (Stream.of(RotorNames.values()).noneMatch(x -> name.equals(x.toString()))
                    && !(rotorNames[3].equals(M4RotorNames.Beta.toString()) || rotorNames[3].equals(M4RotorNames.Gamma.toString()))) {
                throw new EnigmaException("Invalid Rotor Name for M4");
            }
        }
    }

    @Override
    protected void checkValidityOfReflector(String reflector) throws EnigmaException {
        if (Stream.of(EnigmaM4.ReflectorNames.values()).noneMatch(x -> reflector.equals(x.toString()))) {
            throw new EnigmaException("Invalid Reflector Name for M4.");
        }
    }

    @Override
    protected void checkNoOfRotorOffsets(int[] rotorOffsets) throws EnigmaException {
        if (rotorOffsets.length != NO_OF_ROTORS) {
            throw new EnigmaException("Number of rotor offsets is not 4!");
        }
    }

    @Override
    protected void checkValidityOfRotorOffsets(int[] rotorOffsets) throws EnigmaException{
        for (int offset : rotorOffsets) {
            if (offset < 0 || offset > 25) {
                throw new EnigmaException("Invalid Rotor Offset.");
            }
        }
    }

    @Override
    protected void checkNoOfRingSettings(int[] ringSettings) throws EnigmaException {
        if (ringSettings.length != NO_OF_ROTORS) {
            throw new EnigmaException("Number of ring settings is not 4!");
        }
    }

    @Override
    protected void checkValidityOfRingSettings(int[] ringSettings) throws EnigmaException {
        for (int setting : ringSettings) {
            if (setting < 0 || setting > 25) {
                throw new EnigmaException("Invalid Ring Setting.");
            }
        }
    }
}
