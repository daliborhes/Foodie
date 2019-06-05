package com.daliborhes.foodie.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daliborhes.foodie.Model.User;
import com.daliborhes.foodie.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    @BindView(R.id.signup_username_et)
    EditText usernameEt;
    @BindView(R.id.signup_phone_et)
    EditText phoneEt;
    @BindView(R.id.signup_email_et)
    EditText emailEt;
    @BindView(R.id.signup_password_et)
    EditText passwordEt;
    @BindView(R.id.signup_password_confirm_et)
    EditText confirmPasswordEt;
    @BindView(R.id.signup_activity_btn)
    Button signupBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        ButterKnife.bind(this);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = usernameEt.getText().toString();
                String phone = phoneEt.getText().toString();
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();
                signupNewUser(name, phone, email, password);
            }
        });

    }

    private void signupNewUser(final String name, final String phone, final String email, final String password) {
        if (registrationIsValid()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                final FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            String userId = user.getUid();
                                            Log.d(TAG, "USERID is: " + userId);
                                            writeUserToDatabase(userId, name, phone, email, password);
                                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                            finish();
                                            Toast.makeText(SignupActivity.this, "Please verify your email address!", Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(SignupActivity.this, "Verification email was not sent!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignupActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }

    }

    private boolean registrationIsValid() {
        boolean ret = true;
        String phone = phoneEt.getText().toString();
        String username = usernameEt.getText().toString();
        String email = emailEt.getText().toString();
        String password = passwordEt.getEditableText().toString();
        String confirmPassword = confirmPasswordEt.getText().toString();
        View focusView = null;

        if (TextUtils.isEmpty(username)) {
            usernameEt.setError(getString(R.string.error_field_required));
            focusView = usernameEt;
            ret = false;
        }
        if (TextUtils.isEmpty(phone)) {
            phoneEt.setError(getString(R.string.error_field_required));
            focusView = phoneEt;
            ret = false;
        }
        if (TextUtils.isEmpty(email)) {
            emailEt.setError(getString(R.string.error_field_required));
            focusView = this.emailEt;
            ret = false;
        } else if (!email.contains("@") || !email.contains(".")) {
            emailEt.setError("Email is invalid");
            focusView = this.emailEt;
            ret = false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEt.setError(getString(R.string.error_field_required));
            focusView = passwordEt;
            ret = false;
        } else if (password.length() < 6) {
            passwordEt.setError("Password must have at least 6 characters.");
            focusView = passwordEt;
            ret = false;
        }
        if (!confirmPassword.equals(password)) {
            confirmPasswordEt.setError("Passwords does not match.");
            focusView = confirmPasswordEt;
            ret = false;
        }

        if (focusView != null)
            focusView.requestFocus();
        return ret;
    }

    private void writeUserToDatabase(final String userId, String userName, String userPhone, String userEmail, String userPassword) {
        final User newUser = new User(userName, userPhone, userEmail, userPassword);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.child("User").child(userId).setValue(newUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignupActivity.this, EnteringActivity.class));
        finish();
    }
}
