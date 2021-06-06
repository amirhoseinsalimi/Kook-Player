package com.example.cassette.views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cassette.R
import com.example.cassette.adapter.Songs_Adapter
import com.example.cassette.datamodels.Songs_ViewModel
import com.example.cassette.models.Song_Model
import com.example.cassette.utlis.MusicUtils
import kotlinx.android.synthetic.main.fragment_library.*

class Library : Fragment() {

    companion object {
        var arraylist: ArrayList<Song_Model>? = null
    }

    override fun onResume() {
        super.onResume()

        val viewModel = ViewModelProvider(this).get(Songs_ViewModel::class.java)
        viewModel.getMutableLiveData().observe(this, songListUpdateObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_library, container, false)

//        TODO(check if the manifest permissions had been granted)

        arraylist = context?.let { MusicUtils.getListOfMusics(it) }

        return view
    }

    val songListUpdateObserver =
        object : Observer<ArrayList<Any>> {
            override fun onChanged(songArrayList: ArrayList<Any>) {
                val recyclerViewAdapter =
                    activity?.let {
                        Songs_Adapter(
                            it,
                            songArrayList as ArrayList<Song_Model>
                        )
                    }
                songs_rv.layoutManager = LinearLayoutManager(context)
                songs_rv.adapter = recyclerViewAdapter
            }
        }

}