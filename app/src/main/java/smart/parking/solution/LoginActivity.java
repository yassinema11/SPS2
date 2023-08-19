package smart.parking.solution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;


public class LoginActivity extends AppCompatActivity
{
    EditText EmailId,PasswordId;
    Button LoginUser,PhoneLog;
    TextView noAccount,EmailLoginField;
    ImageView Show,Hide,PE;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebasedatabase;
    FirebaseFirestore db;


    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EmailId = findViewById(R.id.EmailAdressLogin);
        PasswordId = findViewById(R.id.PasswordLogin);
        LoginUser = findViewById(R.id.Login_button);
        EmailLoginField = findViewById(R.id.Email_login_field);

        noAccount = findViewById(R.id.accountCreate);
        PhoneLog = findViewById(R.id.btnPhone);

        Show = findViewById(R.id.eye);
        Hide = findViewById(R.id.hidden);

        firebasedatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        LoginUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String Email = EmailId.getText().toString();
                String Password = PasswordId.getText().toString();

                if (Email.isEmpty())
                {
                    EmailId.setError("Field is Required !");
                    EmailId.requestFocus();
                }

                if (Password.isEmpty())
                {
                    PasswordId.setError("Field is Required !");
                    PasswordId.requestFocus();
                }

                if (Email.isEmpty() && Password.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Fields are Empty ! ", Toast.LENGTH_LONG).show();
                }

                    db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task)
                        {
                            if(task.isSuccessful())
                            {
                                for(QueryDocumentSnapshot doc : task.getResult())
                                {
                                    String a = doc.getString("Email");
                                    String b = doc.getString("Password");
                                    String c = doc.getString("Phone");

                                    String a1 = EmailId.getText().toString();
                                    String b1 = PasswordId.getText().toString();


                                    //email login
                                    if(a.equals(a1) && b.equals(b1))
                                    {
                                        Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                        Intent home = new Intent(LoginActivity.this, Dashboard.class);
                                        startActivity(home);
                                        finish();
                                    }
                                    else
                                    {
                                        LoginUser.setText("ERROR ! ");
                                        LoginUser.setError("");
                                        LoginUser.setText("LOGIN");
                                    }


                                    //phone login
                                    if(c.equals(a1) && b.equals(b1))
                                    {
                                        Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                        Intent home = new Intent(LoginActivity.this, Dashboard.class);
                                        startActivity(home);
                                        finish();
                                    }
                                    else
                                    {
                                        LoginUser.setText("ERROR ! ");
                                        LoginUser.setError("");
                                        LoginUser.setText("LOGIN");
                                    }

                                }
                            }
                        }
                    });
                }
        });

        Show.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Show.setVisibility(View.INVISIBLE);
                Hide.setVisibility(View.VISIBLE);
                PasswordId.setTransformationMethod(null);

            }
        });

        Hide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Show.setVisibility(View.VISIBLE);
                Hide.setVisibility(View.INVISIBLE);
                PasswordId.setTransformationMethod(new PasswordTransformationMethod());
            }
        });


        noAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent nextAct = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(nextAct);
            }
        });
    }
}