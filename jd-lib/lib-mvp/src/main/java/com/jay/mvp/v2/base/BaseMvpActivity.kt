package com.jay.mvp.v2.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jay.mvp.v2.contract.IMvpView
import com.jay.mvp.v2.contract.IPresenter
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

/**
 * @author jaydroid
 * @version 1.0
 * @date 3/2/21
 */
abstract class BaseMvpActivity<out P : BaseMvpPresenter<BaseMvpActivity<P>>> : AppCompatActivity(),
    IMvpView<P> {

    final override val presenter: P

    init {
        // 在 V 中实例化 P
        presenter = createPresenterKt()
        this.also {
            // 将 V 绑定到 P
            presenter.attachView(it)
        }
    }

    /**
     * 利用 Kotlin 反射实例化 Presenter
     *
     * @return Presenter
     */
    private fun createPresenterKt(): P {
        // 序列与集合相比具有懒加载，只有在用到时才会去加载，
        // https://kotlinlang.org/docs/sequences.html
        sequence {
            var thisClass: KClass<*> = this@BaseMvpActivity::class
            while (true) {
                //获取这个类的直接父类的列表，按照它们在源代码中列出的顺序排列。
                // yield() 函数主要主要就是把虚拟线程暂停，然后把值抛给调用者，等调用者执行完成再执行下面的代码
                yield(thisClass.supertypes)
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure ?: break
            }
        }.flatMap { it -> // it: List<KType>
            it.flatMap {
                //泛型参数 arguments: 例如，在类型`Outer <A，B> .Inner <C，D>`中，返回的列表是`[C，D，A，B]`
                it.arguments
            }.asSequence()
        }.first {
            //这里协定第一个泛型必须放 Presenter 并且必须实现 IPresenter
            it.type?.jvmErasure?.isSubclassOf(IPresenter::class) ?: false
        }.let {
            //实例化 Presenter
            return it.type!!.jvmErasure.primaryConstructor!!.call() as P
        }
    }


    /**
     * 利用 Java 反射实例化 Presenter
     *
     * @return Presenter
     */
    private fun createPresenter(): P {
        sequence {
            var thisClass: Class<*> = this@BaseMvpActivity.javaClass
            while (true) {
                yield(thisClass.genericSuperclass)
                thisClass = thisClass.superclass ?: break
            }
        }.filter {
            it is ParameterizedType
        }.flatMap {
            (it as ParameterizedType).actualTypeArguments.asSequence()
        }.first {
            it is Class<*> && IPresenter::class.java.isAssignableFrom(it)
        }.let {
            return (it as Class<P>).newInstance()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        onViewStateRestored(savedInstanceState)
        presenter.onViewStateRestored(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {}

}
