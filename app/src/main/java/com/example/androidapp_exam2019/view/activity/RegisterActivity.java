package com.example.androidapp_exam2019.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidapp_exam2019.R;
import com.example.androidapp_exam2019.dataAccess.CustomerDAO;
import com.example.androidapp_exam2019.dataAccess.CustomerDataAccess;
import com.example.androidapp_exam2019.dataAccess.retrofit.RetrofitSingleton;
import com.example.androidapp_exam2019.dataAccess.IDressApi;
import com.example.androidapp_exam2019.model.Customer;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.registerUsername) public TextInputLayout registerUsername;
    @BindView(R.id.registerPassword) public TextInputLayout registerPassword;
    @BindView(R.id.registerPasswordConfirmation) public TextInputLayout registerPasswordConfirmation;
    @BindView(R.id.registerEmail) public TextInputLayout registerEmail;
    @BindView(R.id.registerFName) public TextInputLayout registerFName;
    @BindView(R.id.registerLName) public TextInputLayout registerLName;
    @BindView(R.id.registerAddress) public TextInputLayout registerAddress;
    @BindView(R.id.registerPhoneNumber) public TextInputLayout registerPhoneNumber;
    @BindView(R.id.registerConfirmButton) public Button registerConfirmButton;
    private static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$^+=!*()@%&]).{8,20}$";
    private CustomerDataAccess dataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        dataAccess = new CustomerDAO(this);

        registerConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validatePassword() | !validateUsername() | !validateFName() | !validateLName() | !validateAddress()) {
                    return;
                }
                Customer customerCreated = new Customer(
                        null,
                        registerFName.getEditText().getText().toString(),
                        registerLName.getEditText().getText().toString(),
                        registerUsername.getEditText().getText().toString(),
                        registerPassword.getEditText().getText().toString(),
                        registerEmail.getEditText().getText().toString(),
                        registerPhoneNumber.getEditText().getText().toString(),
                        registerAddress.getEditText().getText().toString(),
                        0
                );
                dataAccess.registerUser(customerCreated);
            }
        });

    }

    private boolean validateEmail() {
        String emailInput = registerEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            registerEmail.setError(getString(R.string.emptyFieldError));
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            registerEmail.setError(getString(R.string.emailInvalidFormatError));
            return false;
        } else {
            registerEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = registerPassword.getEditText().getText().toString().trim();
        String passwordConfirmationInput = registerPasswordConfirmation.getEditText().getText().toString().trim();
        if (passwordInput.isEmpty()) {
            registerPassword.setError(getString(R.string.emptyFieldError));
            return false;
        } else if (!passwordInput.matches(REGEX_PASSWORD)) {
            registerPassword.setError(getString(R.string.passwordInvalidFormat));
            return false;
        } else if (!passwordInput.equals(passwordConfirmationInput)) {
            registerPassword.setError(getString(R.string.passwordMismatchError));
            registerPasswordConfirmation.setError(getString(R.string.passwordMismatchError));
            return false;
        } else {
            registerPassword.setError(null);
            registerPasswordConfirmation.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String usernameInput = registerUsername.getEditText().getText().toString().trim();
        if (usernameInput.isEmpty()) {
            registerUsername.setError(getString(R.string.emptyFieldError));
            return false;
        } else if (usernameInput.length() < 4) {
            registerUsername.setError(getString(R.string.usernameMinLengthError));
            return false;
        } else {
            registerUsername.setError(null);
            return true;
        }
    }

    private boolean validateFName() {
        String fnameInput = registerFName.getEditText().getText().toString().trim();
        if (fnameInput.isEmpty()) {
            registerFName.setError(getString(R.string.emptyFieldError));
            return false;
        } else {
            registerFName.setError(null);
            return true;
        }
    }

    private boolean validateLName() {
        String lnameInput = registerLName.getEditText().getText().toString().trim();
        if (lnameInput.isEmpty()) {
            registerLName.setError(getString(R.string.emptyFieldError));
            return false;
        } else {
            registerLName.setError(null);
            return true;
        }
    }

    private boolean validateAddress() {
        String addressInput = registerAddress.getEditText().getText().toString().trim();
        if (addressInput.isEmpty()) {
            registerAddress.setError(getString(R.string.emptyFieldError));
            return false;
        } else {
            registerAddress.setError(null);
            return true;
        }
    }

    private boolean validateForm() {
        return !validateEmail() | !validatePassword() | !validateUsername();
    }
}
