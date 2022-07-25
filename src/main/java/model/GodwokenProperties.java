package model;

import com.google.gson.Gson;
import lombok.Data;
import lombok.ToString;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
@ToString
public class GodwokenProperties {
    private String rpcUrl;
    private String[] privateKeys;
    private String CkbProxyAddress;
    private Gson gson;
    public GodwokenProperties(String path) throws IOException {
        gson = new Gson();
        Properties properties = getProperties(path);
        rpcUrl = properties.getProperty("RpcUrl");
        String privateKeysString = properties.getProperty("privateKeys");
        privateKeys = gson.fromJson(privateKeysString,String[].class);
        CkbProxyAddress = properties.getProperty("proxyCkbAddress");
    }

    private Properties getProperties(String path) throws IOException {
        InputStream in;
        if(isAbsolutePath(path)){
            System.out.println();
            in = new FileInputStream(path);
            Properties prop = new Properties();
            prop.load(in);
            return prop;
        }
        in = this.getClass().getClassLoader().getResourceAsStream(path);
        Properties prop = new Properties();
        prop.load(in);
        return prop;
    }

    private static boolean isAbsolutePath(String path) {
        return path.startsWith("/") || path.startsWith("file:/") || path.contains(":\\");
    }

}
