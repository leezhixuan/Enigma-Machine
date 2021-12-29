package enigma;

public class EnigmaM3 {

    public Rotor firstRotor; // counted from the right
    public Rotor secondRotor;
    public Rotor thirdRotor;

    public Reflector reflector;

    public PlugBoard plugBoard;

    public EnigmaM3(String[] rotorNames, String reflector, int[] rotorOffsets, int[] ringSettings, String plugboardConnections) {
        this.firstRotor = Rotor.createRotor(rotorNames[0], rotorOffsets[0], ringSettings[0]);
        this.secondRotor = Rotor.createRotor(rotorNames[1], rotorOffsets[1], ringSettings[1]);
        this.thirdRotor = Rotor.createRotor(rotorNames[2], rotorOffsets[2], ringSettings[2]);
        this.reflector = Reflector.createReflector(reflector);
        this.plugBoard = new PlugBoard(plugboardConnections);
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

    public int encrypt(int input) {
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

    public char encrypt(char input) {
        return (char) (this.encrypt(input - 65) + 65);
    }

    public char[] encrypt(char[] input) {
        char[] output = new char[input.length];

        for (int i = 0; i < input.length; i++) {
            output[i] = this.encrypt(input[i]);
        }

        return output;
    }
}
