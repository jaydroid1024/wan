
package com.hannesdorfmann.mosby3.mvp.layout;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Parcelable;
import android.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.hannesdorfmann.mosby3.mvp.contract.MvpPresenter;
 import com.hannesdorfmann.mosby3.mvp.contract.MvpView;
import com.hannesdorfmann.mosby3.mvp.delegate.ViewGroupDelegateCallback;
import com.hannesdorfmann.mosby3.mvp.delegate.ViewGroupMvpDelegate;
import com.hannesdorfmann.mosby3.mvp.delegate.ViewGroupMvpDelegateImpl;

/**
 * A RelativeLayout that can be used as view with an presenter.
 *
 * @author Hannes Dorfmann
 * @since 1.1
 */
public abstract class MvpRelativeLayout<V extends MvpView, P extends MvpPresenter<V>>
    extends RelativeLayout implements MvpView, ViewGroupDelegateCallback<V, P> {

  protected P presenter;
  protected ViewGroupMvpDelegate<V, P> mvpDelegate;
  private boolean retainInstance = false;

  public MvpRelativeLayout(Context context) {
    super(context);
  }

  public MvpRelativeLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MvpRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @TargetApi(21)
  public MvpRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  /**
   * Get the mvp delegate. This is internally used for creating presenter, attaching and detaching
   * view from presenter etc.
   *
   * <p><b>Please note that only one instance of mvp delegate should be used per android.view.View
   * instance</b>.
   * </p>
   *
   * <p>
   * Only override this method if you really know what you are doing.
   * </p>
   *
   * @return {@link ViewGroupMvpDelegate}
   */
  @NonNull protected ViewGroupMvpDelegate<V, P> getMvpDelegate() {
    if (mvpDelegate == null) {
      mvpDelegate = new ViewGroupMvpDelegateImpl<>(this, this, true);
    }

    return mvpDelegate;
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    getMvpDelegate().onAttachedToWindow();
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    getMvpDelegate().onDetachedFromWindow();
  }

  @SuppressLint("MissingSuperCall") @Override protected Parcelable onSaveInstanceState() {
    return getMvpDelegate().onSaveInstanceState();
  }

  @SuppressLint("MissingSuperCall") @Override
  protected void onRestoreInstanceState(Parcelable state) {
    getMvpDelegate().onRestoreInstanceState(state);
  }

  /**
   * Instantiate a presenter instance
   *
   * @return The {@link MvpPresenter} for this view
   */
  @Override
  public abstract P createPresenter();

  @Override public P getPresenter() {
    return presenter;
  }

  @Override public void setPresenter(P presenter) {
    this.presenter = presenter;
  }

  @Override public V getMvpView() {
    return (V) this;
  }

  @Override public final Parcelable superOnSaveInstanceState() {
    return super.onSaveInstanceState();
  }

  @Override public final void superOnRestoreInstanceState(Parcelable state) {
    super.onRestoreInstanceState(state);
  }
}
