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
import com.bdtask.restoraposroomdbtab.Room.Entity.DeliveryCompany;
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
public final class DeliveryCompanyDao_Impl implements DeliveryCompanyDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DeliveryCompany> __insertionAdapterOfDeliveryCompany;

  public DeliveryCompanyDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDeliveryCompany = new EntityInsertionAdapter<DeliveryCompany>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `delivery_company` (`id`,`companyName`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DeliveryCompany value) {
        stmt.bindLong(1, value.getId());
        if (value.getCompanyName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCompanyName());
        }
      }
    };
  }

  @Override
  public Object insertDeliveryCompany(final DeliveryCompany deliveryCompany,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDeliveryCompany.insert(deliveryCompany);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public LiveData<List<DeliveryCompany>> getDeliveryCompany() {
    final String _sql = "SELECT * FROM delivery_company";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"delivery_company"}, false, new Callable<List<DeliveryCompany>>() {
      @Override
      public List<DeliveryCompany> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCompanyName = CursorUtil.getColumnIndexOrThrow(_cursor, "companyName");
          final List<DeliveryCompany> _result = new ArrayList<DeliveryCompany>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final DeliveryCompany _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpCompanyName;
            if (_cursor.isNull(_cursorIndexOfCompanyName)) {
              _tmpCompanyName = null;
            } else {
              _tmpCompanyName = _cursor.getString(_cursorIndexOfCompanyName);
            }
            _item = new DeliveryCompany(_tmpId,_tmpCompanyName);
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
