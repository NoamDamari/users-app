package com.example.usersapp.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.usersapp.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AlertDialogUtils {
    /**
     * Creates a confirmation dialog with a customizable message and actions.
     *
     * @param context The context to use for creating the dialog.
     * @param title The title of the dialog.
     * @param message The message to display in the dialog.
     * @param positiveButtonText The text for the positive button.
     * @param negativeButtonText The text for the negative button.
     * @param onPositiveClick The action to perform when the positive button is clicked.
     * @param onNegativeClick The action to perform when the negative button is clicked.
     * @return The created AlertDialog.
     */
    public static AlertDialog createConfirmationDialog(
            Context context,
            String title,
            String message,
            String positiveButtonText,
            String negativeButtonText,
            DialogInterface.OnClickListener onPositiveClick,
            DialogInterface.OnClickListener onNegativeClick) {

        AlertDialog.Builder builder = new MaterialAlertDialogBuilder(context, R.style.AlertDialog);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, onPositiveClick)
                .setNegativeButton(negativeButtonText, onNegativeClick);

        return builder.create();
    }
}
