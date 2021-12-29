package enigma;

abstract class Enigma {

    abstract void rotate();

    abstract int encrypt(int input);

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
