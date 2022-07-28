package com.tests.transferEoa;

import com.tests.Common;
import constant.Constant;
import org.testng.annotations.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import utils.CredentialsUtil;

/**
 * 1. @description: New account test
 * 2. @author: Dawn
 * 3. @time: 2022/7/26
 */
public class NewAccountTest extends Common {
    @Test
    public void NewAccount() {
        Web3j web3j = getGodwokenClient().getWeb3();
        Credentials credentials1 = getGodwokenClient().getCredentialsByIdx(0);

        for (int i = 0; i < 10; i++) {
            Credentials newAccount = CredentialsUtil.createAccount();
            System.out.println("address:  " + newAccount.getAddress());
        }
    }
}
