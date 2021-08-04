package com.example.cassette.views.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cassette.R
import com.example.cassette.databinding.FragmentPlayerPanelBinding
import com.example.cassette.player.PlayerRemote
import com.example.cassette.utlis.TimeUtils
import com.frolo.waveformseekbar.WaveformSeekBar
import kotlinx.android.synthetic.main.player_remote.*
import kotlin.random.Random


class PlayerPanelFragment: Fragment() {

    lateinit var binding: FragmentPlayerPanelBinding
    var likeState: Boolean = false
    var currentMode: PlayerRemote.playerMode = PlayerRemote.playerMode.NORMAL

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_player_panel, container, false)

        binding = FragmentPlayerPanelBinding.bind(view)

        return view
    }

    override fun onResume() {
        super.onResume()

        binding.playerRemote.seekBar.visibility = View.GONE

        binding.likeIv.setOnClickListener {
            likeState = when (likeState) {
                true -> {
                    binding.likeIv.setImageResource(R.drawable.ic_heart)
                    false
                }
                false -> {
                    binding.likeIv.setImageResource(R.drawable.ic_filled_heart)
                    true
                }
            }
        }

        binding.playerRemote.waveformSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener,
            WaveformSeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                waveform_seek_bar.setProgressInPercentage(0.25F)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onProgressInPercentageChanged(
                seekBar: WaveformSeekBar?,
                percent: Float,
                fromUser: Boolean
            ) {
                if (PlayerRemote.mediaPlayer.isPlaying) {
                    binding.playerRemote.musicMin.text = TimeUtils.milliSecToDuration(
                        (percent * TimeUtils.getDurationOfCurrentMusic().toLong()).toLong()
                    )
                }
            }

            override fun onStartTrackingTouch(seekBar: WaveformSeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: WaveformSeekBar?) {
                Toast.makeText(
                    context,
                    "Tracked: percent=" + waveform_seek_bar.progressPercent,
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
        waveform_seek_bar.setWaveform(createWaveform(), true)


        context?.let { PlayerRemote.setupRemote(it, binding.musicAlbumImage, binding.musicTitleTv) }

        binding.playerRemote.nextBtn.setOnClickListener() {
            PlayerRemote.playNextMusic(currentMode)
        }


        binding.playerRemote.prevBtn.setOnClickListener {
            PlayerRemote.playPrevMusic(currentMode)
        }

        binding.playerRemote.playBtn.setOnClickListener {

            if (!PlayerRemote.mediaPlayer.isPlaying) {
                PlayerRemote.playerProgressbar.resumePlaying()
            } else {
                PlayerRemote.playerProgressbar.pauseMusic()
                play_btn.setImageResource(R.drawable.ic_pause)
            }
            updateUI()
        }

        binding.playerRemote.shuffleBtn.setOnClickListener {
            when (currentMode) {
                PlayerRemote.playerMode.NORMAL -> {
                    currentMode = PlayerRemote.playerMode.SHUFFLE
                }

                PlayerRemote.playerMode.SHUFFLE -> {
                    currentMode = PlayerRemote.playerMode.NORMAL
                }
            }
        }
    }

    private fun createWaveform(): IntArray? {
        val random = Random(System.currentTimeMillis())
        val length: Int = 50 + random.nextInt(50)
        val values = IntArray(length)
        var maxValue = 0
        for (i in 0 until length) {
            val newValue: Int = 5 + random.nextInt(50)
            if (newValue > maxValue) {
                maxValue = newValue
            }
            values[i] = newValue
        }
        return values
    }

    private fun updateUI() {
        binding.playerRemote.musicMax.text =
            TimeUtils.milliSecToDuration(TimeUtils.getDurationOfCurrentMusic().toLong())
    }
}