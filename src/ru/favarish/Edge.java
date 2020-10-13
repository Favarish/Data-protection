package ru.favarish;


import java.math.BigInteger;
import java.util.Random;

/**
 * Условные цвета:
 * 0 - красный
 * 1 - синий
 * 2 - желтый
 */
public class Edge {
    private Integer vertex1;
    private Integer vertex2;

    private Integer color1;
    private Integer color2;

    private static final Integer bitLength = 30;
    private BigInteger p1;
    private BigInteger p2;
    private BigInteger q1;
    private BigInteger q2;
    private BigInteger n1;
    private BigInteger n2;
    private BigInteger c1;
    private BigInteger c2;
    private BigInteger d1;
    private BigInteger d2;
    private BigInteger r1;
    private BigInteger r2;

    private BigInteger z1;
    private BigInteger z2;


     Edge(Integer vertex1, Integer vertex2, Integer color1, Integer color2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.color1 = color1;
        this.color2 = color2;
    }

    public void genrateDataRSA() {
        Random random = new Random();
        Vector resultEuclid;
        BigInteger f;

        do {
            q1 = BigInteger.probablePrime(bitLength, random);
        } while (!q1.isProbablePrime(3));

        do {
            p1 = BigInteger.probablePrime(bitLength, random);
        } while (!p1.isProbablePrime(3));

        n1 = q1.multiply(p1);
        f = (p1.subtract(BigInteger.ONE).multiply(q1.subtract(BigInteger.ONE)));

        while (true) {
            d1 = BigInteger.probablePrime(bitLength, random);
            resultEuclid = Lab1.generalizedEuclidsAlgorithm(f, d1);
            if (resultEuclid.gcd.compareTo(BigInteger.ONE) == 0) {
                break;
            }
        }

        if (resultEuclid.y.compareTo(BigInteger.ZERO) < 0) {
            resultEuclid.y = resultEuclid.y.add(f);
        }
        c1 = resultEuclid.y;

        //Вычисляем значения RSA для второй вершины
        do {
            q2 = BigInteger.probablePrime(bitLength, random);
        } while (!q2.isProbablePrime(3));

        do {
            p2 = BigInteger.probablePrime(bitLength, random);
        } while (!p2.isProbablePrime(3));

        n2 = q2.multiply(p2);
        f = (p2.subtract(BigInteger.ONE).multiply(q2.subtract(BigInteger.ONE)));

        while (true) {
            d2 = BigInteger.probablePrime(bitLength, random);
            resultEuclid = Lab1.generalizedEuclidsAlgorithm(f, d2);
            if (resultEuclid.gcd.compareTo(BigInteger.ONE) == 0) {
                break;
            }
        }

        if (resultEuclid.y.compareTo(BigInteger.ZERO) < 0) {
            resultEuclid.y = resultEuclid.y.add(f);
        }
        c2 = resultEuclid.y;

        //тут должен быть момент присваивания рандомным числам (r) кодов цветов на конце 00 01 10
        r1 = BigInteger.probablePrime(bitLength, random);
        r2 = BigInteger.probablePrime(bitLength, random);

        r1 = subStrAndAddColor(r1.toString(2), color1);
        r1 = subStrAndAddColor(r1.toString(2), color2);

        z1 = Lab1.fastModuloExponentiation(r1, d1, n1);
        z2 = Lab1.fastModuloExponentiation(r2, d2, n2);
    }

    public BigInteger subStrAndAddColor(String str, Integer colorNum){
        String subStr = str.substring(0, str.length() - 3);
        subStr += Integer.toBinaryString(colorNum);
        return new BigInteger(subStr);
    }

    //генерация чисел z и r, если она происходит каждый раз заново. Иначе это можно сделать в RSA
    public void generateRandZ(int colorFirst, int colorSecond, int colorThird) {
        switch (color1){
            case 0:
                color1 = colorFirst;
                break;
            case 1:
                color1 = colorSecond;
                break;
            case 2:
                color1 = colorThird;
                break;
        }

        switch (color2){
            case 0:
                color2 = colorFirst;
                break;
            case 1:
                color2 = colorSecond;
                break;
            case 2:
                color2 = colorThird;
                break;
        }

        Random random = new Random();
        r1 = BigInteger.probablePrime(bitLength, random);
        r2 = BigInteger.probablePrime(bitLength, random);

        r1 = subStrAndAddColor(r1.toString(2), color1);
        r1 = subStrAndAddColor(r1.toString(2), color2);

        z1 = Lab1.fastModuloExponentiation(r1, d1, n1);
        z2 = Lab1.fastModuloExponentiation(r2, d2, n2);
    }

    public BigInteger getN1() {
        return n1;
    }

    public BigInteger getN2() {
        return n2;
    }

    public BigInteger getC1() {
        return c1;
    }

    public BigInteger getC2() {
        return c2;
    }

    public BigInteger getD1() {
        return d1;
    }

    public BigInteger getD2() {
        return d2;
    }

    public BigInteger getZ1() {
        return z1;
    }

    public BigInteger getZ2() {
        return z2;
    }
}
