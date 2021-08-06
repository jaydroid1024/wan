package com.jay.base_lib.utils.fragment;

/**
 * Created by zhanghao on 2017-08-24 下午3:01.
 * <p>
 */

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.LinkedList;
import java.util.List;

/**
 * Fragment的mUserVisibleHint属性控制器，用于准确的监听Fragment是否对用户可见
 * <br>
 * <br>mUserVisibleHint属性有什么用？
 * <br>* 使用ViewPager时我们可以通过Fragment的getUserVisibleHint()&&isResume()方法来判断用户是否能够看见某个Fragment
 * <br>* 利用这个特性我们可以更精确的统计页面的显示事件和标准化页面初始化流程（真正对用户可见的时候才去请求数据）
 * <br>
 * <br>解决BUG
 * <br>* FragmentUserVisibleController还专门解决了在Fragment或ViewPager嵌套ViewPager时子Fragment
 * 的mUserVisibleHint属性与父Fragment
 * 的mUserVisibleHint属性不同步的问题
 * <br>* 例如外面的Fragment的mUserVisibleHint属性变化时，其包含的ViewPager中的Fragment的mUserVisibleHint
 * 属性并不会随着改变，这是ViewPager的BUG
 * <br>
 * <br>使用方式（假设你的基类Fragment是MyFragment）：
 * <br>1. 在你的MyFragment的构造函数中New一个FragmentUserVisibleController（一定要在构造函数中new）
 * <br>2. 重写Fragment的onActivityCreated()、onResume()、onPause()、setUserVisibleHint(boolean)
 * 方法，分别调用FragmentUserVisibleController的activityCreated()、resume()、pause()、setUserVisibleHint
 * (boolean)方法
 * <br>3. 实现FragmentUserVisibleController.UserVisibleCallback接口并实现以下方法
 * <br>&nbsp&nbsp&nbsp&nbsp* void setWaitingShowToUser(boolean)
 * ：直接调用FragmentUserVisibleController的setWaitingShowToUser(boolean)即可
 * <br>&nbsp&nbsp&nbsp&nbsp* void isWaitingShowToUser()
 * ：直接调用FragmentUserVisibleController的isWaitingShowToUser()即可
 * <br>&nbsp&nbsp&nbsp&nbsp* void callSuperSetUserVisibleHint(boolean)
 * ：调用父Fragment的setUserVisibleHint(boolean)方法即可
 * <br>&nbsp&nbsp&nbsp&nbsp* void onVisibleToUserChanged(boolean, boolean)
 * ：当Fragment对用户可见或不可见的就会回调此方法，你可以在这个方法里记录页面显示日志或初始化页面
 * <br>&nbsp&nbsp&nbsp&nbsp* boolean isVisibleToUser()
 * ：判断当前Fragment是否对用户可见，直接调用FragmentUserVisibleController的isVisibleToUser()即可
 */
@SuppressLint("LongLogTag")
public class FragmentUserVisibleController {
    private static final String TAG = "FragmentUserVisibleController";

    public static boolean DEBUG = false;

    @SuppressWarnings("FieldCanBeLocal")
    private String fragmentName;

    private boolean waitingShowToUser;

    private Fragment fragment;

    private UserVisibleCallback userVisibleCallback;

    private List<OnUserVisibleListener> userVisibleListenerList;

    public FragmentUserVisibleController(
            Fragment fragment,
            UserVisibleCallback userVisibleCallback) {

        this.fragment = fragment;
        this.userVisibleCallback = userVisibleCallback;
        //noinspection ConstantConditions
        this.fragmentName = DEBUG ? fragment.getClass().getSimpleName() : null;
    }

    public void activityCreated() {

        if (DEBUG) {
            Log.d(TAG,
                    fragmentName + ": activityCreated, userVisibleHint=" + fragment.getUserVisibleHint());
        }
        if (fragment.getUserVisibleHint()) {
            Fragment parentFragment = fragment.getParentFragment();
            if (parentFragment != null && !parentFragment.getUserVisibleHint()) {
                if (DEBUG) {
                    Log.d(TAG,
                            fragmentName + ": activityCreated, parent " + parentFragment.getClass().getSimpleName
                                    () + " is hidden, therefore hidden self");
                }
                userVisibleCallback.setWaitingShowToUser(true);
                userVisibleCallback.callSuperSetUserVisibleHint(false);
            }
        }
    }

    public void resume() {

        if (DEBUG) {
            Log.d(TAG, fragmentName + ": resume, userVisibleHint=" + fragment.getUserVisibleHint());
        }
        if (fragment.getUserVisibleHint()) {
            userVisibleCallback.onVisibleToUserChanged(true, true);
            callbackListener(true, true);
            if (DEBUG) {
                Log.i(TAG, fragmentName + ": visibleToUser on resume");
            }
        }
    }

    public void pause() {

        if (DEBUG) {
            Log.d(TAG, fragmentName + ": pause, userVisibleHint=" + fragment.getUserVisibleHint());
        }
        if (fragment.getUserVisibleHint()) {
            userVisibleCallback.onVisibleToUserChanged(false, true);
            callbackListener(false, true);
            if (DEBUG) {
                Log.w(TAG, fragmentName + ": hiddenToUser on pause");
            }
        }
    }

