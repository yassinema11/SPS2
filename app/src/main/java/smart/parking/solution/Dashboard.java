package smart.parking.solution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity
{
    ImageView I1, I2;
    TextView sp1, sp2;
    Button bp1, bp2, btnDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        I1 = findViewById(R.id.p1);
        I2 = findViewById(R.id.p2);

        sp1 = findViewById(R.id.sp1);
        sp2 = findViewById(R.id.sp2);

        bp1 = findViewById(R.id.btnp1);
        bp2 = findViewById(R.id.btnp2);

        btnDisconnect = findViewById(R.id.disconnect);

        FirebaseApp.initializeApp(Dashboard.this);
        DatabaseReference database;

        database = FirebaseDatabase.getInstance("https://sps-v2-default-rtdb.firebaseio.com").getReference();


        sp1.setText("Data 1 ");
        database.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    sp1.setText("Data 2 ");

                    String place1 = snapshot.child("Spot1").getValue(String.class);
                    sp2.setText(place1);

                        sp1.setText("Data 3 ");
                        sp2.setText("Data 4 ");
                        bp1.setText(place1);

                    if(place1 != null)
                    {
                        if (place1.equals("Free"))
                        {
                            bp1.setText(place1);
                            I1.setVisibility(View.INVISIBLE);

                            sp1.setText("Data 5");
                        }

                        if (place1.equals("Reserved"))
                        {
                            bp1.setText(place1);
                            I1.setVisibility(View.VISIBLE);

                            sp1.setText("Data 6 ");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                sp1.setText("Data 7 ");
                Toast.makeText(Dashboard.this, "No Data", Toast.LENGTH_LONG).show();
            }
        });

        sp1.setText("Data 8 ");

        database.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String place2 = snapshot.child("Spot2").getValue(String.class);

                    if (place2 != null)
                    {
                        bp2.setText(place2);

                        if (place2.equals("Free"))
                        {
                            bp2.setText(place2);
                            I2.setVisibility(View.INVISIBLE);
                        }

                        if (place2.equals("Reserved"))
                        {
                            bp2.setText(place2);
                            I2.setVisibility(View.VISIBLE);
                        }
                    }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //Toast.makeText(Home.this, "No Data", Toast.LENGTH_LONG).show();
            }
        });

        btnDisconnect.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                SessionManagement sessionManagement = new SessionManagement(Dashboard.this);
                sessionManagement.removeSession();

                moveToLogin();
            }
        });
    }

    private void moveToLogin()
    {
        Intent intent = new Intent(Dashboard.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }
}