
package com.hannesdorfmann.mosby3.mvp.utils;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.AbsSavedState;

import androidx.annotation.RequiresApi;
import androidx.core.os.ParcelableCompat;
import androidx.core.os.ParcelableCompatCreatorCallbacks;

/**
 * The SavedState implementation to store the view's internal id to
 *
 * @author Hannes Dorfmann
 * @since 3.0
 */
public class MosbySavedState extends AbsSavedState {

  public static final Parcelable.Creator<MosbySavedState> CREATOR =
      ParcelableCompat.newCreator(
          new ParcelableCompatCreatorCallbacks<MosbySavedState>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public MosbySavedState createFromParcel(Parcel in, ClassLoader loader) {
              if (loader == null) {
                loader = MosbySavedState.class.getClassLoader();
              }
              return new MosbySavedState(in, loader);
            }

            @Override
            public MosbySavedState[] newArray(int size) {
              return new MosbySavedState[size];
            }
          });

  private String mosbyViewId;

  public MosbySavedState(Parcelable superState, String mosbyViewId) {
    super(superState);
    this.mosbyViewId = mosbyViewId;
  }

  @RequiresApi(api = Build.VERSION_CODES.N)
  protected MosbySavedState(Parcel in, ClassLoader loader) {
    super(in, loader);
    this.mosbyViewId = in.readString();
  }

  @Override
  public void writeToParcel(Parcel out, int flags) {
    super.writeToParcel(out, flags);
    out.writeString(mosbyViewId);
  }

  public String getMosbyViewId() {
    return mosbyViewId;
  }
}
