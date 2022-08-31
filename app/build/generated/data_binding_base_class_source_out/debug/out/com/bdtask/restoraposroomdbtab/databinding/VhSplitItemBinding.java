// Generated by view binder compiler. Do not edit!
package com.bdtask.restoraposroomdbtab.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class VhSplitItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView spiQnty;

  @NonNull
  public final TextView spiTitle;

  private VhSplitItemBinding(@NonNull LinearLayout rootView, @NonNull TextView spiQnty,
      @NonNull TextView spiTitle) {
    this.rootView = rootView;
    this.spiQnty = spiQnty;
    this.spiTitle = spiTitle;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static VhSplitItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static VhSplitItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.vh_split_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static VhSplitItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.spiQnty;
      TextView spiQnty = ViewBindings.findChildViewById(rootView, id);
      if (spiQnty == null) {
        break missingId;
      }

      id = R.id.spiTitle;
      TextView spiTitle = ViewBindings.findChildViewById(rootView, id);
      if (spiTitle == null) {
        break missingId;
      }

      return new VhSplitItemBinding((LinearLayout) rootView, spiQnty, spiTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}