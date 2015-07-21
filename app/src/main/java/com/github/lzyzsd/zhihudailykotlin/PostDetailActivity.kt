package com.github.lzyzsd.zhihudailykotlin

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.bumptech.glide.Glide

import com.github.lzyzsd.zhihudailykotlin.R

/**
 * Created by bruce on 7/17/15.
 */
public class PostDetailActivity : AppCompatActivity() {
    companion object {
        public val EXTRA_NAME: String = "post_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = getIntent()
        val cheeseName = intent.getStringExtra(EXTRA_NAME)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

        val collapsingToolbar = findViewById(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbar.setTitle(cheeseName)

        loadBackdrop()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadBackdrop() {
        val imageView = findViewById(R.id.backdrop) as ImageView
        Glide.with(this).load("").centerCrop().into(imageView)
    }
}