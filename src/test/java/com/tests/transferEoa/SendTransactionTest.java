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
 * 1. @description: send transaction
 * 2. @author: Dawn
 * 3. @time: 2022/7/28
 */


public class SendTransactionTest extends Common {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SendTransactionTest.class);
    /**
     * 1. @description: The new account sends a transaction to the new account
     */
    @Test
    public void TransferSuccess() throws Exception{
        Web3j web3j = getGodwokenClient().getWeb3();

        Credentials credentials1 = getGodwokenClient().getCredentialsByIdx(0);
        BigInteger sendBalanceBefore = web3j.ethGetBalance(credentials1.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();
        log.info("{}  transfer balance before :{}",credentials1.getAddress(),sendBalanceBefore);
        log.info("transfer  balance to new Account ");
        Credentials newAccount = CredentialsUtil.createAccount();
        getGodwokenClient().transferCkb(credentials1,newAccount.getAddress(), Constant.ETHER.multiply(new BigInteger("60")));
        BigInteger blanceForNewAccount = web3j.ethGetBalance(newAccount.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();
        log.info("new Account:{} Balance:{}",newAccount.getAddress(),blanceForNewAccount);
        log.info("new Account can transfer without register");
        Credentials newAccounts = CredentialsUtil.createAccount();
        getGodwokenClient().transferCkb(newAccount,newAccounts.getAddress(),Constant.ETHER.multiply(new BigInteger("55")));
        log.info("check new Account transfer successful ");
        blanceForNewAccount = web3j.ethGetBalance(newAccounts.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();
        log.info("after transfer new Account:{} Balance:{}",newAccounts.getAddress(),blanceForNewAccount);
        Credentials newAccountss = CredentialsUtil.createAccount();
        getGodwokenClient().transferCkb(newAccounts,newAccountss.getAddress(),Constant.ETHER.multiply(new BigInteger("45")));
        log.info("new Account:{} Balance:{}",newAccountss.getAddress(),blanceForNewAccount);
        getGodwokenClient().transferCkb(newAccountss,credentials1.getAddress(),Constant.ETHER.multiply(new BigInteger("40")));
        blanceForNewAccount = web3j.ethGetBalance(newAccountss.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();
        log.info("after transfer new Account:{} Balance:{}",newAccountss.getAddress(),blanceForNewAccount);
        Assert.assertTrue(blanceForNewAccount.compareTo(Constant.ETHER.multiply(new BigInteger("45")))<0);
    }

    /**
     * 1. @description: The newly registered account sends transactions to multiple accounts
     */
    @Test
    public void SendTransaction() throws Exception{
        Web3j web3j = getGodwokenClient().getWeb3();

        Credentials credentials1 = getGodwokenClient().getCredentialsByIdx(0);
        BigInteger sendBalanceBefore = web3j.ethGetBalance(credentials1.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();
        log.info("{}  transfer balance before :{}",credentials1.getAddress(),sendBalanceBefore);
        log.info("transfer  balance to new Account ");
        Credentials newAccount = CredentialsUtil.createAccount();
        getGodwokenClient().transferCkb(credentials1,newAccount.getAddress(), Constant.ETHER.multiply(new BigInteger("65")));
        BigInteger blanceForNewAccount = web3j.ethGetBalance(newAccount.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();
        log.info("new Account:{} Balance:{}",newAccount.getAddress(),blanceForNewAccount);
        log.info("new Account can transfer without register");
        Credentials newAccounts = CredentialsUtil.createAccount();
        getGodwokenClient().transferCkb(newAccount,newAccounts.getAddress(),Constant.ETHER);
        Credentials newAccountss = CredentialsUtil.createAccount();
        getGodwokenClient().transferCkb(newAccount,newAccountss.getAddress(),Constant.ETHER);
        Credentials newAccountsss = CredentialsUtil.createAccount();
        getGodwokenClient().transferCkb(newAccount,newAccountsss.getAddress(),Constant.ETHER);
        Credentials newAccountssss = CredentialsUtil.createAccount();
        getGodwokenClient().transferCkb(newAccount,newAccountssss.getAddress(),Constant.ETHER);
        getGodwokenClient().transferCkb(newAccount,newAccountssss.getAddress(),Constant.ETHER.multiply(new BigInteger("45")));
        blanceForNewAccount = web3j.ethGetBalance(newAccount.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();
        log.info("new Account:{} Balance:{}",newAccount.getAddress(),blanceForNewAccount);
    }

    /**
     * 1. @description: New account circularly initiates transactions
     */
    @Test
    public void CircularTransaction() throws Exception{
        Web3j web3j = getGodwokenClient().getWeb3();

        Credentials credentials1 = getGodwokenClient().getCredentialsByIdx(0);
        BigInteger sendBalanceBefore = web3j.ethGetBalance(credentials1.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();
        log.info("{}  transfer balance before :{}",credentials1.getAddress(),sendBalanceBefore);
        log.info("transfer  balance to new Account ");
        Credentials newAccount = CredentialsUtil.createAccount();
        getGodwokenClient().transferCkb(credentials1,newAccount.getAddress(), Constant.ETHER.multiply(new BigInteger("100")));
        BigInteger blanceForNewAccount = web3j.ethGetBalance(newAccount.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();
        log.info("new Account:{} Balance:{}",newAccount.getAddress(),blanceForNewAccount);
        log.info("new Account can transfer without register");
        for (int i = 0; i < 18; i++) {
            getGodwokenClient().transferCkb(newAccount,credentials1.getAddress(), Constant.ETHER);
            log.info("Cycles :[" + i + "]");
        }
        blanceForNewAccount = web3j.ethGetBalance(newAccount.getAddress(), DefaultBlockParameterName.PENDING).send().getBalance();
        log.info("new Account:{} Balance:{}",newAccount.getAddress(),blanceForNewAccount);
    }

}
