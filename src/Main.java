import enigma.EnigmaM3;
import enigma.EnigmaM4;

public class Main {

    public static void main(String[] args) {

        String input = "HELLO";

        String[] rotorNamesM3 = new String[]{"I", "II", "III"};
        int[] rotorOffsetsM3 = new int[]{0, 0, 0};
        int[] ringSettingsM3 = new int[]{0, 0, 0};
        EnigmaM3 enigmaM3 = new EnigmaM3(rotorNamesM3, "B", rotorOffsetsM3, ringSettingsM3, "");
        String cipherText_M3 = enigmaM3.encrypt(input);

        System.out.println(cipherText_M3);

        EnigmaM3 enigmaM3_2 = new EnigmaM3(rotorNamesM3, "B", rotorOffsetsM3, ringSettingsM3, "");
        System.out.println(enigmaM3_2.encrypt(cipherText_M3));

        String[] rotorNamesM4 = new String[]{"I", "II", "III", "Gamma"};
        int[] rotorOffsetsM4 = new int[]{0, 0, 0, 0};
        int[] ringSettingsM4 = new int[]{0, 0, 0, 0};
        EnigmaM4 enigmaM4 = new EnigmaM4(rotorNamesM4, "B Thin", rotorOffsetsM4, ringSettingsM4, "");
        String cipherText_M4 = enigmaM4.encrypt(input);

        System.out.println(cipherText_M4);

        EnigmaM4 enigmaM4_2 = new EnigmaM4(rotorNamesM4, "B Thin", rotorOffsetsM4, ringSettingsM4, "");
        System.out.println(enigmaM4_2.encrypt(cipherText_M4));
    }
}
