package com.example.cassette.views.dialogs

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cassette.R
import com.example.cassette.adapter.AddSongToPlaylistAdapter
import com.example.cassette.databinding.AddSongToPlaylistBinding
import com.example.cassette.myInterface.PassDataForSelectPlaylists
import com.example.cassette.repositories.appdatabase.entities.PlaylistModel
import com.example.cassette.repositories.appdatabase.entities.SongModel
import com.example.cassette.utlis.ScreenSizeUtils
import kotlinx.android.synthetic.main.add_song_to_playlist.view.*

class AddSongToPlaylistDialog(val array: ArrayList<PlaylistModel>) : DialogFragment() {

    lateinit var binding: AddSongToPlaylistBinding
    var playlistAdapter: AddSongToPlaylistAdapter? = null
    lateinit var dataSend: OnDataSend


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner_bg);

        val view = inflater.inflate(R.layout.add_song_to_playlist, container, false)
        initBinding(view)

        playlistAdapter = activity?.let {
            AddSongToPlaylistAdapter(
                it,
                array
            )
        }

        binding.playlists.layoutManager = LinearLayoutManager(context)
        binding.playlists.adapter = playlistAdapter


        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.addSongToPlaylistLayout.layoutParams.width =
            ScreenSizeUtils.getScreenWidth() * 6 / 10
        binding.addSongToPlaylistLayout.layoutParams.height =
            ScreenSizeUtils.getScreenHeight() * 6 / 10
        binding.addSongToPlaylistLayout.requestLayout()

        binding.addSongToPlaylistLayout.playlists.layoutParams.width =
            binding.addSongToPlaylistLayout.layoutParams.width * 10 / 10
        binding.addSongToPlaylistLayout.playlists.layoutParams.height =
            (binding.addSongToPlaylistLayout.layoutParams.height* 6.5 / 10).toInt()
        binding.addSongToPlaylistLayout.playlists.requestLayout()

        binding.addSongToPlaylistLayout.acceptSelectedPlaylist_btn.layoutParams.width =
            binding.addSongToPlaylistLayout.layoutParams.width * 4 / 10
        binding.addSongToPlaylistLayout.acceptSelectedPlaylist_btn.layoutParams.height =
            (binding.addSongToPlaylistLayout.layoutParams.height* 1.2 / 10).toInt()
        binding.addSongToPlaylistLayout.acceptSelectedPlaylist_btn.requestLayout()

    }

    override fun onResume() {
        super.onResume()

        binding.acceptSelectedPlaylistBtn.setOnClickListener {

            val targetFragment = targetFragment
            val passData: PassDataForSelectPlaylists = targetFragment as PassDataForSelectPlaylists
            passData.passDataToInvokingFragment(AddSongToPlaylistAdapter.choices)

            this.dismiss()
        }
    }

    fun initBinding(view: View) {
        binding = AddSongToPlaylistBinding.bind(view)
    }

    interface OnDataSend {
        fun onSend(context: Activity, songModel: SongModel)
    }

    fun OnDataSend(dataSend: OnDataSend) {
        this.dataSend = dataSend
    }

}



