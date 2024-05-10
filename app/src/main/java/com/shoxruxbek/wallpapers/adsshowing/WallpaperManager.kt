package com.shoxruxbek.wallpapers.adsshowing

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.shoxruxbek.wallpapers.MainActivity

class WallpaperManager (private val context: Context,private val activity: MainActivity) {
    private var mInterstitialAd: InterstitialAd? = null
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("WallpaperPrefs", Context.MODE_PRIVATE)

    fun showImage() {
        // Display the image


        // Increment the view count
        incrementViewCount()

        // Check if it's time to display an ad
        if (getViewCount() % 3 == 0) {
            // Show ad
            showAd()
        }
    }

    private fun incrementViewCount() {
        val currentCount = getViewCount()
        val editor = sharedPreferences.edit()
        editor.putInt("viewCount", currentCount + 1)
        editor.apply()

    }

    private fun getViewCount(): Int {
        return sharedPreferences.getInt("viewCount", 0)
    }

    private fun showAd() {
        // Code to display ad
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context, // Use requireContext() to get the Fragment's context
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
                        }
                    }
                    mInterstitialAd?.show(activity) // Show the ad here after it's loaded
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    mInterstitialAd = null
                }
            }
        )


    }
}