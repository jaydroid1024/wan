package com.jay.detail.web

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jay.base_component.network.bean.wan.detail.WebOptBean
import com.jay.detail.R
import com.jay.detail.web.adapter.WebOptAdapter


class WebDialogFragment : DialogFragment() {

    private lateinit var mContext: Context
    private var dataList: ArrayList<WebOptBean>? = ArrayList()

    companion object {
        fun newInstance(arrayList: ArrayList<WebOptBean>): WebDialogFragment {
            val webDialogFragment = WebDialogFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList("dataList", arrayList)
            webDialogFragment.arguments = bundle
            return webDialogFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataList = arguments?.getParcelableArrayList("dataList")
        setStyle(STYLE_NO_TITLE, R.style.biz_detail_web_dialog_fragment_style) //dialog全屏
    }

    override fun onStart() {
        super.onStart()
        Log.e("debug", "onstart")
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.setBackgroundDrawable(ColorDrawable())
        dialog?.window?.setDimAmount(0f)
        val attribute = dialog?.window?.attributes
        attribute?.height = WindowManager.LayoutParams.WRAP_CONTENT   // 高度为 wrap_content，布局文件中设置无效
        attribute?.gravity = Gravity.TOP
        attribute?.windowAnimations = R.style.biz_detail_web_dialog_fragment_animation
        dialog?.window?.attributes = attribute
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("debug", "onCreateView")
        val rootView: View =
            inflater.inflate(R.layout.biz_detail_dialog_web, container, false)
        val parentView: View = rootView.findViewById(R.id.ll_web_dialog_parent)
        val closeImgView: View = rootView.findViewById(R.id.iv_dialog_web_close)
        closeImgView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                dismissAllowingStateLoss()
            }
        })
        parentView.background = ContextCompat.getDrawable(context!!, R.color.gray_8f)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.rv_web_opt)
        recyclerView.layoutManager = GridLayoutManager(mContext, 4)
        val adapter = WebOptAdapter(R.layout.biz_detail_item_web_opt, dataList)
        adapter.onItemClickListener = object : BaseQuickAdapter.OnItemClickListener {
            override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
                dismissAllowingStateLoss()
                listener?.onItemClick(position)
            }
        }
        recyclerView.adapter = adapter

        return rootView
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return super.onCreateDialog(savedInstanceState)
    }

}