package sg.edu.rp.c346.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements OnClickListener{

     Button btnRegister;
     EditText etEmail,etAddress,etPhone,etPassword;
     TextView tvSignIn;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            // user has login
            finish();
            startActivity(new Intent(getApplicationContext(),ProfilesActivity.class));
        }

        progressDialog = new ProgressDialog(this);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        etEmail = (EditText) findViewById(R.id.editEmail);
        etPassword = (EditText)findViewById(R.id.editPassword);
        tvSignIn = (TextView) findViewById(R.id.textView);

        btnRegister.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);


    }
    private void registerUser (){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please fill in  your Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please fill in your password",Toast.LENGTH_SHORT).show();
            return;
        }



        progressDialog.setMessage("Registering User..");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    finish();
                    startActivity(new Intent(getApplicationContext(),ProfilesActivity.class));
                }else{
                    Toast.makeText(MainActivity.this,"Registered Fail, Please Try again.",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        });


    }


    @Override
    public void onClick(View v) {
        if(v == btnRegister){
            registerUser();

        }
        if(v == tvSignIn){
            //go to login
            finish();
            startActivity(new Intent(this,LoginActivity.class));

        }

    }
}
