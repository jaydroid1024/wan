package com.hannesdorfmann.mosby3.mvp.utils;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.util.ArrayMap;

import com.hannesdorfmann.mosby3.mvp.contract.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.contract.MvpView;

import java.util.Map;

/**
 * This class basically represents a Map for View Id to the Presenter / ViewState. One instance of
 * this class is also associated by {@link PresenterManager} to one Activity (kept across screen
 * orientation changes)
 *
 * @author Hannes Dorfmann
 * @since 3.0.0
 */
class ActivityScopedCache {

  private final Map<String, PresenterHolder> presenterMap = new ArrayMap<>();

  ActivityScopedCache() {
    // package private
  }

  public void clear() {
    presenterMap.clear();
  }

  /**
   * Get the Presenter for a given {@link MvpView} if exists or <code>null</code>
   *
   * @param viewId The mosby internal view id
   * @param <P> The type tof the {@link MvpPresenter}
   * @return The Presenter for the given view id or <code>null</code>
   */
  @Nullable
  public <P> P getPresenter(@NonNull String viewId) {
    PresenterHolder holder = presenterMap.get(viewId);
    return holder == null ? null : (P) holder.presenter;
  }

  /**
   * Get the ViewState for a given {@link MvpView} if exists or <code>null</code>
   *
   * @param viewId The mosby internal view id
   * @param <VS> The type tof the {@link MvpPresenter}
   * @return The ViewState for the given view id or <code>null</code>
   */
  @Nullable
  public <VS> VS getViewState(@NonNull String viewId) {
    PresenterHolder holder = presenterMap.get(viewId);
    return holder == null ? null : (VS) holder.viewState;
  }

  /**
   * Put the presenter in the internal cache
   *
   * @param viewId The mosby internal View id of the {@link MvpView} which the presenter is
   *     associated to.
   * @param presenter The Presenter
   */
  public void putPresenter(
      @NonNull String viewId, @NonNull MvpPresenter<? extends MvpView> presenter) {

    if (viewId == null) {
      throw new NullPointerException("ViewId is null");
    }

    if (presenter == null) {
      throw new NullPointerException("Presenter is null");
    }

    PresenterHolder presenterHolder = presenterMap.get(viewId);
    if (presenterHolder == null) {
      presenterHolder = new PresenterHolder();
      presenterHolder.presenter = presenter;
      presenterMap.put(viewId, presenterHolder);
    } else {
      presenterHolder.presenter = presenter;
    }
  }

  /**
   * Put the viewstate in the internal cache
   *
   * @param viewId The mosby internal View id of the {@link MvpView} which the presenter is
   *     associated to.
   * @param viewState The Viewstate
   */
  public void putViewState(@NonNull String viewId, @NonNull Object viewState) {

    if (viewId == null) {
      throw new NullPointerException("ViewId is null");
    }

    if (viewState == null) {
      throw new NullPointerException("ViewState is null");
    }

    PresenterHolder presenterHolder = presenterMap.get(viewId);
    if (presenterHolder == null) {
      presenterHolder = new PresenterHolder();
      presenterHolder.viewState = viewState;
      presenterMap.put(viewId, presenterHolder);
    } else {
      presenterHolder.viewState = viewState;
    }
  }

  /**
   * Removes the Presenter (and ViewState) from the internal storage
   *
   * @param viewId The msoby internal view id
   */
  public void remove(@NonNull String viewId) {

    if (viewId == null) {
      throw new NullPointerException("View Id is null");
    }

    presenterMap.remove(viewId);
  }

  /** Internal config change Cache entry */
  static final class PresenterHolder {
    private MvpPresenter<?> presenter;
    private Object viewState; // workaround: didn't want to introduce dependency to viewstate module
  }

}
