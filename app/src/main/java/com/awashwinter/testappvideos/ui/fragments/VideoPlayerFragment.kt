package com.awashwinter.testappvideos.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.RepeatMode
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.Player.STATE_READY
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView.ControllerVisibilityListener
import com.awashwinter.testappvideos.databinding.FragmentVideoPlayerBinding
import com.awashwinter.testappvideos.models.VideoItem
import com.awashwinter.testappvideos.viewmodels.PlayerViewModel
import com.awashwinter.testappvideos.viewmodels.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoPlayerFragment : Fragment() {

    companion object {
        fun newInstance() = VideoPlayerFragment()
    }

    private val shareDataViewModel: ShareViewModel by activityViewModels<ShareViewModel>()
    private val playerViewModel: PlayerViewModel by viewModels<PlayerViewModel>()
    private lateinit var binding: FragmentVideoPlayerBinding
    private var player: ExoPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(UnstableApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPlayer()
        initPLayerControls()
        initPlayList()
    }

    override fun onPause() {
        super.onPause()
        pause()
        playerViewModel.setCurrentDuration(player?.currentPosition)
    }

    override fun onResume() {
        super.onResume()
        play()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    @UnstableApi
    private fun initPlayer() {
        player = ExoPlayer.Builder(requireContext())
            .setPauseAtEndOfMediaItems(true)
            .build()
            .apply {
                addListener(playerListener)
                repeatMode = Player.REPEAT_MODE_ONE
            }
    }

    private fun initPLayerControls() = with(binding) {
        btnNext.setOnClickListener {
            seekPlayNext()
        }

        btnPrev.setOnClickListener {
            seekPlayPrev()
        }

        btnPlayPause.setOnClickListener {
            player?.isPlaying?.let {
                if (it) {
                    pause()
                } else {
                    play()
                }
            }
        }

        playerView.setControllerVisibilityListener(ControllerVisibilityListener {
            setControllerVisibility(it)
        })
    }

    @OptIn(UnstableApi::class)
    private fun initPlayList() {
        shareDataViewModel.liveDataVideoPlaylist.observe(viewLifecycleOwner) { videos ->
            Log.d("playlist", "playlistUpdated")
            setupPlaylist(videos)
        }
    }

    private fun setupPlaylist(videos: List<VideoItem>) {
        player?.clearMediaItems()
        for (videoItem in videos) {
            player?.addMediaItem(MediaItem.fromUri(Uri.parse(videoItem.url)))
        }
        shareDataViewModel.liveDataSelectedVideoPosition.value?.let {
            playerViewModel.liveDataCurrentVideoDuration.value?.let { duration ->
                Log.d("Duration", "Restored duration: $duration   Seek to item: $it")
                player?.seekTo(it,
                    duration
                )
            }
        }
        player?.prepare()
    }

    private fun setControllerVisibility(visibility: Int) = with(binding) {
        centerController.visibility = visibility
    }

    private fun seekPlayPrev() {
        player?.seekToPreviousMediaItem()
        shareDataViewModel.setPrevVideo()
    }

    private fun seekPlayNext() {
        player?.seekToNextMediaItem()
        shareDataViewModel.setNextVideo()
    }

    private fun releasePlayer() {
        player?.apply {
            playWhenReady = false
            release()
        }
        player = null
    }

    private fun pause() {
        player?.playWhenReady = false
    }

    private fun play() {
        player?.playWhenReady = true
    }

    private fun restartPlayer() {
        player?.seekTo(0)
        player?.playWhenReady = true
    }

    private fun animatePlayBtn(isPlaying: Boolean) {
        if(isPlaying) {
            binding.btnPlayPause.setImageResource(androidx.media3.session.R.drawable.media3_icon_pause)
        } else {
            binding.btnPlayPause.setImageResource(androidx.media3.session.R.drawable.media3_icon_play)
        }
    }

    private val playerListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when (playbackState) {
                STATE_ENDED -> restartPlayer()
                STATE_READY -> {
                    binding.playerView.player = player
                    play()
                }
            }
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            animatePlayBtn(isPlaying)
        }

    }
}