package cn.anyongliang.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Properties;
import java.util.function.Function;

/**
 * 操作properties的工具类
 * create by Rock-Ayl 2019-6-13
 */
public class PropertyFileUtil extends Properties {

    private static final Logger logger = LoggerFactory.getLogger(PropertyFileUtil.class);

    public PropertyFileUtil() {
        defaults = new Properties();
    }

    public PropertyFileUtil use(String fileName) {
        try {
            InputStream stream = PropertyFileUtil.class.getResourceAsStream("/" + fileName);
            InputStreamReader in = new InputStreamReader(stream, "UTF-8");
            defaults.load(in);
            in.close();
        } catch (IOException e) {
            logger.error(fileName, e);
        }
        return this;
    }

    public String asString(String name, String defaultValue) {
        return getOrDefault(name, defaultValue, String::toString);
    }

    public int asInt(String name, int defaultValue) {
        return getOrDefault(name, defaultValue, Integer::parseInt);
    }

    public boolean asBool(String name, boolean defaultValue) {
        return getOrDefault(name, defaultValue, Boolean::parseBoolean);
    }

    public <R> R get(String key, Function<String, R> f) {
        String value = defaults.getProperty(key);
        R result = f.apply(value);
        return result;
    }

    public <R> R getOrDefault(String key, R defaultValue, Function<String, R> f) {
        String value = defaults.getProperty(key);
        String resultValue = value == null ? defaultValue.toString() : value;
        R result = f.apply(resultValue);
        return result;
    }

    /**
     * 演示 配置文件在resources中
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //获取配置文件
        PropertyFileUtil properties = new PropertyFileUtil().use("file.properties");
        String dataCenter = properties.getProperty("EMAILHOST");
        System.out.println(dataCenter);
        Boolean result = properties.get("EMAILHOST", Boolean::parseBoolean);
        System.out.println(result);
        String[] developEtcdHosts = properties.get("EMAILUSERADDR", i -> i.split(","));
        System.out.println(Arrays.toString(developEtcdHosts));
        Integer abc = properties.getOrDefault("EMAILPASSWORD", 1, Integer::parseInt);
        System.out.println(abc);
        Boolean debug = properties.getOrDefault("debug", false, Boolean::parseBoolean);
        System.out.println(debug);
    }
}
