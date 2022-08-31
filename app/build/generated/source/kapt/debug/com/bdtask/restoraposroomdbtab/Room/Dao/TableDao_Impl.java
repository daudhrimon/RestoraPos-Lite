package com.bdtask.restoraposroomdbtab.Room.Dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.bdtask.restoraposroomdbtab.Room.Entity.Table;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TableDao_Impl implements TableDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Table> __insertionAdapterOfTable;

  public TableDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTable = new EntityInsertionAdapter<Table>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `table_tbl` (`id`,`tNm`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Table value) {
        stmt.bindLong(1, value.getId());
        if (value.getTNm() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTNm());
        }
      }
    };
  }

  @Override
  public Object insertTable(final Table table, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTable.insert(table);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<Table>> getAllTable() {
    final String _sql = "SELECT * FROM table_tbl";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"table_tbl"}, false, new Callable<List<Table>>() {
      @Override
      public List<Table> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTNm = CursorUtil.getColumnIndexOrThrow(_cursor, "tNm");
          final List<Table> _result = new ArrayList<Table>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Table _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTNm;
            if (_cursor.isNull(_cursorIndexOfTNm)) {
              _tmpTNm = null;
            } else {
              _tmpTNm = _cursor.getString(_cursorIndexOfTNm);
            }
            _item = new Table(_tmpId,_tmpTNm);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
