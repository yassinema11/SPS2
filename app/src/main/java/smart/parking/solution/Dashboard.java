package smart.parking.solution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    ImageView I1, I2 ,I3 ,I4, C1;
    TextView sp1, sp2 ,sp3, sp4;
    Button bp1, bp2 ,bp3 ,bp4 , btnDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        I1 = findViewById(R.id.p1);
        I2 = findViewById(R.id.p2);
        I3 = findViewById(R.id.p3);
        I4 = findViewById(R.id.p4);

        sp1 = findViewById(R.id.sp1);
        sp2 = findViewById(R.id.sp2);
        sp3 = findViewById(R.id.sp3);
        sp4 = findViewById(R.id.sp4);

        bp1 = findViewById(R.id.btnp1);
        bp2 = findViewById(R.id.btnp2);
        bp3 = findViewById(R.id.btnp3);
        bp4 = findViewById(R.id.btnp4);

        C1 = findViewById(R.id.cam1);

        btnDisconnect = findViewById(R.id.disconnect);

        FirebaseApp.initializeApp(Dashboard.this);
        DatabaseReference database;

        database = FirebaseDatabase.getInstance("https://sps-v2-default-rtdb.firebaseio.com").getReference();


        database.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String place1 = snapshot.child("Spot1").getValue(String.class);
                    if(place1 != null)
                    {
                        if (place1.equals("Free"))
                        {
                            sp1.setText("Place 1 : "+place1);
                            I1.setVisibility(View.INVISIBLE);
                            bp1.setEnabled(false);
                        }

                        if (place1.equals("Reserved"))
                        {
                            sp1.setText("Place 1 : "+place1);
                            I1.setVisibility(View.VISIBLE);
                            bp1.setEnabled(true);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(Dashboard.this, "No Data", Toast.LENGTH_LONG).show();
            }
        });

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
                        if (place2.equals("Free"))
                        {
                            sp2.setText("Place 2 : "+place2);
                            I2.setVisibility(View.INVISIBLE);
                            bp2.setEnabled(false);
                        }

                        if (place2.equals("Reserved"))
                        {
                            sp2.setText("Place 2 : "+place2);
                            I2.setVisibility(View.VISIBLE);
                            bp2.setEnabled(true);
                        }
                    }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //Toast.makeText(Home.this, "No Data", Toast.LENGTH_LONG).show();
            }
        });

        database.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String place3 = snapshot.child("Spot3").getValue(String.class);

                    if (place3 != null)
                    {
                        if (place3.equals("Free"))
                        {
                            sp3.setText("Place 3 : "+place3);
                            I3.setVisibility(View.INVISIBLE);
                            bp3.setEnabled(false);
                        }

                        if (place3.equals("Reserved"))
                        {
                            sp3.setText("Place 3 : "+place3);
                            I3.setVisibility(View.VISIBLE);
                            bp3.setEnabled(true);
                        }
                    }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //Toast.makeText(Home.this, "No Data", Toast.LENGTH_LONG).show();
            }
        });


        database.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    String place4 = snapshot.child("Spot4").getValue(String.class);

                    if (place4 != null)
                    {
                        if (place4.equals("Free"))
                        {
                            sp4.setText("Place 4 : "+place4);
                            I4.setVisibility(View.INVISIBLE);
                            bp4.setEnabled(false);
                        }package smart.parking.solution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
                            ImageView I1, I2 ,I3 ,I4, C1;
                            TextView sp1, sp2 ,sp3, sp4;
                            Button bp1, bp2 ,bp3 ,bp4 , btnDisconnect;

                            @Override
                            protected void onCreate(Bundle savedInstanceState)
                            {
                                super.onCreate(savedInstanceState);
                                setContentView(R.layout.activity_dashboard);

                                I1 = findViewById(R.id.p1);
                                I2 = findViewById(R.id.p2);
                                I3 = findViewById(R.id.p3);
                                I4 = findViewById(R.id.p4);

                                sp1 = findViewById(R.id.sp1);
                                sp2 = findViewById(R.id.sp2);
                                sp3 = findViewById(R.id.sp3);
                                sp4 = findViewById(R.id.sp4);

                                bp1 = findViewById(R.id.btnp1);
                                bp2 = findViewById(R.id.btnp2);
                                bp3 = findViewById(R.id.btnp3);
                                bp4 = findViewById(R.id.btnp4);

                                C1 = findViewById(R.id.cam1);

                                btnDisconnect = findViewById(R.id.disconnect);

                                FirebaseApp.initializeApp(smart.parking.solution.Dashboard.this);
                                DatabaseReference database;

                                database = FirebaseDatabase.getInstance("https://sps-v2-default-rtdb.firebaseio.com").getReference();


                                database.addValueEventListener(new ValueEventListener()
                                {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                    {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                        {
                                            String place1 = snapshot.child("Spot1").getValue(String.class);
                                            if(place1 != null)
                                            {
                                                if (place1.equals("Free"))
                                                {
                                                    sp1.setText("Place 1 : "+place1);
                                                    I1.setVisibility(View.INVISIBLE);
                                                    bp1.setEnabled(false);
                                                }

                                                if (place1.equals("Reserved"))
                                                {
                                                    sp1.setText("Place 1 : "+place1);
                                                    I1.setVisibility(View.VISIBLE);
                                                    bp1.setEnabled(true);
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError)
                                    {
                                        Toast.makeText(smart.parking.solution.Dashboard.this, "No Data", Toast.LENGTH_LONG).show();
                                    }
                                });

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
                                                if (place2.equals("Free"))
                                                {
                                                    sp2.setText("Place 2 : "+place2);
                                                    I2.setVisibility(View.INVISIBLE);
                                                    bp2.setEnabled(false);
                                                }

                                                if (place2.equals("Reserved"))
                                                {
                                                    sp2.setText("Place 2 : "+place2);
                                                    I2.setVisibility(View.VISIBLE);
                                                    bp2.setEnabled(true);
                                                }
                                            }}
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError)
                                    {
                                        //Toast.makeText(Home.this, "No Data", Toast.LENGTH_LONG).show();
                                    }
                                });

                                database.addValueEventListener(new ValueEventListener()
                                {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                    {

                                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                        {
                                            String place3 = snapshot.child("Spot3").getValue(String.class);

                                            if (place3 != null)
                                            {
                                                if (place3.equals("Free"))
                                                {
                                                    sp3.setText("Place 3 : "+place3);
                                                    I3.setVisibility(View.INVISIBLE);
                                                    bp3.setEnabled(false);
                                                }

                                                if (place3.equals("Reserved"))
                                                {
                                                    sp3.setText("Place 3 : "+place3);
                                                    I3.setVisibility(View.VISIBLE);
                                                    bp3.setEnabled(true);
                                                }
                                            }}
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError)
                                    {
                                        //Toast.makeText(Home.this, "No Data", Toast.LENGTH_LONG).show();
                                    }
                                });


                                database.addValueEventListener(new ValueEventListener()
                                {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                    {

                                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                        {
                                            String place4 = snapshot.child("Spot4").getValue(String.class);

                                            if (place4 != null)
                                            {
                                                if (place4.equals("Free"))
                                                {
                                                    sp4.setText("Place 4 : "+place4);
                                                    I4.setVisibility(View.INVISIBLE);
                                                    bp4.setEnabled(false);
                                                }

                                                if (place4.equals("Reserved"))
                                                {
                                                    sp4.setText("Place 4 : "+place4);
                                                    I4.setVisibility(View.VISIBLE);
                                                    bp4.setEnabled(true);
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
                                        Intent toLoginActivity = new Intent(smart.parking.solution.Dashboard.this, LoginActivity.class);
                                        startActivity(toLoginActivity);
                                        finish();
                                    }
                                });

                                C1.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        Intent camAct = new Intent(smart.parking.solution.Dashboard.this, CameraView.class);
                                        startActivity(camAct);
                                    }
                                });


                            }
                        }

                        if (place4.equals("Reserved"))
                        {
                            sp4.setText("Place 4 : "+place4);
                            I4.setVisibility(View.VISIBLE);
                            bp4.setEnabled(true);
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
                Intent toLoginActivity = new Intent(Dashboard.this, LoginActivity.class);
                startActivity(toLoginActivity);
                finish();
            }
        });

        C1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent camAct = new Intent(Dashboard.this, CameraView.class);
                startActivity(camAct);
            }
        });


    }
}