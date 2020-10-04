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
    private static BigInteger p;

    static void mentalPoker() throws IOException {

        Random random = new Random();
        Scanner in = new Scanner(System.in);
        LinkedList<Player> players;
        Map<Integer, String> cards = createCards();
        ArrayList<Integer> hashCards = new ArrayList<>(cards.keySet());
        //игровая колода
        ArrayList<BigInteger> playingDeck;
        int numberPlayers = 0;


        p = BigInteger.probablePrime(bitLength, random);


        do {
            try {
                System.out.println("Введите количество игроков (от 2 до 8!)\n");
                numberPlayers = Integer.valueOf(in.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Ну почти 8...");
            }
        } while (numberPlayers < 2 || numberPlayers > 8);

        players = initPlayers(numberPlayers + 1, p);

        playingDeck = encryptionCycle(players, hashCards);

        for (Player pl : players) {
            takeCards(players, pl, playingDeck);
        }

        //Добавляем крупье (на стол) еще одну карту
        takeCardForStickman(players, playingDeck);

        showPlayerCards(players, cards);
//        String[] example = new String[2];
//        example[0] = cards.get(Integer.valueOf(players.get(numberPlayers).getCards().get(0).toString()));
//        example[1] = cards.get(Integer.valueOf(players.get(numberPlayers).getCards().get(1).toString()));
//
//        System.out.println("Карты крупье: " + example[0] + " и " + example[1]);
    }


    private static void takeCards(LinkedList<Player> players, Player player, ArrayList<BigInteger> playingDeck) {
        ArrayList<BigInteger> cards = new ArrayList<>();

        for (Player p : players) {
            if (p == player) {
                continue;
            }
            playingDeck.set(0, decipherRSA(playingDeck.get(0), p.getD()));
            playingDeck.set(1, decipherRSA(playingDeck.get(1), p.getD()));
        }

        cards.add(decipherRSA(playingDeck.get(0), player.getD()));
        cards.add(decipherRSA(playingDeck.get(1), player.getD()));
        playingDeck.remove(0);
        playingDeck.remove(0);

        player.setCards(cards);
    }

    private static void takeCardForStickman(LinkedList<Player> players, ArrayList<BigInteger> playingDeck) {
        Player stickman = players.get(players.size() - 1);

        for (Player p : players) {
            if (p == stickman) {
                continue;
            }
            playingDeck.set(0, decipherRSA(playingDeck.get(0), p.getD()));
        }

        stickman.getCards().add(decipherRSA(playingDeck.get(0), stickman.getD()));

        playingDeck.remove(0);
    }

    private static void showPlayerCards(LinkedList<Player> players, Map<Integer, String> cards) {
        players.forEach(s -> {
            StringBuilder sb = new StringBuilder();

            s.getCards().forEach(c -> {
                sb.append(cards.get(Integer.valueOf(c + "")));
                sb.append(" | ");
            });
            sb.append("\n");

            System.out.println(sb);
        });
    }

    private static Map<Integer, String> createCards() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("cards.txt"));
        Map<Integer, String> result = new HashMap<>();

        String tmp;
        while ((tmp = reader.readLine()) != null) {
            result.put(tmp.hashCode(), tmp);
        }

        return result;
    }


    private static LinkedList<Player> initPlayers(int numberPlayers, BigInteger p) {

        LinkedList<Player> result = new LinkedList<>();

        for (int i = 0; i < numberPlayers; i++) {
            result.add(new Player(p));
        }

        return result;
    }


    private static ArrayList<BigInteger> encryptionCycle(LinkedList<Player> players, ArrayList<Integer> hashCards) {
        ArrayList<BigInteger> result;

        result = copyToBigIntArray(hashCards);

        for (Player player : players) {
            result = (ArrayList<BigInteger>) result.stream()
                    .map(b -> encryptRSA(b, player.getC()))
                    .sorted(BigInteger::compareTo)
                    .collect(Collectors.toList());
        }

        return result;
    }

    private static ArrayList<BigInteger> copyToBigIntArray(ArrayList<Integer> hashCards) {
        ArrayList<BigInteger> result = new ArrayList<>();

        for (Integer i : hashCards) {
            result.add(new BigInteger(i.toString()));
        }

        return result;
    }

    private static BigInteger encryptRSA( BigInteger m, BigInteger c) {
        return Lab1.fastModuloExponentiation(m, c, p);
    }

    private static BigInteger decipherRSA(BigInteger m, BigInteger d) {
        return Lab1.fastModuloExponentiation(m, d, p);
    }
}
