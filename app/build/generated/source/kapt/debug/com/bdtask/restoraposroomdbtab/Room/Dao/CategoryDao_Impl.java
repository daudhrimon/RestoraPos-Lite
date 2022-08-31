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
import com.bdtask.restoraposroomdbtab.Room.Entity.Catgry;
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
public final class CategoryDao_Impl implements CategoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Catgry> __insertionAdapterOfCatgry;

  private final EntityDeletionOrUpdateAdapter<Catgry> __deletionAdapterOfCatgry;

  private final EntityDeletionOrUpdateAdapter<Catgry> __updateAdapterOfCatgry;

  public CategoryDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCatgry = new EntityInsertionAdapter<Catgry>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `category_tbl` (`id`,`fCat`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Catgry value) {
        stmt.bindLong(1, value.getId());
        if (value.getFCat() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFCat());
        }
      }
    };
    this.__deletionAdapterOfCatgry = new EntityDeletionOrUpdateAdapter<Catgry>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `category_tbl` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Catgry value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfCatgry = new EntityDeletionOrUpdateAdapter<Catgry>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `category_tbl` SET `id` = ?,`fCat` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Catgry value) {
        stmt.bindLong(1, value.getId());
        if (value.getFCat() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFCat());
        }
        stmt.bindLong(3, value.getId());
      }
    };
  }

  @Override
  public Object insertCategory(final Catgry catgry, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCatgry.insert(catgry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteCategory(final Catgry catgry, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCatgry.handle(catgry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateCategory(final Catgry catgry, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCatgry.handle(catgry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<Catgry>> getAllCategory() {
    final String _sql = "SELECT * FROM category_tbl";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"category_tbl"}, false, new Callable<List<Catgry>>() {
      @Override
      public List<Catgry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFCat = CursorUtil.getColumnIndexOrThrow(_cursor, "fCat");
          final List<Catgry> _result = new ArrayList<Catgry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Catgry _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpFCat;
            if (_cursor.isNull(_cursorIndexOfFCat)) {
              _tmpFCat = null;
            } else {
              _tmpFCat = _cursor.getString(_cursorIndexOfFCat);
            }
            _item = new Catgry(_tmpId,_tmpFCat);
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
  public LiveData<List<String>> getCategories() {
    final String _sql = "SELECT fCat FROM category_tbl";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"category_tbl"}, false, new Callable<List<String>>() {
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
