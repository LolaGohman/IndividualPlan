package com.pitchbook.bootcamp.task1.factory;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EncodersTest {

    @Test
    public void check_if_encoder_would_be_found_by_name_if_exists(){
        String encoderClassName = Encoders.getEncoder("caEsar").getClass().getSimpleName();
        Assert.assertEquals(encoderClassName, "CaesarCoder");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void check_if_encoder_factory_would_throw_an_exception_if_name_does_not_exist(){
       Encoders.getEncoder("APPLE");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void check_if_encoder_factory_would_throw_an_exception_if_input_is_null(){
        Encoders.getEncoder(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void check_if_encoder_factory_would_throw_an_exception_if_input_is_empty(){
        Encoders.getEncoder("");
    }

}
