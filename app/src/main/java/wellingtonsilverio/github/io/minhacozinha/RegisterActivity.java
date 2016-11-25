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

public class RegisterActivity extends AppCompatActivity{

    private EditText editTextEmail;
    private EditText editTextPass;
    private Button buttonSigIn;
    private TextView textViewSigUp;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getBaseContext(), HomeActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        buttonSigIn = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        textViewSigUp = (TextView) findViewById(R.id.textViewSignIn);

        buttonSigIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        textViewSigUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
            }
        });
    }

    private void registerUser(){
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

        progressDialog.setMessage("Cadastrando Usuario...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getBaseContext(), HomeActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this, "Cadastro não foi bem sucedido, tente novamente mais tarde", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
