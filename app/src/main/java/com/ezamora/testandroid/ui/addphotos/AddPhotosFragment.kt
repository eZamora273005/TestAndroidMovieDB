package com.ezamora.testandroid.ui.addphotos

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.ezamora.testandroid.databinding.FragmentAddPhotosBinding
import java.io.File
import android.Manifest
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ezamora.testandroid.R
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.util.UUID

class AddPhotosFragment : Fragment() {
    private var _binding: FragmentAddPhotosBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null

    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private val ZERO_VALUE = 0

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            manageImageView()

            val storageRef = Firebase.storage.reference
            val imageRef = storageRef.child("images/${UUID.randomUUID()}${getString(R.string.extension_jpg)}")
            saveImages(imageRef)
        } else {

        }
    }

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            takePhoto()
        } else {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.error_msg))
                    .setMessage(getString(R.string.alert_message_content_permission_denied))
                    .setPositiveButton(getString(R.string.alert_positive_button)) { p0, p1 ->

                    }.show()
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.message_permission_needed), Toast.LENGTH_LONG).show()
            }
            manageProgressDialog(false)
        }
    }


    private fun saveImages(imageRef: StorageReference) {
        imageUri?.let {
            imageRef.putFile(it)
                .addOnSuccessListener {
                    binding.btnCamara.isEnabled = false
                    manageProgressDialog(false)
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),
                        getString(R.string.error_msg), Toast.LENGTH_LONG).show()
                    manageProgressDialog(false)
                }
        }
    }

    private fun manageImageView() {
        binding.ivImagePreview.setImageURI(imageUri)
        binding.ivDeletePicture.visibility = View.VISIBLE
    }

    private fun manageProgressDialog(isVisible: Boolean) {
        if (isVisible)
            binding.layoutProgress.visibility = View.VISIBLE
        else
            binding.layoutProgress.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        with(binding) {
            btnCamara.setOnClickListener {
                manageProgressDialog(true)
                verifyCameraPermission()
            }

            ivDeletePicture.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.alert_message_title))
                    .setMessage(getString(R.string.alert_message_content))
                    .setPositiveButton(getString(R.string.alert_positive_button)) { p0, p1 ->
                        ivImagePreview.setImageResource(ZERO_VALUE)
                        btnCamara.isEnabled = true
                        ivDeletePicture.visibility = View.GONE
                    }.show()
            }
        }
    }

    private fun takePhoto() {
        val photoFile = File.createTempFile(getString(R.string.img_prefix),
            getString(R.string.extension_jpg), requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES))
        imageUri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", photoFile)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        }

        cameraLauncher.launch(intent)
    }

    private fun verifyCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            takePhoto()
        }
    }
}