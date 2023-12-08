package com.test.mabale_ciedner_semi_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.mabale_ciedner_semi_final.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.DELETE

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tweetAdapter: TweetAdapter
    private lateinit var tweetApiService: TweetApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tweetsRecyclerView.layoutManager = LinearLayoutManager(this)
        tweetAdapter = TweetAdapter(emptyList())
        binding.tweetsRecyclerView.adapter = tweetAdapter

        tweetApiService = RetrofitClient.instance

        loadTweets()

        binding.btnPostTweet.setOnClickListener {
            val userName = binding.editTextName.text.toString()
            val description = binding.editTextTweet.text.toString()
            val newTweet = Tweet("", userName, description, emptyMap()) // kaning emptyMap para null ra sa timestamp since di man ko kahibalo mo tawag aning timestamp
            createNewTweet(newTweet)
        }

        binding.btnUpdateTweet.setOnClickListener {
            val id = binding.editId.text.toString()
            val userName = binding.editTextName.text.toString()
            val description = binding.editTextTweet.text.toString()

            val updatedTweet = Tweet(id, userName, description, emptyMap()) // update gamit ning mga parameters

            updateTweet(id, updatedTweet)
        }

        binding.btnDeleteTweet.setOnClickListener {
            val id = binding.editId.text.toString()
            val userName = binding.editTextName.text.toString()
            val description = binding.editTextTweet.text.toString()

            val deletedTweet = Tweet(id, userName, description, emptyMap())
            deleteTweet(id, userName, deletedTweet)
        }
    }

    private fun loadTweets() { //kaning dapita gamiton ang lastname na parameter para mo load ang tweet
        tweetApiService.listTweets("mabale").enqueue(object : Callback<List<Tweet>> {
            override fun onResponse(call: Call<List<Tweet>>, response: Response<List<Tweet>>) {
                if (response.isSuccessful) {
                    val tweets = response.body() ?: emptyList()
                    tweetAdapter = TweetAdapter(tweets)
                    binding.tweetsRecyclerView.adapter = tweetAdapter
                } else {
                    showToast("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Tweet>>, t: Throwable) {
                showToast("Network request failed: ${t.message}")
            }
        })
    }

    private fun createNewTweet(newTweet: Tweet) {
        tweetApiService.createTweet(newTweet).enqueue(object : Callback<Tweet> {
            override fun onResponse(call: Call<Tweet>, response: Response<Tweet>) {
                if (response.isSuccessful) {
                    showToast("Tweet successfully posted")
                    loadTweets() // i load ang mga na save na tweet
                } else {
                    showToast("Error posting tweet: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Tweet>, t: Throwable) {
                showToast("Network error: ${t.message}")
            }
        })
    }


    private fun updateTweet(tweetId: String, updatedTweet: Tweet) { // ari i update pero d ma update kay wala koy
        tweetApiService.updateTweet(tweetId, updatedTweet).enqueue(object : Callback<Tweet> {
            override fun onResponse(call: Call<Tweet>, response: Response<Tweet>) {
                if (response.isSuccessful) {
                    showToast("Tweet successfully updated")
                    loadTweets() //ari sad i reload niya ang mga na save na tweet
                } else {
                    showToast("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Tweet>, t: Throwable) {
                showToast("Network request failed: ${t.message}")
            }
        })
    }

    private fun deleteTweet(tweetId: String, userName: String, deletedTweet: Tweet) {
        tweetApiService.deleteTweet(tweetId, userName, deletedTweet).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    showToast("Tweet successfully deleted")
                } else {
                    showToast("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                showToast("Network request failed: ${t.message}")
            }
        })
    }



    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}