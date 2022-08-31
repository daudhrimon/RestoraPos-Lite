package com.bdtask.restoraposroomdbtab.Room.Dao;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.bdtask.restoraposroomdbtab.Model.Cart;
import com.bdtask.restoraposroomdbtab.Model.OdrInf;
import com.bdtask.restoraposroomdbtab.Room.Converters;
import com.bdtask.restoraposroomdbtab.Room.Entity.Order;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
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
public final class OrderDao_Impl implements OrderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Order> __insertionAdapterOfOrder;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Order> __deletionAdapterOfOrder;

  private final EntityDeletionOrUpdateAdapter<Order> __updateAdapterOfOrder;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSplitStatus;

  public OrderDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfOrder = new EntityInsertionAdapter<Order>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `order_tbl` (`id`,`sts`,`spl`,`mrg`,`dat`,`tkn`,`vat`,`crg`,`odrInf`,`cart`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Order value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSts());
        stmt.bindLong(3, value.getSpl());
        stmt.bindLong(4, value.getMrg());
        if (value.getDat() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDat());
        }
        if (value.getTkn() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getTkn());
        }
        stmt.bindDouble(7, value.getVat());
        stmt.bindDouble(8, value.getCrg());
        final String _tmp = __converters.orderInfoToJson(value.getOdrInf());
        if (_tmp == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, _tmp);
        }
        final String _tmp_1 = __converters.foodCartListToJson(value.getCart());
        if (_tmp_1 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfOrder = new EntityDeletionOrUpdateAdapter<Order>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `order_tbl` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Order value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfOrder = new EntityDeletionOrUpdateAdapter<Order>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `order_tbl` SET `id` = ?,`sts` = ?,`spl` = ?,`mrg` = ?,`dat` = ?,`tkn` = ?,`vat` = ?,`crg` = ?,`odrInf` = ?,`cart` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Order value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSts());
        stmt.bindLong(3, value.getSpl());
        stmt.bindLong(4, value.getMrg());
        if (value.getDat() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDat());
        }
        if (value.getTkn() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getTkn());
        }
        stmt.bindDouble(7, value.getVat());
        stmt.bindDouble(8, value.getCrg());
        final String _tmp = __converters.orderInfoToJson(value.getOdrInf());
        if (_tmp == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, _tmp);
        }
        final String _tmp_1 = __converters.foodCartListToJson(value.getCart());
        if (_tmp_1 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, _tmp_1);
        }
        stmt.bindLong(11, value.getId());
      }
    };
    this.__preparedStmtOfUpdateSplitStatus = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE order_tbl SET spl=? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrder(final Order order, final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfOrder.insertAndReturnId(order);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteOnGoing(final Order order, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfOrder.handle(order);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateOnGoing(final Order order, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfOrder.handle(order);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateSplitStatus(final int status, final long id,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSplitStatus.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, status);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfUpdateSplitStatus.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<Order>> getTodayOrder() {
    final String _sql = "SELECT * FROM order_tbl";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"order_tbl"}, false, new Callable<List<Order>>() {
      @Override
      public List<Order> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSts = CursorUtil.getColumnIndexOrThrow(_cursor, "sts");
          final int _cursorIndexOfSpl = CursorUtil.getColumnIndexOrThrow(_cursor, "spl");
          final int _cursorIndexOfMrg = CursorUtil.getColumnIndexOrThrow(_cursor, "mrg");
          final int _cursorIndexOfDat = CursorUtil.getColumnIndexOrThrow(_cursor, "dat");
          final int _cursorIndexOfTkn = CursorUtil.getColumnIndexOrThrow(_cursor, "tkn");
          final int _cursorIndexOfVat = CursorUtil.getColumnIndexOrThrow(_cursor, "vat");
          final int _cursorIndexOfCrg = CursorUtil.getColumnIndexOrThrow(_cursor, "crg");
          final int _cursorIndexOfOdrInf = CursorUtil.getColumnIndexOrThrow(_cursor, "odrInf");
          final int _cursorIndexOfCart = CursorUtil.getColumnIndexOrThrow(_cursor, "cart");
          final List<Order> _result = new ArrayList<Order>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Order _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpSts;
            _tmpSts = _cursor.getInt(_cursorIndexOfSts);
            final int _tmpSpl;
            _tmpSpl = _cursor.getInt(_cursorIndexOfSpl);
            final int _tmpMrg;
            _tmpMrg = _cursor.getInt(_cursorIndexOfMrg);
            final String _tmpDat;
            if (_cursor.isNull(_cursorIndexOfDat)) {
              _tmpDat = null;
            } else {
              _tmpDat = _cursor.getString(_cursorIndexOfDat);
            }
            final String _tmpTkn;
            if (_cursor.isNull(_cursorIndexOfTkn)) {
              _tmpTkn = null;
            } else {
              _tmpTkn = _cursor.getString(_cursorIndexOfTkn);
            }
            final double _tmpVat;
            _tmpVat = _cursor.getDouble(_cursorIndexOfVat);
            final double _tmpCrg;
            _tmpCrg = _cursor.getDouble(_cursorIndexOfCrg);
            final OdrInf _tmpOdrInf;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfOdrInf)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfOdrInf);
            }
            _tmpOdrInf = __converters.jsonToOrderInfo(_tmp);
            final List<Cart> _tmpCart;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCart)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCart);
            }
            _tmpCart = __converters.jsonToFoodCartList(_tmp_1);
            _item = new Order(_tmpId,_tmpSts,_tmpSpl,_tmpMrg,_tmpDat,_tmpTkn,_tmpVat,_tmpCrg,_tmpOdrInf,_tmpCart);
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

  @Override
  public LiveData<List<Order>> getTodayOrder(final String date, final String status) {
    final String _sql = "SELECT * FROM order_tbl WHERE dat LIKE ? AND sts LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    _argIndex = 2;
    if (status == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, status);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"order_tbl"}, false, new Callable<List<Order>>() {
      @Override
      public List<Order> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSts = CursorUtil.getColumnIndexOrThrow(_cursor, "sts");
          final int _cursorIndexOfSpl = CursorUtil.getColumnIndexOrThrow(_cursor, "spl");
          final int _cursorIndexOfMrg = CursorUtil.getColumnIndexOrThrow(_cursor, "mrg");
          final int _cursorIndexOfDat = CursorUtil.getColumnIndexOrThrow(_cursor, "dat");
          final int _cursorIndexOfTkn = CursorUtil.getColumnIndexOrThrow(_cursor, "tkn");
          final int _cursorIndexOfVat = CursorUtil.getColumnIndexOrThrow(_cursor, "vat");
          final int _cursorIndexOfCrg = CursorUtil.getColumnIndexOrThrow(_cursor, "crg");
          final int _cursorIndexOfOdrInf = CursorUtil.getColumnIndexOrThrow(_cursor, "odrInf");
          final int _cursorIndexOfCart = CursorUtil.getColumnIndexOrThrow(_cursor, "cart");
          final List<Order> _result = new ArrayList<Order>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Order _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpSts;
            _tmpSts = _cursor.getInt(_cursorIndexOfSts);
            final int _tmpSpl;
            _tmpSpl = _cursor.getInt(_cursorIndexOfSpl);
            final int _tmpMrg;
            _tmpMrg = _cursor.getInt(_cursorIndexOfMrg);
            final String _tmpDat;
            if (_cursor.isNull(_cursorIndexOfDat)) {
              _tmpDat = null;
            } else {
              _tmpDat = _cursor.getString(_cursorIndexOfDat);
            }
            final String _tmpTkn;
            if (_cursor.isNull(_cursorIndexOfTkn)) {
              _tmpTkn = null;
            } else {
              _tmpTkn = _cursor.getString(_cursorIndexOfTkn);
            }
            final double _tmpVat;
            _tmpVat = _cursor.getDouble(_cursorIndexOfVat);
            final double _tmpCrg;
            _tmpCrg = _cursor.getDouble(_cursorIndexOfCrg);
            final OdrInf _tmpOdrInf;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfOdrInf)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfOdrInf);
            }
            _tmpOdrInf = __converters.jsonToOrderInfo(_tmp);
            final List<Cart> _tmpCart;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCart)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCart);
            }
            _tmpCart = __converters.jsonToFoodCartList(_tmp_1);
            _item = new Order(_tmpId,_tmpSts,_tmpSpl,_tmpMrg,_tmpDat,_tmpTkn,_tmpVat,_tmpCrg,_tmpOdrInf,_tmpCart);
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

  @Override
  public LiveData<List<Order>> getOngoing(final int status) {
    final String _sql = "SELECT * FROM order_tbl WHERE sts LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, status);
    return __db.getInvalidationTracker().createLiveData(new String[]{"order_tbl"}, false, new Callable<List<Order>>() {
      @Override
      public List<Order> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSts = CursorUtil.getColumnIndexOrThrow(_cursor, "sts");
          final int _cursorIndexOfSpl = CursorUtil.getColumnIndexOrThrow(_cursor, "spl");
          final int _cursorIndexOfMrg = CursorUtil.getColumnIndexOrThrow(_cursor, "mrg");
          final int _cursorIndexOfDat = CursorUtil.getColumnIndexOrThrow(_cursor, "dat");
          final int _cursorIndexOfTkn = CursorUtil.getColumnIndexOrThrow(_cursor, "tkn");
          final int _cursorIndexOfVat = CursorUtil.getColumnIndexOrThrow(_cursor, "vat");
          final int _cursorIndexOfCrg = CursorUtil.getColumnIndexOrThrow(_cursor, "crg");
          final int _cursorIndexOfOdrInf = CursorUtil.getColumnIndexOrThrow(_cursor, "odrInf");
          final int _cursorIndexOfCart = CursorUtil.getColumnIndexOrThrow(_cursor, "cart");
          final List<Order> _result = new ArrayList<Order>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Order _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpSts;
            _tmpSts = _cursor.getInt(_cursorIndexOfSts);
            final int _tmpSpl;
            _tmpSpl = _cursor.getInt(_cursorIndexOfSpl);
            final int _tmpMrg;
            _tmpMrg = _cursor.getInt(_cursorIndexOfMrg);
            final String _tmpDat;
            if (_cursor.isNull(_cursorIndexOfDat)) {
              _tmpDat = null;
            } else {
              _tmpDat = _cursor.getString(_cursorIndexOfDat);
            }
            final String _tmpTkn;
            if (_cursor.isNull(_cursorIndexOfTkn)) {
              _tmpTkn = null;
            } else {
              _tmpTkn = _cursor.getString(_cursorIndexOfTkn);
            }
            final double _tmpVat;
            _tmpVat = _cursor.getDouble(_cursorIndexOfVat);
            final double _tmpCrg;
            _tmpCrg = _cursor.getDouble(_cursorIndexOfCrg);
            final OdrInf _tmpOdrInf;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfOdrInf)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfOdrInf);
            }
            _tmpOdrInf = __converters.jsonToOrderInfo(_tmp);
            final List<Cart> _tmpCart;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfCart)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfCart);
            }
            _tmpCart = __converters.jsonToFoodCartList(_tmp_1);
            _item = new Order(_tmpId,_tmpSts,_tmpSpl,_tmpMrg,_tmpDat,_tmpTkn,_tmpVat,_tmpCrg,_tmpOdrInf,_tmpCart);
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
