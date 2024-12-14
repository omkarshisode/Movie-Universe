package com.example.movieuniverse.activites

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movieuniverse.R
import com.example.movieuniverse.databinding.ActivityVideoPlayerBinding
import com.example.movieuniverse.utiles.Constants
import com.example.movieuniverse.utiles.Loader
import com.example.movieuniverse.utiles.showAlert
import com.example.movieuniverse.viewModels.VideoPlayerViewModel

class VideoPlayerActivity : AppCompatActivity() {
    private var binding: ActivityVideoPlayerBinding? = null
    private val videoPlayerViewModel = VideoPlayerViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fetchVideoId()

        // Observe any error happen
        onError()
    }

    private fun fetchVideoId() {
        val movieId = intent.getStringExtra(Constants.MOVIE_ID) ?: return
        videoPlayerViewModel.getMovieVideo(movieId).observe(this) { video ->
            val videoId = video.results.first().key
            playVideo(videoId)
        }
    }

    private fun playVideo(videoId: String) {
        val youtubeUrl = "https://www.youtube.com/watch?v=$videoId"
        val webView: WebView? = binding?.webview
        webView?.settings?.javaScriptEnabled = true
        webView?.webViewClient = WebViewClient()
        webView?.loadUrl(youtubeUrl)
    }

    private fun onError() {
        videoPlayerViewModel.errorMessage.observe(this) { error ->
            Loader.hideLoader()
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }
}