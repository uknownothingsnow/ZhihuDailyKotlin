package com.github.lzyzsd.zhihudailykotlin

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import com.bumptech.glide.Glide

import com.github.lzyzsd.zhihudailykotlin.R

/**
 * Created by bruce on 7/17/15.
 */
public class StoryDetailActivity : AppCompatActivity() {
    companion object {
        public val EXTRA_NAME: String = "extra_story"
    }

    private var webview: WebView? = null
    private var story: Story? = null

    private var storyDetail: StoryDetail? = null
    private val dataCallback = Runnable { ->
        showStoryDetail(storyDetail!!)
    }
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_detail)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        webview = findViewById(R.id.wv_story_content) as WebView

        val webSettings: WebSettings = webview!!.getSettings()
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8")

        val intent = getIntent()
        story = intent.getParcelableExtra<Story>(EXTRA_NAME)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setTitle(story?.title)
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

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData() {
        Thread(Runnable {
            storyDetail = MyRetrofitAdapter.restApi.getStoryDetail(story?.id!!)
            handler.post(dataCallback)
        }).start()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(dataCallback)
    }

    private fun showStoryDetail(storyDetail: StoryDetail) {
        val newContent: String = buildHtml(storyDetail)
        webview?.loadData(newContent, "text/html; charset=UTF-8", null);
    }

    fun buildHtml(storyDetail: StoryDetail): String {
        return "<html><head>" + buildCssAndJs(storyDetail) + "</head><body>" + insertHeaderImageToBody(storyDetail) + "</body></html>"
    }

    fun getCustomerCss(): String {
        return """
            <style type="text/css">
                .headline .img-wrap {
                    position: relative;
                    max-height: 375px;
                    overflow: hidden;
                }

                .headline .headline-title {
                    margin: 20px 0;
                    bottom: 10px;
                    z-index: 1;
                    position: absolute;
                    color: white;
                    text-shadow: 0px 1px 2px rgba(0,0,0,0.3);
                }

                .headline .img-source {
                    position: absolute;
                    bottom: 10px;
                    z-index: 1;
                    font-size: 12px;
                    color: rgba(255,255,255,.6);
                    right: 40px;
                    text-shadow: 0px 1px 2px rgba(0,0,0,.3);
                }
            </style>
        """
    }

    fun insertHeaderImageToBody(newsDetail: StoryDetail): String {
        val image = """
            <div class="headline">
                <div class="img-wrap">
                    <h1 class="headline-title">${newsDetail.title}</h1>
                    <span class="img-source">图片：Lily / CC BY</span>
                    <img src="${newsDetail.image}" alt="">
                </div>
            </div>
        """
        val body = newsDetail.body
        if (body.isNullOrEmpty())
            return ""
        else
            return body?.replaceFirst("<div class=\"img-place-holder\"></div>", image)!!
    }

    fun buildCssAndJs(newsDetail: StoryDetail): String {
        val sb = StringBuilder()
        for (css in newsDetail.css) {
            sb.append("<link rel=\"stylesheet\" href=\"").append(css).append("\"></link>");
        }

        for (js in newsDetail.js) {
            sb.append("<script src=\"").append(js).append("\"></script>");
        }

        sb.append(getCustomerCss())
        return sb.toString();
    }
}