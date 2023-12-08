package com.test.mabale_ciedner_semi_final

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.mabale_ciedner_semi_final.databinding.ItemTweetBinding

class TweetAdapter (private val tweets: List<Tweet>) : RecyclerView.Adapter<TweetAdapter.TweetViewHolder>() {

        class TweetViewHolder(val binding: ItemTweetBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
            val binding = ItemTweetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TweetViewHolder(binding)
        }

        override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
            val tweet = tweets[position]

            holder.binding.nameTextView.text = tweet.name
            holder.binding.descriptionTextView.text = tweet.description
            holder.binding.timestampTextView.text = "Timestamp: ${tweet.timestamp["seconds"]} seconds"
        }

        override fun getItemCount() = tweets.size
}