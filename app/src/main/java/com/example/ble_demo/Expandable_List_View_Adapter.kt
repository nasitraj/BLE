package com.example.ble_demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes

class Expandable_List_View_Adapter(context: Context, services : HashMap<String, ArrayList<String>>, servicename : ArrayList<String>) : BaseExpandableListAdapter() {
    var serv = services
    var servname = servicename
    val con = context
    override fun getGroupCount(): Int {
        return servname.size
    }

    override fun getChildrenCount(p0: Int): Int {
          return serv.get(servname[p0])!!.size
    }

    override fun getGroup(p0: Int): String {
        return servname[p0]

    }

    override fun getChild(p0: Int, p1: Int): String {
        return serv.get(servname[p0])!![p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        var convertView = p2
        if(convertView == null){
            val inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = LayoutInflater.from(con).inflate(R.layout.expandable_group, p3, false) as View
        }
        val tvservice = convertView.findViewById<TextView>(R.id.tv_service)

        tvservice.text = servname[p0]
        return convertView
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        var convertView = p3
        if(convertView == null){
            val inflater = con.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.expandable_child, p4,false) as View
        }
        val tvservice = convertView.findViewById<TextView>(R.id.chara)
        tvservice.text = getChild(p0,p1)
        return convertView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return  true
    }

}