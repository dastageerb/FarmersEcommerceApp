package com.example.farmersecom.utils.imageUtils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.theartofdev.edmodo.cropper.CropImage


object ImageCropHelper
{

    val pickImageFromGalley = object:ActivityResultContract<Any?, Uri?>()
    {
        override fun createIntent(context: Context, input: Any?): Intent
        {
            return CropImage.activity()
                .setAspectRatio(4,3)
                .getIntent(context)
        } // createIntent closed

        override fun parseResult(resultCode: Int, intent: Intent?): Uri?
        {
            return CropImage.getActivityResult(intent)?.uri
        } // parseResult closed

    } // pickImageFromGalleryClosed



} // ImageCrop Helper