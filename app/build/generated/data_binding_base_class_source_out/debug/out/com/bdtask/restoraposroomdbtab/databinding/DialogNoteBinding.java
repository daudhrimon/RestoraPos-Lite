// Generated by view binder compiler. Do not edit!
package com.bdtask.restoraposroomdbtab.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.bdtask.restoraposroomdbtab.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DialogNoteBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final Button addNoteBtn;

  @NonNull
  public final Button cancelBtn;

  @NonNull
  public final EditText noteEt;

  private DialogNoteBinding(@NonNull CardView rootView, @NonNull Button addNoteBtn,
      @NonNull Button cancelBtn, @NonNull EditText noteEt) {
    this.rootView = rootView;
    this.addNoteBtn = addNoteBtn;
    this.cancelBtn = cancelBtn;
    this.noteEt = noteEt;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static DialogNoteBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DialogNoteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.dialog_note, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DialogNoteBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addNoteBtn;
      Button addNoteBtn = ViewBindings.findChildViewById(rootView, id);
      if (addNoteBtn == null) {
        break missingId;
      }

      id = R.id.cancelBtn;
      Button cancelBtn = ViewBindings.findChildViewById(rootView, id);
      if (cancelBtn == null) {
        break missingId;
      }

      id = R.id.noteEt;
      EditText noteEt = ViewBindings.findChildViewById(rootView, id);
      if (noteEt == null) {
        break missingId;
      }

      return new DialogNoteBinding((CardView) rootView, addNoteBtn, cancelBtn, noteEt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
