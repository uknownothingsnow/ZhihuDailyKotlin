package com.github.lzyzsd.zhihudailykotlin

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import java.util.*

/**
 * Created by bruce on 7/1/15.
 */
public class MainActivity : AppCompatActivity() {
    private var mDrawerLayout: DrawerLayout? = null
    private var recyclerView : RecyclerView? = null
    private var storyList = StoryList()
    private val dataCallback = Runnable { ->
        val adapter = (recyclerView?.getAdapter() as StoryListAdapter)
        adapter.stories = storyList.stories!!;
        adapter.notifyDataSetChanged()
    }
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main);
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val ab = getSupportActionBar()
        ab.setHomeAsUpIndicator(R.drawable.ic_menu)
        ab.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        setupRecyclerView(recyclerView!!)

        mDrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        setupDrawerContent(navigationView)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setLayoutManager(LinearLayoutManager(recyclerView.getContext()))
        recyclerView.setAdapter(StoryListAdapter(this))
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST))
    }

    override fun onResume() {
        super.onResume()
        Thread(Runnable {
            storyList = MyRetrofitAdapter.restApi.getPostList();
            handler.post(dataCallback)
        }).start()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(dataCallback)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            android.R.id.home -> {
                mDrawerLayout!!.openDrawer(GravityCompat.START)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                menuItem.setChecked(true)
                mDrawerLayout?.closeDrawers()
                return true
            }
        })
    }
}