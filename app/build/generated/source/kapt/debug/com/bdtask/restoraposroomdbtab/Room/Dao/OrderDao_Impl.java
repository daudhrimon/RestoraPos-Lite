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
import com.bdtask.restoraposroomdbtab.Model.FoodCart;
import com.bdtask.restoraposroomdbtab.Model.OrderInfo;
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

  public OrderDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfOrder = new EntityInsertionAdapter<Order>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `order_tbl` (`id`,`status`,`date`,`token`,`cartList`,`orderInfoList`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Order value) {
        stmt.bindLong(1, value.getId());
        if (value.getStatus() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getStatus());
        }
        if (value.getDate() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDate());
        }
        if (value.getToken() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getToken());
        }
        final String _tmp = __converters.foodCartListToJson(value.getCartList());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp);
        }
        final String _tmp_1 = __converters.orderInfoListToJson(value.getOrderInfoList());
        if (_tmp_1 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp_1);
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
        return "UPDATE OR ABORT `order_tbl` SET `id` = ?,`status` = ?,`date` = ?,`token` = ?,`cartList` = ?,`orderInfoList` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Order value) {
        stmt.bindLong(1, value.getId());
        if (value.getStatus() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getStatus());
        }
        if (value.getDate() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDate());
        }
        if (value.getToken() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getToken());
        }
        final String _tmp = __converters.foodCartListToJson(value.getCartList());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp);
        }
        final String _tmp_1 = __converters.orderInfoListToJson(value.getOrderInfoList());
        if (_tmp_1 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp_1);
        }
        stmt.bindLong(7, value.getId());
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
  public LiveData<List<Order>> getTodayOrder() {
    final String _sql = "SELECT * FROM order_tbl";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"order_tbl"}, false, new Callable<List<Order>>() {
      @Override
      public List<Order> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfToken = CursorUtil.getColumnIndexOrThrow(_cursor, "token");
          final int _cursorIndexOfCartList = CursorUtil.getColumnIndexOrThrow(_cursor, "cartList");
          final int _cursorIndexOfOrderInfoList = CursorUtil.getColumnIndexOrThrow(_cursor, "orderInfoList");
          final List<Order> _result = new ArrayList<Order>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Order _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpToken;
            if (_cursor.isNull(_cursorIndexOfToken)) {
              _tmpToken = null;
            } else {
              _tmpToken = _cursor.getString(_cursorIndexOfToken);
            }
            final List<FoodCart> _tmpCartList;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfCartList)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfCartList);
            }
            _tmpCartList = __converters.jsonToFoodCartList(_tmp);
            final List<OrderInfo> _tmpOrderInfoList;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfOrderInfoList)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfOrderInfoList);
            }
            _tmpOrderInfoList = __converters.jsonToOrderInfoList(_tmp_1);
            _item = new Order(_tmpId,_tmpStatus,_tmpDate,_tmpToken,_tmpCartList,_tmpOrderInfoList);
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
    final String _sql = "SELECT * FROM order_tbl WHERE date LIKE ? AND status LIKE ?";
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
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfToken = CursorUtil.getColumnIndexOrThrow(_cursor, "token");
          final int _cursorIndexOfCartList = CursorUtil.getColumnIndexOrThrow(_cursor, "cartList");
          final int _cursorIndexOfOrderInfoList = CursorUtil.getColumnIndexOrThrow(_cursor, "orderInfoList");
          final List<Order> _result = new ArrayList<Order>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Order _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpToken;
            if (_cursor.isNull(_cursorIndexOfToken)) {
              _tmpToken = null;
            } else {
              _tmpToken = _cursor.getString(_cursorIndexOfToken);
            }
            final List<FoodCart> _tmpCartList;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfCartList)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfCartList);
            }
            _tmpCartList = __converters.jsonToFoodCartList(_tmp);
            final List<OrderInfo> _tmpOrderInfoList;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfOrderInfoList)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfOrderInfoList);
            }
            _tmpOrderInfoList = __converters.jsonToOrderInfoList(_tmp_1);
            _item = new Order(_tmpId,_tmpStatus,_tmpDate,_tmpToken,_tmpCartList,_tmpOrderInfoList);
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
  public LiveData<List<Order>> getOngoing(final String status) {
    final String _sql = "SELECT * FROM order_tbl WHERE status LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
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
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfToken = CursorUtil.getColumnIndexOrThrow(_cursor, "token");
          final int _cursorIndexOfCartList = CursorUtil.getColumnIndexOrThrow(_cursor, "cartList");
          final int _cursorIndexOfOrderInfoList = CursorUtil.getColumnIndexOrThrow(_cursor, "orderInfoList");
          final List<Order> _result = new ArrayList<Order>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Order _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            final String _tmpDate;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmpDate = null;
            } else {
              _tmpDate = _cursor.getString(_cursorIndexOfDate);
            }
            final String _tmpToken;
            if (_cursor.isNull(_cursorIndexOfToken)) {
              _tmpToken = null;
            } else {
              _tmpToken = _cursor.getString(_cursorIndexOfToken);
            }
            final List<FoodCart> _tmpCartList;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfCartList)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfCartList);
            }
            _tmpCartList = __converters.jsonToFoodCartList(_tmp);
            final List<OrderInfo> _tmpOrderInfoList;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfOrderInfoList)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfOrderInfoList);
            }
            _tmpOrderInfoList = __converters.jsonToOrderInfoList(_tmp_1);
            _item = new Order(_tmpId,_tmpStatus,_tmpDate,_tmpToken,_tmpCartList,_tmpOrderInfoList);
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
