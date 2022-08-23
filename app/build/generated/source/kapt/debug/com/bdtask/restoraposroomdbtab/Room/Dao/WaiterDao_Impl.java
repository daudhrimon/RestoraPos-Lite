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
import com.bdtask.restoraposroomdbtab.Room.Entity.Waiter;
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
public final class WaiterDao_Impl implements WaiterDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Waiter> __insertionAdapterOfWaiter;

  public WaiterDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWaiter = new EntityInsertionAdapter<Waiter>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `waiter_tbl` (`id`,`wName`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Waiter value) {
        stmt.bindLong(1, value.getId());
        if (value.getWName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getWName());
        }
      }
    };
  }

  @Override
  public Object insertWaiter(final Waiter waiter, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWaiter.insert(waiter);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public LiveData<List<Waiter>> getAllWaiter() {
    final String _sql = "SELECT * FROM waiter_tbl";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"waiter_tbl"}, false, new Callable<List<Waiter>>() {
      @Override
      public List<Waiter> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWName = CursorUtil.getColumnIndexOrThrow(_cursor, "wName");
          final List<Waiter> _result = new ArrayList<Waiter>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Waiter _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpWName;
            if (_cursor.isNull(_cursorIndexOfWName)) {
              _tmpWName = null;
            } else {
              _tmpWName = _cursor.getString(_cursorIndexOfWName);
            }
            _item = new Waiter(_tmpId,_tmpWName);
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
