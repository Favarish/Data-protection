package ru.favarish;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Lab5 {
    private static final int bitLength = 512;
    private static Server server;


    public static void Vote() throws IOException {
        Random random = new Random();
        Scanner in = new Scanner(System.in);
        BigInteger n, rnd, v, r, h, _h, _s, s;
        Vector resultEuclid;
        Integer voice = -1;
        BigInteger[] bulletin = new BigInteger[2];

        server = Server.startServer();

        do {
            try {
                System.out.println("Выберите за что голосуете:\n");
                System.out.println("0 - за си");
                System.out.println("1 - за джавку");
                voice = Integer.valueOf(in.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Некорректный ввод. Попробуйте еще раз");
            }
        } while (voice < 0 || voice > 1);
        //Пункт 1
        rnd = BigInteger.probablePrime(bitLength, random);
        v = new BigInteger(voice.toString()/* + Server.getAddress()*/);

        n = new BigInteger(rnd.toString() + v);

        //Пункт 2
        do {
            r = BigInteger.probablePrime(30, random);
            resultEuclid = Lab1.generalizedEuclidsAlgorithm(server.getN(), r);
        } while (resultEuclid.gcd.compareTo(BigInteger.ONE) != 0);

        //Пункт 3
        h = new BigInteger(n.hashCode() + "").abs();

        //Пункт 4 и 5
        _h = h.multiply(Lab1.fastModuloExponentiation(r, server.getD(), server.getN()));
        _s = server.giveBulletin(_h);

        //Пункт 6
        s = _s.multiply(r.modInverse(server.getN()));

        //Пункт 7
        bulletin[0] = n;
        bulletin[1] = s;

        if (server.checkVoice(bulletin, "Alice")) {
            System.out.println("Ваш голос успешно зачтен!");
        } else {
            System.out.println("Произошла ошибка при принятии голоса. Возможно, вы пытаетесь проголосовать второй раз");
        }

        voting(rnd, 1);

        readResultVote();

        Server.stopServer();
    }

    public static void voting(BigInteger rnd, Integer voice) throws IOException {
        BigInteger n, v, r, h, _h, _s, s;
        Random random = new Random();
        Vector resultEuclid;
        BigInteger[] bulletin = new BigInteger[2];


        v = new BigInteger(voice.toString()/* + Server.getAddress()*/);

        n = new BigInteger(rnd.toString() + v);

        //Пункт 2
        do {
            r = BigInteger.probablePrime(30, random);
            resultEuclid = Lab1.generalizedEuclidsAlgorithm(server.getN(), r);
        } while (resultEuclid.gcd.compareTo(BigInteger.ONE) != 0);

        //Пункт 3
        h = new BigInteger(n.hashCode() + "").abs();

        //Пункт 4 и 5
        _h = h.multiply(Lab1.fastModuloExponentiation(r, server.getD(), server.getN()));
        _s = server.giveBulletin(_h);

        //Пункт 6
        s = _s.multiply(r.modInverse(server.getN()));

        //Пункт 7
        bulletin[0] = n;
        bulletin[1] = s;

        if (server.checkVoice(bulletin, "Alice")) {
            System.out.println("Ваш голос успешно зачтен!");
        } else {
            System.out.println("Произошла ошибка при принятии голоса. Возможно, вы пытаетесь проголосовать второй раз");
        }
    }

    public static void readResultVote() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Result Vote.txt"));
        String tmp;

        while ((tmp = reader.readLine()) != null) {
            System.out.println(tmp);
        }
    }
}
