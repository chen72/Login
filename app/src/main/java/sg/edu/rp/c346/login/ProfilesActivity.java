package sg.edu.rp.c346.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfilesActivity extends AppCompatActivity implements View.OnClickListener  {

    Button btnLogout;

    TextView tvUserEmail;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        tvUserEmail = (TextView) findViewById(R.id.UserEmail);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            // user has login
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        tvUserEmail.setText(user.getEmail());


        progressDialog = new ProgressDialog(this);
        btnLogout = (Button)findViewById(R.id.btnLogout);


        btnLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == btnLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }


    }
}
