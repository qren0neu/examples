package org.tensorflow.lite.examples.objectdetection.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import org.tensorflow.lite.examples.objectdetection.R
import org.tensorflow.lite.examples.objectdetection.activities.LoginActivity
import org.tensorflow.lite.examples.objectdetection.databinding.MainFragLayoutBinding
import org.tensorflow.lite.examples.objectdetection.services.UserService

class MainFragment : Fragment() {
    private var mainFragBinding: MainFragLayoutBinding? = null
    private val CODE_PICK_RESULT = 100015;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainFragBinding = MainFragLayoutBinding.inflate(inflater, container, false)

        return mainFragBinding?.root
    }

    override fun onResume() {
        super.onResume()
        if (UserService.checkLogin()) {
            val user = UserService.getUser()
            mainFragBinding!!.tvWelcome.visibility = View.VISIBLE
            mainFragBinding!!.tvWelcome.text = "Welcome! \n" + user?.email
            mainFragBinding!!.btnLogout.visibility = View.VISIBLE
        } else {
            mainFragBinding!!.tvWelcome.visibility = View.GONE
            mainFragBinding!!.tvWelcome.text = ""
            mainFragBinding!!.btnLogout.visibility = View.GONE
        }
        mainFragBinding?.btnPermission?.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(MainFragmentDirections.actionMainToPermission())
        }
        mainFragBinding?.btnCamera?.setOnClickListener {
            if (!UserService.checkLogin()) {
                val intent = Intent()
                intent.setClass(requireContext(), LoginActivity::class.java)
                requireActivity().startActivity(intent)
                return@setOnClickListener
            }
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(MainFragmentDirections.actionMainToCamera())
        }
        mainFragBinding?.btnGallery?.setOnClickListener {
            if (!UserService.checkLogin()) {
                val intent = Intent()
                intent.setClass(requireContext(), LoginActivity::class.java)
                requireActivity().startActivity(intent)
                return@setOnClickListener
            }
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            requireActivity().startActivityForResult(intent, CODE_PICK_RESULT)
        }
        mainFragBinding?.btnLogout?.setOnClickListener {
            UserService.logout()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_PICK_RESULT && resultCode == FragmentActivity.RESULT_OK) {
            // picked up
            val imageUri = data?.data

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainFragBinding = null
    }
}