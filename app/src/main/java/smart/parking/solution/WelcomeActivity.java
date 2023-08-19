package smart.parking.solution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button LoginWelcome = (Button) findViewById(R.id.Login_btn);
        Button RegisterWelcome = (Button) findViewById(R.id.Register_btn);

        LoginWelcome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent NextAct = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(NextAct);
            }
        });

        RegisterWelcome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent NextAct = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(NextAct);
            }
        });
    }
}