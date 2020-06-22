package com.prototype.moviedbtest.ui.base

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import com.prototype.moviedbtest.ui.main.MainActivity
import com.prototype.moviedbtest.ui.movie.MovieDetailActivity
import com.prototype.moviedbtest.util.CommonUtils
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity(), BaseView, BaseFragment.CallBack {

    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        performDI()
        super.onCreate(savedInstanceState)
    }

    override fun showLoading() {
        hideLoading()
        progressDialog = CommonUtils.showLoadingDialog(this)
    }

    override fun hideLoading() {
        progressDialog?.let { if (it.isShowing) it.cancel() }
    }

    private fun performDI() = AndroidInjection.inject(this)

    fun gotoMovieDetail(movieId: Long){
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("movie_id", movieId)
        startActivity(intent)
    }

    fun gotoFavorite(){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("is_favorite", true)
        startActivity(intent)
    }

    fun getAppName(): String{
        var appName: String
        val applicationInfo = this.getApplicationInfo()
        val stringId = applicationInfo.labelRes
        if (stringId == 0) {appName = applicationInfo.nonLocalizedLabel.toString()}
        else { appName = this.getString(stringId)}
        return appName
    }
}