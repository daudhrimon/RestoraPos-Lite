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
import com.bdtask.restoraposroomdbtab.Room.Entity.Customer;
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

  private final EntityInsertionAdapter<Customer> __insertionAdapterOfCustomer;

  private final EntityDeletionOrUpdateAdapter<Customer> __deletionAdapterOfCustomer;

  private final EntityDeletionOrUpdateAdapter<Customer> __updateAdapterOfCustomer;

  public CustomerDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCustomer = new EntityInsertionAdapter<Customer>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `customer_tbl` (`id`,`name`,`email`,`mobile`,`address`,`favAddress`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Customer value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getEmail());
        }
        if (value.getMobile() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMobile());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAddress());
        }
        if (value.getFavAddress() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getFavAddress());
        }
      }
    };
    this.__deletionAdapterOfCustomer = new EntityDeletionOrUpdateAdapter<Customer>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `customer_tbl` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Customer value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfCustomer = new EntityDeletionOrUpdateAdapter<Customer>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `customer_tbl` SET `id` = ?,`name` = ?,`email` = ?,`mobile` = ?,`address` = ?,`favAddress` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Customer value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getEmail() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getEmail());
        }
        if (value.getMobile() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getMobile());
        }
        if (value.getAddress() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAddress());
        }
        if (value.getFavAddress() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getFavAddress());
        }
        stmt.bindLong(7, value.getId());
      }
    };
  }

  @Override
  public Object insertCustomer(final Customer customer, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCustomer.insert(customer);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteCustomer(final Customer customer, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCustomer.handle(customer);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateCustomer(final Customer customer, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCustomer.handle(customer);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public LiveData<List<Customer>> getAllCustomer() {
    final String _sql = "SELECT * FROM customer_tbl";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"customer_tbl"}, false, new Callable<List<Customer>>() {
      @Override
      public List<Customer> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfMobile = CursorUtil.getColumnIndexOrThrow(_cursor, "mobile");
          final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
          final int _cursorIndexOfFavAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "favAddress");
          final List<Customer> _result = new ArrayList<Customer>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Customer _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpMobile;
            if (_cursor.isNull(_cursorIndexOfMobile)) {
              _tmpMobile = null;
            } else {
              _tmpMobile = _cursor.getString(_cursorIndexOfMobile);
            }
            final String _tmpAddress;
            if (_cursor.isNull(_cursorIndexOfAddress)) {
              _tmpAddress = null;
            } else {
              _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
            }
            final String _tmpFavAddress;
            if (_cursor.isNull(_cursorIndexOfFavAddress)) {
              _tmpFavAddress = null;
            } else {
              _tmpFavAddress = _cursor.getString(_cursorIndexOfFavAddress);
            }
            _item = new Customer(_tmpId,_tmpName,_tmpEmail,_tmpMobile,_tmpAddress,_tmpFavAddress);
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
