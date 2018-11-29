package db.model;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
public class DBVo {
    List<TableVo> tableVoList = null;

    public DBVo()
    {
        tableVoList = new ArrayList<>();
    }
}
