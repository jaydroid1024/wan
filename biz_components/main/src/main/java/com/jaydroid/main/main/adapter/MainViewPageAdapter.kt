package com.jaydroid.main.main.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jaydroid.main.R
import com.jaydroid.base_component.network.bean.wan.FragmentItem

class MainViewPageAdapter(
    var context: Context,
    fm: FragmentManager,
    var fragmentItems: List<FragmentItem>
) :
    FragmentPagerAdapter(fm) {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItem(position: Int): Fragment {
        return fragmentItems[position].fragment
    }

    override fun getCount(): Int {
        return fragmentItems.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentItems[position].title
    }

    fun setDataSource(list: List<FragmentItem>) {
        fragmentItems = list
        notifyDataSetChanged()
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }

    fun getTabView(position: Int): View {
        val view: View = layoutInflater.inflate(R.layout.layout_main_tab, null, false)
        val textView: TextView = view.findViewById(R.id.tv_tab_title)
        textView.text = getPageTitle(position)
        textView.setTextColor(Color.parseColor("#C2C1C1"))
        textView.textSize = 14f
        return view
    }
}