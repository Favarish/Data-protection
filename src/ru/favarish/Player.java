package ru.favarish;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Player {
    private static final int bitLength = 30;
    private BigInteger p;
    private BigInteger q;
    private BigInteger c;
    private BigInteger d;
    private BigInteger n;

    private ArrayList<BigInteger> cards;

    public Player(BigInteger p, BigInteger q) {
        Vector resultEuclid;
        Random random = new Random();

        this.p = p;
        this.q = q;

        this.n = p.multiply(q);

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

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getC() {
        return c;
    }

    public BigInteger getD() {
        return d;
    }

    public BigInteger getN() {
        return n;
    }

    public ArrayList<BigInteger> getCards() {
        return cards;
    }

    public void setCards(ArrayList<BigInteger> cards) {
        this.cards = cards;
    }
}
