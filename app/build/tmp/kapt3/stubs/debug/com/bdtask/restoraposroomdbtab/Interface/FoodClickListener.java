package com.bdtask.restoraposroomdbtab.Interface;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J=\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\tH&\u00a2\u0006\u0002\u0010\r\u00a8\u0006\u000e"}, d2 = {"Lcom/bdtask/restoraposroomdbtab/Interface/FoodClickListener;", "", "onFoodClick", "", "foodId", "", "foodTitle", "", "variantlist", "", "Lcom/bdtask/restoraposroomdbtab/Model/Variant;", "adnList", "Lcom/bdtask/restoraposroomdbtab/Model/Adn;", "(Ljava/lang/Long;Ljava/lang/String;Ljava/util/List;Ljava/util/List;)V", "app_debug"})
public abstract interface FoodClickListener {
    
    public abstract void onFoodClick(@org.jetbrains.annotations.Nullable()
    java.lang.Long foodId, @org.jetbrains.annotations.Nullable()
    java.lang.String foodTitle, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Variant> variantlist, @org.jetbrains.annotations.NotNull()
    java.util.List<com.bdtask.restoraposroomdbtab.Model.Adn> adnList);
}