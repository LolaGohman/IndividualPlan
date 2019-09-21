package com.pitchbook.bootcamp.task1.encoder;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CaesarCoderTest {

    private CaesarCoder caesarCoder;
    private CaesarCoder caesarCoderWithoutKeyParameter;

    @BeforeMethod
    public void setUp(){
        caesarCoder = new CaesarCoder(3);
        caesarCoderWithoutKeyParameter = new CaesarCoder();
    }

    @Test(dataProvider = "invalidStringsForEncoding", expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_try_to_encode_invalid_input(String invalidString) {
       caesarCoder.encode(invalidString);
    }

    @Test(dataProvider = "validStringsForEncoding")
    public void should_return_encoded_string_when_try_to_encode_valid_input(String regularString, String expectedString) {
        String actualResult = caesarCoder.encode(regularString);
        Assert.assertEquals(actualResult, expectedString);
    }

    @Test(dataProvider = "invalidStringsForDecoding", expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_try_to_decode_invalid_input(String invalidString) {
        caesarCoder.decode(invalidString);
    }

    @Test(dataProvider = "validStringsForDecoding")
    public void should_return_decoded_string_when_input_is_valid(String input, String expectedResult) {
        String actualResult = caesarCoder.decode(input);
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void should_encode_symbols_with_default_key_when_created_without_key_parameter(){
        String actualResult = caesarCoderWithoutKeyParameter.encode("test test");
        Assert.assertEquals(actualResult, "whvw whvw");
    }

    @Test
    public void should_decode_symbols_with_default_key_when_created_without_key_parameter(){
        String actualResult = caesarCoderWithoutKeyParameter.decode("khoor khoor");
        Assert.assertEquals(actualResult, "hello hello");
    }

    @DataProvider(name = "validStringsForEncoding")
    public static Object[][] validStringsForEncoding() {
        return new Object[][]{
                {"test test", "whvw whvw"},
                {"TEST TEst", "WHVW WHvw"},
                {"hello", "khoor"},
                {" running!! Running." , "uxqqlqj!! Uxqqlqj."},
                {"231 hello! ^^)", "231 khoor! ^^)"},
                {"x   x", "a   a"},
                {"123", "123"}
        };
    }

    @DataProvider(name = "invalidStringsForEncoding")
    public static Object[][] invalidStringsForEncoding() {
        return new Object[][]{
                {null},
                {"aaaaaaaaaabbbbbbbbbbcccccccccc"},
                {""},
                {"       "},
                {" оорпаорп"}
        };
    }

    @DataProvider(name = "validStringsForDecoding")
    public static Object[][] validStringsForDecoding() {
        return new Object[][]{
                {"Pdwlogd! :)", "Matilda! :)"},
                {"uhwxuq 12345 !", "return 12345 !"},
                {"L'p odwh!", "I'm late!"},
                {"khoor khoor", "hello hello"},
                {"VlPsOh UhWxUq ... :)", "SiMpLe ReTuRn ... :)"}
        };
    }

    @DataProvider(name = "invalidStringsForDecoding")
    public static Object[][] invalidStringsForDecoding() {
        return new Object[][]{
                {null},
                {""},
                {"    "},
                {"TРiс iс зе keu"},
                {"абвн"}
        };
    }



}
