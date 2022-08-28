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
import com.bdtask.restoraposroomdbtab.Model.Addon;
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
        return "INSERT OR ABORT INTO `food_tbl` (`id`,`fCategory`,`fTitle`,`fVariant`,`fImage`,`fAddons`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Food value) {
        stmt.bindLong(1, value.getId());
        if (value.getFCategory() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFCategory());
        }
        if (value.getFTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getFTitle());
        }
        final String _tmp = __converters.variantListToJson(value.getFVariant());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp);
        }
        if (value.getFImage() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getFImage());
        }
        final String _tmp_1 = __converters.addonListToJson(value.getFAddons());
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
        return "UPDATE OR ABORT `food_tbl` SET `id` = ?,`fCategory` = ?,`fTitle` = ?,`fVariant` = ?,`fImage` = ?,`fAddons` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Food value) {
        stmt.bindLong(1, value.getId());
        if (value.getFCategory() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFCategory());
        }
        if (value.getFTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getFTitle());
        }
        final String _tmp = __converters.variantListToJson(value.getFVariant());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp);
        }
        if (value.getFImage() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getFImage());
        }
        final String _tmp_1 = __converters.addonListToJson(value.getFAddons());
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
          final int _cursorIndexOfFCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "fCategory");
          final int _cursorIndexOfFTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "fTitle");
          final int _cursorIndexOfFVariant = CursorUtil.getColumnIndexOrThrow(_cursor, "fVariant");
          final int _cursorIndexOfFImage = CursorUtil.getColumnIndexOrThrow(_cursor, "fImage");
          final int _cursorIndexOfFAddons = CursorUtil.getColumnIndexOrThrow(_cursor, "fAddons");
          final List<Food> _result = new ArrayList<Food>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Food _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpFCategory;
            if (_cursor.isNull(_cursorIndexOfFCategory)) {
              _tmpFCategory = null;
            } else {
              _tmpFCategory = _cursor.getString(_cursorIndexOfFCategory);
            }
            final String _tmpFTitle;
            if (_cursor.isNull(_cursorIndexOfFTitle)) {
              _tmpFTitle = null;
            } else {
              _tmpFTitle = _cursor.getString(_cursorIndexOfFTitle);
            }
            final List<Variant> _tmpFVariant;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfFVariant)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfFVariant);
            }
            _tmpFVariant = __converters.jsonToVariantList(_tmp);
            final String _tmpFImage;
            if (_cursor.isNull(_cursorIndexOfFImage)) {
              _tmpFImage = null;
            } else {
              _tmpFImage = _cursor.getString(_cursorIndexOfFImage);
            }
            final List<Addon> _tmpFAddons;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfFAddons)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfFAddons);
            }
            _tmpFAddons = __converters.jsonToAddonList(_tmp_1);
            _item = new Food(_tmpId,_tmpFCategory,_tmpFTitle,_tmpFVariant,_tmpFImage,_tmpFAddons);
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
