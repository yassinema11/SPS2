package smart.parking.solution;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.concurrent.TimeUnit;

public class Dashboard extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private CountDownTimer[] timers = new CountDownTimer[4];
    private boolean[] isSpotReserved = new boolean[4];
    private ImageView[] spotImages = new ImageView[4];
    private TextView[] Texts = new TextView[4];
    private Button[] TimerBtn = new Button[4];
    private long[] timersStartTimes = new long[4]; // Store the start times of timers
    Button btnCam, btnDisconnect;
    ImageView C1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Parking_Spots");

        setupSpotListeners();

        if (!isUserLoggedIn()) {
            Intent intent = new Intent(Dashboard.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLoginActivity = new Intent(Dashboard.this, LoginActivity.class);
                clearUserSession();
                startActivity(toLoginActivity);
                finish();
            }
        });

        C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camAct = new Intent(Dashboard.this, CameraView.class);
                startActivity(camAct);
            }
        });

        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camAct = new Intent(Dashboard.this, CameraView.class);
                startActivity(camAct);
            }
        });
    }

    private void setupSpotListeners() {
        for (int i = 0; i < 4; i++) {
            final int spotIndex = i;
            databaseReference.child("Spot" + (spotIndex + 1)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String status = dataSnapshot.child("Status").getValue(String.class);
                    if (status != null) {
                        updateUI(spotIndex, status);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle error
                }
            });
        }
    }

    private void updateUI(int spotIndex, String status) {
        if (status.equals("Reserved")) {
            if (!isSpotReserved[spotIndex]) {
                startTimer(spotIndex);
            }
            isSpotReserved[spotIndex] = true;
            spotImages[spotIndex].setVisibility(View.VISIBLE); // Show image
            Texts[spotIndex].setText("Spot " + (spotIndex + 1) + ": " + status);
        } else if (status.equals("Free")) {
            stopTimer(spotIndex);
            isSpotReserved[spotIndex] = false;
            calculateAndDisplayAmount(spotIndex);
            spotImages[spotIndex].setVisibility(View.INVISIBLE); // Hide image
            Texts[spotIndex].setText("Spot " + (spotIndex + 1) + ": " + status);
        }
    }

    private void startTimer(int spotIndex) {
        timersStartTimes[spotIndex] = System.currentTimeMillis();

        timers[spotIndex] = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long elapsedTime = System.currentTimeMillis() - timersStartTimes[spotIndex];
                updateTimerText(spotIndex, elapsedTime);
            }

            @Override
            public void onFinish() {
                // Timer finished
            }
        }.start();
    }

    private void updateTimerText(int spotIndex, long millisUntilFinished)
    {
        long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) ;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;
        TimerBtn[spotIndex].setText(hours + "h " + minutes + "m " + seconds + "s");
    }

    private void stopTimer(int spotIndex) {
        if (timers[spotIndex] != null) {
            timers[spotIndex].cancel();
            timers[spotIndex] = null;
        }
    }

    private void calculateAndDisplayAmount(int spotIndex)
    {
        long elapsedTime = System.currentTimeMillis() - timersStartTimes[spotIndex];
        double hours = TimeUnit.MILLISECONDS.toHours(elapsedTime);
        double amount = hours * 0.50; // Billing rate
        Texts[spotIndex].setText(""+amount);
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        return sharedPreferences.contains("email") && sharedPreferences.contains("password");
    }

    private void clearUserSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
