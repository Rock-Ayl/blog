package cn.anyongliang.db.jdbc;

import java.sql.ResultSet;

public interface IObserveRecord {

    boolean parse(ResultSet rs, int fieldCount);

    boolean wrapperParam();

}
