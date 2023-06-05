package com.taskperformance.pairgame;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.media.SoundPool;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Expert extends AppCompatActivity {

    private ImageButton firstCard;
    private ImageButton secondCard;
    private int firstCardId;
    private int secondCardId;
    private SoundPool soundPool;
    private int clickSoundId;
    private int pairFoundSoundId;
    private int incorrectPairSoundId;
    private int allPairsFoundSoundId;
    private int gameoverSoundId;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;

    private int[] cardIds = new int[]{R.id.card1, R.id.card2, R.id.card3, R.id.card4, R.id.card5, R.id.card6, R.id.card7, R.id.card8, R.id.card9, R.id.card10, R.id.card11, R.id.card12, R.id.card13, R.id.card14, R.id.card15, R.id.card16, R.id.card17, R.id.card18, R.id.card19, R.id.card20};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        setContentView(R.layout.activity_expert);

        timerTextView = findViewById(R.id.timer);

        soundPool = new SoundPool.Builder().setMaxStreams(2).build();
        clickSoundId = soundPool.load(this, R.raw.click, 1);
        pairFoundSoundId = soundPool.load(this, R.raw.correctanswer, 1);
        incorrectPairSoundId = soundPool.load(this, R.raw.wronganswerfinal, 1);
        allPairsFoundSoundId = soundPool.load(this, R.raw.congratulations, 1);
        gameoverSoundId = soundPool.load(this, R.raw.gameover, 1);

        resetTimer();

        setupCard(findViewById(R.id.card1), R.drawable.ic_robin1);
        setupCard(findViewById(R.id.card2), R.drawable.ic_starfire2);
        setupCard(findViewById(R.id.card3), R.drawable.ic_robin1);
        setupCard(findViewById(R.id.card4), R.drawable.ic_starfire2);
        setupCard(findViewById(R.id.card5), R.drawable.ic_bestboy3);
        setupCard(findViewById(R.id.card6), R.drawable.ic_bestboy3);
        setupCard(findViewById(R.id.card7), R.drawable.ic_raven4);
        setupCard(findViewById(R.id.card8), R.drawable.ic_blckfire6);
        setupCard(findViewById(R.id.card9), R.drawable.ic_cyborg5);
        setupCard(findViewById(R.id.card10), R.drawable.ic_raven4);
        setupCard(findViewById(R.id.card11), R.drawable.ic_cyborg5);
        setupCard(findViewById(R.id.card12), R.drawable.ic_blckfire6);
        setupCard(findViewById(R.id.card13), R.drawable.ic_luna7);
        setupCard(findViewById(R.id.card14), R.drawable.ic_enemy9);
        setupCard(findViewById(R.id.card15), R.drawable.ic_enemyagain8);
        setupCard(findViewById(R.id.card16), R.drawable.ic_enemy9);
        setupCard(findViewById(R.id.card17), R.drawable.ic_enemyagain8);
        setupCard(findViewById(R.id.card18), R.drawable.ic_deathstroke10);
        setupCard(findViewById(R.id.card19), R.drawable.ic_deathstroke10);
        setupCard(findViewById(R.id.card20), R.drawable.ic_luna7);


        ImageButton btHome = findViewById(R.id.btHome);
        btHome.setOnClickListener(v -> {
            Intent intent = new Intent(Expert.this, AppMenu.class);
            startActivity(intent);
        });

        ImageButton btRefresh = findViewById(R.id.btRefresh);
        btRefresh.setOnClickListener(v -> resetGame());
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
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }

    private void resetGame() {
        for (int id : cardIds) {
            ImageButton card = findViewById(id);
            card.setBackgroundResource(R.drawable.ic_teenlogo);
            card.setEnabled(true);
        }
        firstCard = null;
        secondCard = null;
        resetTimer();
    }

    private void flipAllCardsBack() {
        for (int id : cardIds) {
            ImageButton card = findViewById(id);
            card.setBackgroundResource(R.drawable.ic_teenlogo);
            card.setEnabled(true);
        }
        firstCard = null;
        secondCard = null;
    }


    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                timerTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
            }

            public void onFinish() {
                if (!isWin()) {
                    soundPool.play(gameoverSoundId, 1, 1, 0, 0, 1);
                    showGameOverDialog();
                }
            }
        }.start();
    }

    private void setupCard(ImageButton card, int frontImageRes) {
        card.setOnClickListener(v -> {
            soundPool.play(clickSoundId, 1, 1, 0, 0, 1);

            if (firstCard == null) {
                firstCard = card;
                firstCardId = frontImageRes;
                card.setBackgroundResource(frontImageRes);
            } else if (secondCard == null) {
                secondCard = card;
                secondCardId = frontImageRes;
                card.setBackgroundResource(frontImageRes);

                if (firstCardId == secondCardId) {
                    // Pair found
                    soundPool.play(pairFoundSoundId, 1, 1, 0, 0, 1);
                    firstCard.setEnabled(false);
                    secondCard.setEnabled(false);
                    firstCard = null;
                    secondCard = null;
                } else {
                    // Pair not found
                    soundPool.play(incorrectPairSoundId, 1, 1, 0, 0, 1);
                    secondCard.postDelayed(() -> {
                        flipAllCardsBack();
                        Toast.makeText(Expert.this, "Incorrect pair", Toast.LENGTH_SHORT).show();
                    }, 1000); // Delay for 1 second before resetting the game
                }
            }

            if (isWin()) {
                soundPool.play(allPairsFoundSoundId, 1, 1, 0, 0, 1);
                showCongratsDialog();
            }
        });
    }

    private void showCongratsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Congratulations!");
        builder.setMessage("You've found all pairs.");
        builder.setPositiveButton("Play Again", (dialog, id) -> {
            // User clicked the "Play Again" button, so reset the game
            resetGame();
        });
        builder.setNegativeButton("Exit", (dialog, id) -> {
            Intent intent = new Intent(Expert.this, AppMenu.class);
            startActivity(intent);
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showGameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Game Over!");
        builder.setMessage("You've failed to find all pairs.");
        builder.setPositiveButton("Play Again", (dialog, id) -> resetGame());
        builder.setNegativeButton("Exit", (dialog, id) -> {
            Intent intent = new Intent(Expert.this, AppMenu.class);
            startActivity(intent);
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Check whether all cards have been flipped
    private boolean isWin() {
        for (int id : cardIds) {
            if (((ImageButton) findViewById(id)).isEnabled()) {
                return false;
            }
        }
        return true;
    }
}

