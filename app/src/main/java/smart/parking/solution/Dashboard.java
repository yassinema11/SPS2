package smart.parking.solution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.concurrent.TimeUnit;

public class Dashboard extends AppCompatActivity
{
    ImageView I1, I2;
    TextView sp1, sp2;
    Button bp1, bp2, btnDisconnect;
    private Handler timerHandler = new Handler();
    private long startTime;
    private long endTime;

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
                        if (place1.equals("Reserved"))
                        {
                            startTime = System.currentTimeMillis();
                            timerHandler.postDelayed(updateTimerRunnable, 0);
                            sp1.setText("Place 1 : " + place1);
                            I1.setVisibility(View.VISIBLE);
                        }

                        if(place1.equals("Free"))
                        {
                            endTime = System.currentTimeMillis();
                            timerHandler.removeCallbacks(updateTimerRunnable); // Stop the timer runnable
                            showPaymentDialog(endTime - startTime); // Show the payment dialog
                            startTime = 0; // Reset the timer
                            sp1.setText("Place 1 : " + place1);
                            I1.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }

            Runnable updateTimerRunnable = new Runnable()
            {
                @Override
                public void run()
                {
                    long timeElapsedMillis = System.currentTimeMillis() - startTime;
                    updateTimeOnButton(bp1, timeElapsedMillis);
                    timerHandler.postDelayed(this, 1000); // Update every second
                }
            };

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
                        // Inside onDataChange for Spot2
                        if (place2.equals("Reserved"))
                        {
                            startTime = System.currentTimeMillis();
                            timerHandler.postDelayed(updateTimerRunnable, 0);
                            sp2.setText("Place 2 : " + place2);
                            I2.setVisibility(View.VISIBLE);
                        }

                        if (place2.equals("Free"))
                        {
                            endTime = System.currentTimeMillis();
                            timerHandler.removeCallbacks(updateTimerRunnable); // Stop the timer runnable
                            showPaymentDialog(endTime - startTime); // Show the payment dialog
                            startTime = 0; // Reset the timer
                            sp2.setText("Place 2 : " + place2);
                            I2.setVisibility(View.INVISIBLE);
                        }
                    }
                }
                Runnable updateTimerRunnable = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        long timeElapsedMillis = System.currentTimeMillis() - startTime;
                        updateTimeOnButton(bp2, timeElapsedMillis);
                        timerHandler.postDelayed(this, 1000); // Update every second
                    }
                };
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
    }
    private Runnable updateTimerRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            long timeElapsedMillis = System.currentTimeMillis() - startTime;
            updateTimeOnButton(bp1, timeElapsedMillis);
            updateTimeOnButton(bp2, timeElapsedMillis);
            timerHandler.postDelayed(this, 1000); // Update every second
        }
    };

    private void updateTimeOnButton(Button button, long timeElapsedMillis)
    {
        long hours = TimeUnit.MILLISECONDS.toHours(timeElapsedMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeElapsedMillis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeElapsedMillis) % 60;

        String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        button.setText("Timer: " + timeFormatted);
    }


    // Method to show payment AlertDialog
    private void showPaymentDialog(long timeElapsedMillis)
    {
        // Calculate payment amount (adjust this calculation based on your pricing logic)
        double paymentAmount = calculatePaymentAmount(timeElapsedMillis);

        // Convert time elapsed to hours, minutes, and seconds
        long hours = TimeUnit.MILLISECONDS.toHours(timeElapsedMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeElapsedMillis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeElapsedMillis) % 60;

        String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Dashboard.this);
        alertDialogBuilder.setTitle("Parking Payment");
        alertDialogBuilder.setMessage("Duration: " + timeFormatted + "\n Payment Amount: $" + paymentAmount);
        alertDialogBuilder.setPositiveButton("OK", null);
        alertDialogBuilder.show();
    }

    // Method to calculate payment amount (adjust this based on your pricing logic)
    private double calculatePaymentAmount(long timeElapsedMillis)
    {
        // Your payment calculation logic here
        // For example, assuming $0.50 per hour:
        double hourlyRate = 3000;
        double hours = (double) timeElapsedMillis / (1000 * 60 * 60);
        return hourlyRate * hours;
    }
}