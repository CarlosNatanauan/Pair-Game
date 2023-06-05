package com.taskperformance.pairgame;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AppMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        setContentView(R.layout.activity_app_menu);



        Button btBeginner = findViewById(R.id.btBeginner);
        btBeginner.setOnClickListener(view -> {
            Intent gameIntent = new Intent(AppMenu.this, Beginner.class);
            gameIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            AppMenu.this.startActivity(gameIntent);
        });

        Button btIntermediate = findViewById(R.id.btIntermediate);
        btIntermediate.setOnClickListener(view -> {
            Intent gameIntent = new Intent(AppMenu.this, Intermediate.class);
            gameIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            AppMenu.this.startActivity(gameIntent);
        });

        Button btExpert = findViewById(R.id.btExpert);
        btExpert.setOnClickListener(view -> {
            Intent gameIntent = new Intent(AppMenu.this, Expert.class);
            gameIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            AppMenu.this.startActivity(gameIntent);
        });

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "App Exit", Toast.LENGTH_SHORT).show();
        finishAffinity();  // This method closes all activities and exits the app
    }
}
