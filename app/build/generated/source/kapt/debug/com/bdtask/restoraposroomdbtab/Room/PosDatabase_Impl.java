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
import com.bdtask.restoraposroomdbtab.Room.Dao.CustomerDao;
import com.bdtask.restoraposroomdbtab.Room.Dao.CustomerDao_Impl;
import com.bdtask.restoraposroomdbtab.Room.Dao.DeliveryCompanyDao;
import com.bdtask.restoraposroomdbtab.Room.Dao.DeliveryCompanyDao_Impl;
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

  private volatile DeliveryCompanyDao _deliveryCompanyDao;

  private volatile WaiterDao _waiterDao;

  private volatile TableDao _tableDao;

  private volatile OrderDao _orderDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `category_tbl` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `fCategory` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `food_tbl` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `fCategory` TEXT NOT NULL, `fTitle` TEXT NOT NULL, `fVariant` TEXT NOT NULL, `fImage` TEXT NOT NULL, `fAddons` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `customer_tbl` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `email` TEXT NOT NULL, `mobile` TEXT NOT NULL, `address` TEXT NOT NULL, `favAddress` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `delivery_company` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `companyName` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `waiter_tbl` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `wName` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `table_tbl` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `tName` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `order_tbl` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `status` TEXT NOT NULL, `date` TEXT NOT NULL, `token` TEXT NOT NULL, `cartList` TEXT NOT NULL, `orderInfoList` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '2c73a47003b8e3b5dd68a6fcba1895e5')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `category_tbl`");
        _db.execSQL("DROP TABLE IF EXISTS `food_tbl`");
        _db.execSQL("DROP TABLE IF EXISTS `customer_tbl`");
        _db.execSQL("DROP TABLE IF EXISTS `delivery_company`");
        _db.execSQL("DROP TABLE IF EXISTS `waiter_tbl`");
        _db.execSQL("DROP TABLE IF EXISTS `table_tbl`");
        _db.execSQL("DROP TABLE IF EXISTS `order_tbl`");
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
        _columnsCategoryTbl.put("fCategory", new TableInfo.Column("fCategory", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategoryTbl = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategoryTbl = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCategoryTbl = new TableInfo("category_tbl", _columnsCategoryTbl, _foreignKeysCategoryTbl, _indicesCategoryTbl);
        final TableInfo _existingCategoryTbl = TableInfo.read(_db, "category_tbl");
        if (! _infoCategoryTbl.equals(_existingCategoryTbl)) {
          return new RoomOpenHelper.ValidationResult(false, "category_tbl(com.bdtask.restoraposroomdbtab.Room.Entity.Category).\n"
                  + " Expected:\n" + _infoCategoryTbl + "\n"
                  + " Found:\n" + _existingCategoryTbl);
        }
        final HashMap<String, TableInfo.Column> _columnsFoodTbl = new HashMap<String, TableInfo.Column>(6);
        _columnsFoodTbl.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodTbl.put("fCategory", new TableInfo.Column("fCategory", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodTbl.put("fTitle", new TableInfo.Column("fTitle", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodTbl.put("fVariant", new TableInfo.Column("fVariant", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodTbl.put("fImage", new TableInfo.Column("fImage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodTbl.put("fAddons", new TableInfo.Column("fAddons", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
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
        _columnsCustomerTbl.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomerTbl.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomerTbl.put("mobile", new TableInfo.Column("mobile", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomerTbl.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomerTbl.put("favAddress", new TableInfo.Column("favAddress", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCustomerTbl = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCustomerTbl = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCustomerTbl = new TableInfo("customer_tbl", _columnsCustomerTbl, _foreignKeysCustomerTbl, _indicesCustomerTbl);
        final TableInfo _existingCustomerTbl = TableInfo.read(_db, "customer_tbl");
        if (! _infoCustomerTbl.equals(_existingCustomerTbl)) {
          return new RoomOpenHelper.ValidationResult(false, "customer_tbl(com.bdtask.restoraposroomdbtab.Room.Entity.Customer).\n"
                  + " Expected:\n" + _infoCustomerTbl + "\n"
                  + " Found:\n" + _existingCustomerTbl);
        }
        final HashMap<String, TableInfo.Column> _columnsDeliveryCompany = new HashMap<String, TableInfo.Column>(2);
        _columnsDeliveryCompany.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDeliveryCompany.put("companyName", new TableInfo.Column("companyName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDeliveryCompany = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDeliveryCompany = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDeliveryCompany = new TableInfo("delivery_company", _columnsDeliveryCompany, _foreignKeysDeliveryCompany, _indicesDeliveryCompany);
        final TableInfo _existingDeliveryCompany = TableInfo.read(_db, "delivery_company");
        if (! _infoDeliveryCompany.equals(_existingDeliveryCompany)) {
          return new RoomOpenHelper.ValidationResult(false, "delivery_company(com.bdtask.restoraposroomdbtab.Room.Entity.DeliveryCompany).\n"
                  + " Expected:\n" + _infoDeliveryCompany + "\n"
                  + " Found:\n" + _existingDeliveryCompany);
        }
        final HashMap<String, TableInfo.Column> _columnsWaiterTbl = new HashMap<String, TableInfo.Column>(2);
        _columnsWaiterTbl.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWaiterTbl.put("wName", new TableInfo.Column("wName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
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
        _columnsTableTbl.put("tName", new TableInfo.Column("tName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTableTbl = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTableTbl = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTableTbl = new TableInfo("table_tbl", _columnsTableTbl, _foreignKeysTableTbl, _indicesTableTbl);
        final TableInfo _existingTableTbl = TableInfo.read(_db, "table_tbl");
        if (! _infoTableTbl.equals(_existingTableTbl)) {
          return new RoomOpenHelper.ValidationResult(false, "table_tbl(com.bdtask.restoraposroomdbtab.Room.Entity.Table).\n"
                  + " Expected:\n" + _infoTableTbl + "\n"
                  + " Found:\n" + _existingTableTbl);
        }
        final HashMap<String, TableInfo.Column> _columnsOrderTbl = new HashMap<String, TableInfo.Column>(6);
        _columnsOrderTbl.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderTbl.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderTbl.put("date", new TableInfo.Column("date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderTbl.put("token", new TableInfo.Column("token", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderTbl.put("cartList", new TableInfo.Column("cartList", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderTbl.put("orderInfoList", new TableInfo.Column("orderInfoList", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOrderTbl = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesOrderTbl = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoOrderTbl = new TableInfo("order_tbl", _columnsOrderTbl, _foreignKeysOrderTbl, _indicesOrderTbl);
        final TableInfo _existingOrderTbl = TableInfo.read(_db, "order_tbl");
        if (! _infoOrderTbl.equals(_existingOrderTbl)) {
          return new RoomOpenHelper.ValidationResult(false, "order_tbl(com.bdtask.restoraposroomdbtab.Room.Entity.Order).\n"
                  + " Expected:\n" + _infoOrderTbl + "\n"
                  + " Found:\n" + _existingOrderTbl);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "2c73a47003b8e3b5dd68a6fcba1895e5", "ca3d019e8828c5e68ee08fd36b764a40");
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
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "category_tbl","food_tbl","customer_tbl","delivery_company","waiter_tbl","table_tbl","order_tbl");
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
      _db.execSQL("DELETE FROM `delivery_company`");
      _db.execSQL("DELETE FROM `waiter_tbl`");
      _db.execSQL("DELETE FROM `table_tbl`");
      _db.execSQL("DELETE FROM `order_tbl`");
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
    _typeConvertersMap.put(DeliveryCompanyDao.class, DeliveryCompanyDao_Impl.getRequiredConverters());
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
  public DeliveryCompanyDao deliveryCompanyDao() {
    if (_deliveryCompanyDao != null) {
      return _deliveryCompanyDao;
    } else {
      synchronized(this) {
        if(_deliveryCompanyDao == null) {
          _deliveryCompanyDao = new DeliveryCompanyDao_Impl(this);
        }
        return _deliveryCompanyDao;
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
