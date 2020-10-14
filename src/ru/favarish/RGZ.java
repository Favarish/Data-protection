package ru.favarish;

import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class RGZ {
    private static int n;
    private static int m;
    private static ArrayList<Edge> edges = new ArrayList<>();

    private static final boolean DEBUG_MODE = false;

    public static final String fileName = "testGraphIncorrect.txt";
    //testGraphCorrect
    //testGraphIncorrect

    public static void graphColoringTask() throws IOException {
        Edge edgeForBob;
        BigInteger z1Bobs;
        BigInteger z2Bobs;
        BigInteger _z1;
        BigInteger _z2;

        readFileGraph();

        // TODO: как быть с RSA?
        for (Edge e : edges) {
            e.genrateDataRSA();
        }

        //Тут должен быть цикл (пробегаемся по большому количеству вершин)
        //в нем нужно добавить перетасовку вершин
        for (int i = 0; i < 1000000000; i++) {
            ArrayList<Integer> colorsRand = randomColors();
            reColoringGraph(colorsRand.get(0), colorsRand.get(1), colorsRand.get(2));

            if(DEBUG_MODE)
                System.out.println("цвета " +colorsRand.get(0)+ colorsRand.get(1) + colorsRand.get(2));

            int indexEdge = random(0, edges.size() - 1);
            edgeForBob = edges.get(indexEdge);
            z1Bobs = edgeForBob.getZ1();
            z2Bobs = edgeForBob.getZ2();

            _z1 = Lab1.fastModuloExponentiation(z1Bobs, edgeForBob.getC1(), edgeForBob.getN1());
            _z2 = Lab1.fastModuloExponentiation(z2Bobs, edgeForBob.getC2(), edgeForBob.getN2());
            if (equalsYoungBit(_z1, _z2)) {
                System.out.println("НЕПРАВИЛЬНЫЕ ЦВЕТА!");

                if(DEBUG_MODE)
                    System.out.println((indexEdge+2)+" ребро имеет цвета "+ edgeForBob.getColor1() +" ## "+ edgeForBob.getColor2());

                break; //для выхода из цикла
            } else {
                System.out.println("Вершины разных цветов");
            }
        }




    }

    public static ArrayList<Integer> randomColors() {
        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(random(0, 2));

        int temp;
        do {
            temp = random(0, 2);
        } while (colors.contains(temp));
        colors.add(temp);

        do {
            temp = random(0, 2);
        } while (colors.contains(temp));
        colors.add(temp);

//        int color;
//
//        for (int i = 0; i < 3; i++) {
//            do {
//                color = random(0,2);
//                System.out.println("blyat;");
//            } while (!colors.contains(color));
//            colors.add(color);
//        }

        return colors;
    }

    public static void reColoringGraph(int colorFirst, int colorSecond, int colorThird) {
        for (Edge edge: edges) {
            edge.generateRandZ(colorFirst, colorSecond, colorThird);
        }
    }


    private static void readFileGraph() throws IOException {
        String tmp;
        String[] tmpMas;

        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        tmpMas = reader.readLine().split(",");
        n = Integer.valueOf(tmpMas[0]);
        m = Integer.parseInt(tmpMas[1]);


        while ((tmp = reader.readLine()) != null) {
            tmpMas = tmp.split(",");
            edges.add(new Edge(Integer.valueOf(tmpMas[0]), Integer.valueOf(tmpMas[1]), Integer.valueOf(tmpMas[2]),
                    Integer.valueOf(tmpMas[3])));

        }
    }

    //метод Боба для выбора очередного ребра
    private static int random(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    private static boolean equalsYoungBit(BigInteger a, BigInteger b) {
        String first = a.toString(2).substring(a.toString(2).length()-2);
        String second = b.toString(2).substring(b.toString(2).length()-2);

        if(DEBUG_MODE)
            System.out.println(first+" && " + second);

        return first.equals(second);
    }
}
