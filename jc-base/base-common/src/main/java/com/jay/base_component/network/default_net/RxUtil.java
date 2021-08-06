package com.jay.base_component.network.default_net;

import android.content.Context;

import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * RxUtil
 *
 * @author wangxuejie
 * @version 1.0
 * @date 2019-12-24 17:18
 */
public class RxUtil {

  private static final FlowableTransformer threadFlowableTransformer =
      observable ->
          (observable)
              .subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread());
  private static final ObservableTransformer threadObservableTransformer =
      observable ->
          (observable)
              .subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread());
  private static final FlowableTransformer threadTransformerWithDb =
      observable -> (observable).subscribeOn(Schedulers.newThread());
  private static Context staticContext;

  private RxUtil() {
    throw new UnsupportedOperationException("cannot be instantiated");
  }

  public static void init(Context context) {
    staticContext = context;
  }

  @SuppressWarnings("unchecked")
  public static <T> FlowableTransformer<T, T> applyFlowableTransformer() {
    return (FlowableTransformer<T, T>) threadFlowableTransformer;
  }

  @SuppressWarnings("unchecked")
  public static <T> ObservableTransformer<T, T> applyObservableTransformer() {
    return (ObservableTransformer<T, T>) threadObservableTransformer;
  }

  @SuppressWarnings("unchecked")
  public static <T> FlowableTransformer<T, T> applyThreadTransformerWithDb() {
    return (FlowableTransformer<T, T>) threadTransformerWithDb;
  }
}
