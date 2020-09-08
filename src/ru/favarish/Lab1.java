package ru.favarish;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

public class Lab1 {
    public static BigInteger fastModuloExponentiation(BigInteger a, BigInteger x, BigInteger p) {
        //return a.modPow(x, p);
        BigInteger y = new BigInteger("1");
        BigInteger s = new BigInteger(a.toString());
        String binary = x.toString(2);

        for (int i = binary.length() - 1; i >= 0; i--) {
            //49 символ = единице
            if (binary.charAt(i) == 49) y = (y.multiply(s)).mod(p);
            s = (s.multiply(s)).mod(p);
        }

        return y;
    }

    public static Vector generalizedEuclidsAlgorithm(BigInteger a, BigInteger b) {
        if (a.compareTo(b) == -1) return null;

        BigInteger q;
        Vector U = new Vector(a, new BigInteger("1"), new BigInteger("0"));
        Vector V = new Vector(b, new BigInteger("0"), new BigInteger("1"));

        while (V.gcd.compareTo(BigInteger.ZERO) != 0) {
            q = U.gcd.divide(V.gcd);
            Vector T = new Vector(U.gcd.mod(V.gcd), U.x.subtract(q.multiply(V.x)), U.y.subtract(q.multiply(V.y)));
            U = V;
            V = T;
        }

        return U;
    }


    public static void schemeDiffieHellman(BigInteger secretKeyA, BigInteger secretKeyB) {
        Random random = new Random();
        BigInteger p, q, g, openKeyA, openKeyB, resultA, resultB;


        do {
            q = BigInteger.probablePrime(90, random);
            p = new BigInteger((q.multiply(BigInteger.TWO)).add(BigInteger.ONE).toString());
        } while (!p.isProbablePrime(3));

        do {
            g = new BigInteger(p.bitLength(), random);
        } while (fastModuloExponentiation(g, q, p).compareTo(BigInteger.ONE) == 0);

        openKeyA = fastModuloExponentiation(g, secretKeyA, p);
        openKeyB = fastModuloExponentiation(g, secretKeyB, p);

        resultA = fastModuloExponentiation(openKeyB, secretKeyA, p);
        resultB = fastModuloExponentiation(openKeyA, secretKeyB, p);

        System.out.println(resultA + " = " + resultB);
    }

    public static boolean isPrime(BigInteger p) {
        if (p.compareTo(BigInteger.TWO) <= 0) return false;
        BigInteger b = p.sqrt();
        BigInteger i = new BigInteger("2");
        for (; i.compareTo(p) <= 0; i = i.add(BigInteger.ONE)) {
            if ((p.mod(i)) == BigInteger.ZERO) return false;
        }
        return true;
    }

    public static BigInteger stepBabyGiant(BigInteger a, BigInteger y, BigInteger p) {
        if (y.compareTo(p.subtract(BigInteger.ONE)) > 0) {
            System.out.println("Значение y  не может превышать p!");
            return null;
        }

        BigInteger m = p.sqrt().add(BigInteger.ONE);
        BigInteger k = new BigInteger(m.toString());
        //ArrayList<BigInteger> A = new ArrayList<>();
        ArrayList<BigInteger> B = new ArrayList<>();
        TreeMap<BigInteger, BigInteger> treeMap = new TreeMap<>();

        for (BigInteger i = new BigInteger("0"); i.compareTo(m) == -1; i = i.add(BigInteger.ONE)) {
            //A.add(fastModuloExponentiation(a.multiply(y), i, p));
            treeMap.put((fastModuloExponentiation(a, i, p).multiply(y)).mod(p), i);
        }

        for (BigInteger i = new BigInteger("1"); i.compareTo(k) == -1; i = i.add(BigInteger.ONE)) {
            B.add(fastModuloExponentiation(a, i.multiply(m), p));
        }


        for (BigInteger i = new BigInteger("1"); i.compareTo(k) <= 0; i = i.add(BigInteger.ONE)) {
            if (treeMap.containsKey(B.get(i.intValue() - 1))) {
                BigInteger x = (i.multiply(m)).subtract(treeMap.get(B.get(i.intValue() - 1)));
                return x;
            }
        }

        return null;
    }
}
