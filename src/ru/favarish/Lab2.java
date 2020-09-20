package ru.favarish;

import java.io.*;
import java.math.BigInteger;
import java.util.Random;

import static java.lang.Byte.*;

public class Lab2 {
    private static final String fileName = "example";
    private static final String expansion = "png";
    private static final int bitLength = 30;

    public static void cipherShamir() throws IOException {
        Random random = new Random();
        Vector resultEuclid;
//        FileReader readerOriginalFile = new FileReader("example.png");
        FileInputStream fileInputStream = new FileInputStream(fileName + "." + expansion);
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

/*        for (byte b : readBytes) {
            int i = b;
*//*            BigInteger x1 = Lab1.fastModuloExponentiation(new BigInteger(i + ""), cA, p);
            BigInteger x2 = Lab1.fastModuloExponentiation(x1, cB, p);
            BigInteger x3 = Lab1.fastModuloExponentiation(x2, dA, p);
            BigInteger x4 = Lab1.fastModuloExponentiation(x3, dB, p);*//*

            BigInteger x1 = new BigInteger(i + "").pow(cA, p);

            System.out.println(b + " = " + x4);
        }*/

        try (FileWriter fileWriter = new FileWriter("ShamirEncoder." + expansion)/*BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("encoder.png"), "utf-8"))*/) {
            for (byte i : readBytes) {
                int intByte = i + 128;
                BigInteger x1 = Lab1.fastModuloExponentiation(new BigInteger(intByte + ""), cA, p);
                BigInteger x2 = Lab1.fastModuloExponentiation(x1, cB, p);
                BigInteger x3 = Lab1.fastModuloExponentiation(x2, dA, p);
                fileWriter.write(x3.toString() + "\n");
            }
        }

        BufferedReader reader = new BufferedReader(new FileReader("ShamirEncoder." + expansion));
        ;
        try (FileOutputStream fileOutputStream = new FileOutputStream("ShamirDecoder." + expansion)/*FileWriter fileWriter = new FileWriter("decoder.png")*//*BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("decoder.png"), "UTF-8"))*/) {
            String str;
            byte[] bytes = new byte[readBytes.length];
            int i = 0;
            while ((str = reader.readLine()) != null) {
                BigInteger x4 = Lab1.fastModuloExponentiation(new BigInteger(str), dB, p);
//                byte temp =(byte) (Integer.valueOf(x4.toString()) - 128);
                bytes[i] = (byte) (Integer.valueOf(x4.toString()) - 128);
                i++;
//               fileWriter.write(temp + "");
//                fileWriter.write((char) temp);
            }
            fileOutputStream.write(bytes);
//            String string = new String(bytes);
//            System.out.println(string);
//            bufferedWriter.write(string);
        }

//        FileWriter fileWriter = new FileWriter("decoder.png");
//        FileOutputStream fileOutputStream = new FileOutputStream("decoder.png");
//        fileOutputStream.write();

    }


    public static void cipherRSA() throws IOException {
        Random random = new Random();
        BigInteger p, q, n, f, d, c, e, perem;
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
            d = BigInteger.probablePrime(bitLength, random);
            resultEuclid = Lab1.generalizedEuclidsAlgorithm(f, d);
            if (resultEuclid.gcd.compareTo(BigInteger.ONE) == 0) {
                break;
            }
        }

        if (resultEuclid.y.compareTo(BigInteger.ZERO) < 0) {
            resultEuclid.y = resultEuclid.y.add(f);
        }
        c = resultEuclid.y;

        byte[] readBytes = fileInputStream.readAllBytes();

        try (FileWriter fileWriter = new FileWriter("RSAEncoder." + expansion)) {
            for (byte i : readBytes) {
                int intByte = i + 128;
                BigInteger temp = new BigInteger(intByte + "");
                e = Lab1.fastModuloExponentiation(temp, d, n);
                fileWriter.write(e + "\n");
            }
        }

