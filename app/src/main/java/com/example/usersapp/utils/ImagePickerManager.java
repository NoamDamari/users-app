package com.example.usersapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.usersapp.R;

public class ImagePickerManager {

    /**
     * Creates an ActivityResultLauncher for image picking.
     *
     * @param fragment The Fragment that will register the launcher.
     * @param callback The callback to invoke when an image is picked.
     * @return The ActivityResultLauncher for image picking.
     */
    public static ActivityResultLauncher<String[]> createImagePickerLauncher(
            Fragment fragment,
            ImagePickerCallback callback) {
        return fragment.registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null) {
                        requestUriPermissions((Activity) fragment.requireContext(), uri);
                        callback.onImagePicked(uri);
                    }
                });
    }

    /**
     * Handles the image URI by loading it into an ImageView and taking URI permissions.
     *
     * @param context   The context to use for loading the image and handling permissions.
     * @param uri       The URI of the image to be loaded.
     * @param imageView The ImageView to load the image into.
     */
    public static void handleImageUri(
            Context context,
            Uri uri,
            ImageView imageView) {
        RequestOptions requestOptions = getImageRequestOptions(30);
        loadImageWithGlide(context, uri, requestOptions, imageView);
    }

    /**
     * Returns RequestOptions for loading images with Glide.
     *
     * @param cornerRadius The radius of the image corners.
     * @return The RequestOptions for Glide.
     */
    public static RequestOptions getImageRequestOptions(int cornerRadius) {
        return new RequestOptions()
                .transform(new CenterCrop(), new RoundedCorners(cornerRadius));
    }

    /**
     * Loads an image into an ImageView using Glide.
     *
     * @param context       The context to use for loading the image.
     * @param uri           The URI of the image to load.
     * @param requestOptions The Glide request options.
     * @param imageView     The ImageView to load the image into.
     */
    public static void loadImageWithGlide(
            Context context,
            Uri uri,
            RequestOptions requestOptions,
            ImageView imageView) {
        Glide.with(context)
                .load(uri)
                .apply(requestOptions)
                .placeholder(R.drawable.icon_user)
                .error(R.drawable.icon_user)
                .into(imageView);
    }

    /**
     * Requests URI permissions for the given URI.
     *
     * @param activity The Activity to use for requesting permissions.
     * @param uri      The URI for which to request permissions.
     */
    public static void requestUriPermissions(Activity activity, Uri uri) {
        if (activity != null) {
            activity.getContentResolver().takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            );
        } else {
            throw new IllegalArgumentException("Activity must not be null.");
        }
    }

    /**
     * Callback interface for image picking.
     */
    public interface ImagePickerCallback {
        /**
         * Called when an image is picked.
         *
         * @param uri The URI of the picked image.
         */
        void onImagePicked(Uri uri);
    }
}
