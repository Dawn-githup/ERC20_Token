package model;

import constant.Constant;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;

import java.io.IOException;
import java.math.BigInteger;

public class Web3GasProvider implements ContractGasProvider {
    private Web3j web3j;
    public Web3GasProvider(Web3j web3j){
        this.web3j = web3j;
    }

    @Override
    public BigInteger getGasPrice(String s) {
        return Constant.GAS_LIMIT;
    }

    @Override
    public BigInteger getGasPrice() {
        try {
            return web3j.ethGasPrice().send().getGasPrice();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("get gas Price failed ");
        }
    }

    @Override
    public BigInteger getGasLimit(String s) {
        return Constant.GAS_LIMIT;
    }

    @Override
    public BigInteger getGasLimit() {
        return Constant.GAS_LIMIT;
    }
}
