// Generated by view binder compiler. Do not edit!
package com.bdtask.restoraposroomdbtab.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.bdtask.restoraposroomdbtab.R;
import com.chinodev.androidneomorphframelayout.NeomorphFrameLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogCloseAlertBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final NeomorphFrameLayout closeLay;

  @NonNull
  public final ImageView img1;

  @NonNull
  public final ImageView img2;

  @NonNull
  public final NeomorphFrameLayout logoutLay;

  private DialogCloseAlertBinding(@NonNull CardView rootView, @NonNull NeomorphFrameLayout closeLay,
      @NonNull ImageView img1, @NonNull ImageView img2, @NonNull NeomorphFrameLayout logoutLay) {
    this.rootView = rootView;
    this.closeLay = closeLay;
    this.img1 = img1;
    this.img2 = img2;
    this.logoutLay = logoutLay;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogCloseAlertBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogCloseAlertBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_close_alert, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogCloseAlertBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.closeLay;
      NeomorphFrameLayout closeLay = ViewBindings.findChildViewById(rootView, id);
      if (closeLay == null) {
        break missingId;
      }

      id = R.id.img1;
      ImageView img1 = ViewBindings.findChildViewById(rootView, id);
      if (img1 == null) {
        break missingId;
      }

      id = R.id.img2;
      ImageView img2 = ViewBindings.findChildViewById(rootView, id);
      if (img2 == null) {
        break missingId;
      }

      id = R.id.logoutLay;
      NeomorphFrameLayout logoutLay = ViewBindings.findChildViewById(rootView, id);
      if (logoutLay == null) {
        break missingId;
      }

      return new DialogCloseAlertBinding((CardView) rootView, closeLay, img1, img2, logoutLay);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}