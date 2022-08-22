// Generated by view binder compiler. Do not edit!
package com.bdtask.restoraposroomdbtab.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.bdtask.restoraposroomdbtab.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SingleValueSpdateDltBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView udItemCross;

  @NonNull
  public final TextView udItemDel;

  @NonNull
  public final EditText udItemEt;

  @NonNull
  public final TextView udItemTv;

  @NonNull
  public final TextView udItemUp;

  private SingleValueSpdateDltBinding(@NonNull LinearLayout rootView,
      @NonNull ImageView udItemCross, @NonNull TextView udItemDel, @NonNull EditText udItemEt,
      @NonNull TextView udItemTv, @NonNull TextView udItemUp) {
    this.rootView = rootView;
    this.udItemCross = udItemCross;
    this.udItemDel = udItemDel;
    this.udItemEt = udItemEt;
    this.udItemTv = udItemTv;
    this.udItemUp = udItemUp;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static SingleValueSpdateDltBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SingleValueSpdateDltBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.single_value_spdate_dlt, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SingleValueSpdateDltBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.udItemCross;
      ImageView udItemCross = ViewBindings.findChildViewById(rootView, id);
      if (udItemCross == null) {
        break missingId;
      }

      id = R.id.udItemDel;
      TextView udItemDel = ViewBindings.findChildViewById(rootView, id);
      if (udItemDel == null) {
        break missingId;
      }

      id = R.id.udItemEt;
      EditText udItemEt = ViewBindings.findChildViewById(rootView, id);
      if (udItemEt == null) {
        break missingId;
      }

      id = R.id.udItemTv;
      TextView udItemTv = ViewBindings.findChildViewById(rootView, id);
      if (udItemTv == null) {
        break missingId;
      }

      id = R.id.udItemUp;
      TextView udItemUp = ViewBindings.findChildViewById(rootView, id);
      if (udItemUp == null) {
        break missingId;
      }

      return new SingleValueSpdateDltBinding((LinearLayout) rootView, udItemCross, udItemDel,
          udItemEt, udItemTv, udItemUp);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
