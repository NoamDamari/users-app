package com.example.usersapp;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class UserInputValidator {
    public static boolean validateUserInputFields(EditText firstNameET, TextInputLayout firstNameTF,
                                         EditText lastNameET, TextInputLayout lastNameTF,
                                         EditText emailET, TextInputLayout emailTF) {
        boolean isFirstNameValid = validateFirstName(firstNameET, firstNameTF);
        boolean isLastNameValid = validateLastName(lastNameET, lastNameTF);
        boolean isEmailValid = validateEmail(emailET, emailTF);

        return isFirstNameValid && isLastNameValid && isEmailValid;
    }

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

    private static boolean validateEmail(EditText emailET, TextInputLayout emailTF) {
        String email = emailET.getText().toString().trim();
        if (email.isEmpty()) {
            emailTF.setError("Email is required");
            return false;
        } else {
            emailTF.setError(null);
            return true;
        }
    }
}
