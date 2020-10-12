package cn.anyongliang.cache;

import cn.anyongliang.db.jdbc.SqlTable;
import cn.anyongliang.json.JsonObject;
import cn.anyongliang.json.JsonObjects;
import org.apache.commons.collections4.CollectionUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统缓存-所有的缓存统统往这里放
 * create by Rock-Ayl 2019-6-14
 */
public class Caches {

    //用户名称缓存<id,name>
    public static ConcurrentHashMap<Long, String> UserNameCache = new ConcurrentHashMap();

    /**
     * 系统启动自动加载缓存
     */
    public static void init() {
        reloadUserNames(null);
    }

    /**
     * 用户名称缓存
     */
    public static void reloadUserNames(Long userId) {
        //查询
        JsonObjects records;
        //判断是刷新单个还是全部
        if (userId == null) {
            //清空所有
            UserNameCache.clear();
            //查询全部
            records = SqlTable.use().queryObjects("SELECT id,userName FROM `user`", new Object[]{});
        } else {
            //查询单个用户
            records = SqlTable.use().queryObjects("SELECT id,userName FROM `user` WHERE id = ?", new Object[]{userId});
        }
        //判空
        if (CollectionUtils.isEmpty(records)) {
            //返回
            return;
        }
        //循环
        for (JsonObject record : records) {
            //记录缓存
            UserNameCache.put(record.getLong("id"), record.getString("userName"));
        }
    }

}
