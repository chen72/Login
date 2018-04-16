package sg.edu.rp.c346.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSignIn;
    EditText editEmail, editPassword;
    TextView tvSignUp;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);





        progressDialog = new ProgressDialog(this);
        btnSignIn = (Button)findViewById(R.id.btnLogin);
        editEmail = (EditText) findViewById(R.id.loginEmail);
        editPassword = (EditText)findViewById(R.id.loginPassword);
        tvSignUp = (TextView) findViewById(R.id.textViewSignUp);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            // user has login
            finish();
            startActivity(new Intent(getApplicationContext(),ProfilesActivity.class));
        }

        btnSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);

    }
    private void userLogin(){
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please fill in  your Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please fill in your password",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    progressDialog.dismiss();
                    finish();
                    startActivity(new Intent(getApplicationContext(),ProfilesActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this,"Login Fail, Please Try again.",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v == btnSignIn){
            userLogin();

        }
        if(v == tvSignUp){
            //go to Sign up
            finish();
            startActivity(new Intent(this,MainActivity.class));

        }

    }
}
