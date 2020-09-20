package ru.favarish;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class Lab3 {
    private static final String fileName = "example";
    private static final String expansion = "png";
    private static final int bitLength = 30;

    public static void digitalSignatureRSA() throws IOException {
        Random random = new Random();
        BigInteger p, q, n, f, d, c, y, s, w;
        byte name = 1;
        Vector resultEuclid;
        FileInputStream fileInputStream = new FileInputStream(fileName + "." + expansion);

        do {
            q = BigInteger.probablePrime(bitLength, random);
        } while (!q.isProbablePrime(3));

        do {
            p = BigInteger.probablePrime(bitLength, random);
        } while (!p.isProbablePrime(3));

        n = q.multiply(p);
        f = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));

        while (true) {
            d = BigInteger.probablePrime(bitLength / 5, random);
            resultEuclid = Lab1.generalizedEuclidsAlgorithm(f, d);
            if (resultEuclid.gcd.compareTo(BigInteger.ONE) == 0) {
                break;
            }
        }

        if (resultEuclid.x.compareTo(BigInteger.ZERO) < 0) {
            resultEuclid.x = resultEuclid.x.add(f);
        }
        c = resultEuclid.x;

        byte[] readBytes = fileInputStream.readAllBytes();

        y = new BigInteger(readBytes.toString().hashCode() + "");
        s = Lab1.fastModuloExponentiation(y, c, n);

        w = Lab1.fastModuloExponentiation(s, d, n);

        if (w.compareTo(y) == 0) {
            System.out.println(w + " = " + y);
        } else {
            System.out.println(w + " != " + y);
        }

        /*        try (FileWriter fileWriter = new FileWriter("RSA/digitalSignature." + expansion)*//*BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("encoder.png"), "utf-8"))*//*) {

            String str = new String(readBytes);

            fileWriter.write(str.hashCode());
        }

        try (FileWriter fileWriter = new FileWriter("RSA/verificationDigitalSignature." + expansion) {

        }*/
    }
}
