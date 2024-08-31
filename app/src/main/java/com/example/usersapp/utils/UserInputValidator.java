package com.example.usersapp.utils;

import android.util.Patterns;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

public class UserInputValidator {

    /**
     * Validates all user input fields.
     *
     * @param firstNameET EditText for the first name.
     * @param firstNameTF TextInputLayout for the first name.
     * @param lastNameET  EditText for the last name.
     * @param lastNameTF  TextInputLayout for the last name.
     * @param emailET     EditText for the email.
     * @param emailTF     TextInputLayout for the email.
     * @return true if all fields are valid, false otherwise.
     */
    public static boolean validateUserInputFields(EditText firstNameET, TextInputLayout firstNameTF,
                                         EditText lastNameET, TextInputLayout lastNameTF,
                                         EditText emailET, TextInputLayout emailTF) {
        boolean isFirstNameValid = validateFirstName(firstNameET, firstNameTF);
        boolean isLastNameValid = validateLastName(lastNameET, lastNameTF);
        boolean isEmailValid = validateEmail(emailET, emailTF);

        return isFirstNameValid && isLastNameValid && isEmailValid;
    }

    /**
     * Validates the first name field.
     *
     * @param firstNameET EditText for the first name.
     * @param firstNameTF TextInputLayout for the first name.
     * @return true if the first name is valid, false otherwise.
     */
    private static boolean validateFirstName(EditText firstNameET, TextInputLayout firstNameTF) {
        String firstName = firstNameET.getText().toString().trim();
        if (firstName.isEmpty()) {
            firstNameTF.setError("First name is required");
            return false;
        } else {
            firstNameTF.setError(null);
            return true;
        }
    }

    /**
     * Validates the last name field.
     *
     * @param lastNameET EditText for the last name.
     * @param lastNameTF TextInputLayout for the last name.
     * @return true if the last name is valid, false otherwise.
     */
    private static boolean validateLastName(EditText lastNameET, TextInputLayout lastNameTF) {
        String lastName = lastNameET.getText().toString().trim();
        if (lastName.isEmpty()) {
            lastNameTF.setError("Last name is required");
            return false;
        } else {
            lastNameTF.setError(null);
            return true;
        }
    }

    /**
     * Validates the email field.
     *
     * @param emailET EditText for the email.
     * @param emailTF TextInputLayout for the email.
     * @return true if the email is valid, false otherwise.
     */
    private static boolean validateEmail(EditText emailET, TextInputLayout emailTF) {
        String email = emailET.getText().toString().trim();
        boolean isValidEmail = false;
        if (email.isEmpty()) {
            emailTF.setError("Email is required");
        } else if (!isValidEmail(email)) {
            emailTF.setError("Please enter a valid email address");
        } else {
            emailTF.setError(null);
            isValidEmail = true;
        }
        return isValidEmail;
    }

    /**
     * Checks if the given email is valid.
     *
     * @param email The email address to check.
     * @return true if the email matches the email pattern, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
