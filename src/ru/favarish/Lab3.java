package ru.favarish;

import java.io.*;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Lab3 {
    private static final String fileName = "example";
    private static final String expansion = "png";
    private static final int bitLength = 40;

    public static void digitalSignatureRSA() throws IOException {
        Random random = new Random();
        BigInteger p, q, n, f, d, c, y, s, w;
        Vector resultEuclid;
        File file = new File(fileName + "." + expansion);

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

        if (resultEuclid.y.compareTo(BigInteger.ZERO) < 0) {
            resultEuclid.y = resultEuclid.y.add(f);
        }
        c = resultEuclid.y;

        //создание и запись в файл цифровой подписи
        try (FileWriter fileWriter = new FileWriter("hashCodeRSA")) {
            y = new BigInteger(file.hashCode() + "");
            y = y.abs();
            s = Lab1.fastModuloExponentiation(y, c, n);
            fileWriter.write(s.toString());
        }

        //проверка цифровой подписи
        BufferedReader reader = new BufferedReader(new FileReader("hashCodeRSA"));
        y = new BigInteger(file.hashCode() + "");
        y = y.abs();
        s = new BigInteger(reader.readLine());
        w = Lab1.fastModuloExponentiation(s, d, n);

        if (w.compareTo(y) == 0) {
            System.out.println(w + " = " + y);
        }
    }

    public static void digitalSignatureElGamal() throws IOException {
        Random random = new Random();
        BigInteger p, q, g, x, y, h, k, r, u, s;
        File file = new File(fileName + "." + expansion);

        do {
            p = BigInteger.probablePrime(bitLength, random);
            q = (p.subtract(BigInteger.ONE).divide(BigInteger.TWO));
        } while (!p.isProbablePrime(3) || !q.isProbablePrime(3));

        do {
            g = BigInteger.probablePrime(bitLength - 1, random);
        } while (Lab1.fastModuloExponentiation(g, q, p).compareTo(BigInteger.ONE) == 0);
        x = BigInteger.probablePrime(bitLength - 1, random);
        y = Lab1.fastModuloExponentiation(g, x, p);

        try (FileWriter fileWriter = new FileWriter("hashCodeElGamal")){
            Vector vector;
            h = new BigInteger(file.hashCode() + "");
            h = h.abs();
            do {
                k = BigInteger.probablePrime(bitLength - 1, random);
                vector = Lab1.generalizedEuclidsAlgorithm(p.subtract(BigInteger.ONE), k);
            } while (vector.gcd.compareTo(BigInteger.ONE) != 0);

            r = Lab1.fastModuloExponentiation(g, k, p);
            u = (h.subtract(x.multiply(r))).mod(p.subtract(BigInteger.ONE));
            if (u.compareTo(BigInteger.ZERO) < 0) {
//                u = u.add(p.subtract(BigInteger.ONE));
                System.out.println("u < 0!:");
            }
            vector = Lab1.generalizedEuclidsAlgorithm(p.subtract(BigInteger.ONE), k);
            s = ((vector.y.add(p.subtract(BigInteger.ONE))).multiply(u)).mod(p.subtract(BigInteger.ONE));
            fileWriter.write(r + " " + s);
        }


        //провека подписи
        BufferedReader reader = new BufferedReader(new FileReader("hashCodeElGamal"));
        h = new BigInteger(file.hashCode() + "");
        h = h.abs();
        String[] str = reader.readLine().split(" ");
        r = new BigInteger(str[0]);
        s = new BigInteger(str[1]);

        BigInteger temp1 = (Lab1.fastModuloExponentiation(y, r, p).multiply(Lab1.fastModuloExponentiation(r, s, p))).mod(p);
        BigInteger temp2 = Lab1.fastModuloExponentiation(g, h, p);

        if (temp1.compareTo(temp2) == 0) {
            System.out.println("Проверка подписи пройдена успешно");
        } else {
            System.out.println("Подпись НЕ прошла проверку!");
        }
    }

    public static void digitalSignatureGOST() throws IOException {
        Random random = new Random();
        BigInteger p, q, b, g, a, x, y, h, hMinOne, k, r, s, u1, u2, v;
        File file = new File(fileName + "." + expansion);

        q = BigInteger.probablePrime(256, random);
        p = BigInteger.probablePrime(1024, random);

        b = (p.subtract(BigInteger.ONE)).divide(q);

        //магическим образом вычисляем а
        do {
            g = BigInteger.probablePrime(1023, random);
            a = Lab1.fastModuloExponentiation(g, b, p);
        } while (a.compareTo(BigInteger.TWO) >= 0 );

        //bitLength просто меньше 256
        x = BigInteger.probablePrime(bitLength, random);
        y = Lab1.fastModuloExponentiation(a, x, p);

        try (FileWriter fileWriter = new FileWriter("digitalSignatureGOST")){
            h = new BigInteger(file.hashCode() + "");
            h = h.abs();
            do {
                k = BigInteger.probablePrime(bitLength, random);
                r = Lab1.fastModuloExponentiation(a, k, p);
            } while (r.compareTo(BigInteger.ZERO) == 0);

            do {
                s = ((k.multiply(h)).add(x.multiply(r))).mod(q);
            } while (s.compareTo(BigInteger.ZERO) == 0);
            fileWriter.write(r + " " + s);
        }

        //проверка подписи
        BufferedReader reader = new BufferedReader(new FileReader("digitalSignatureGOST"));
        h = new BigInteger(file.hashCode() + "");
        h = h.abs();
        String[] str = reader.readLine().split(" ");
        r = new BigInteger(str[0]);
        s = new BigInteger(str[1]);
        if (r.compareTo(q) >= 0 || s.compareTo(q) >= 0) {
            System.out.println("Числа r/s не удовлетворяют диапазону 0<r<q или 0<s<q!\n" +
                    "Дальнейшая проверка подписи не имеет смысла!");
            return;
        }
//        Vector vector = Lab1.generalizedEuclidsAlgorithm(q, h);
//        hMinOne = vector.y.add(q);
        hMinOne = h.modInverse(p);
        u1 = (s.multiply(hMinOne)).mod(q);
        u2 = ((r.multiply(new BigInteger("-1"))).multiply(hMinOne)).mod(q);
        v = (Lab1.fastModuloExponentiation(a, u1, p).multiply(Lab1.fastModuloExponentiation(y, u2, p))).mod(q);

        if (v.compareTo(r) == 0) {
            System.out.println("Проверка подписи ГОСТ успешно пройдена!");
        } else {
            System.out.println("Цифровая подпись не прошла проверку! (ГОСТ)");
        }
    }




}
