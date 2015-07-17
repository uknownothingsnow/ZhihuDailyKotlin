package com.github.lzyzsd.zhihudailykotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.util.*

/**
 * Created by bruce on 7/17/15.
 */
public class PostListFragment : Fragment() {

    private var recyclerView : RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_post_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        setupRecyclerView(recyclerView!!)
        return view;
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setLayoutManager(LinearLayoutManager(recyclerView.getContext()))
        recyclerView.setAdapter(SimpleStringRecyclerViewAdapter(getActivity(), getRandomSublist(Post.sCheeseStrings, 30)))
    }

    private fun getRandomSublist(array: Array<String>, amount: Int): List<String> {
        val list = ArrayList<String>(amount)
        val random = Random()
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.size())])
        }
        return list
    }

    public class SimpleStringRecyclerViewAdapter(context: Context, private val mValues: List<String>) : RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder>() {

        private val mTypedValue = TypedValue()
        private val mBackground: Int

        public class ViewHolder(public val mView: View) : RecyclerView.ViewHolder(mView) {
            public var mBoundString: String? = null
            public val mImageView: ImageView
            public val mTextView: TextView

            init {
                mImageView = mView.findViewById(R.id.avatar) as ImageView
                mTextView = mView.findViewById(android.R.id.text1) as TextView
            }

            override fun toString(): String {
                return super.toString() + " '" + mTextView.getText()
            }
        }

        public fun getValueAt(position: Int): String {
            return mValues.get(position)
        }

        init {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true)
            mBackground = mTypedValue.resourceId
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false)
            view.setBackgroundResource(mBackground)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mBoundString = mValues.get(position)
            holder.mTextView.setText(mValues.get(position))

            holder.mView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    val context = v.getContext()
                    val intent = Intent(context, javaClass<PostDetailActivity>())
                    intent.putExtra(PostDetailActivity.EXTRA_NAME, holder.mBoundString)

                    context.startActivity(intent)
                }
            })

            Glide.with(holder.mImageView.getContext()).load(Post.getRandomCheeseDrawable()).fitCenter().into(holder.mImageView)
        }

        override fun getItemCount(): Int {
            return mValues.size()
        }
    }
}