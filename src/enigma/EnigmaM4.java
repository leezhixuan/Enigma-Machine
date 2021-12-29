package enigma;

public class EnigmaM4 extends EnigmaM3{

    public Rotor fourthRotor;

    public EnigmaM4(String[] rotorNames, String reflector, int[] rotorOffsets, int[] ringSettings, String plugboardConnections) {
        super(rotorNames, reflector, rotorOffsets, ringSettings, plugboardConnections);
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
}
