package smart.parking.solution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
        DatabaseReference database1,database2,database3,database4;

        database1 = FirebaseDatabase.getInstance("https://sps-v2-default-rtdb.firebaseio.com").getReference();
        database2 = FirebaseDatabase.getInstance("https://sps-v2-default-rtdb.firebaseio.com").getReference();
        database3 = FirebaseDatabase.getInstance("https://sps-v2-default-rtdb.firebaseio.com").getReference();
        database4 = FirebaseDatabase.getInstance("https://sps-v2-default-rtdb.firebaseio.com").getReference();

        if (!isUserLoggedIn())
        {
            Intent intent = new Intent(Dashboard.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        database1.addValueEventListener(new ValueEventListener()
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
                            sp1.setText("Place 1 : " + place1);
                            I1.setVisibility(View.INVISIBLE);
                            bp1.setEnabled(false);
                            bp1.setText("Free");

                            if (timer1 != null)
                            {
                                timer1.cancel();
                            }

                        }
                        if (place1.equals("Reserved"))
                        {
                            sp1.setText("Place 1 : "+place1);
                            I1.setVisibility(View.VISIBLE);
                            bp1.setEnabled(true);

                            timer1 = new CountDownTimer(Long.MAX_VALUE, 1000)
                            {
                                @Override
                                public void onTick(long millisUntilFinished1)
                                {
                                    // Calculate elapsed time
                                    long elapsedTimeMillis1 = System.currentTimeMillis() - startTimeMillis1;
                                    long elapsedSeconds1 = elapsedTimeMillis1 / 1000;

                                    // Update UI with elapsed time
                                    bp1.setText(String.format("Time: %02d:%02d:%02d",
                                            elapsedSeconds1 / 3600, (elapsedSeconds1 % 3600) / 60, elapsedSeconds1 % 60));
                                }

                                @Override
                                public void onFinish()
                                {
                                }
                            };
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

        database2.addValueEventListener(new ValueEventListener()
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
                            bp2.setText("Free");

                            if (timer2 != null)
                            {
                                timer2.cancel();
                            }

                        }

                        if (place2.equals("Reserved"))
                        {
                            sp2.setText("Place 2 : "+place2);
                            I2.setVisibility(View.VISIBLE);
                            bp2.setEnabled(true);

                            timer2 = new CountDownTimer(Long.MAX_VALUE, 1000)
                            {
                                @Override
                                public void onTick(long millisUntilFinished2)
                                {
                                    // Calculate elapsed time
                                    long elapsedTimeMilli2 = System.currentTimeMillis() - startTimeMillis2;
                                    long elapsedSecond2 = elapsedTimeMilli2 / 1000;

                                    // Update UI with elapsed time
                                    bp2.setText(String.format("Time: %02d:%02d:%02d",
                                            elapsedSecond2 / 3600, (elapsedSecond2 % 3600) / 60, elapsedSecond2 % 60));
                                }

                                @Override
                                public void onFinish()
                                {
                                }
                            };

                        }
                    }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //Toast.makeText(Home.this, "No Data", Toast.LENGTH_LONG).show();
            }
        });

        database3.addValueEventListener(new ValueEventListener()
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
                            bp3.setText("Free");
                        }

                        if (place3.equals("Reserved"))
                        {
                            sp3.setText("Place 3 : "+place3);
                            I3.setVisibility(View.VISIBLE);
                            bp3.setEnabled(true);

                            timer3 = new CountDownTimer(Long.MAX_VALUE, 1000)
                            {
                                @Override
                                public void onTick(long millisUntilFinishe3)
                                {
                                    // Calculate elapsed time
                                    long elapsedTimeMilli3 = System.currentTimeMillis() - startTimeMillis3;
                                    long elapsedSecond3 = elapsedTimeMilli3 / 1000;

                                    // Update UI with elapsed time
                                    bp3.setText(String.format("Time: %02d:%02d:%02d",
                                            elapsedSecond3 / 3600, (elapsedSecond3 % 3600) / 60, elapsedSecond3 % 60));
                                }

                                @Override
                                public void onFinish()
                                {
                                }
                            };

                        }
                    }}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //Toast.makeText(Home.this, "No Data", Toast.LENGTH_LONG).show();
            }
        });


        database4.addValueEventListener(new ValueEventListener()
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
                            bp4.setText("Free");
                        }

                        if (place4.equals("Reserved"))
                        {
                            sp4.setText("Place 4 : "+place4);
                            I4.setVisibility(View.VISIBLE);
                            bp4.setEnabled(true);

                            timer4 = new CountDownTimer(Long.MAX_VALUE, 1000)
                            {
                                @Override
                                public void onTick(long millisUntilFinished4)
                                {
                                    // Calculate elapsed time
                                    long elapsedTimeMilli4 = System.currentTimeMillis() - startTimeMillis4;
                                    long elapsedSeconds4 = elapsedTimeMilli4 / 1000;

                                    // Update UI with elapsed time
                                    bp4.setText(String.format("Time: %02d:%02d:%02d",
                                            elapsedSeconds4 / 3600, (elapsedSeconds4 % 3600) / 60, elapsedSeconds4 % 60));
                                }

                                @Override
                                public void onFinish()
                                {
                                }
                            };

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
                clearUserSession();
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
                // Update UI to show the place is free
            }
        };
        timer1.start();
    }

    private boolean isUserLoggedIn()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        return sharedPreferences.contains("email") && sharedPreferences.contains("password");
    }

    private void clearUserSession()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}