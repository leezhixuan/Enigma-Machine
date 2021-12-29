package enigma;

abstract class Enigma {

    public Rotor firstRotor; // counted from the right
    public Rotor secondRotor;
    public Rotor thirdRotor;

    public Reflector reflector;

    public PlugBoard plugBoard;

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

    abstract int encrypt(int input);

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
