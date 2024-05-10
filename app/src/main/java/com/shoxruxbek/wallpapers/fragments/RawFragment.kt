package com.shoxruxbek.wallpapers.fragments

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.shoxruxbek.wallpapers.MainActivity
import com.shoxruxbek.wallpapers.adsshowing.WallpaperManager
import com.shoxruxbek.wallpapers.databinding.FragmentRawBinding
import android.Manifest
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.google.android.gms.ads.AdListener
import com.shoxruxbek.wallpapers.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RawFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RawFragment : Fragment() {
    lateinit var binding: FragmentRawBinding
    private var mInterstitialAd: InterstitialAd? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRawBinding.inflate(inflater, container, false)

        val largeImageUrl = arguments?.getString("wall")
        Toast.makeText(binding.root.context, "Please wait, image is being loaded", Toast.LENGTH_SHORT).show()
        Glide.with(this)
            .load(largeImageUrl)
            .into(binding.profile)

        count()
        download()



        return binding.root
    }

    private fun download() {

        binding.download.setOnClickListener {

            showAdDialog()





        }
    }

    private fun showAdDialog() {
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setTitle("Watch ad to download?")
            .setMessage("After watching, image will be downloaded automatically")
            .setPositiveButton("Watch Ad") { dialog, which ->
                // Load and display ad (not implemented here)
                // For simplicity, proceed with download after dialog is dismissed
                dialog.dismiss()
                watchAd()
                Toast.makeText(binding.root.context, "Ad showing", Toast.LENGTH_SHORT).show()

            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    private fun watchAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            requireContext(), // Use requireContext() to get the Fragment's context
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            // You may want to load a new ad here if desired
                            mInterstitialAd = null
                            startDownloading()
                        }
                    }
                    mInterstitialAd?.show(activity as MainActivity) // Show the ad here after it's loaded
                }


                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    mInterstitialAd = null
                }


            }
        )











    }

    private fun startDownloading() {
        val largeImageUrl = arguments?.getString("wall")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // For Android 13 or higher, use MediaStore API
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                downloadImage(largeImageUrl)
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION
                )
            }
        } else {
            // For older versions of Android, just download the image
            downloadImage(largeImageUrl)
        }
    }

    private fun downloadImage(imageUrl: String?) {
        imageUrl?.let {
            Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        saveImage(resource)
                    }
                })
        }
    }

    private fun saveImage(bitmap: Bitmap) {
        val resolver = requireContext().contentResolver
        val fileName = "Image_${System.currentTimeMillis()}.jpg"

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }

        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        imageUri?.let { uri ->
            try {
                val outputStream = resolver.openOutputStream(uri)
                outputStream?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                    Toast.makeText(requireContext(), "Image saved to gallery", Toast.LENGTH_SHORT).show()
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.clear()
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(uri, contentValues, null, null)
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Failed to save image", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val largeImageUrl = arguments?.getString("wall")
                downloadImage(largeImageUrl)
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 101

        fun newInstance(largeImageUrl: String): RawFragment {
            val fragment = RawFragment()
            val bundle = Bundle().apply {
                putString("wall", largeImageUrl)
            }
            fragment.arguments = bundle
            return fragment
        }
    }





    private fun count() {
        val wallpaperManager = WallpaperManager(binding.root.context,activity as MainActivity)
        wallpaperManager.showImage()





    }




    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        (activity as MainActivity).showBottomNavigation()
        super.onDetach()

    }


}