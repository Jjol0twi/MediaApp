package com.example.mediaapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import coil.load
import com.example.mediaapp.data.model.video.Item
import com.example.mediaapp.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

// overridePendingTransition(R.anim.anim_left, R.anim.anim_left_exit)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        shareButton()
        return binding.root
    }

    // 공유하기 버튼 구현, 위에 shareButton() 포함
    private fun shareButton(){
        binding.detailBtnShare.setOnClickListener {
            val shareIntent = Intent().apply() {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=$url") // <- 공유할 URL 적기, 파이어베이스 - 다이나믹 링크
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "null"))
        }
    }

    // 정보 전달되는 부분

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        Log.d("jina", "DetailFragment: arguments")
        val item = arguments?.getSerializable("Video_data") as Item?
        item?.let {
            with(binding) {
                detailTxtChannel.text = it.snippet.channelTitle
                detailTxtVideoTitle.text = it.snippet.title
                detailImgThumnail.load(it.snippet.thumbnails.medium.url)
                detailTxtVideoDetail.text = it.snippet.description
            }
            url = it.id
        }

        // 뒤로가기 버튼 구현
        binding.detailBtnBack.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.detail_framelayout, HomeFragment()).commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
