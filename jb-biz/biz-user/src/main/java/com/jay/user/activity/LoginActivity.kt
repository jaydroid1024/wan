package com.jay.user.activity

import android.text.SpannableString
import android.text.TextUtils
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.jay.base_component.arouter.ARHelper
import com.jay.base_component.arouter.ARHelper.PathUser.REGISTER_ACTIVITY_PATH
import com.jay.base_component.base.mvp.BaseMVPActivity
import com.jay.base_component.network.bean.wan.user.User
import com.jay.base_component.widget.ClearEditText
import com.jay.base_component.widget.LoginView
import com.jay.base_lib.utils.L
import com.jay.user.R
import com.jay.user.contract.LoginContract
import com.jay.user.presenter.LoginPresenter

@Route(path = ARHelper.PathUser.LOGIN_ACTIVITY_PATH)
class LoginActivity : BaseMVPActivity<LoginContract.View, LoginPresenter>(),
    LoginContract.View, View.OnClickListener {

    private lateinit var usernameEditText: ClearEditText
    private lateinit var passwordEditText: ClearEditText
    private lateinit var loginView: LoginView
    private lateinit var registerTxtView: TextView
    private lateinit var closeImgView: ImageView

    override fun getLayoutResId(): Int {
        return R.layout.biz_user_activity_login
    }

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter()
    }

    override fun initView() {
        usernameEditText = findViewById(R.id.cet_login_username)
        passwordEditText = findViewById(R.id.cet_login_password)
        closeImgView = findViewById(R.id.iv_login_close)
        loginView = findViewById(R.id.lv_login)
        registerTxtView = findViewById(R.id.tv_user_register)

        //todo debug
        usernameEditText.setText("mvvm")
        passwordEditText.setText("123456")
    }

    override fun initData() {
        super.initData()
        val spannableString = SpannableString(registerTxtView.text.toString().trim())
        spannableString.setSpan(
            UnderlineSpan(),
            0,
            registerTxtView.text.toString().trim().length,
            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
        )
        registerTxtView.text = spannableString
        registerTxtView.setOnClickListener(this)
        loginView.setOnClickListener(this)
        closeImgView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.lv_login -> {
                login()
            }
            R.id.tv_user_register -> {
                ARHelper.routerTo(REGISTER_ACTIVITY_PATH)
            }
            R.id.iv_login_close -> {
                finish()
            }
        }
    }

    private fun login() {
        val username = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(mContext, "请输入用户名", Toast.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "请输入密码", Toast.LENGTH_LONG).show()
            return
        }
        presenter.login(username, password)
    }


    override fun showLoading() {
        loginView.setState(LoginView.STATE_LOADING)
    }

    override fun dismissLoading() {
        loginView.setState(LoginView.STATE_FAILED)
    }

    override fun onLoginResult(username: String, user: User?) {

        ARHelper.routerTo(
            ARHelper.PathMain.MAIN_ACTIVITY_PATH,
            this,
            object : NavigationCallback {
                override fun onLost(postcard: Postcard?) {
                    L.d(TAG, "onLost:${postcard?.path}")
                }

                override fun onFound(postcard: Postcard?) {
                    L.d(TAG, "onFound:${postcard?.path}")
                    finish()
                }

                override fun onInterrupt(postcard: Postcard?) {
                    L.d(TAG, "onInterrupt:${postcard?.path}")
                }

                override fun onArrival(postcard: Postcard?) {
                    L.d(TAG, "onArrival:${postcard?.path}")
                }

            })
    }


    override fun onDestroy() {
        super.onDestroy()
        loginView.release()
    }

    companion object {

        const val TAG = "Login"
    }
}
