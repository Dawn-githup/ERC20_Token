package utils;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class CredentialsUtil {

    public static Credentials createAccount() {
        try {
            ECKeyPair ecKeyPair = Keys.createEcKeyPair();
            BigInteger privateKeyInDec = ecKeyPair.getPrivateKey();
            String privateKey = privateKeyInDec.toString(16);

            return Credentials.create(privateKey);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("create account failed");
        }

    }

    public static Credentials getAccount(String path) throws IOException {
        String priv = FileUtil.readFile(FileUtil.readFileAsStream(path));
        return Credentials.create(priv);
    }

}
