package com.example.usersapp.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarUtils {

    /**
     * Displays a short Snackbar with a message.
     *
     * @param view    The view to anchor the Snackbar.
     * @param message The message to display in the Snackbar.
     */
    public static void showSnackBar(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Displays a Snackbar with a message and an action button.
     *
     * @param view           The view to anchor the Snackbar.
     * @param message        The message to display in the Snackbar.
     * @param actionText     The text to display on the action button.
     * @param actionListener The listener to handle action button clicks.
     */
    public static void showSnackBarWithAction(View view, String message, String actionText, View.OnClickListener actionListener) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setAction(actionText, actionListener);
        snackbar.show();
    }
}
