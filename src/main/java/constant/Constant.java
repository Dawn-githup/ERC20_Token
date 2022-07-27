package constant;


import com.sun.org.apache.bcel.internal.generic.NEW;

import java.math.BigInteger;

public class Constant {
    //  1000000000000000000
    public static final BigInteger ETHER = new BigInteger("1000000000000000000");

    public static final BigInteger ETHERSMALL = new BigInteger("11518500000");

    //   5000000
    public static final BigInteger GAS_LIMIT = new BigInteger("5000000");
    public static final BigInteger GAS_PRICE = new BigInteger("40000000000000");
}