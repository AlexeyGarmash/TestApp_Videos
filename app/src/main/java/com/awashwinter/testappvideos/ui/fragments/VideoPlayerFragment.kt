package com.awashwinter.testappvideos.ui.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.fragment.app.activityViewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.Player.STATE_READY
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.awashwinter.testappvideos.databinding.FragmentVideoPlayerBinding
import com.awashwinter.testappvideos.viewmodels.ShareViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoPlayerFragment : Fragment() {

    companion object {
        fun newInstance() = VideoPlayerFragment()
    }

    private val shareDataViewModel: ShareViewModel by activityViewModels<ShareViewModel>()
    private lateinit var binding: FragmentVideoPlayerBinding
    private var player: ExoPlayer? = null
    private val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()


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
        initPlayList()
    }

    @OptIn(UnstableApi::class)
    private fun initPlayList() {
        shareDataViewModel.liveDataVideoPlaylist.observe(viewLifecycleOwner) { videos ->
            for (videoItem in videos) {
                val source = videoItem.url?.let { videoUrl -> getProgressiveMediaSource(videoUrl) }
                if (source != null) {
                    player?.addMediaItem(source.mediaItem)
                }
            }
        }

        shareDataViewModel.liveDataSelectedVideoPosition.observe(viewLifecycleOwner) {
            player?.seekToDefaultPosition(it)
            player?.prepare()
        }
    }

    override fun onPause() {
        super.onPause()
        pause()
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
            }
    }


    @androidx.annotation.OptIn(UnstableApi::class)
    private fun getProgressiveMediaSource(videoUrl: String): MediaSource {
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(videoUrl)))
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
    }
}