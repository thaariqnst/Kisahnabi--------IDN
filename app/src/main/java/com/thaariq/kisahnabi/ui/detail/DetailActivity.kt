package com.thaariq.kisahnabi.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.thaariq.kisahnabi.data.KisahResponse
import com.thaariq.kisahnabi.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding as ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = intent.getParcelableExtra<KisahResponse>(EXTRA_DATA)

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showData(data)
    }

    private fun showData(data : KisahResponse?) {

        binding.apply {
            Glide.with(detailImage.context).load(data?.imageUrl).into(detailImage)
            detailDesk.text = data?.description
            detailNama.text = data?.name
            detailTempat.text = data?.tmp
            val usia = "Usia: " + data?.usia + " Tahun"
            detailUsia.text = usia

        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}