package enigma;

import enigma.Exceptions.EnigmaException;

import java.util.stream.Stream;

public class EnigmaM4 extends Enigma {

    private final int NO_OF_ROTORS = 4;

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

    public Rotor firstRotor; // counted from the right
    public Rotor secondRotor;
    public Rotor thirdRotor;
    public Rotor fourthRotor;

    public Reflector reflector;

    public PlugBoard plugBoard;

    public EnigmaM4(String[] rotorNames, String reflector, int[] rotorOffsets, int[] ringSettings, String plugBoardConnections) {
        try {
            checkNoOfRotors(rotorNames);
            checkValidityOfRotorNames(rotorNames);
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
        this.fourthRotor = Rotor.createRotor(rotorNames[3], rotorOffsets[3], ringSettings[3]);
        this.reflector = Reflector.createReflector(reflector);
        this.plugBoard = new PlugBoard(plugBoardConnections);
    }

    @Override
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
    void checkNoOfRotors(String[] rotorNames) throws EnigmaException {
        if (rotorNames.length != this.NO_OF_ROTORS) {
            throw new EnigmaException("Number of rotors is not 3!");
        }
    }

    @Override
    void checkValidityOfRotorNames(String[] rotorNames) throws EnigmaException {
        for (String name : rotorNames) {
            if (!Stream.of(RotorNames.values()).anyMatch(x -> name.equals(x.toString()))) {
                throw new EnigmaException("Invalid Rotor Name.");
            }
        }
    }

    @Override
    void checkValidityOfReflector(String reflector) throws EnigmaException {
        if (!Stream.of(EnigmaM4.ReflectorNames.values()).anyMatch(x -> reflector.equals(x.toString()))) {
            throw new EnigmaException("Invalid Reflector Name.");
        }
    }

    @Override
    void checkNoOfRotorOffsets(int[] rotorOffsets) throws EnigmaException {
        if (rotorOffsets.length != 3) {
            throw new EnigmaException("Number of rotor offsets is not 3!");
        }
    }

    @Override
    void checkValidityOfRotorOffsets(int[] rotorOffsets) throws EnigmaException{
        for (int offset : rotorOffsets) {
            if (offset < 0 || offset > 25) {
                throw new EnigmaException("Invalid Rotor Offset.");
            }
        }
    }

    @Override
    void checkNoOfRingSettings(int[] ringSettings) throws EnigmaException {
        if (ringSettings.length != NO_OF_ROTORS) {
            throw new EnigmaException("Number of ring settings is not 3!");
        }
    }

    @Override
    void checkValidityOfRingSettings(int[] ringSettings) throws EnigmaException {
        for (int setting : ringSettings) {
            if (setting < 0 || setting > 25) {
                throw new EnigmaException("Invalid Ring Setting.");
            }
        }
    }

}
