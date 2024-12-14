package com.example.movieuniverse.activites

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.movieuniverse.R
import com.example.movieuniverse.adapters.MovieCategoryAdapter
import com.example.movieuniverse.adapters.TrendingMovieAdapter
import com.example.movieuniverse.databinding.ActivityMainBinding
import com.example.movieuniverse.model.Result
import com.example.movieuniverse.utiles.Loader
import com.example.movieuniverse.utiles.showAlert
import com.example.movieuniverse.viewModels.MovieViewModel

class HomeActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var trendingMovieAdapter: TrendingMovieAdapter? = null
    private var movieCategoryAdapter: MovieCategoryAdapter? = null
    private val movieViewModel = MovieViewModel()
    private var lastButtonClicked: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Add all the adapters to respective recycler view
        setAdapters()

        // Set on click listener on each category button
        setOnClickListener()
        showNowPlayingMovies()
        // Fetch trending movies
        fetchTrendingMovies()

        // Observe any error happen
        onError()
    }

    private fun setAdapters() {
        // Trending movie adapter setUp
        trendingMovieAdapter = TrendingMovieAdapter(this)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        binding?.rvTrending?.layoutManager = layoutManager
        binding?.rvTrending?.adapter = trendingMovieAdapter

        // Category movie adapter
        movieCategoryAdapter = MovieCategoryAdapter(this)
        val gridLayoutManager = GridLayoutManager(this, 3)
        binding?.rvMovies?.layoutManager = gridLayoutManager
        binding?.rvMovies?.adapter = movieCategoryAdapter
    }

    private fun fetchTrendingMovies() {
        Loader.showLoader(this, "Movie loading", "Please wait..")
        movieViewModel.getTrendingMovies().observe(this) { movieResponse ->
            val topFiveMovie = movieResponse.results.take(5) as ArrayList<Result>
            trendingMovieAdapter?.addTrendingMovies(topFiveMovie)
            Loader.hideLoader()
        }
    }

    private fun setOnClickListener() {
        binding?.apply {
            btnNowPlaying.setOnClickListener {
                changeButtonColour(btnNowPlaying)
                showNowPlayingMovies()
            }
            btnPopular.setOnClickListener {
                changeButtonColour(btnPopular)
                showPopularMovies()
            }
            btnUpcoming.setOnClickListener {
                changeButtonColour(btnUpcoming)
                showUpcomingMovie()
            }
            btnTopRated.setOnClickListener {
                changeButtonColour(btnTopRated)
                showTopRatedMovies()
            }
        }
    }

    private fun showNowPlayingMovies() {
        val nowPlayingButton = binding?.btnNowPlaying
        if (nowPlayingButton != null) {
            changeButtonColour(nowPlayingButton)
        }
        movieViewModel.fetchNowPlayingMovie().observe(this) { movieResponse ->
            movieCategoryAdapter?.setMovieList(movieResponse.results)
        }
    }

    private fun showTopRatedMovies() {
        Loader.showLoader(this, "Movie loading", "Please wait..")
        movieViewModel.getTopRatedMovies().observe(this) { movieResponse ->
            movieCategoryAdapter?.setMovieList(movieResponse.results)
            Loader.hideLoader()
        }
    }

    private fun showPopularMovies() {
        Loader.showLoader(this, "Movie loading", "Please wait..")
        movieViewModel.getPopularMovies().observe(this) { movieResponse ->
            movieCategoryAdapter?.setMovieList(movieResponse.results)
            Loader.hideLoader()
        }
    }

    private fun showUpcomingMovie() {
        Loader.showLoader(this, "Movie loading", "Please wait..")
        movieViewModel.getUpcomingMovies().observe(this) { movieResponse ->
            movieCategoryAdapter?.setMovieList(movieResponse.results)
            Loader.hideLoader()
        }
    }

    private fun onError() {
        movieViewModel.errorMessage.observe(this) { error ->
            Loader.hideLoader()
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }

    private fun changeButtonColour(btnClicked: Button) {
        lastButtonClicked?.setTextColor(Color.WHITE)
        btnClicked.setTextColor(Color.BLUE)
        lastButtonClicked = btnClicked
    }
}