package com.example.feedjungle.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.feedjungle.R
import com.example.feedjungle.databinding.FragmentSampleBinding


class SampleFragment : Fragment() {
private var _binding: FragmentSampleBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSampleBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    companion object {

    }
}