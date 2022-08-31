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
import com.bdtask.restoraposroomdbtab.Room.Entity.Cmpny;
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
public final class CompanyDao_Impl implements CompanyDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Cmpny> __insertionAdapterOfCmpny;

  public CompanyDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCmpny = new EntityInsertionAdapter<Cmpny>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `company` (`id`,`cNm`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cmpny value) {
        stmt.bindLong(1, value.getId());
        if (value.getCNm() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCNm());
        }
      }
    };
  }

  @Override
  public Object insertDeliveryCompany(final Cmpny deliveryCmpny,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCmpny.insert(deliveryCmpny);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<Cmpny>> getDeliveryCompany() {
    final String _sql = "SELECT * FROM company";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"company"}, false, new Callable<List<Cmpny>>() {
      @Override
      public List<Cmpny> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCNm = CursorUtil.getColumnIndexOrThrow(_cursor, "cNm");
          final List<Cmpny> _result = new ArrayList<Cmpny>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Cmpny _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpCNm;
            if (_cursor.isNull(_cursorIndexOfCNm)) {
              _tmpCNm = null;
            } else {
              _tmpCNm = _cursor.getString(_cursorIndexOfCNm);
            }
            _item = new Cmpny(_tmpId,_tmpCNm);
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
