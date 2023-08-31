package smart.parking.solution;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.concurrent.TimeUnit;

public class Dashboard extends AppCompatActivity
{
    private DatabaseReference databaseReference;
    private CountDownTimer[] timers = new CountDownTimer[4];
    private boolean[] isSpotReserved = new boolean[4];
    private ImageView[] spotImages = new ImageView[4];
    private TextView[] Texts = new TextView[4];
    private Button[] TimerBtn = new Button[4];
    private long[] timersStartTimes = new long[4];
    private Button btnCam, btnDisconnect;
    private ImageView C1,helpicon;
    private int TOTAL_SPOTS = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        spotImages[0] = findViewById(R.id.p1);
        spotImages[1] = findViewById(R.id.p2);
        spotImages[2] = findViewById(R.id.p3);
        spotImages[3] = findViewById(R.id.p4);

        TimerBtn[0] = findViewById(R.id.btnp1);
        TimerBtn[1] = findViewById(R.id.btnp2);
        TimerBtn[2] = findViewById(R.id.btnp3);
        TimerBtn[3] = findViewById(R.id.btnp4);

        Texts[0] = findViewById(R.id.sp1);
        Texts[1] = findViewById(R.id.sp2);
        Texts[2] = findViewById(R.id.sp3);
        Texts[3] = findViewById(R.id.sp4);

        C1 = findViewById(R.id.cam1);
        btnCam = findViewById(R.id.camView);
        btnDisconnect = findViewById(R.id.disconnect);
        helpicon = findViewById(R.id.helpdesk);

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://sps-v2-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference("Parking_Spots");

        setupSpotListeners();

        if (!isUserLoggedIn())
        {
            Intent intent = new Intent(Dashboard.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

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

        // Update the available spots count
        int availableSpots = TOTAL_SPOTS - countReservedSpots();
        updateAvailableSpotsText(availableSpots);

        helpicon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
                builder.setTitle("HELP DESK")
                        .setMessage("What do you prefer to contact us   ?")
                        .setPositiveButton("Email", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // Handle "Yes" button click
                                // For example, you can perform an action here
                                Intent intent = new Intent (Intent.ACTION_SEND);
                                intent.setType("plain/text");
                                intent .putExtra(Intent.EXTRA_EMAIL, new String[]{"yassinemanai955@gmail.com"});
                                intent.putExtra(Intent.EXTRA_SUBJECT, "Réclamation Client 0 0 0 ");
                                intent.putExtra(Intent.EXTRA_TEXT, "Réclamation Client");

                                startActivity(Intent.createChooser(intent,"SEND"));
                            }
                        })
                        .setNeutralButton("Phone", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // Handle "No" button click
                                // For example, you can cancel an action here
                                Intent intent = new Intent (Intent.ACTION_CALL);
                                intent.setData(Uri.parse("0021693014027"));

                                if (ActivityCompat.checkSelfPermission(Dashboard.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                                {
                                    return;
                                }
                                startActivity(intent);
                            }
                        });

                // Create and show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void setupSpotListeners()
    {
        for (int i = 0; i < 4; i++)
        {
            final int spotIndex = i;
            databaseReference.child("Spot" + (spotIndex + 1)).addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    String status = dataSnapshot.child("Status").getValue(String.class);
                    if (status != null)
                    {
                        updateUI(spotIndex, status);

                        if (dataSnapshot.hasChild("ElapsedTime"))
                        {
                            long elapsedTime = dataSnapshot.child("ElapsedTime").getValue(Long.class);
                            updateTimerText(spotIndex, elapsedTime * 1000); // Convert back to milliseconds
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {
                    // Handle error
                }
            });
        }
    }

    private void updateUI(int spotIndex, String status)
    {
        if (status.equals("Reserved"))
        {
            if (!isSpotReserved[spotIndex])
            {
                startTimer(spotIndex);
            }

            isSpotReserved[spotIndex] = true;
            spotImages[spotIndex].setVisibility(View.VISIBLE);
            TimerBtn[spotIndex].setEnabled(false);
            Texts[spotIndex].setText("Spot " + (spotIndex + 1) + ": " + status);
        }
        else if (status.equals("Free"))
        {
            stopTimer(spotIndex);
            isSpotReserved[spotIndex] = false;
            TimerBtn[spotIndex].setEnabled(false);
            spotImages[spotIndex].setVisibility(View.INVISIBLE);
            Texts[spotIndex].setText("Spot " + (spotIndex + 1) + ": " + status);
        }

        // Update the available spots count
        int availableSpots = TOTAL_SPOTS - countReservedSpots();
        updateAvailableSpotsText(availableSpots);
    }

    private void startTimer(int spotIndex)
    {
        timersStartTimes[spotIndex] = System.currentTimeMillis();

        timers[spotIndex] = new CountDownTimer(Long.MAX_VALUE, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                long elapsedTime = System.currentTimeMillis() - timersStartTimes[spotIndex];
                updateTimerText(spotIndex, elapsedTime);
                databaseReference.child("Spot" + (spotIndex + 1)).child("ElapsedTime").setValue(elapsedTime / 1000);

            }

            @Override
            public void onFinish()
            {
                // Timer stop
            }
        }.start();
    }

    private void updateTimerText(int spotIndex, long millisUntilFinished)
    {
        long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;
        float monney = (float) (minutes * 0.050) + (float) (hours * 3.00);
        TimerBtn[spotIndex].setText(hours + "h " + minutes + "m " + seconds + "s" + " / " + monney + " TND");
    }

    private void stopTimer(int spotIndex)
    {
        if (timers[spotIndex] != null)
        {
            timers[spotIndex].cancel();
            timers[spotIndex] = null;
        }
    }

    private int countReservedSpots()
    {
        int reservedCount = 0;
            for (boolean reserved : isSpotReserved)
            {
                if (reserved)
                {
                    reservedCount++;
                }
        }
        return reservedCount;
    }

    private void updateAvailableSpotsText(int availableSpots)
    {
        TextView availableSpotsTextView = findViewById(R.id.available_spots_text); // Replace with your actual TextView
        availableSpotsTextView.setText("Available Spots: " + availableSpots);
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