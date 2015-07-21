package com.github.lzyzsd.zhihudailykotlin

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.util.Log;
import android.view.View;
/**
 * Created by bruce on 7/21/15.
 */

public class DividerItemDecoration(context: Context, val orientation: Int) : RecyclerView.ItemDecoration() {

    private val ATTRS = intArrayOf(android.R.attr.listDivider)

    companion object {
        public val HORIZONTAL_LIST: Int = LinearLayoutManager.HORIZONTAL

        public val VERTICAL_LIST: Int = LinearLayoutManager.VERTICAL;
    }

    private var divider: Drawable;

    init {
        val a = context.obtainStyledAttributes(ATTRS);
        divider = a.getDrawable(0);
        a.recycle();
    }

    override fun onDraw(c: Canvas , parent: RecyclerView) {
        Log.v("recyclerview - itemdecoration", "onDraw()");

        if (orientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }


    fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.getPaddingLeft()
        val right = parent.getWidth() - parent.getPaddingRight()

        val childCount = parent.getChildCount()
        for (i in 0..childCount-1) {
            val child = parent.getChildAt(i)
            val params = child.getLayoutParams() as RecyclerView.LayoutParams
            val top = child.getBottom() + params.bottomMargin
            val bottom = top + divider.getIntrinsicHeight()
            divider.setBounds(left, top, right, bottom)
            divider.draw(c);
        }
    }

    fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.getPaddingTop()
        val bottom = parent.getHeight() - parent.getPaddingBottom()

        val childCount = parent.getChildCount()
        for (i in 0..childCount-1) {
            val child = parent.getChildAt(i)
            val params = child.getLayoutParams() as RecyclerView.LayoutParams
            val left = child.getRight() + params.rightMargin;
            val right = left + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        if (orientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, divider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, divider.getIntrinsicWidth(), 0);
        }
    }
}