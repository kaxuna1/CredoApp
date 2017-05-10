package credo.ge.credoapp.ExtensionClasses;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.orm.StringUtil;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.orm.SugarRecord;

/**
 * Created by kaxge on 5/10/2017.
 */

public class DBAdapter<T> extends SugarRecord<T> {
    void save(SQLiteDatabase db) {

        List<Field> columns = getTableFields();
        ContentValues values = new ContentValues(columns.size());
        for (Field column : columns) {
            column.setAccessible(true);
            Class<?> columnType = column.getType();
            try {
                String columnName = StringUtil.toSQLName(column.getName());
                Object columnValue = column.get(this);
                if (SugarRecord.class.isAssignableFrom(columnType)) {
                    values.put(columnName,
                            (columnValue != null)
                                    ? String.valueOf(((SugarRecord) columnValue).getId())
                                    : "0");
                } else {
                    //if (!"id".equalsIgnoreCase(column.getName())) {
                    if (columnType.equals(Short.class) || columnType.equals(short.class)) {
                        values.put(columnName, (Short) columnValue);
                    }
                    else if (columnType.equals(Integer.class) || columnType.equals(int.class)) {
                        values.put(columnName, (Integer) columnValue);
                    }
                    else if (columnType.equals(Long.class) || columnType.equals(long.class)) {
                        values.put(columnName, (Long) columnValue);
                    }
                    else if (columnType.equals(Float.class) || columnType.equals(float.class)) {
                        values.put(columnName, (Float) columnValue);
                    }
                    else if (columnType.equals(Double.class) || columnType.equals(double.class)) {
                        values.put(columnName, (Double) columnValue);
                    }
                    else if (columnType.equals(Boolean.class) || columnType.equals(boolean.class)) {
                        values.put(columnName, (Boolean) columnValue);
                    }
                    else if (Date.class.equals(columnType)) {
                        values.put(columnName, ((Date) column.get(this)).getTime());
                    }
                    else if (Calendar.class.equals(columnType)) {
                        values.put(columnName, ((Calendar) column.get(this)).getTimeInMillis());
                    }else{
                        values.put(columnName, String.valueOf(columnValue));
                    }

                    //}
                }

            } catch (IllegalAccessException e) {
                Log.e("Sugar", e.getMessage());
            }
        }

        id = db.insertWithOnConflict(getSqlName(), null, values, SQLiteDatabase.CONFLICT_REPLACE);
        /*
        if (id == null)
            id = db.insert(getSqlName(), null, values);
        else
            db.update(getSqlName(), values, "ID = ?", new String[]{String.valueOf(id)});
        */
        Log.i("Sugar", getClass().getSimpleName() + " saved : " + id);
    }
}
