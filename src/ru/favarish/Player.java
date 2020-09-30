package ru.favarish;

import java.math.BigInteger;
import java.util.Random;

public class Player {
    private static final int bitLength = 30;
    private BigInteger c;
    private BigInteger d;

    public Player(BigInteger p, BigInteger q) {
        Vector resultEuclid;
        Random random = new Random();

        BigInteger f = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
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
}
