// Generated by view binder compiler. Do not edit!
package com.bdtask.restoraposroomdbtab.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.bdtask.restoraposroomdbtab.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogInsertAddonBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView addonAddBtn;

  @NonNull
  public final ImageView addonCrossBtn;

  @NonNull
  public final TextView addonHeaderTv;

  @NonNull
  public final EditText addonNameEt;

  @NonNull
  public final EditText addonPriceEt;

  private DialogInsertAddonBinding(@NonNull CardView rootView, @NonNull TextView addonAddBtn,
      @NonNull ImageView addonCrossBtn, @NonNull TextView addonHeaderTv,
      @NonNull EditText addonNameEt, @NonNull EditText addonPriceEt) {
    this.rootView = rootView;
    this.addonAddBtn = addonAddBtn;
    this.addonCrossBtn = addonCrossBtn;
    this.addonHeaderTv = addonHeaderTv;
    this.addonNameEt = addonNameEt;
    this.addonPriceEt = addonPriceEt;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogInsertAddonBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogInsertAddonBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_insert_addon, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogInsertAddonBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addonAddBtn;
      TextView addonAddBtn = ViewBindings.findChildViewById(rootView, id);
      if (addonAddBtn == null) {
        break missingId;
      }

      id = R.id.addonCrossBtn;
      ImageView addonCrossBtn = ViewBindings.findChildViewById(rootView, id);
      if (addonCrossBtn == null) {
        break missingId;
      }

      id = R.id.addonHeaderTv;
      TextView addonHeaderTv = ViewBindings.findChildViewById(rootView, id);
      if (addonHeaderTv == null) {
        break missingId;
      }

      id = R.id.addonNameEt;
      EditText addonNameEt = ViewBindings.findChildViewById(rootView, id);
      if (addonNameEt == null) {
        break missingId;
      }

      id = R.id.addonPriceEt;
      EditText addonPriceEt = ViewBindings.findChildViewById(rootView, id);
      if (addonPriceEt == null) {
        break missingId;
      }

      return new DialogInsertAddonBinding((CardView) rootView, addonAddBtn, addonCrossBtn,
          addonHeaderTv, addonNameEt, addonPriceEt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
