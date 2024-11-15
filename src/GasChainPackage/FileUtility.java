package GasChainPackage;

import java.io.IOException;

public interface FileUtility {
    boolean writeToFile(String fileName, JSONObject jsonObject) throws IOException;
}