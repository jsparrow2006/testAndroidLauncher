package com.example.dashinglauncher
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AppAdapter(private val context: Context, private val apps: List<AppInfo>) : BaseAdapter() {

    override fun getCount(): Int {
        return apps.size
    }

    override fun getItem(position: Int): Any {
        return apps[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.app_item, parent, false)

        val app = apps[position]
        val iconView: ImageView = view.findViewById(R.id.app_icon)
        val nameView: TextView = view.findViewById(R.id.app_name)

        iconView.setImageDrawable(app.icon)
        nameView.text = app.name

        return view
    }
}
