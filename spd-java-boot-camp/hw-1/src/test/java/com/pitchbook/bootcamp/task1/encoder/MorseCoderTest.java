package com.pitchbook.bootcamp.task1.encoder;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MorseCoderTest {

    private MorseCoder morseCoder;

    @BeforeMethod
    public void setUp() {
        morseCoder = new MorseCoder();
    }

    @Test(dataProvider = "invalidStringsForEncoding", expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_try_to_encode_invalid_input(String invalidString) {
        morseCoder.encode(invalidString);
    }

    @Test(dataProvider = "validStringsForEncoding")
    public void should_return_encoded_string_when_try_to_encode_valid_input(String regularString, String expectedString) {
        String actualResult = morseCoder.encode(regularString);
        Assert.assertEquals(actualResult, expectedString);
    }

    @Test(dataProvider = "invalidStringsForDecoding", expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_try_to_decode_invalid_input(String invalidString) {
        morseCoder.decode(invalidString);
    }

    @Test(dataProvider = "validStringsForDecoding")
    public void should_return_decoded_string_when_input_is_valid(String input, String expectedResult) {
        String actualResult = morseCoder.decode(input);
        Assert.assertEquals(actualResult, expectedResult);
    }

    @DataProvider(name = "validStringsForEncoding")
    public static Object[][] validStringsForEncoding() {
        return new Object[][]{
                {"test test", "- . ... - /- . ... - /"},
                {"TEST TEst", "- . ... - /- . ... - /"},
                {"hi 19", ".... .. /.---- ----. /"},
                {"  hi $1!9 ", ".... .. / .----  ----. /"}
        };
    }

    @DataProvider(name = "invalidStringsForEncoding")
    public static Object[][] invalidStringsForEncoding() {
        return new Object[][]{
                {null},
                {"aaaaaaaaaabbbbbbbbbbcccccccccc"},
                {""},
                {"       "},
                {" hihi абвгд"}
        };
    }

    @DataProvider(name = "validStringsForDecoding")
    public static Object[][] validStringsForDecoding() {
        return new Object[][]{
                {".... .. /.---- ----. /", "HI 19 "},
                {"    .-.. --- ...- . /-- .", "LOVE ME "},
                {"....  .. /.---- ----. /", "H I 19 "},
                {"-- .- .-. .. .- /.---- ..--- ...--", "MARIA 123 "}
        };
    }

    @DataProvider(name = "invalidStringsForDecoding")
    public static Object[][] invalidStringsForDecoding() {
        return new Object[][]{
                {null},
                {""},
                {"    "},
                {"---------"},
                {"абвн"}
        };
    }

}
