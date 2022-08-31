package com.bdtask.restoraposroomdbtab.Room;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.bdtask.restoraposroomdbtab.Room.Dao.CategoryDao;
import com.bdtask.restoraposroomdbtab.Room.Dao.CategoryDao_Impl;
import com.bdtask.restoraposroomdbtab.Room.Dao.CompanyDao;
import com.bdtask.restoraposroomdbtab.Room.Dao.CompanyDao_Impl;
import com.bdtask.restoraposroomdbtab.Room.Dao.CustomerDao;
import com.bdtask.restoraposroomdbtab.Room.Dao.CustomerDao_Impl;
import com.bdtask.restoraposroomdbtab.Room.Dao.FoodDao;
import com.bdtask.restoraposroomdbtab.Room.Dao.FoodDao_Impl;
import com.bdtask.restoraposroomdbtab.Room.Dao.OrderDao;
import com.bdtask.restoraposroomdbtab.Room.Dao.OrderDao_Impl;
import com.bdtask.restoraposroomdbtab.Room.Dao.TableDao;
import com.bdtask.restoraposroomdbtab.Room.Dao.TableDao_Impl;
import com.bdtask.restoraposroomdbtab.Room.Dao.WaiterDao;
import com.bdtask.restoraposroomdbtab.Room.Dao.WaiterDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PosDatabase_Impl extends PosDatabase {
  private volatile CategoryDao _categoryDao;

  private volatile FoodDao _foodDao;

  private volatile CustomerDao _customerDao;

  private volatile CompanyDao _companyDao;

  private volatile WaiterDao _waiterDao;

  private volatile TableDao _tableDao;

  private volatile OrderDao _orderDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `category_tbl` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `fCat` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `food_tbl` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `fCate` TEXT NOT NULL, `fTitle` TEXT NOT NULL, `fVar` TEXT NOT NULL, `fImg` TEXT NOT NULL, `fAdns` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `customer_tbl` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nm` TEXT NOT NULL, `eml` TEXT NOT NULL, `mbl` TEXT NOT NULL, `adrs` TEXT NOT NULL, `fAdrs` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `company` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cNm` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `waiter_tbl` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `wNm` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `table_tbl` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `tNm` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `order_tbl` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sts` INTEGER NOT NULL, `spl` INTEGER NOT NULL, `mrg` INTEGER NOT NULL, `dat` TEXT NOT NULL, `tkn` TEXT NOT NULL, `vat` REAL NOT NULL, `crg` REAL NOT NULL, `odrInf` TEXT NOT NULL, `cart` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `split_tbl` (`id` TEXT NOT NULL, `ref` INTEGER NOT NULL, `sts` INTEGER NOT NULL, `csInf` TEXT NOT NULL, `cart` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'e92aafd6798e904511cf031d1b2aa39d')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `category_tbl`");
        _db.execSQL("DROP TABLE IF EXISTS `food_tbl`");
        _db.execSQL("DROP TABLE IF EXISTS `customer_tbl`");
        _db.execSQL("DROP TABLE IF EXISTS `company`");
        _db.execSQL("DROP TABLE IF EXISTS `waiter_tbl`");
        _db.execSQL("DROP TABLE IF EXISTS `table_tbl`");
        _db.execSQL("DROP TABLE IF EXISTS `order_tbl`");
        _db.execSQL("DROP TABLE IF EXISTS `split_tbl`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsCategoryTbl = new HashMap<String, TableInfo.Column>(2);
        _columnsCategoryTbl.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategoryTbl.put("fCat", new TableInfo.Column("fCat", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategoryTbl = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategoryTbl = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCategoryTbl = new TableInfo("category_tbl", _columnsCategoryTbl, _foreignKeysCategoryTbl, _indicesCategoryTbl);
        final TableInfo _existingCategoryTbl = TableInfo.read(_db, "category_tbl");
        if (! _infoCategoryTbl.equals(_existingCategoryTbl)) {
          return new RoomOpenHelper.ValidationResult(false, "category_tbl(com.bdtask.restoraposroomdbtab.Room.Entity.Catgry).\n"
                  + " Expected:\n" + _infoCategoryTbl + "\n"
                  + " Found:\n" + _existingCategoryTbl);
        }
        final HashMap<String, TableInfo.Column> _columnsFoodTbl = new HashMap<String, TableInfo.Column>(6);
        _columnsFoodTbl.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodTbl.put("fCate", new TableInfo.Column("fCate", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodTbl.put("fTitle", new TableInfo.Column("fTitle", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodTbl.put("fVar", new TableInfo.Column("fVar", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodTbl.put("fImg", new TableInfo.Column("fImg", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodTbl.put("fAdns", new TableInfo.Column("fAdns", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFoodTbl = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFoodTbl = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFoodTbl = new TableInfo("food_tbl", _columnsFoodTbl, _foreignKeysFoodTbl, _indicesFoodTbl);
        final TableInfo _existingFoodTbl = TableInfo.read(_db, "food_tbl");
        if (! _infoFoodTbl.equals(_existingFoodTbl)) {
          return new RoomOpenHelper.ValidationResult(false, "food_tbl(com.bdtask.restoraposroomdbtab.Room.Entity.Food).\n"
                  + " Expected:\n" + _infoFoodTbl + "\n"
                  + " Found:\n" + _existingFoodTbl);
        }
        final HashMap<String, TableInfo.Column> _columnsCustomerTbl = new HashMap<String, TableInfo.Column>(6);
        _columnsCustomerTbl.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomerTbl.put("nm", new TableInfo.Column("nm", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomerTbl.put("eml", new TableInfo.Column("eml", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomerTbl.put("mbl", new TableInfo.Column("mbl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomerTbl.put("adrs", new TableInfo.Column("adrs", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomerTbl.put("fAdrs", new TableInfo.Column("fAdrs", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCustomerTbl = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCustomerTbl = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCustomerTbl = new TableInfo("customer_tbl", _columnsCustomerTbl, _foreignKeysCustomerTbl, _indicesCustomerTbl);
        final TableInfo _existingCustomerTbl = TableInfo.read(_db, "customer_tbl");
        if (! _infoCustomerTbl.equals(_existingCustomerTbl)) {
          return new RoomOpenHelper.ValidationResult(false, "customer_tbl(com.bdtask.restoraposroomdbtab.Room.Entity.Cstmr).\n"
                  + " Expected:\n" + _infoCustomerTbl + "\n"
                  + " Found:\n" + _existingCustomerTbl);
        }
        final HashMap<String, TableInfo.Column> _columnsCompany = new HashMap<String, TableInfo.Column>(2);
        _columnsCompany.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCompany.put("cNm", new TableInfo.Column("cNm", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCompany = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCompany = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCompany = new TableInfo("company", _columnsCompany, _foreignKeysCompany, _indicesCompany);
        final TableInfo _existingCompany = TableInfo.read(_db, "company");
        if (! _infoCompany.equals(_existingCompany)) {
          return new RoomOpenHelper.ValidationResult(false, "company(com.bdtask.restoraposroomdbtab.Room.Entity.Cmpny).\n"
                  + " Expected:\n" + _infoCompany + "\n"
                  + " Found:\n" + _existingCompany);
        }
        final HashMap<String, TableInfo.Column> _columnsWaiterTbl = new HashMap<String, TableInfo.Column>(2);
        _columnsWaiterTbl.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWaiterTbl.put("wNm", new TableInfo.Column("wNm", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWaiterTbl = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWaiterTbl = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWaiterTbl = new TableInfo("waiter_tbl", _columnsWaiterTbl, _foreignKeysWaiterTbl, _indicesWaiterTbl);
        final TableInfo _existingWaiterTbl = TableInfo.read(_db, "waiter_tbl");
        if (! _infoWaiterTbl.equals(_existingWaiterTbl)) {
          return new RoomOpenHelper.ValidationResult(false, "waiter_tbl(com.bdtask.restoraposroomdbtab.Room.Entity.Waiter).\n"
                  + " Expected:\n" + _infoWaiterTbl + "\n"
                  + " Found:\n" + _existingWaiterTbl);
        }
        final HashMap<String, TableInfo.Column> _columnsTableTbl = new HashMap<String, TableInfo.Column>(2);
        _columnsTableTbl.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTableTbl.put("tNm", new TableInfo.Column("tNm", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTableTbl = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTableTbl = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTableTbl = new TableInfo("table_tbl", _columnsTableTbl, _foreignKeysTableTbl, _indicesTableTbl);
        final TableInfo _existingTableTbl = TableInfo.read(_db, "table_tbl");
        if (! _infoTableTbl.equals(_existingTableTbl)) {
          return new RoomOpenHelper.ValidationResult(false, "table_tbl(com.bdtask.restoraposroomdbtab.Room.Entity.Table).\n"
                  + " Expected:\n" + _infoTableTbl + "\n"
                  + " Found:\n" + _existingTableTbl);
        }
        final HashMap<String, TableInfo.Column> _columnsOrderTbl = new HashMap<String, TableInfo.Column>(10);
        _columnsOrderTbl.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderTbl.put("sts", new TableInfo.Column("sts", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderTbl.put("spl", new TableInfo.Column("spl", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderTbl.put("mrg", new TableInfo.Column("mrg", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderTbl.put("dat", new TableInfo.Column("dat", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderTbl.put("tkn", new TableInfo.Column("tkn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderTbl.put("vat", new TableInfo.Column("vat", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderTbl.put("crg", new TableInfo.Column("crg", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderTbl.put("odrInf", new TableInfo.Column("odrInf", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderTbl.put("cart", new TableInfo.Column("cart", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOrderTbl = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesOrderTbl = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoOrderTbl = new TableInfo("order_tbl", _columnsOrderTbl, _foreignKeysOrderTbl, _indicesOrderTbl);
        final TableInfo _existingOrderTbl = TableInfo.read(_db, "order_tbl");
        if (! _infoOrderTbl.equals(_existingOrderTbl)) {
          return new RoomOpenHelper.ValidationResult(false, "order_tbl(com.bdtask.restoraposroomdbtab.Room.Entity.Order).\n"
                  + " Expected:\n" + _infoOrderTbl + "\n"
                  + " Found:\n" + _existingOrderTbl);
        }
        final HashMap<String, TableInfo.Column> _columnsSplitTbl = new HashMap<String, TableInfo.Column>(5);
        _columnsSplitTbl.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSplitTbl.put("ref", new TableInfo.Column("ref", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSplitTbl.put("sts", new TableInfo.Column("sts", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSplitTbl.put("csInf", new TableInfo.Column("csInf", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSplitTbl.put("cart", new TableInfo.Column("cart", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSplitTbl = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSplitTbl = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSplitTbl = new TableInfo("split_tbl", _columnsSplitTbl, _foreignKeysSplitTbl, _indicesSplitTbl);
        final TableInfo _existingSplitTbl = TableInfo.read(_db, "split_tbl");
        if (! _infoSplitTbl.equals(_existingSplitTbl)) {
          return new RoomOpenHelper.ValidationResult(false, "split_tbl(com.bdtask.restoraposroomdbtab.Room.Entity.Split).\n"
                  + " Expected:\n" + _infoSplitTbl + "\n"
                  + " Found:\n" + _existingSplitTbl);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "e92aafd6798e904511cf031d1b2aa39d", "3fdf0eb0c5538345df9b21da1318866f");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "category_tbl","food_tbl","customer_tbl","company","waiter_tbl","table_tbl","order_tbl","split_tbl");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `category_tbl`");
      _db.execSQL("DELETE FROM `food_tbl`");
      _db.execSQL("DELETE FROM `customer_tbl`");
      _db.execSQL("DELETE FROM `company`");
      _db.execSQL("DELETE FROM `waiter_tbl`");
      _db.execSQL("DELETE FROM `table_tbl`");
      _db.execSQL("DELETE FROM `order_tbl`");
      _db.execSQL("DELETE FROM `split_tbl`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CategoryDao.class, CategoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FoodDao.class, FoodDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CustomerDao.class, CustomerDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CompanyDao.class, CompanyDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WaiterDao.class, WaiterDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TableDao.class, TableDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(OrderDao.class, OrderDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public CategoryDao categoryDao() {
    if (_categoryDao != null) {
      return _categoryDao;
    } else {
      synchronized(this) {
        if(_categoryDao == null) {
          _categoryDao = new CategoryDao_Impl(this);
        }
        return _categoryDao;
      }
    }
  }

  @Override
  public FoodDao foodDao() {
    if (_foodDao != null) {
      return _foodDao;
    } else {
      synchronized(this) {
        if(_foodDao == null) {
          _foodDao = new FoodDao_Impl(this);
        }
        return _foodDao;
      }
    }
  }

  @Override
  public CustomerDao customerDao() {
    if (_customerDao != null) {
      return _customerDao;
    } else {
      synchronized(this) {
        if(_customerDao == null) {
          _customerDao = new CustomerDao_Impl(this);
        }
        return _customerDao;
      }
    }
  }

  @Override
  public CompanyDao deliveryCompanyDao() {
    if (_companyDao != null) {
      return _companyDao;
    } else {
      synchronized(this) {
        if(_companyDao == null) {
          _companyDao = new CompanyDao_Impl(this);
        }
        return _companyDao;
      }
    }
  }

  @Override
  public WaiterDao waiterDao() {
    if (_waiterDao != null) {
      return _waiterDao;
    } else {
      synchronized(this) {
        if(_waiterDao == null) {
          _waiterDao = new WaiterDao_Impl(this);
        }
        return _waiterDao;
      }
    }
  }

  @Override
  public TableDao tableDao() {
    if (_tableDao != null) {
      return _tableDao;
    } else {
      synchronized(this) {
        if(_tableDao == null) {
          _tableDao = new TableDao_Impl(this);
        }
        return _tableDao;
      }
    }
  }

  @Override
  public OrderDao orderDao() {
    if (_orderDao != null) {
      return _orderDao;
    } else {
      synchronized(this) {
        if(_orderDao == null) {
          _orderDao = new OrderDao_Impl(this);
        }
        return _orderDao;
      }
    }
  }
}