    @SuppressWarnings("RestrictedApi")
    public void setUserVisibleHint(boolean isVisibleToUser) {

        Fragment parentFragment = fragment.getParentFragment();
        if (DEBUG) {
            String parent;
            if (parentFragment != null) {
                parent = "parent " + parentFragment.getClass().getSimpleName() + " " +
                        "userVisibleHint=" + parentFragment
                        .getUserVisibleHint();
            } else {
                parent = "parent is null";
            }
            Log.d(TAG,
                    fragmentName + ": setUserVisibleHint, userVisibleHint=" + isVisibleToUser +
                            ", " + (fragment
                            .isResumed() ? "resume" : "pause") + ", " + parent);
        }

        // 父Fragment还没显示，你着什么急
        if (isVisibleToUser) {
            if (parentFragment != null && !parentFragment.getUserVisibleHint()) {
                if (DEBUG) {
                    Log.d(TAG,
                            fragmentName + ": setUserVisibleHint, parent " + parentFragment.getClass()
                                    .getSimpleName() + " is hidden, therefore hidden self");
                }
                userVisibleCallback.setWaitingShowToUser(true);
                userVisibleCallback.callSuperSetUserVisibleHint(false);
                return;
            }
        }

        if (fragment.isResumed()) {
            userVisibleCallback.onVisibleToUserChanged(isVisibleToUser, false);
            callbackListener(isVisibleToUser, false);
            if (DEBUG) {
                if (isVisibleToUser) {
                    Log.i(TAG, fragmentName + ": visibleToUser on setUserVisibleHint");
                } else {
                    Log.w(TAG, fragmentName + ": hiddenToUser on setUserVisibleHint");
                }
            }
        }

        if (fragment.getActivity() != null) {
            List<Fragment> childFragmentList = fragment.getChildFragmentManager().getFragments();
            if (isVisibleToUser) {
                // 显示待显示的子Fragment
                if (childFragmentList != null && childFragmentList.size() > 0) {
                    for (Fragment childFragment : childFragmentList) {
                        if (childFragment instanceof UserVisibleCallback) {
                            UserVisibleCallback userVisibleCallback =
                                    (UserVisibleCallback) childFragment;
                            if (userVisibleCallback.isWaitingShowToUser()) {
                                if (DEBUG) {
                                    Log.d(TAG,
                                            fragmentName + ": setUserVisibleHint, show child " + childFragment
                                                    .getClass().getSimpleName());
                                }
                                userVisibleCallback.setWaitingShowToUser(false);
                                childFragment.setUserVisibleHint(true);
                            }
                        }
                    }
                }
            } else {
                // 隐藏正在显示的子Fragment
                if (childFragmentList != null && childFragmentList.size() > 0) {
                    for (Fragment childFragment : childFragmentList) {
                        if (childFragment instanceof UserVisibleCallback) {
                            UserVisibleCallback userVisibleCallback =
                                    (UserVisibleCallback) childFragment;
                            if (childFragment.getUserVisibleHint()) {
                                if (DEBUG) {
                                    Log.d(TAG, fragmentName + ": setUserVisibleHint, hidden child" +
                                            " " + childFragment
                                            .getClass().getSimpleName());
                                }
                                userVisibleCallback.setWaitingShowToUser(true);
                                childFragment.setUserVisibleHint(false);
                            }
                        }
                    }
                }
            }
        }
    }

    private void callbackListener(boolean isVisibleToUser, boolean invokeInResumeOrPause) {

        if (userVisibleListenerList != null && userVisibleListenerList.size() > 0) {
            for (OnUserVisibleListener listener : userVisibleListenerList) {
                listener.onVisibleToUserChanged(isVisibleToUser, invokeInResumeOrPause);
            }
        }
    }

    /**
     * 当前Fragment是否对用户可见
     */
    @SuppressWarnings("unused")
    public boolean isVisibleToUser() {

        return fragment.isResumed() && fragment.getUserVisibleHint();
    }

    public boolean isWaitingShowToUser() {

        return waitingShowToUser;
    }

    public void setWaitingShowToUser(boolean waitingShowToUser) {

        this.waitingShowToUser = waitingShowToUser;
    }

    public void addOnUserVisibleListener(OnUserVisibleListener listener) {

        if (listener != null) {
            if (userVisibleListenerList == null) {
                userVisibleListenerList = new LinkedList<OnUserVisibleListener>();
            }
            userVisibleListenerList.add(listener);
        }
    }

    public void removeOnUserVisibleListener(OnUserVisibleListener listener) {

        if (listener != null && userVisibleListenerList != null) {
            userVisibleListenerList.remove(listener);
        }
    }

    public interface UserVisibleCallback {
        boolean isWaitingShowToUser();

        void setWaitingShowToUser(boolean waitingShowToUser);

        boolean isVisibleToUser();

        void callSuperSetUserVisibleHint(boolean isVisibleToUser);

        void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause);
    }

    public interface OnUserVisibleListener {
        void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause);
    }
}
