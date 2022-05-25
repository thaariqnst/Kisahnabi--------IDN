package com.thaariq.kisahnabi.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.thaariq.kisahnabi.data.KisahResponse
import com.thaariq.kisahnabi.databinding.ActivityMainBinding
import com.thaariq.kisahnabi.ui.detail.DetailActivity
import com.thaariq.kisahnabi.utils.OnItemClickCallback

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding as ActivityMainBinding

    private var _viewModel : MainViewModel? = null
    private val viewModel get() = _viewModel as MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getData()

        viewModel.kisahResponse.observe(this){ showData(it) }
        viewModel.isLoading.observe(this){ showLoading(it) }
        viewModel.isError.observe(this){ showError(it) }
    }

    private fun showData(data: List<KisahResponse>?) {
        binding.recyclerMain.apply {
            val madapter = KisahAdapter()
            madapter.setData(data)

            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = madapter
            madapter.setOnItemClickCallback(object : OnItemClickCallback{
                override fun onItemClicked(item: KisahResponse) {
                    startActivity(Intent(applicationContext, DetailActivity::class.java).putExtra(DetailActivity.EXTRA_DATA, item))
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean?) {
        if (isLoading == false){
            binding.progressMain.visibility = View.VISIBLE
            binding.recyclerMain.visibility = View.INVISIBLE
        }else {
            binding.progressMain.visibility = View.INVISIBLE
            binding.recyclerMain.visibility = View.VISIBLE
        }
    }

    private fun showError(error: Throwable?) {
        Log.e("MainActivity", "showError: $error", )
    }
}