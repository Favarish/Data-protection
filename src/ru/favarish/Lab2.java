package ru.favarish;

import java.io.*;
import java.math.BigInteger;
import java.util.Random;

import static java.lang.Byte.*;

public class Lab2 {
    private static final int bitLength = 30;

    public static void cipherShamir() throws IOException {
        Random random = new Random();
        Vector resultEuclid;
        FileReader readerOriginalFile = new FileReader("example.png");
        FileInputStream fileInputStream = new FileInputStream("example.png");
        BigInteger cA, dA, cB, dB, p, perem;
        byte name;

        do {
            p = BigInteger.probablePrime(bitLength, random);
        } while (!p.isProbablePrime(3));

        do {
            cA = BigInteger.probablePrime(bitLength - 1, random);
        } while (!cA.isProbablePrime(3));

        resultEuclid = Lab1.generalizedEuclidsAlgorithm(p.subtract(BigInteger.ONE), cA);

        if (resultEuclid.y.compareTo(BigInteger.ZERO) == -1) {
            resultEuclid.y = resultEuclid.y.add(p.subtract(BigInteger.ONE));
        }
        dA = resultEuclid.y;

        do {
            cB = BigInteger.probablePrime(bitLength - 1, random);
        } while (!cB.isProbablePrime(3));

        resultEuclid = Lab1.generalizedEuclidsAlgorithm(p.subtract(BigInteger.ONE), cB);

        if (resultEuclid.y.compareTo(BigInteger.ZERO) == -1) {
            resultEuclid.y = resultEuclid.y.add(p.subtract(BigInteger.ONE));
        }
        dB = resultEuclid.y;

        byte[] readBytes = fileInputStream.readAllBytes();

        try (/*FileWriter fileWriter = new FileWriter("encoder.png")*/BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("encoder.png"), "utf-8"))) {
            for (byte i : readBytes) {
                int intByte = i;
                BigInteger x1 = Lab1.fastModuloExponentiation(new BigInteger(i + ""), cA, p);
                BigInteger x2 = Lab1.fastModuloExponentiation(x1, cB, p);
                BigInteger x3 = Lab1.fastModuloExponentiation(x2, dA, p);
                String temp = x3.toString() + "\n";
                bufferedWriter.write(temp.toCharArray());
            }
        }

        BufferedReader reader = new BufferedReader(new FileReader("encoder.png"));
        ;
        try (/*FileWriter fileWriter = new FileWriter("decoder.png")*/BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("decoder.png"), "UTF-8"))) {
            String str;
            while ((str = reader.readLine()) != null) {
                BigInteger x4 = Lab1.fastModuloExponentiation(new BigInteger(str), dB, p);
                int temp = Integer.valueOf(x4.toString());
                bufferedWriter.write((char) temp);
//                fileWriter.write((char) temp);
            }
        }


    }
}
