package com.example.movieuniverse.activites

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieuniverse.R
import com.example.movieuniverse.adapters.CastAdapter
import com.example.movieuniverse.databinding.ActivityMovieDetailsBinding
import com.example.movieuniverse.utiles.Constants
import com.example.movieuniverse.utiles.Loader
import com.example.movieuniverse.utiles.loadImage
import com.example.movieuniverse.utiles.showAlert
import com.example.movieuniverse.viewModels.MovieDetailsViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MovieDetailsActivity : AppCompatActivity() {
    private var binding: ActivityMovieDetailsBinding? = null
    private val movieDetailsViewModel = MovieDetailsViewModel()
    private var castAdapter: CastAdapter? = null
    private var movieOverview: String? = null
    private var lastTextClicked: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        title = "Details"
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Add the cast adapter to the activity
        addCastViewAdapter()
        showMovieDetails()

        binding?.aboutMovieTitle?.setOnClickListener {
            binding?.apply {
                aboutMovieDescription.visibility = View.VISIBLE
                castRecyclerViewFrame.visibility = View.GONE
            }
            binding?.aboutMovieDescription?.text = movieOverview
        }

        binding?.apply {

            aboutMovieTitle.setOnClickListener {
                changeTextColour(aboutMovieTitle)
                aboutMovieDescription.visibility = View.VISIBLE
                castRecyclerViewFrame.visibility = View.GONE
                binding?.aboutMovieDescription?.text = movieOverview
            }

            castTitle.setOnClickListener {
                changeTextColour(castTitle)
                showCastMembers()
            }
            playVideo.setOnClickListener {
                startVideoPlayer()
            }
        }
        // Observe any error happen
        onError()
    }

    private fun startVideoPlayer() {
        val intent = Intent(this@MovieDetailsActivity, VideoPlayerActivity::class.java)
        val movieId = this.intent.getStringExtra(Constants.MOVIE_ID)
        intent.putExtra(Constants.MOVIE_ID, movieId)
        this@MovieDetailsActivity.startActivity(intent)
    }

    private fun showMovieDetails() {
        val movieId = intent.getStringExtra(Constants.MOVIE_ID) ?: return
        Loader.showLoader(this, "Movie Details Loading..", "Please wait..")
        movieDetailsViewModel.getMovieDetails(movieId).observe(this) { movieDetails ->
            binding?.apply {
                movieTitle.text = movieDetails.title

                val smallImageUrl = Constants.IMAGE_BASE_URL + movieDetails.poster_path
                val bannerImageUrl = Constants.IMAGE_BASE_URL + movieDetails.backdrop_path
                bannerImage.loadImage(bannerImageUrl)
                smallImage.loadImage(smallImageUrl)

                val date = movieDetails.release_date
                var genre = ""
                movieDetails.genres.forEach {
                    genre = "$genre${it.name} - "
                }

                val duration = movieDetails.runtime
                val hours = duration / 60
                val remainingMinutes = duration % 60
                val totalTime = "$hours hours $remainingMinutes minutes"
                val details = "$date - $genre $totalTime"
                tvMovieDetails.text = details
                aboutMovieDescription.visibility = View.VISIBLE
                movieOverview = movieDetails.overview
                aboutMovieDescription.text = movieOverview

                tvYear.text = getYearFromDate(movieDetails.release_date)
                tvMinutes.text = "${movieDetails.runtime} min"
            }
            Loader.hideLoader()
        }
    }

    private fun addCastViewAdapter() {
        val layoutManager = GridLayoutManager(this, 2)
        binding?.castRecyclerView?.layoutManager = layoutManager
        castAdapter = CastAdapter()
        binding?.castRecyclerView?.adapter = castAdapter
    }

    private fun showCastMembers() {
        val movieId = intent.getStringExtra(Constants.MOVIE_ID) ?: return
        Loader.showLoader(this, "Loading", "Please wait..")
        binding?.apply {
            aboutMovieDescription.visibility = View.GONE
            castRecyclerViewFrame.visibility = View.VISIBLE
        }
        movieDetailsViewModel.getCastCredits(movieId).observe(this) { casts ->
            castAdapter?.setCastList(casts.cast)
            Loader.hideLoader()
        }
    }

    private fun getYearFromDate(dateString: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())  // Define the date format
        val date = format.parse(dateString)  // Parse the date string to a Date object

        val calendar = Calendar.getInstance()
        calendar.time = date  // Set the calendar time to the parsed date

        return calendar.get(Calendar.YEAR).toString()  // Extract and return the year as a string
    }

    private fun onError() {
        movieDetailsViewModel.errorMessage.observe(this) { error ->
            Loader.hideLoader()
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }

    private fun changeTextColour(textClicked: TextView) {
        lastTextClicked?.setTextColor(Color.WHITE)
        textClicked.setTextColor(Color.BLUE)
        lastTextClicked = textClicked
    }
}