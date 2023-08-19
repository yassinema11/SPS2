package smart.parking.solution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity
{
    EditText EditEmail,EditPassword,EditName,EditPhoneNumber;
    Button ButtonRegister;
    TextView Account_exist;
    ImageView Hide,Show;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase;
    private CountryCodePicker countryCodePicker;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    String countryCode = "216";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        EditName = findViewById(R.id.Name_register);
        EditEmail = findViewById(R.id.Email_register);

        EditPhoneNumber = findViewById(R.id.Phone_register);
        EditPassword = findViewById(R.id.Password_register);

        Account_exist = findViewById(R.id.login_exist);
        ButtonRegister = findViewById(R.id.Register_button);

        Show = findViewById(R.id.Rshow);
        Hide = findViewById(R.id.Rhide);

        countryCodePicker = findViewById(R.id.countryCodePicker);

        ref = firebaseFirestore.collection("Users").document("SPS");

        countryCodePicker.setOnCountryChangeListener(() ->
        {
            countryCode = countryCodePicker.getSelectedCountryCode();
        });

        ButtonRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String Name,Email,PhoneNumber,Password;

                Name = String.valueOf(EditName.getText());
                Email = String.valueOf(EditEmail.getText());
                PhoneNumber = String.valueOf(EditPhoneNumber.getText());
                Password = String.valueOf(EditPassword.getText());

                if(Name.isEmpty())
                {
                    EditName.setError("Field is Empty");
                    EditName.requestFocus();
                }

                if(Email.isEmpty())
                {
                    EditEmail.setError("Field is Empty");
                    EditEmail.requestFocus();
                }

                if(PhoneNumber.isEmpty())
                {
                    EditPhoneNumber.setError("Field is Empty");
                    EditPhoneNumber.requestFocus();
                }

                if(Password.isEmpty())
                {
                    EditPassword.setError("Field is Empty");
                    EditPassword.requestFocus();
                }

                if (!(Name.isEmpty() && Email.isEmpty() && PhoneNumber.isEmpty() && Password.isEmpty()))
                {

                    ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                    {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot)
                        {
                            if (documentSnapshot.exists())
                            {
                                Toast.makeText(RegisterActivity.this, "Sorry,this user exists", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Map<String, Object> reg_entry = new HashMap<>();
                                reg_entry.put("Name", EditName.getText().toString());
                                reg_entry.put("Phone", EditPhoneNumber.getText().toString());
                                reg_entry.put("Email", EditEmail.getText().toString());
                                reg_entry.put("Country", countryCode);
                                reg_entry.put("Password", EditPassword.getText().toString());
                                reg_entry.put("Date", new Timestamp(new Date()));


                                //   String myId = ref.getId();
                                firebaseFirestore.collection("Users").add(reg_entry)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                                        {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference)
                                            {
                                                Toast.makeText(RegisterActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                                                Intent NextAct = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(NextAct);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener()
                                        {
                                            @Override
                                            public void onFailure(@NonNull Exception e)
                                            {
                                                Log.d("Error", e.getMessage());
                                            }
                                        });
                            }
                        }
                    });
                }
            }
        });

        Account_exist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent LoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
            }
        });

        Show.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Show.setVisibility(View.INVISIBLE);
                Hide.setVisibility(View.VISIBLE);
                EditPassword.setTransformationMethod(null);

            }
        });

        Hide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Show.setVisibility(View.VISIBLE);
                Hide.setVisibility(View.INVISIBLE);
                EditPassword.setTransformationMethod(new PasswordTransformationMethod());

            }
        });



    }
}