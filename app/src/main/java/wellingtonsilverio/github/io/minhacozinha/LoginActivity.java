package wellingtonsilverio.github.io.minhacozinha;

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

public class LoginActivity extends AppCompatActivity{

    private EditText editTextEmail;
    private EditText editTextPass;
    private Button buttonSigUp;
    private TextView textViewSigIn;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getBaseContext(), HomeActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        buttonSigUp = (Button) findViewById(R.id.buttonLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        textViewSigIn = (TextView) findViewById(R.id.textViewSignUp);

        buttonSigUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        textViewSigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
            }
        });
    }

    private void loginUser(){
        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Por favor, preencha o campo de E-mail", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Por favor, preencha o campo de Senha", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Entrando...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logado com sucesso", Toast.LENGTH_SHORT);
                            finish();
                            startActivity(new Intent(getBaseContext(), HomeActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Login falhou, tente novamente", Toast.LENGTH_SHORT);
                        }
                    }
                });
    }
}
