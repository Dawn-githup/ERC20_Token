package com.tests.transferEoa;

import com.tests.Common;
import constant.Constant;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import utils.CredentialsUtil;

import java.math.BigInteger;

/**
 * 1. @description: Automatic registration account transfer succeeded
 * 2. @author: Dawn
 * 3. @time: 2022/7/27
 */
public class TransferSuccessful extends Common {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TransferSuccessful.class);
    @Test
    public void demo() throws Exception{
        Web3j web3j = getGodwokenClient().getWeb3();

        Credentials credentials1 = getGodwokenClient().getCredentialsByIdx(0);
        BigInteger sendBalanceBefore = web3j.ethGetBalance(credentials1.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();
        log.info("{}  transfer balance before :{}",credentials1.getAddress(),sendBalanceBefore);
        log.info("transfer  balance to new Account ");
        Credentials newAccount = CredentialsUtil.createAccount();
        getGodwokenClient().transferCkb(credentials1,newAccount.getAddress(), Constant.ETHER);
        BigInteger blanceForNewAccount = web3j.ethGetBalance(newAccount.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();
        log.info("new Account:{} Balance:{}",newAccount.getAddress(),blanceForNewAccount);
        log.info("new Account can transfer without register");
        BigInteger divide = Constant.ETHER.divide(new BigInteger("2"));
        System.out.println("divide   " +divide);
        getGodwokenClient().transferCkb(newAccount,credentials1.getAddress(),Constant.ETHER.divide(new BigInteger("2")));
        log.info("check new Account transfer successful ");
        blanceForNewAccount = web3j.ethGetBalance(newAccount.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();
        log.info("after transfer new Account:{} Balance:{}",newAccount.getAddress(),blanceForNewAccount);
        Assert.assertTrue(blanceForNewAccount.compareTo(Constant.ETHER.divide(new BigInteger("2")))<0);
    }
}
