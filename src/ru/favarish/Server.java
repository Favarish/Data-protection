package ru.favarish;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class Server {
    private static Server single;
    private static final int bitLength = 1024;
    private static final int address = 314159265;
    private static FileWriter fileWriter;
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger f;
    private BigInteger c;
    private BigInteger d;

    private Server() throws IOException {
        Random random = new Random();
        Vector resultEuclid;
        fileWriter = new FileWriter("Result Vote.txt");

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

    public BigInteger giveBulletin(BigInteger _h) {
        return Lab1.fastModuloExponentiation(_h, c, n);
    }

    public boolean checkVoice(BigInteger[] bulletin, String name) throws IOException {
        char voteChar = bulletin[0].toString(2).charAt(513);
        String vote = new String();

        if (voteChar == '0') {
            vote = "За си";
        } else if (voteChar == '1') {
            vote = "За Джавку";
        }

        if (new BigInteger(bulletin[0].hashCode() + "").compareTo(Lab1.fastModuloExponentiation(bulletin[1], d, n)) == 0) {
            fileWriter.write(name + " " + vote);
            return true;
        } else {
            return false;
        }
    }

    public static Server startServer() throws IOException {
        if (single == null) {
            single = new Server();
        }
        return single;
    }

    public static int getAddress() {
        return address;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getD() {
        return d;
    }
}
