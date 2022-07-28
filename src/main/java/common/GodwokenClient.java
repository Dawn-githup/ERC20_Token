package common;

import constant.Constant;
import contracts.GodwokenERC20;
import model.GodwokenProperties;
import model.Web3GasProvider;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import utils.TransactionUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class GodwokenClient {

    private Web3j web3;
    private List<Credentials> credentialsArrays;
    private static String DefaultPath = "./Godwoken.properties";
    private static GodwokenClient godwokenClientInstance;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GodwokenClient.class);


    //todo : lazy load
    private GodwokenERC20 godwokenERC20;

    private GodwokenClient() throws Exception {
        GodwokenProperties godwokenProperties = new GodwokenProperties(DefaultPath);
        String[] privateKeys = godwokenProperties.getPrivateKeys();
        credentialsArrays = new ArrayList<>();
        for (String priv : privateKeys) {
            Credentials credentials = Credentials.create(priv);
            credentialsArrays.add(credentials);
        }
        web3 = Web3j.build(new HttpService(godwokenProperties.getRpcUrl()));
        // if ckb address is empty deploy ckb contract
        String ckbAddress = godwokenProperties.getCkbProxyAddress();
        RawTransactionManager rawTransactionManager = TransactionUtil.getTxManage(web3, credentialsArrays.get(0));
        if (ckbAddress.length() < 10) {
            //
            godwokenERC20 = GodwokenERC20.deploy(web3, rawTransactionManager, new Web3GasProvider(web3)).send();
        } else {
            godwokenERC20 = GodwokenERC20.load(ckbAddress, web3, rawTransactionManager, new Web3GasProvider(web3));
        }

    }

    public static GodwokenClient GetGodwokenInstance() {
        if (GodwokenClient.godwokenClientInstance == null) {
            synchronized (GodwokenClient.class) {
                try {
                    godwokenClientInstance = new GodwokenClient();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("init Godwoken failed ");
                }
                return godwokenClientInstance;
            }
        }
        return godwokenClientInstance;
    }


    public Credentials getCredentialsByIdx(Integer idx) {
        return credentialsArrays.get(idx);
    }

    public List<Credentials> getgetCredentialsArrays() {
        return credentialsArrays;
    }

    public void transferCkb(Credentials credentials, String to, BigInteger value) {
        GodwokenERC20 godwokenERC20 = getGodwokenErc20(credentials);
        try {
            godwokenERC20.transfer(to, value).send();
        } catch (Exception e) {
            log.info("from id not found by from Address{...} have you deposited?");
//            e.printStackTrace();
        }
    }

    public void balanceOfckb(String amount) {
        try {
            godwokenERC20.balanceOf(amount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GodwokenERC20 getGodwokenErc20(Credentials credentials) {
        RawTransactionManager rawTransactionManager;
        try {
            rawTransactionManager = TransactionUtil.getTxManage(this.web3, credentials);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("getTxManage failed ");
        }
        return GodwokenERC20.load(this.godwokenERC20.getContractAddress(), this.web3, rawTransactionManager, new Web3GasProvider(this.web3));
    }

    public Web3j getWeb3() {
        return web3;
    }

    public void setWeb3(Web3j web3) {
        this.web3 = web3;
    }

    public List<Credentials> getCredentialsArrays() {
        return credentialsArrays;
    }

    public void setCredentialsArrays(List<Credentials> credentialsArrays) {
        this.credentialsArrays = credentialsArrays;
    }

    public static String getDefaultPath() {
        return DefaultPath;
    }

    public static void setDefaultPath(String defaultPath) {
        DefaultPath = defaultPath;
    }

    public static GodwokenClient getGodwokenClientInstance() {
        return godwokenClientInstance;
    }

    public static void setGodwokenClientInstance(GodwokenClient godwokenClientInstance) {
        GodwokenClient.godwokenClientInstance = godwokenClientInstance;
    }

    public GodwokenERC20 getGodwokenERC20() {
        return godwokenERC20;
    }

    public void setGodwokenERC20(GodwokenERC20 godwokenERC20) {
        this.godwokenERC20 = godwokenERC20;
    }

}
