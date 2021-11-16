

package com.hannesdorfmann.mosby3.mvp.delegate;

import android.os.Parcelable;
import android.view.View;
import android.widget.FrameLayout;
import com.hannesdorfmann.mosby3.mvp.contract.MvpPresenter;
 import com.hannesdorfmann.mosby3.mvp.contract.MvpView;

/**
 * The mvp delegate used for everything that derives from {@link View} like {@link FrameLayout}
 * etc.
 *
 * <p>
 * The following methods must be called from the corresponding View lifecycle method:
 * <ul>
 * <li>{@link #onAttachedToWindow()}</li>
 * <li>{@link #onDetachedFromWindow()}</li>
 * </ul>
 * </p>
 *
 * @author Hannes Dorfmann
 * @since 1.1.0
 */
public interface ViewGroupMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {

  /**
   * Must be called from {@link View#onAttachedToWindow()}
   */
  void onAttachedToWindow();

  /**
   * Must be called from {@link View#onDetachedFromWindow()}
   */
  void onDetachedFromWindow();

  /**
   * Must be called from {@link View#onRestoreInstanceState(Parcelable)}
   *
   * @param state The parcelable state
   */
  void onRestoreInstanceState(Parcelable state);

  /**
   * Save the instatnce state
   *
   * @return The state with all the saved data
   */
  Parcelable onSaveInstanceState();
}
