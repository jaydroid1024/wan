

package com.hannesdorfmann.mosby3.mvp.delegate;

import android.annotation.Nullable;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.hannesdorfmann.mosby3.mvp.contract.MvpPresenter;
 import com.hannesdorfmann.mosby3.mvp.contract.MvpView;

/**
 * A delegate for Fragments to attach them to mosby mvp.
 * <p>
 * The following methods must be invoked from the corresponding Fragments lifecycle methods:
 *
 * <ul>
 * <li>{@link #onCreate(Bundle)}</li>
 * <li>{@link #onDestroy()}</li>
 * <li>{@link #onPause()} </li>
 * <li>{@link #onResume()} </li>
 * <li>{@link #onStart()} </li>
 * <li>{@link #onStop()} </li>
 * <li>{@link #onViewCreated(View, Bundle)} </li>
 * <li>{@link #onActivityCreated(Bundle)} </li>
 * <li>{@link #onSaveInstanceState(Bundle)} </li>
 * <li>{@link #onAttach(Activity)} </li>
 * <li>{@link #onAttach(Context)} </li>
 * <li>{@link #onDetach()}</li>
 * <li></li>
 * </ul>
 * </p>
 *
 * @param <V> The type of {@link MvpView}
 * @param <P> The type of {@link MvpPresenter}
 * @author Hannes Dorfmann
 * @since 1.1.0
 */
public interface FragmentMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {

  /**
   * Must be called from {@link Fragment#onCreate(Bundle)}
   *
   * @param saved The bundle
   */
  void onCreate(Bundle saved);

  /**
   * Must be called from {@link Fragment#onDestroy()}
   */
  void onDestroy();

  /**
   * Must be called from {@link Fragment#onViewCreated(View, Bundle)}
   *
   * @param view The inflated view
   * @param savedInstanceState the bundle with the viewstate
   */
  void onViewCreated(View view, @Nullable Bundle savedInstanceState);

  /**
   * Must be called from {@link Fragment#onDestroyView()}
   */
  void onDestroyView();

  /**
   * Must be called from {@link Fragment#onPause()}
   */
  void onPause();

  /**
   * Must be called from {@link Fragment#onResume()}
   */
  void onResume();

  /**
   * Must be called from {@link Fragment#onStart()}
   */
  void onStart();

  /**
   * Must be called from {@link Fragment#onStop()}
   */
  void onStop();

  /**
   * Must be called from {@link Fragment#onActivityCreated(Bundle)}
   *
   * @param savedInstanceState The saved bundle
   */
  void onActivityCreated(Bundle savedInstanceState);

  /**
   * Must be called from {@link Fragment#onAttach(Activity)}
   *
   * @param activity The activity the fragment is attached to
   */
  @Deprecated
  void onAttach(Activity activity);

  /**
   * Must be called from {@link Fragment#onAttach(Context)}
   *
   * @param context The context the fragment is attached to
   */
  void onAttach(Context context);

  /**
   * Must be called from {@link Fragment#onDetach()}
   */
  void onDetach();

  /**
   * Must be called from {@link Fragment#onSaveInstanceState(Bundle)}
   */
  void onSaveInstanceState(Bundle outState);
}
