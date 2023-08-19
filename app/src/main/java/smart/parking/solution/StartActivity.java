package smart.parking.solution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button getStarted = (Button) findViewById(R.id.btn_getStarted);
        getStarted.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent NextAct = new Intent(StartActivity.this, WelcomeActivity.class);
                startActivity(NextAct);
                Toast.makeText(StartActivity.this, "Welcome To Smart Parking Solution", Toast.LENGTH_LONG).show();
            }
        });
    }
}