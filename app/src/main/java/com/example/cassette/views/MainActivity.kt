package com.example.cassette.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.example.cassette.R
import com.example.cassette.adapter.ViewPagerFragmentAdapter
import com.example.cassette.databinding.ActivityMainBinding
import com.example.cassette.player.MediaPlayerAgent
import com.example.cassette.views.Fragments.*
import com.google.android.material.tabs.TabLayoutMediator
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.base.*


class MainActivity : AppCompatActivity(), LifecycleOwner {

    val PERMISSIONS_REQUEST_CODE = 1

    val PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    lateinit var mediaPlayer: MediaPlayerAgent

    lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor", "WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        initBottomSheet()

//        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
//        setSupportActionBar(toolbar)


//        TODO( "implement hideStatusBar() function");


//        val adapter = ViewPagerAdapter(tabList.asList())
//        viewpager_home.adapter = adapter

        val res: Resources = resources
        val tabNames = res.getStringArray(R.array.tabNames)

        val adapter = ViewPagerFragmentAdapter(supportFragmentManager, lifecycle)
        adapter.addFragment(Library())
        adapter.addFragment(RecentlyAdded())
        adapter.addFragment(Playlist())
        adapter.addFragment(Favorite())
        viewpager_home.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager_home.adapter = adapter


        TabLayoutMediator(tabLayout_home, viewpager_home)
        { tab, position ->
            tab.text = tabNames[position]
        }.attach()

        if (!hasPermissions(this, *PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSIONS_REQUEST_CODE)
        }


        // bottomsheet manager
//        val playerPanel = PlayerPanel_bottomSheet()
//        playerPanel.setup(this, bottomSheet, baseContext)

        val playerPanel = binding.slidingLayout
        playerPanel.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener
        {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {

            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {
                when(playerPanel.panelState)
                {
                    SlidingUpPanelLayout.PanelState.EXPANDED -> Toast.makeText(baseContext, "expanded", Toast.LENGTH_SHORT).show()
                    SlidingUpPanelLayout.PanelState.COLLAPSED -> Toast.makeText(baseContext, "collapsed", Toast.LENGTH_SHORT).show()
                }
            }
        })


//        val mHandler = Handler()
//        runOnUiThread(object : Runnable {
//            override fun run() {
//                if (PlayerRemote.mediaPlayer != null) {
//                    val mCurrentPosition = PlayerRemote.mediaPlayer.currentPosition / 1000
//                    seekBar.setProgress(mCurrentPosition)
//                    seekBar.max = PlayerRemote.mediaPlayer.duration / 1000
//                }
//                mHandler.postDelayed(this, 1000)
//            }
//        })


        //spr
//        val mHandler1 = Handler()
//        runOnUiThread(object : Runnable {
//            override fun run() {
//                if (PlayerRemote.mediaPlayer != null) {
//                    val mCurrentPosition = PlayerRemote.mediaPlayer.currentPosition / 1000
//                    if (mCurrentPosition > 0) {
//                        waveform_seek_bar.setProgressInPercentage((mCurrentPosition / 100.0).toFloat())
//                    }
//                }
//                mHandler1.postDelayed(this, 1000)
//            }
//        })





//        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
//            override fun onProgressChanged(
//                seekBar: SeekBar,
//                progress: Int,
//                fromUser: Boolean
//            ) {
//
//                music_min.text =
//                    TimeUtils.milliSecToDuration((seekBar.max - progress).toLong())
////                textView.setText(progress.toString() + "/" + seekBar.max)
////                PlayerRemote.mediaPlayer.seekTo(progress * 1000)
//
//                if (seekBar.max - progress <= 0) {
//                    PlayerRemote.playNextMusic(currentMode)
//                }
//
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar) {}
//            override fun onStopTrackingTouch(seekBar: SeekBar) {}
//
//        })

//
//
        binding.includeBase.includeToolbar.sortIv.setOnClickListener {

            val bottomSheetDialog = Custom_BottomSheetDialogFragment.newInstance()
            bottomSheetDialog?.setStyle(
                R.style.AppBottomSheetDialogTheme,
                R.style.AppBottomSheetDialogTheme
            )
            bottomSheetDialog?.show(supportFragmentManager, "btmsheet")

        }


        ///////////////////////////////////////////////////////
//        tablayout_home.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//                Toast.makeText(
//                    applicationContext,
//                    "tab reselected: ${tab?.text}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                Toast.makeText(
//                    applicationContext,
//                    "tab unselected: ${tab?.text}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                Toast.makeText(applicationContext, "tab selected: ${tab?.text}", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        })

//        val viewModel =
//            ViewModelProvider(this).get(com.example.cassette.datamodels.Songs::class.java)
//        viewModel.getMutableLiveData().observe(this, songListUpdateObserver)
//////////////////////////////////////

        //temp: a button to check if the absolute path is correct
//        button.setOnClickListener {
//
//            File(FilePathUtlis.MUSIC_CANONICAL_PATH).walk().forEach {
//                if (it.isDirectory) {
//                    Toast.makeText(applicationContext, FileUtils.listfiles(it).toString(), Toast.LENGTH_SHORT).show()
//                }
//                if (it.isFile) {
//                    Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
//                }
////                Toast.makeText(applicationContext, FileUtils.listfiles(System.getProperty(FilePathUtlis.MUSIC_CANONICAL_PATH)), Toast.LENGTH_SHORT).show()
//            }
//        }
    }


    private fun hasPermissions(context: Context, vararg permissions: String): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(this, "permissions granted", Toast.LENGTH_SHORT).show()

//                    Library.notifyDataSetChanges()

                } else {
                    Toast.makeText(this, "no permissions granted", Toast.LENGTH_SHORT).show()
                    finish()
                }
                return
            }
        }
    }


    private fun initBottomSheet() {
        val fragment = PlayerPanelFragment()
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.add(binding.bottomSheetContainer.id, fragment, "bottom sheet container").commit()
    }
}