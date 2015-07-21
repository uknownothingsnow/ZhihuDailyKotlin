package com.github.lzyzsd.zhihudailykotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
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
import java.util
import java.util.*
import android.os.Message

/**
 * Created by bruce on 7/17/15.
 */
public class PostListFragment : Fragment() {

    private var recyclerView : RecyclerView? = null
    private var storyList = StoryList()
    private val dataCallback = Runnable { ->
        val adapter = (recyclerView?.getAdapter() as SimpleStringRecyclerViewAdapter)
        adapter.stories = storyList.stories!!;
        adapter.notifyDataSetChanged()
    }
    private val handler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_post_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerview) as RecyclerView
        setupRecyclerView(recyclerView!!)
        return view;
    }

    override fun onResume() {
        super.onResume()
        Thread(Runnable {
            storyList = MyRetrofitAdapter.restApi.getPostList();
            handler.post(dataCallback)
        }).start()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setLayoutManager(LinearLayoutManager(recyclerView.getContext()))
        recyclerView.setAdapter(SimpleStringRecyclerViewAdapter(getActivity()))
        recyclerView.addItemDecoration(DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST))
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(dataCallback)
    }

    public class SimpleStringRecyclerViewAdapter(context: Context) : RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder>() {

        var stories: List<Story> = ArrayList<Story>()
        private val typedValue = TypedValue()
        private val background: Int

        public class ViewHolder(public val view: View) : RecyclerView.ViewHolder(view) {
            public var boundString: String? = null
            public val imageView: ImageView
            public val textView: TextView

            init {
                imageView = view.findViewById(R.id.avatar) as ImageView
                textView = view.findViewById(android.R.id.text1) as TextView
            }

            override fun toString(): String {
                return super.toString() + " '" + textView.getText()
            }
        }

        init {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true)
            background = typedValue.resourceId
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false)
            view.setBackgroundResource(background)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val story = stories.get(position)
            holder.boundString = story.title
            holder.textView.setText(story.title)

            holder.view.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    val context = v.getContext()
                    val intent = Intent(context, javaClass<PostDetailActivity>())
                    intent.putExtra(PostDetailActivity.EXTRA_NAME, holder.boundString)

                    context.startActivity(intent)
                }
            })

            Glide.with(holder.imageView.getContext()).load(story.images!![0]!!).fitCenter().into(holder.imageView)
        }

        override fun getItemCount(): Int {
            return stories.size()
        }
    }
}