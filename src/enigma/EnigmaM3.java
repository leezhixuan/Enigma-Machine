package enigma;

import enigma.Exceptions.EnigmaException;

import java.util.stream.Stream;

public class EnigmaM3 extends Enigma {

    private final int NO_OF_ROTORS = 3;

    private enum ReflectorNames {
        B {
            @Override
            public String toString() {
                return "B";
            }
        },
        C {
            @Override
            public String toString() {
                return "C";
            }
        }
    }

    public EnigmaM3(String[] rotorNames, String reflector, int[] rotorOffsets, int[] ringSettings, String plugBoardConnections) {
        super(rotorNames, reflector, rotorOffsets, ringSettings, plugBoardConnections);
    }

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

    protected int encrypt(int input) {
        rotate();

        int newInput = this.plugBoard.goForward(input);

        // newInput will go through a series of 7 mappings at the rotors

        int firstChange = firstRotor.goForward(newInput);
        int secondChange = secondRotor.goForward(firstChange);
        int thirdChange = thirdRotor.goForward(secondChange);

        int fourthChange = reflector.goForward(thirdChange);

        int fifthChange = thirdRotor.goBackward(fourthChange);
        int sixthChange = secondRotor.goBackward(fifthChange);
        int seventhChange = firstRotor.goBackward(sixthChange);

        int finalResult = plugBoard.goForward(seventhChange);

        return finalResult;
    }

    @Override
    protected void checkNoOfRotors(String[] rotorNames) throws EnigmaException {
        if (rotorNames.length != this.NO_OF_ROTORS) {
            throw new EnigmaException("Number of rotors is not 3!");
        }
    }

    @Override
    protected void checkValidityOfRotorNames(String[] rotorNames) throws EnigmaException {
        for (String name : rotorNames) {
            if (Stream.of(RotorNames.values()).noneMatch(x -> name.equals(x.toString()))) {
                throw new EnigmaException("Invalid Rotor Name for M3.");
            }
        }
    }

    @Override
    protected void checkValidityOfReflector(String reflector) throws EnigmaException {
        if (Stream.of(ReflectorNames.values()).noneMatch(x -> reflector.equals(x.toString()))) {
            throw new EnigmaException("Invalid Reflector Name for M3.");
        }
    }

    @Override
    protected void checkNoOfRotorOffsets(int[] rotorOffsets) throws EnigmaException {
        if (rotorOffsets.length != NO_OF_ROTORS) {
            throw new EnigmaException("Number of rotor offsets is not 3!");
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
            throw new EnigmaException("Number of ring settings is not 3!");
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
