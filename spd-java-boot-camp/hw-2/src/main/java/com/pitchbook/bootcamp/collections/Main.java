package com.pitchbook.bootcamp.collections;

import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
       Test test = new Test();
       Test test1 = new Test();
        List<Test> list = Arrays.asList(test, test1);
        System.out.println(TaxiParkTransformMethods.toUpperCase(list));
    }

}
