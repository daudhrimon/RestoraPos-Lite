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
import com.bdtask.restoraposroomdbtab.Model.Adn;
import com.bdtask.restoraposroomdbtab.Model.Variant;
import com.bdtask.restoraposroomdbtab.Room.Converters;
import com.bdtask.restoraposroomdbtab.Room.Entity.Food;
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
public final class FoodDao_Impl implements FoodDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Food> __insertionAdapterOfFood;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Food> __deletionAdapterOfFood;

  private final EntityDeletionOrUpdateAdapter<Food> __updateAdapterOfFood;

  public FoodDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFood = new EntityInsertionAdapter<Food>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `food_tbl` (`id`,`fCate`,`fTitle`,`fVar`,`fImg`,`fAdns`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Food value) {
        stmt.bindLong(1, value.getId());
        if (value.getFCate() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFCate());
        }
        if (value.getFTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getFTitle());
        }
        final String _tmp = __converters.variantListToJson(value.getFVar());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp);
        }
        if (value.getFImg() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getFImg());
        }
        final String _tmp_1 = __converters.addonListToJson(value.getFAdns());
        if (_tmp_1 == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfFood = new EntityDeletionOrUpdateAdapter<Food>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `food_tbl` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Food value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfFood = new EntityDeletionOrUpdateAdapter<Food>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `food_tbl` SET `id` = ?,`fCate` = ?,`fTitle` = ?,`fVar` = ?,`fImg` = ?,`fAdns` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Food value) {
        stmt.bindLong(1, value.getId());
        if (value.getFCate() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFCate());
        }
        if (value.getFTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getFTitle());
        }
        final String _tmp = __converters.variantListToJson(value.getFVar());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp);
        }
        if (value.getFImg() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getFImg());
        }
        final String _tmp_1 = __converters.addonListToJson(value.getFAdns());
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
  public Object insertFood(final Food food, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFood.insert(food);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteFood(final Food food, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFood.handle(food);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateFood(final Food food, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfFood.handle(food);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<Food>> getAllFood() {
    final String _sql = "SELECT * FROM food_tbl";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"food_tbl"}, false, new Callable<List<Food>>() {
      @Override
      public List<Food> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFCate = CursorUtil.getColumnIndexOrThrow(_cursor, "fCate");
          final int _cursorIndexOfFTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "fTitle");
          final int _cursorIndexOfFVar = CursorUtil.getColumnIndexOrThrow(_cursor, "fVar");
          final int _cursorIndexOfFImg = CursorUtil.getColumnIndexOrThrow(_cursor, "fImg");
          final int _cursorIndexOfFAdns = CursorUtil.getColumnIndexOrThrow(_cursor, "fAdns");
          final List<Food> _result = new ArrayList<Food>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Food _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpFCate;
            if (_cursor.isNull(_cursorIndexOfFCate)) {
              _tmpFCate = null;
            } else {
              _tmpFCate = _cursor.getString(_cursorIndexOfFCate);
            }
            final String _tmpFTitle;
            if (_cursor.isNull(_cursorIndexOfFTitle)) {
              _tmpFTitle = null;
            } else {
              _tmpFTitle = _cursor.getString(_cursorIndexOfFTitle);
            }
            final List<Variant> _tmpFVar;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfFVar)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfFVar);
            }
            _tmpFVar = __converters.jsonToVariantList(_tmp);
            final String _tmpFImg;
            if (_cursor.isNull(_cursorIndexOfFImg)) {
              _tmpFImg = null;
            } else {
              _tmpFImg = _cursor.getString(_cursorIndexOfFImg);
            }
            final List<Adn> _tmpFAdns;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfFAdns)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfFAdns);
            }
            _tmpFAdns = __converters.jsonToAddonList(_tmp_1);
            _item = new Food(_tmpId,_tmpFCate,_tmpFTitle,_tmpFVar,_tmpFImg,_tmpFAdns);
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
  public LiveData<List<String>> getFoodTitle() {
    final String _sql = "SELECT fTitle FROM food_tbl";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"food_tbl"}, false, new Callable<List<String>>() {
      @Override
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
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
