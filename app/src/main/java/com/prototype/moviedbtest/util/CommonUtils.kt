package com.prototype.moviedbtest.util

import android.app.ProgressDialog
import android.content.Context
import android.content.res.Resources
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Nanang Wahyu H on 19/06/20.
 */
object CommonUtils {

    fun showLoadingDialog(context: Context?): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.let {
            it.setMessage("Please Wait")
            it.show()
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            return it
        }
    }

    fun formatStringtoDate(stringDate: String, formatDate: String): Date? {
        val format = SimpleDateFormat(formatDate)
        try {
            return format.parse(stringDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }

    fun customFormatDate(time: Long, format: String?): String {
        var defaultFormat = "yyyy-MM-dd"

        if (format != null) {
            defaultFormat = format
        }

        val simpleDateFormat = SimpleDateFormat(defaultFormat, Locale.ENGLISH)
        return simpleDateFormat.format(time)
    }

    fun loadImage(context:Context, url:String, imageView: ImageView, radius: Int){
        Glide.with(context)
            .load(url)
            .apply(
                RequestOptions().transform(
                    CenterCrop(),
                    RoundedCorners(radius)
                )
            ).into(imageView)
    }

    fun convertDpToPixelInt(dp: Int): Int {
        return convertDpToPixel(dp.toFloat()).toInt()
    }

    fun convertDpToPixel(dp: Float): Float {
        val metrics = Resources.getSystem().displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return Math.round(px).toFloat()
    }
}