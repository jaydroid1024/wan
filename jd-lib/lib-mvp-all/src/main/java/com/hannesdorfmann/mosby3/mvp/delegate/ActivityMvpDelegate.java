package com.hannesdorfmann.mosby3.mvp.delegate;

import android.app.Activity;
import android.os.Bundle;

import com.hannesdorfmann.mosby3.mvp.contract.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.contract.MvpView;

/**
 * A delegate for Activities to attach them to Mosby mvp.
 *
 * <p>The following methods must be invoked from the corresponding Activities lifecycle methods:
 *
 * <ul>
 *   <li>{@link #onCreate(Bundle)}
 *   <li>{@link #onDestroy()}
 *   <li>{@link #onPause()}
 *   <li>{@link #onResume()}
 *   <li>{@link #onStart()}
 *   <li>{@link #onStop()}
 *   <li>{@link #onRestart()}
 *   <li>{@link #onContentChanged()}
 *   <li>{@link #onSaveInstanceState(Bundle)}
 *   <li>{@link #onPostCreate(Bundle)}
 *   <li>
 * </ul>
 *
 * @param <V> The type of {@link MvpView}
 * @param <P> The type of {@link MvpPresenter}
 * @author Hannes Dorfmann
 * @since 1.1.0
 */
public interface ActivityMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {

  /**
   * This method must be called from {@link Activity#onCreate(Bundle)}. This method internally
   * creates the presenter and attaches the view to it.
   */
  void onCreate(Bundle bundle);

  /**
   * This method must be called from {@link Activity#onDestroy()}}. This method internally detaches
   * the view from presenter
   */
  void onDestroy();

  /** This method must be called from {@link Activity#onPause()} */
  void onPause();

  /** This method must be called from {@link Activity#onResume()} */
  void onResume();

  /** This method must be called from {@link Activity#onStart()} */
  void onStart();

  /** This method must be called from {@link Activity#onStop()} */
  void onStop();

  /** This method must be called from {@link Activity#onRestart()} */
  void onRestart();

  /** This method must be called from {@link Activity#onContentChanged()} */
  void onContentChanged();

  /** This method must be called from {@link Activity#onSaveInstanceState(Bundle)} */
  void onSaveInstanceState(Bundle outState);

  /** This method must be called from {@link Activity#onPostCreate(Bundle)} */
  void onPostCreate(Bundle savedInstanceState);
}
