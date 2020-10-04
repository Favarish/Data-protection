package ru.favarish;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

/*        Random random = new Random();
        System.out.println(Lab1.fastModuloExponentiation(BigInteger.probablePrime(9, random),
                BigInteger.probablePrime(9, random), BigInteger.probablePrime(9, random)));

        Vector vector = Lab1.generalizedEuclidsAlgorithm(new BigInteger("184194831"), new BigInteger("1425213"));
        System.out.println(vector.gcd + " " + vector.x + " " + vector.y);
        Lab1.schemeDiffieHellman(new BigInteger("1384718"), new BigInteger("13413473"));*/


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

/*        String testStr = "Я text, !@#$%^&*()_который должен быть записан";
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
        }*/
        //Lab2.cipherShamir();

/*        String testStr = "I - string at English!";
        String testStrRus = "Я - строка на Русском!";
        String general = "У меня есть и Rus and English symbol!";

        byte[] bytes = testStr.getBytes();
        byte[] bytesRus = testStrRus.getBytes();
        byte[] bytesGeneral = general.getBytes();

        String stringResult = new String(bytes);
        String stringResultRus = new String(bytesRus);
        String stringGeneral = new String(bytesGeneral);
        System.out.println(stringResult + " | " + stringResultRus + " | " + stringGeneral);
//        System.out.println((char) -48);

        Lab2.cipherShamir();*/

//        Lab2.cipherRSA();
/*        Vector vector = Lab1.generalizedEuclidsAlgorithm(new BigInteger("40"), new BigInteger("3"));
        System.out.println(vector.toString());*/
/*        BigInteger bigInteger = new BigInteger("20");
        BigInteger bigInteger2 = new BigInteger("3");
        BigInteger result = bigInteger.gcd(bigInteger2);
        System.out.println(result);*/
        //System.out.println(Lab1.fastModuloExponentiation(new BigInteger("3"), new BigInteger("-1"), new BigInteger("40")));

//        Lab2.cipherShamir();
//        Lab2.cipherElGamal();
//        Lab2.cipherRSA();
//        Lab2.cipherVernam();

//        Lab3.digitalSignatureRSA();
//        Lab3.digitalSignatureElGamal();
//        Lab3.digitalSignatureGOST();
        Lab4.mentalPoker();

    }

}
