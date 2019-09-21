package com.pitchbook.bootcamp.task1;

import org.testng.annotations.Test;


public class MainTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_number_of_arguments_is_not_equal_to_2(){
        Main.main(new String[]{"hello"});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void should_throw_exception_when_no_arguments(){
        Main.main(null);
    }

}
