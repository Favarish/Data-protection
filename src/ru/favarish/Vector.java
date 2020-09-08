package ru.favarish;

import java.math.BigInteger;

public class Vector {
    public BigInteger gcd;
    public BigInteger x;
    public BigInteger y;

    public Vector(BigInteger gcd, BigInteger x, BigInteger y) {
        this.gcd = gcd;
        this.x = x;
        this.y = y;
    }
}
