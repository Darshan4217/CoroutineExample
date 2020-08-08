package com.darshan.coroutineexample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.darshan.coroutineexample.R
import com.darshan.coroutineexample.model.Country
import com.darshan.coroutineexample.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CountryAdapter.Interaction {

    private val TAG = MainActivity::class.simpleName
    private lateinit var viewModel: ListViewModel
    private val countryAdapter: CountryAdapter by lazy { CountryAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = countryAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, Observer { countries ->
            countries?.let {
                recyclerView.visibility = View.VISIBLE
                countryAdapter.swapData(countries)
            }
        })

        viewModel.error.observe(this, Observer { isError ->
            list_error.visibility = if (isError == "") View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                progressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    list_error.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
            }
        })
    }

    override fun countrySelected(model: Country) {
        Log.d(TAG, "Country clicked: $model")
    }
}
