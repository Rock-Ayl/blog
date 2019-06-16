package cn.anyongliang.cache;

import cn.anyongliang.db.jdbc.SqlTable;
import cn.anyongliang.json.JsonObject;
import cn.anyongliang.json.JsonObjects;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存系统-ConcurrentHashMap缓存
 * create by Rock-Ayl 2019-6-14
 */
public class Cache {

    public static ConcurrentHashMap<Long, String> organizes = new ConcurrentHashMap();

    /**
     * 系统的启动加载文件分类,组织结构,文件权限到缓存
     */
    public static void init() {
        reloadOrganizes();
    }

    /**
     * 刷新缓存
     * 当组织结构变化后,重新刷新缓存
     */
    public static void reloadOrganizes() {
        organizes.clear();
        JsonObjects records = SqlTable.use().queryObjects("select id,name from organize", 0, 0);
        for (int i = 0; i < records.size(); i++) {
            JsonObject record = records.get(i);
            organizes.put(record.getLong("id"), record.getString("name"));
        }
    }

}
