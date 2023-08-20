package smart.parking.solution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CameraView extends AppCompatActivity
{
    ImageView retour;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_view);

        retour = findViewById(R.id.retour_dash);

        retour.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent Back = new Intent(CameraView.this ,Dashboard.class);
                startActivity(Back);
            }
        });
    }
}