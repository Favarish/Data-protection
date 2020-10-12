package ru.favarish;

import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class Server {
    private static Server single;
    private static final int bitLength = 1024;
    private static final int address = 314159265;
    private static FileWriter fileWriter;
    private static Map<BigInteger, String> resultVote;
    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger f;
    private BigInteger c;
    private BigInteger d;

    private Server() throws IOException {
        Random random = new Random();
        Vector resultEuclid;
        readFile();
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

    public int checkVoice(BigInteger[] bulletin, String name) throws IOException {
        char voteChar = bulletin[0].toString(2).charAt(bulletin[0].toString(2).length() - 1);
        String vote = new String();

        if (voteChar == '0') {
            vote = "Си";
        } else if (voteChar == '1') {
            vote = "Джавка";
        }

        if (new BigInteger(bulletin[0].hashCode() + "").abs().compareTo(Lab1.fastModuloExponentiation(bulletin[1], d, n)) == 0) {
            if (!resultVote.containsKey(new BigInteger(bulletin[0].hashCode() + "").abs())
                    && !resultVote.containsKey(new BigInteger(bulletin[0].hashCode() + "").abs().add(BigInteger.ONE))
                    && !resultVote.containsKey(new BigInteger(bulletin[0].hashCode() + "").abs().subtract(BigInteger.ONE))) {
                resultVote.put(new BigInteger(bulletin[0].hashCode() + "").abs(), vote);
                return 1;
            }
            return 2;
        } else {
            return 3;
        }
    }

    private static Map<BigInteger, String> readFile() throws IOException {
        resultVote = new HashMap<>();

        File file = new File("Result Vote.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String temp = new String();
        while ((temp = reader.readLine()) != null) {
            String[] tmpMas = temp.split(" ");

            resultVote.put(new BigInteger(tmpMas[0]), tmpMas[1]);
        }

        return resultVote;
    }

    public static Server startServer() throws IOException {
        if (single == null) {
            single = new Server();
        }
        return single;
    }

    public static void stopServer() throws IOException {
        for (Map.Entry<BigInteger, String> map : resultVote.entrySet()) {
            fileWriter.write(map.getKey() + " " + map.getValue() + "\n");
        }

        fileWriter.close();
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
