package common;

import org.apache.commons.lang3.RandomStringUtils;


public class GenerateRandom {

    public String RandomAlpha() {
        String generatedString = RandomStringUtils.randomAlphabetic(10);

        System.out.println(generatedString);
        return generatedString;
    }
    public static void main(String[] args) {
        GenerateRandom randomAlphaString = new GenerateRandom();
        randomAlphaString.RandomAlpha();

    }

}
