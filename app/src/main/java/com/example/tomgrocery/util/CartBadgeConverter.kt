package com.example.tomgrocery.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.tomgrocery.R

object CartBadgeConverter {
    fun convertLayoutToImage(mContext: Context, count: Int, drawableId: Int): BitmapDrawable {
        val inflater = LayoutInflater.from(mContext)
        val view: View = inflater.inflate(R.layout.badge_icon_layout, null)
        (view.findViewById<View>(R.id.icon_badge) as ImageView).setImageResource(drawableId)

        if (count == 0) {
            val counterTextPanel = view.findViewById<View>(R.id.counterValuePanel)
            counterTextPanel.visibility = View.GONE
        } else {
            val textView = view.findViewById<TextView>(R.id.count)
            textView.text = "" + count
        }

        view.measure(
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        )
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)

        view.isDrawingCacheEnabled = true
        view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        val bitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false

        return BitmapDrawable(mContext.resources, bitmap)
    }
}