package org.tensorflow.lite.examples.objectdetection.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import org.tensorflow.lite.examples.objectdetection.R
import org.tensorflow.lite.examples.objectdetection.databinding.MainFragLayoutBinding

class MainFragment: Fragment() {
    private var mainFragBinding: MainFragLayoutBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainFragBinding = MainFragLayoutBinding.inflate(inflater, container, false)

        return mainFragBinding?.root
    }

    override fun onResume() {
        super.onResume()
        mainFragBinding?.btnPermission?.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(MainFragmentDirections.actionMainToPermission())
        }
        mainFragBinding?.btnCamera?.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(MainFragmentDirections.actionMainToCamera())
        }
        mainFragBinding?.btnGallery?.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            requireActivity().startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainFragBinding = null
    }
}