package smart.parking.solution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    Button bp1, bp2 ,bp3 ,bp4 , btnCam, btnDisconnect;

    private CountDownTimer timer1,timer2,timer3,timer4;
    private long startTimeMillis1,startTimeMillis2,startTimeMillis3,startTimeMillis4;
    private long endTimeMillis1,endTimeMillis2,endTimeMillis3,endTimeMillis4;

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
        btnCam = findViewById(R.id.camView);

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
                            if (timer1 != null) {
                                sp1.setText("Place 1 : " + place1);
                                I1.setVisibility(View.INVISIBLE);
                                bp1.setEnabled(false);
                                timer1.onFinish();
                            }
                        }

                        if (place1.equals("Reserved"))
                        {
                            sp1.setText("Place 1 : "+place1);
                            I1.setVisibility(View.VISIBLE);
                            bp1.setEnabled(true);

                            showReservationDialog1();
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

                            showReservationDialog2();
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

                            showReservationDialog3();
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

                            showReservationDialog4();

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

        btnCam.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent camAct = new Intent(Dashboard.this, CameraView.class);
                startActivity(camAct);
            }
        });
    }
    private void showReservationDialog1()
    {

            endTimeMillis1 = System.currentTimeMillis();
            long elapsedTimeMillis = endTimeMillis1 - startTimeMillis1;
            long seconds = elapsedTimeMillis / 1000;
            long amount = seconds * 10; // Example: $10 per second

            startTimeMillis1 = System.currentTimeMillis();
            startTimer1();
    }

    private void startTimer1()
    {
        timer1 = new CountDownTimer(Long.MAX_VALUE, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                // Calculate elapsed time
                long elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis1;
                long elapsedSeconds = elapsedTimeMillis / 1000;

                // Update UI with elapsed time
                bp1.setText(String.format("Time: %02d:%02d:%02d",
                        elapsedSeconds / 3600, (elapsedSeconds % 3600) / 60, elapsedSeconds % 60));
            }

            @Override
            public void onFinish()
            {
                // Timer finished, place becomes free
                timer1 = null;
                bp1.setText("Free");
                // Update UI to show the place is free
            }
        };
        timer1.start();
    }

    private void showReservationDialog2()
    {
            endTimeMillis2 = System.currentTimeMillis();

            long elapsedTimeMillis = endTimeMillis2 - startTimeMillis2;
            long seconds = elapsedTimeMillis / 1000;
            long amount = seconds * 10; // Example: $10 per second
            startTimeMillis2 = System.currentTimeMillis();

            startTimer2();

    }

    private void startTimer2()
    {
        timer2 = new CountDownTimer(Long.MAX_VALUE, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                // Calculate elapsed time
                long elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis2;
                long elapsedSeconds = elapsedTimeMillis / 1000;

                // Update UI with elapsed time
                bp2.setText(String.format("Time: %02d:%02d:%02d",
                        elapsedSeconds / 3600, (elapsedSeconds % 3600) / 60, elapsedSeconds % 60));
            }

            @Override
            public void onFinish()
            {
                // Timer finished, place becomes free
                timer2 = null;
                bp2.setText("Free");
                // Update UI to show the place is free
            }
        };

        timer2.start();
    }

    private void showReservationDialog3()
    {
            endTimeMillis3 = System.currentTimeMillis();

            long elapsedTimeMillis = endTimeMillis3 - startTimeMillis3;
            long seconds = elapsedTimeMillis / 1000;
            long amount = seconds * 10; // Example: $10 per second

            startTimeMillis3 = System.currentTimeMillis();

            startTimer3();

    }

    private void startTimer3()
    {
        timer3 = new CountDownTimer(Long.MAX_VALUE, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                // Calculate elapsed time
                long elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis3;
                long elapsedSeconds = elapsedTimeMillis / 1000;

                // Update UI with elapsed time
                bp3.setText(String.format("Time: %02d:%02d:%02d",
                        elapsedSeconds / 3600, (elapsedSeconds % 3600) / 60, elapsedSeconds % 60));
            }

            @Override
            public void onFinish()
            {
                // Timer finished, place becomes free
                timer3 = null;
                bp3.setText("Free");

                // Update UI to show the place is free
            }
        };

        timer3.start();
    }


    private void showReservationDialog4()
    {
            endTimeMillis4 = System.currentTimeMillis();

            long elapsedTimeMillis = endTimeMillis4 - startTimeMillis4;
            long seconds = elapsedTimeMillis / 1000;
            long amount = seconds * 10; // Example: $10 per second

            startTimeMillis4 = System.currentTimeMillis();
            startTimer4();
    }


    private void startTimer4()
    {
        timer4 = new CountDownTimer(Long.MAX_VALUE, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                // Calculate elapsed time
                long elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis4;
                long elapsedSeconds = elapsedTimeMillis / 1000;

                // Update UI with elapsed time
                bp4.setText(String.format("Time: %02d:%02d:%02d",
                        elapsedSeconds / 3600, (elapsedSeconds % 3600) / 60, elapsedSeconds % 60));
            }

            @Override
            public void onFinish()
            {
                // Timer finished, place becomes free
                timer4 = null;
                // Update UI to show the place is free
                bp4.setText("Free");
            }
        };
        timer4.start();
    }
}