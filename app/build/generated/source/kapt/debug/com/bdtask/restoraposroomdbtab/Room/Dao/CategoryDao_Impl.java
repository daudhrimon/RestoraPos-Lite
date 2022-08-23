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
import com.bdtask.restoraposroomdbtab.Room.Entity.Category;
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

  private final EntityInsertionAdapter<Category> __insertionAdapterOfCategory;

  private final EntityDeletionOrUpdateAdapter<Category> __deletionAdapterOfCategory;

  private final EntityDeletionOrUpdateAdapter<Category> __updateAdapterOfCategory;

  public CategoryDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCategory = new EntityInsertionAdapter<Category>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `category_tbl` (`id`,`fCategory`) VALUES (nullif(?, 0),?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Category value) {
        stmt.bindLong(1, value.getId());
        if (value.getFCategory() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFCategory());
        }
      }
    };
    this.__deletionAdapterOfCategory = new EntityDeletionOrUpdateAdapter<Category>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `category_tbl` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Category value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfCategory = new EntityDeletionOrUpdateAdapter<Category>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `category_tbl` SET `id` = ?,`fCategory` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Category value) {
        stmt.bindLong(1, value.getId());
        if (value.getFCategory() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFCategory());
        }
        stmt.bindLong(3, value.getId());
      }
    };
  }

  @Override
  public Object insertCategory(final Category category,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCategory.insert(category);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteCategory(final Category category,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfCategory.handle(category);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateCategory(final Category category,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfCategory.handle(category);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<Category>> getAllCategory() {
    final String _sql = "SELECT * FROM category_tbl";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"category_tbl"}, false, new Callable<List<Category>>() {
      @Override
      public List<Category> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "fCategory");
          final List<Category> _result = new ArrayList<Category>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Category _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpFCategory;
            if (_cursor.isNull(_cursorIndexOfFCategory)) {
              _tmpFCategory = null;
            } else {
              _tmpFCategory = _cursor.getString(_cursorIndexOfFCategory);
            }
            _item = new Category(_tmpId,_tmpFCategory);
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
    final String _sql = "SELECT fCategory FROM category_tbl";
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
