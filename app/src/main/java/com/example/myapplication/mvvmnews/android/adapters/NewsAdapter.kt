package com.example.myapplication.mvvmnews.android.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.NewsItemPreviewBinding
import com.example.myapplication.mvvmnews.android.model.ArticleViewData

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>() {

    class NewsAdapterViewHolder (newsItemPreviewBinding: NewsItemPreviewBinding) : RecyclerView.ViewHolder(newsItemPreviewBinding.root) {
        val binding: NewsItemPreviewBinding = newsItemPreviewBinding
    }

    var differentCallback = object : DiffUtil.ItemCallback<ArticleViewData>() {
        override fun areItemsTheSame(oldItem: ArticleViewData, newItem: ArticleViewData): Boolean {
           return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(
            oldItem: ArticleViewData,
            newItem: ArticleViewData
        ): Boolean {
           return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differentCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapterViewHolder {
        val view = NewsAdapterViewHolder(
            NewsItemPreviewBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        return view
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsAdapterViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {

            Log.d("AAAAAAAA", "url " + article.urlToImage)
            if (article.urlToImage == null) {
                holder.binding.articleImage.visibility = View.GONE
                Log.d("AAAAAAAA", "visibility = " + holder.binding.articleImage.visibility)
            } else {
                holder.binding.articleImage.visibility = View.VISIBLE
                Glide.with(this).load(article.urlToImage).into(holder.binding.articleImage)
            }

            holder.binding.article = article
        }
        setClickListener(holder.binding, position)
    }

    private fun setClickListener(newsItemPreviewBinding: NewsItemPreviewBinding, position: Int) {
       newsItemPreviewBinding.articleContainer.setOnClickListener {
           Log.d("AAAAAAAA", "tap on item $position")
       }
    }
}