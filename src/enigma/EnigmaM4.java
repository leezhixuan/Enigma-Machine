package enigma;

public class EnigmaM4 extends Enigma {

    public Rotor firstRotor; // counted from the right
    public Rotor secondRotor;
    public Rotor thirdRotor;
    public Rotor fourthRotor;


    public Reflector reflector;

    public PlugBoard plugBoard;

    public EnigmaM4(String[] rotorNames, String reflector, int[] rotorOffsets, int[] ringSettings, String plugBoardConnections) {
        assert rotorNames.length == 4;
        assert rotorOffsets.length == 4;
        assert ringSettings.length == 4;
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

}
