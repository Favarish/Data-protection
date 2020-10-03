package ru.favarish;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Лабораторная работа № 4 - Ментальный покер
 *
 * Игрок players.get(numberPlayers) - условный крупье. Так же участвует в шифровке карт.
 * В итоге у него оказываются карты для выкладывания на стол
 */

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
        //игровая колода
        ArrayList<BigInteger> playingDeck;
        int numberPlayers = 0;


        do {
            q = BigInteger.probablePrime(bitLength, random);
        } while (!q.isProbablePrime(3));

        do {
            p = BigInteger.probablePrime(bitLength, random);
        } while (!p.isProbablePrime(3));

        do {
            try {
                System.out.println("Введите количество игроков (от 2 до 8!)\n");
                numberPlayers = Integer.valueOf(in.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Ну почти 8...");
            }
        } while (numberPlayers < 2 || numberPlayers > 8);

        players = initPlayers(numberPlayers + 1, p, q);

        playingDeck = encryptionCycle(players, hashCards);

        for (Player pl : players) {
            takeCards(players, pl, playingDeck);
        }

        String[] example = new String[2];
        example[0] = cards.get(Integer.valueOf(players.get(numberPlayers).getCards().get(0).toString()));
        example[1] = cards.get(Integer.valueOf(players.get(numberPlayers).getCards().get(1).toString()));

        System.out.println("Карты крупье: " + example[0] + " и " + example[1]);
    }


    public static void takeCards(LinkedList<Player> players, Player player, ArrayList<BigInteger> playingDeck) {
        ArrayList<BigInteger> cards = new ArrayList<>();

        for (Player p : players) {
            if (p == player) {
                continue;
            }
            playingDeck.set(0, decipherRSA(playingDeck.get(0), p.getC(), p.getN()));
            playingDeck.set(1, decipherRSA(playingDeck.get(1), p.getC(), p.getN()));
        }

        cards.add(decipherRSA(playingDeck.get(0), player.getC(), player.getN()));
        cards.add(decipherRSA(playingDeck.get(1), player.getC(), player.getN()));
        playingDeck.remove(0);
        playingDeck.remove(0);

        player.setCards(cards);
    }

    public static void takeCardForStickman(LinkedList<Player> players, ArrayList<BigInteger> playingDeck) {
        for (Player p : players) {
            if (p == players.get(players.size() - 1)) {
                continue;
            }
            playingDeck.set(0, decipherRSA(playingDeck.get(0), p.getC(), p.getN()));
        }

        players.get(players.size() - 1).getCards().add(playingDeck.get(0));

        playingDeck.remove(0);
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


    public static ArrayList<BigInteger> encryptionCycle(LinkedList<Player> players, ArrayList<Integer> hashCards) {
        ArrayList<BigInteger> result;

        result = copyToBigIntArray(hashCards);

        for (Player player : players) {
            for (BigInteger b : result) {
                b = encryptRSA(player.getP(), player.getQ(), player.getC(), player.getD(), b);
            }
            result.sort(BigInteger::compareTo);
        }

        return result;
    }

//    public static ArrayList<BigInteger> requestForDecipher(Player player, ArrayList<BigInteger>)

    public static ArrayList<BigInteger> copyToBigIntArray(ArrayList<Integer> hashCards) {
        ArrayList<BigInteger> result = new ArrayList<>();

        for (Integer i : hashCards) {
            result.add(new BigInteger(i.toString()));
        }

        return result;
    }


    public static BigInteger encryptRSA(BigInteger p, BigInteger q, BigInteger c, BigInteger d, BigInteger m) {
        BigInteger  n, e;

        n = q.multiply(p);
        e = Lab1.fastModuloExponentiation(m, d, n);

        return e;
    }

    public static BigInteger decipherRSA(BigInteger m, BigInteger c, BigInteger n) {
        BigInteger result;

        result = Lab1.fastModuloExponentiation(m, c, n);

        return result;
    }
}
