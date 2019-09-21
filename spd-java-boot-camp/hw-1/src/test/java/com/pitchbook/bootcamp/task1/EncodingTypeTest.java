package com.pitchbook.bootcamp.task1;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EncodingTypeTest {

    @Test
    public void check_if_type_would_be_found_by_name_if_exists(){
        String encodingName = EncodingType.findEncoding("moRse").name();
        Assert.assertEquals(encodingName, "MORSE");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void check_if_encoder_factory_would_throw_an_exception_if_name_does_not_exist(){
       EncodingType.findEncoding("APPLE");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void check_if_encoder_factory_would_throw_an_exception_if_input_is_null(){
        EncodingType.findEncoding(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void check_if_encoder_factory_would_throw_an_exception_if_input_is_empty(){
        EncodingType.findEncoding("");
    }

}
