package com.bdtask.restoraposroomdbtab.Room.Dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.bdtask.restoraposroomdbtab.Room.Entity.Cstmr;
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
public final class CustomerDao_Impl implements CustomerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Cstmr> __insertionAdapterOfCstmr;

  private final EntityDeletionOrUpdateAdapter<Cstmr> __deletionAdapterOfCstmr;

  private final EntityDeletionOrUpdateAdapter<Cstmr> __updateAdapterOfCstmr;

  public CustomerDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCstmr = new EntityInsertionAdapter<Cstmr>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `customer_tbl` (`id`,`nm`,`eml`,`mbl`,`adrs`,`fAdrs`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cstmr value) {
        stmt.bindLong(1, value.getId());
        if (value.getNm() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNm());
        }
        if (value.getEml() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getEml());
        }
        if (value.getMbl() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMbl());
        }
        if (value.getAdrs() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAdrs());
        }
        if (value.getFAdrs() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getFAdrs());
        }
      }
    };
    this.__deletionAdapterOfCstmr = new EntityDeletionOrUpdateAdapter<Cstmr>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `customer_tbl` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cstmr value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfCstmr = new EntityDeletionOrUpdateAdapter<Cstmr>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `customer_tbl` SET `id` = ?,`nm` = ?,`eml` = ?,`mbl` = ?,`adrs` = ?,`fAdrs` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Cstmr value) {
        stmt.bindLong(1, value.getId());
        if (value.getNm() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getNm());
        }
        if (value.getEml() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getEml());
        }
        if (value.getMbl() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMbl());
        }
        if (value.getAdrs() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAdrs());
        }
        if (value.getFAdrs() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getFAdrs());
        }
        stmt.bindLong(7, value.getId());
      }
    };
  }

  @Override
  public Object insertCustomer(final Cstmr cstmr, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCstmr.insert(cstmr);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteCustomer(final Cstmr cstmr, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCstmr.handle(cstmr);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateCustomer(final Cstmr cstmr, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCstmr.handle(cstmr);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<Cstmr>> getAllCustomer() {
    final String _sql = "SELECT * FROM customer_tbl";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"customer_tbl"}, false, new Callable<List<Cstmr>>() {
      @Override
      public List<Cstmr> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfNm = CursorUtil.getColumnIndexOrThrow(_cursor, "nm");
          final int _cursorIndexOfEml = CursorUtil.getColumnIndexOrThrow(_cursor, "eml");
          final int _cursorIndexOfMbl = CursorUtil.getColumnIndexOrThrow(_cursor, "mbl");
          final int _cursorIndexOfAdrs = CursorUtil.getColumnIndexOrThrow(_cursor, "adrs");
          final int _cursorIndexOfFAdrs = CursorUtil.getColumnIndexOrThrow(_cursor, "fAdrs");
          final List<Cstmr> _result = new ArrayList<Cstmr>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Cstmr _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpNm;
            if (_cursor.isNull(_cursorIndexOfNm)) {
              _tmpNm = null;
            } else {
              _tmpNm = _cursor.getString(_cursorIndexOfNm);
            }
            final String _tmpEml;
            if (_cursor.isNull(_cursorIndexOfEml)) {
              _tmpEml = null;
            } else {
              _tmpEml = _cursor.getString(_cursorIndexOfEml);
            }
            final String _tmpMbl;
            if (_cursor.isNull(_cursorIndexOfMbl)) {
              _tmpMbl = null;
            } else {
              _tmpMbl = _cursor.getString(_cursorIndexOfMbl);
            }
            final String _tmpAdrs;
            if (_cursor.isNull(_cursorIndexOfAdrs)) {
              _tmpAdrs = null;
            } else {
              _tmpAdrs = _cursor.getString(_cursorIndexOfAdrs);
            }
            final String _tmpFAdrs;
            if (_cursor.isNull(_cursorIndexOfFAdrs)) {
              _tmpFAdrs = null;
            } else {
              _tmpFAdrs = _cursor.getString(_cursorIndexOfFAdrs);
            }
            _item = new Cstmr(_tmpId,_tmpNm,_tmpEml,_tmpMbl,_tmpAdrs,_tmpFAdrs);
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
