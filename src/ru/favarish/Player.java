package ru.favarish;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Player {
    private static final int bitLength = 30;
    private BigInteger c;
    private BigInteger d;

    private ArrayList<BigInteger> cards;

    Player(BigInteger p) {
        Vector resultEuclid;
        Random random = new Random();

        do {
            c = BigInteger.probablePrime(bitLength - 1, random);
            resultEuclid = Lab1.generalizedEuclidsAlgorithm(p.subtract(BigInteger.ONE), c);
            assert resultEuclid != null;
            d = resultEuclid.y.compareTo(BigInteger.ZERO) > 0 ? resultEuclid.y : resultEuclid.y.add(p.subtract(BigInteger.ONE));
        } while (resultEuclid.gcd.compareTo(BigInteger.ONE) != 0);
    }

    BigInteger getC() {
        return c;
    }

    BigInteger getD() {
        return d;
    }

    ArrayList<BigInteger> getCards() {
        return cards;
    }

    void setCards(ArrayList<BigInteger> cards) {
        this.cards = cards;
    }
}
