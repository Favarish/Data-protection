package ru.favarish;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Lab4 {
    private static final int bitLength = 40;

//    public static void writeFileCards() throws IOException {
//        FileWriter writer = new FileWriter("cards.txt");
//
//        String[] strs = {"П", "Ч", "Б", "К"};
//
//        for (int i = 0; i < strs.length; i++) {
//            for (int j = 2; j < 11; j++) {
//                writer.write(strs[i] + " " + j + "\n");
//            }
//            writer.write(strs[i] + " V\n");
//            writer.write(strs[i] + " D\n");
//            writer.write(strs[i] + " K\n");
//            writer.write(strs[i] + " T\n");
//        }
//
//        writer.close();
//    }

    public static void mentalPoker() throws IOException {

        Random random = new Random();
        Scanner in = new Scanner(System.in);
        BigInteger p, q;
        LinkedList<Player> players = new LinkedList<>();
        Map<Integer, String> cards = createCards();
        ArrayList<Integer> hashCards = new ArrayList<>(cards.keySet());
        int numberPlayers;


        do {
            q = BigInteger.probablePrime(bitLength, random);
        } while (!q.isProbablePrime(3));

        do {
            p = BigInteger.probablePrime(bitLength, random);
        } while (!p.isProbablePrime(3));

        do {
            System.out.println("Введите количество игроков (от 2 до 8!)\n");
            numberPlayers = Integer.valueOf(in.nextLine());
        } while (numberPlayers < 2 || numberPlayers > 8);

        players = initPlayers(numberPlayers, p, q);

        encryptionCycle(players, );

    }


    public static Map<Integer, String> createCards() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("cards.txt"));
        Map<Integer, String> result = new HashMap<>();

        String tmp;
        while ((tmp = reader.readLine()) != null) {
            result.put(tmp.hashCode(), tmp);
        }

        return result;
    }


    public static LinkedList<Player> initPlayers(int numberPlayers, BigInteger p, BigInteger q) {

        LinkedList<Player> result = new LinkedList<>();

        for (int i = 0; i < numberPlayers; i++) {
            result.add(new Player(p, q));
        }

        return result;
    }


    public static void encryptionCycle(LinkedList<Player> players, ) {

    }


    public static void encryptRSA(BigInteger p, BigInteger q, BigInteger c, BigInteger d) throws IOException {

        Random random = new Random();
        BigInteger  n, f, e, perem;
        Vector resultEuclid;
        //FileInputStream fileInputStream = new FileInputStream(fileName + "." + expansion);


        n = q.multiply(p);
        f = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));

//        while (true) {
//            d = BigInteger.probablePrime(bitLength, random);
//            resultEuclid = Lab1.generalizedEuclidsAlgorithm(f, d);
//            if (resultEuclid.gcd.compareTo(BigInteger.ONE) == 0) {
//                break;
//            }
//        }
//
//        if (resultEuclid.y.compareTo(BigInteger.ZERO) < 0) {
//            resultEuclid.y = resultEuclid.y.add(f);
//        }
//        c = resultEuclid.y;

        //byte[] readBytes = fileInputStream.readAllBytes();

//        try (FileWriter fileWriter = new FileWriter("RSAEncoder." + expansion)) {
//            for (byte i : readBytes) {
//                int intByte = i + 128;
//                BigInteger temp = new BigInteger(intByte + "");
//                e = Lab1.fastModuloExponentiation(temp, d, n);
//                fileWriter.write(e + "\n");
//            }
//        }

//        //расшифоровка
//        BufferedReader reader = new BufferedReader(new FileReader("RSAEncoder." + expansion));
//        try (FileOutputStream fileOutputStream = new FileOutputStream("RSADecoder." + expansion)) {
//            String str;
//            byte[] bytes = new byte[readBytes.length];
//            int i = 0;
//
//            while ((str = reader.readLine()) != null) {
//                perem = Lab1.fastModuloExponentiation(new BigInteger(str), c, n);
//                bytes[i] = (byte) (Integer.valueOf(perem + "") - 128);
//                i++;
//            }
//            fileOutputStream.write(bytes);
//        }
    }

    public static void decipherRSA() {

    }
}
