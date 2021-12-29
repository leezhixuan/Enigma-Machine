import enigma.EnigmaM3;

public class Main {

    public static void main(String[] args) {
        String[] rotorNamesM3 = new String[]{"III", "II", "I"};
        int[] rotorOffsetsM3 = new int[]{0, 0, 0};
        int[] ringSettingsM3 = new int[]{0, 0, 0};
        EnigmaM3 enigmaM3 = new EnigmaM3(rotorNamesM3, "B", rotorOffsetsM3, ringSettingsM3, "");
        String input = "HELLO";
        System.out.println(enigmaM3.encrypt(input));


    }
}
