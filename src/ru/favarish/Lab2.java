package ru.favarish;

import java.io.*;
import java.math.BigInteger;
import java.util.Random;

public class Lab2 {
    private static final int bitLength = 30;

    public static void cipherShamir() throws FileNotFoundException {
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
            cA = BigInteger.probablePrime(bitLength, random);
        } while (!cA.isProbablePrime(3));

        resultEuclid = Lab1.generalizedEuclidsAlgorithm(p.subtract(BigInteger.ONE), cA);

        if (resultEuclid.y.compareTo(BigInteger.ZERO) == -1) {
            resultEuclid.y = resultEuclid.y.add(p.subtract(BigInteger.ONE));
        }
        dA = resultEuclid.y;

        do {
            cB = BigInteger.probablePrime(bitLength, random);
        } while (cB.isProbablePrime(3));

        resultEuclid = Lab1.generalizedEuclidsAlgorithm(p.subtract(BigInteger.ONE), cB);

        if (resultEuclid.y.compareTo(BigInteger.ZERO) == -1) {
            resultEuclid.y = resultEuclid.y.add(p.subtract(BigInteger.ONE));
        }
        dB = resultEuclid.y;

        try {
            FileOutputStream writerEncoderFile = new FileOutputStream("encoder.png");
//            name = fileInputStream.read();
//            name = readerOriginalFile.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
