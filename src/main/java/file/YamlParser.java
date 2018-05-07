package file;

import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * parse Yaml
 * @return Map yamlFilepath
 * @return Map
 * @throws IOException
 */

public class YamlParser {
    public static Map toMap(String yamlFilePath) throws IOException{
        InputStream input = new FileInputStream(new File(yamlFilePath));
        Yaml yaml = new Yaml();
        HashMap map = (HashMap) yaml.load(input);
        input.close();
        return map;
    }

    /**
     * get Map's key value
     * @param map
     * @param key
     * @return Map
     */
    public static String getKeyValue(Map map, String key) {
        return map.get(key).toString();
    }

}