        BufferedReader reader = new BufferedReader(new FileReader("RSAEncoder." + expansion));
        try (FileOutputStream fileOutputStream = new FileOutputStream("RSADecoder." + expansion)) {
            String str;
            byte[] bytes = new byte[readBytes.length];
            int i = 0;

            while ((str = reader.readLine()) != null) {
                perem = Lab1.fastModuloExponentiation(new BigInteger(str), c, n);
                bytes[i] = (byte) (Integer.valueOf(perem + "") - 128);
                i++;
            }
            fileOutputStream.write(bytes);
        }
    }


    public static void cipherElGamal() throws IOException {
        Random random = new Random();
        BigInteger p, q, g, x, y, k, r, e, perem;
        FileInputStream fileInputStream = new FileInputStream(fileName + "." + expansion);

        do {
            p = BigInteger.probablePrime(bitLength, random);
            q = (p.subtract(BigInteger.ONE).divide(BigInteger.TWO));
        } while (!p.isProbablePrime(3) || !q.isProbablePrime(3));

        do {
            g = BigInteger.probablePrime(bitLength, random);
        } while (!(g.compareTo(p.subtract(BigInteger.ONE)) < 0) && Lab1.fastModuloExponentiation(g, q, p).compareTo(BigInteger.ONE) == 0);

        x = BigInteger.probablePrime(bitLength - 1, random);
        y = Lab1.fastModuloExponentiation(g, x, p);
        k = BigInteger.probablePrime(bitLength - 1, random);

        byte[] readBytes = fileInputStream.readAllBytes();

        try (FileWriter fileWriter = new FileWriter("ElGamalEncoder." + expansion)){
            for (byte i : readBytes) {
                int intByte = i + 128;
                BigInteger temp = new BigInteger(intByte + "");
                r = Lab1.fastModuloExponentiation(g, k, p);
                e = Lab1.fastModuloExponentiation(temp.multiply(Lab1.fastModuloExponentiation(y, k, p)), BigInteger.ONE, p);
                fileWriter.write(r + " " + e + "\n");
            }
        }

        BufferedReader reader = new BufferedReader(new FileReader("ElGamalEncoder." + expansion));
        try (FileOutputStream fileOutputStream = new FileOutputStream("ElGamaldEcoder." + expansion)) {
            String tmpStr;
            byte[] bytes = new byte[readBytes.length];
            int i = 0;
            while ((tmpStr = reader.readLine()) != null) {
                String[] readStr = tmpStr.split(" ");
                r = new BigInteger(readStr[0]);
                e = new BigInteger(readStr[1]);

                perem = Lab1.fastModuloExponentiation(e.multiply(Lab1.fastModuloExponentiation(r, p.subtract(BigInteger.ONE).subtract(x), p)), BigInteger.ONE, p);
                bytes[i] = (byte) (Integer.valueOf(perem + "") - 128);
                i++;
            }
            fileOutputStream.write(bytes);
        }
    }

    public static void cipherVernam() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName + "." + expansion);
        byte[] readBytes = fileInputStream.readAllBytes();
        int k, e;

        try (FileWriter fileWriter = new FileWriter("VernamEncoder." + expansion)){
            for (byte i : readBytes) {
                int intByte = i + 128;
                k = (int)(Math.random() * 256) + 1;
                e = intByte ^ k;
                fileWriter.write(e + " " + k + "\n");
            }
        }

        BufferedReader reader = new BufferedReader(new FileReader("VernamEncoder." + expansion));
        try (FileOutputStream fileOutputStream = new FileOutputStream("VernamDecoder." + expansion)) {
            String str;
            byte[] bytes = new byte[readBytes.length];
            int i = 0;

            while ((str = reader.readLine()) != null) {
                String[] temp = str.split(" ");
                bytes[i] = (byte) ((Integer.valueOf(temp[0]) ^ Integer.valueOf(temp[1])) - 128);
                i++;
            }
            fileOutputStream.write(bytes);
        }
    }
}






