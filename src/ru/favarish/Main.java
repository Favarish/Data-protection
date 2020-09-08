package ru.favarish;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Main {

    public static void main(String[] args) {
//        BigInteger answer = Lab1.fastModuloExponentiation(new BigInteger("5"), new BigInteger("3"), new BigInteger("7"));
//        System.out.println(answer);
//        System.out.println(answer.toString());
        //Lab1.schemeDiffieHellman(new BigInteger("1"), new BigInteger("2"));
//        Vector otvet = generalizedEuclidsAlgorithm(new BigInteger("28"), new BigInteger("19"));
//        System.out.println(otvet.gcd + " " + otvet.x + " " + otvet.y);

        //schemeDiffieHellman(new BigInteger("3652456374564"), new BigInteger("84723653856145823"));
//        BigInteger x = Lab1.stepBabyGiant(new BigInteger("5"), new BigInteger("6"), new BigInteger("7"));
//        System.out.println(x);
//        BigInteger little = new BigInteger("0");
//        BigInteger midle = new BigInteger("5");
//        BigInteger big = new BigInteger("10");
//
//
//        System.out.println(midle.compareTo(little));
//        System.out.println(midle.compareTo(midle));
//        System.out.println(midle.compareTo(big));


//        Vector testEuclids = Lab1.generalizedEuclidsAlgorithm(new BigInteger("22"), new BigInteger("7"));
//        System.out.println(testEuclids.gcd + " " + testEuclids.x + " " + testEuclids.y);

        String testStr = "Я text, !@#$%^&*()_который должен быть записан";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("newFileTest.txt");
            fileOutputStream.write(testStr.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fileInputStream = new FileInputStream("newFileTest.txt");
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes, 0, fileInputStream.available());
            for (int i = 0; i < bytes.length; i++) {
                System.out.print((char)bytes[i]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
