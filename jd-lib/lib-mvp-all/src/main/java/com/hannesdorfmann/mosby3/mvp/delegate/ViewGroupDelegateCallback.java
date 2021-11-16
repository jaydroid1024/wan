

package com.hannesdorfmann.mosby3.mvp.delegate;

import android.content.Context;
import android.os.Parcelable;
import com.hannesdorfmann.mosby3.mvp.contract.MvpPresenter;
 import com.hannesdorfmann.mosby3.mvp.contract.MvpView;

/**
 * An enhanced version of {@link MvpDelegateCallback} that adds support for
 * android.view.View like FrameLayout etc.
 *
 * @author Hannes Dorfmann
 * @since 3.0
 */
public interface ViewGroupDelegateCallback<V extends MvpView, P extends MvpPresenter<V>>
    extends MvpDelegateCallback<V, P> {

  /**
   * This method must call super.onSaveInstanceState() within any view
   */
  Parcelable superOnSaveInstanceState();

  /**
   * This method must call super.onRestoreInstanceState(state)
   *
   * @param state The parcelable containing the state
   */
  void superOnRestoreInstanceState(Parcelable state);

  /**
   * Get the context
   *
   * @return Get the context
   * @since 3.0
   */
  Context getContext();
}
