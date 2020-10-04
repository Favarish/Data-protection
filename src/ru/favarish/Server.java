package ru.favarish;

import java.math.BigInteger;
import java.util.Random;

public class Server {
    private static Server single;
    private static final int bitLength = 1024;
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger f;
    private BigInteger c;
    private BigInteger d;

    private Server() {
        Random random = new Random();
        Vector resultEuclid;

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
    }

    public static Server startServer() {
        if (single == null) {
            single = new Server();
        }
        return single;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getD() {
        return d;
    }
}
