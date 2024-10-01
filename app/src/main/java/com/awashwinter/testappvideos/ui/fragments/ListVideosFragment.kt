package com.awashwinter.testappvideos.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.awashwinter.testappvideos.R
import com.awashwinter.testappvideos.databinding.FragmentListVideosBinding
import com.awashwinter.testappvideos.ui.adapters.VideosAdapter
import com.awashwinter.testappvideos.ui.decorations.VerticalMarginItemDecorator
import com.awashwinter.testappvideos.utils.DataLoadingState
import com.awashwinter.testappvideos.utils.TaskResult
import com.awashwinter.testappvideos.viewmodels.ShareViewModel
import com.awashwinter.testappvideos.viewmodels.VideosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListVideosFragment : Fragment() {

    companion object {
        fun newInstance() = ListVideosFragment()
    }

    private val videosViewModel: VideosViewModel by activityViewModels<VideosViewModel>()
    private val shareDataViewModel: ShareViewModel by activityViewModels<ShareViewModel>()
    private lateinit var binding: FragmentListVideosBinding
    private var videosAdapter: VideosAdapter? = VideosAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListVideosBinding.inflate(inflater, container, false)
        videosAdapter?.setOnItemClickListener { videoItem, position ->
            Log.d("ItemClicked", "${videoItem.name} clicked!")
            navigateToPlayerFragment(position)
        }
        return binding.root
    }

    private fun navigateToPlayerFragment(position: Int) {
        shareDataViewModel.setSelectedVideoPosition(position)
        findNavController().navigate(R.id.action_listVideosFragment_to_videoPlayerFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVideosAdapter()
        setupVideoViewModel()
    }

    private fun setupVideosAdapter() {
        binding.rvVideos.adapter = videosAdapter
        binding.rvVideos.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvVideos.addItemDecoration(VerticalMarginItemDecorator(12))
    }

    private fun setupVideoViewModel() {
        videosViewModel.liveDataVideos.observe(viewLifecycleOwner) {
            when (it) {
                is TaskResult.Success -> {

                    it.data?.let { videos ->
                        Log.d("liveDataVideos", "liveDataVideos: ${it.data}")
                        videosAdapter?.updateVideos(videos)
                        shareDataViewModel.setPlaylist(videos)
                        binding.rvVideos.smoothScrollToPosition(0)
                    }
                }

                is TaskResult.Error -> {
                    showError(it.message)
                }
            }
        }

        videosViewModel.liveDataLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(loadingState: DataLoadingState) = with(binding) {
        when(loadingState) {
            DataLoadingState.LocalLoading -> {
                progressLoading.visibility = View.VISIBLE
                tvLoadingStatus.text = getString(R.string.loading_local)
            }
            DataLoadingState.Complete -> {
                progressLoading.visibility = View.GONE
                tvLoadingStatus.visibility = View.GONE
            }

            DataLoadingState.RemoteLoading -> {
                progressLoading.visibility = View.VISIBLE
                tvLoadingStatus.text = getString(R.string.loading_remote)
            }
        }
    }

}