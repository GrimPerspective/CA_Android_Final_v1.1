package iss.workshop.ca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    boolean checkCard = false;
    ImageView openedCard;
    CardObject openedObject;
    List<CardObject> gameCards = new ArrayList<>();
    Integer numMatches = 0;
    int elapsedMillis;
    int elapsedSeconds;
    MediaPlayer bgSoundMP;
    MediaPlayer correctSoundMP;
    MediaPlayer wrongSoundMP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // retrieve list of 6 URLs from MainActivity
        Intent intent = getIntent();
        ArrayList<String> selectedImagesURL = (ArrayList<String>) intent.getSerializableExtra("imgSelectedUrl");

        // add URLs to each object
        String url1 = selectedImagesURL.get(0);
        CardObject cardObj1 = new CardObject(url1, 1);

        String url2 = selectedImagesURL.get(1);
        CardObject cardObj2 = new CardObject(url2,2);

        String url3 = selectedImagesURL.get(2);
        CardObject cardObj3 = new CardObject(url3,3);

        String url4 = selectedImagesURL.get(3);
        CardObject cardObj4 = new CardObject(url4,4);

        String url5 = selectedImagesURL.get(4);
        CardObject cardObj5 = new CardObject(url5,5);

        String url6 = selectedImagesURL.get(5);
        CardObject cardObj6 = new CardObject(url6,6);

        //Implement Sounds
        bgSoundMP = MediaPlayer.create(this, R.raw.bg);
        correctSoundMP = MediaPlayer.create(this, R.raw.correct);
        wrongSoundMP = MediaPlayer.create(this, R.raw.wrong);

        //Implement Timer
        Chronometer timerChronometer;
        timerChronometer = (Chronometer)findViewById(R.id.chronometer);
        timerChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer cArg) {
//                long time = SystemClock.elapsedRealtime() - cArg.getBase();
//                int h = (int)(time /3600000);
//                int m = (int)(time - h*3600000)/60000;
//                int s= (int)(time - h*3600000- m*60000)/1000 ;
//                String hh = h < 10 ? "0"+h: h+"";
//                String mm = m < 10 ? "0"+m: m+"";
//                String ss = s < 10 ? "0"+s: s+"";
//                cArg.setText(hh+":"+mm+":"+ss);
                if(numMatches == 6){
                    bgSoundMP.stop();
                    timerChronometer.stop();
                    elapsedMillis = (int) (SystemClock.elapsedRealtime() - timerChronometer.getBase());
                    elapsedSeconds = (int) (elapsedMillis / 1000);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(GameActivity.this)
                            .setTitle(R.string.gameComplete)
                            .setMessage("You took " + elapsedSeconds + " seconds to complete the game!")
                            .setPositiveButton(R.string.backToHome,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    });

                    dlg.show();

                }
            }
        });

        //Start Sound
        bgSoundMP.setLooping(true);
        bgSoundMP.start();


        timerChronometer.setBase(SystemClock.elapsedRealtime());
        timerChronometer.start();
        timerChronometer.setFormat("Elapsed Time : %s");

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    if(numMatches == 6){
//                        timerChronometer.stop();}
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(numMatches == 6){
//                            timerChronometer.stop();
//                        }
//                    }
//                });
//            }
//        }).start();

        // create list of 12 objects
        gameCards.add(cardObj1);
        gameCards.add(cardObj2);
        gameCards.add(cardObj3);
        gameCards.add(cardObj4);
        gameCards.add(cardObj5);
        gameCards.add(cardObj6);
        gameCards.add(cardObj1);
        gameCards.add(cardObj2);
        gameCards.add(cardObj3);
        gameCards.add(cardObj4);
        gameCards.add(cardObj5);
        gameCards.add(cardObj6);

        // shuffle gameCards (List of CardObject)
        Collections.shuffle(gameCards);

        // initialise ImageView for cards
        ImageView card1 = findViewById(R.id.card1);
        ImageView card2 = findViewById(R.id.card2);
        ImageView card3 = findViewById(R.id.card3);
        ImageView card4 = findViewById(R.id.card4);
        ImageView card5 = findViewById(R.id.card5);
        ImageView card6 = findViewById(R.id.card6);
        ImageView card7 = findViewById(R.id.card7);
        ImageView card8 = findViewById(R.id.card8);
        ImageView card9 = findViewById(R.id.card9);
        ImageView card10 = findViewById(R.id.card10);
        ImageView card11 = findViewById(R.id.card11);
        ImageView card12 = findViewById(R.id.card12);

        // cover all cards
        String coverImage = "https://cdn.stocksnap.io/img-thumbs/960w/architecture-building_I0PMKBPL2N.jpg";
        Picasso.get().load(coverImage).resize(300,300).into(card1);
        Picasso.get().load(coverImage).resize(300,300).into(card2);
        Picasso.get().load(coverImage).resize(300,300).into(card3);
        Picasso.get().load(coverImage).resize(300,300).into(card4);
        Picasso.get().load(coverImage).resize(300,300).into(card5);
        Picasso.get().load(coverImage).resize(300,300).into(card6);
        Picasso.get().load(coverImage).resize(300,300).into(card7);
        Picasso.get().load(coverImage).resize(300,300).into(card8);
        Picasso.get().load(coverImage).resize(300,300).into(card9);
        Picasso.get().load(coverImage).resize(300,300).into(card10);
        Picasso.get().load(coverImage).resize(300,300).into(card11);
        Picasso.get().load(coverImage).resize(300,300).into(card12);

        // initialize numMatches
        TextView matched = findViewById(R.id.numMatches);
        matched.setText(numMatches.toString() + " of 6 matches");

        // set onClick for card1
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (card1 == openedCard)
                {
                    return;
                }

                else if (!checkCard)
                {
                    String url = gameCards.get(0).getUrl();
                    Picasso.get().load(url).resize(300,300).into(card1);

                    openedCard = card1;
                    openedObject = gameCards.get(0);

                    checkCard = true;
                }

                else
                {
                    if(!matchCards(gameCards.get(0)))
                    {
                        String url = gameCards.get(0).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card1);

                        Picasso.get().load(coverImage).resize(300,300).into(openedCard);
                        Animation showCard = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate_card);
                        openedCard.startAnimation(showCard);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wrongSoundMP.start();
                                        Picasso.get().load(coverImage).resize(300,300).into(card1);
                                    }
                                });

                            }
                        }).start();

                    }

                    else
                    {
                        String url = gameCards.get(0).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card1);
                        numMatches++;
                        correctSoundMP.start();
                        matched.setText(numMatches.toString() + " of 6 matches");
                    }

                    openedCard = null;
                    openedObject = null;

                    checkCard = false;
                }

            }
        });

        // set onClick for card2
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (card2 == openedCard)
                {
                    return;
                }

                else if (!checkCard)
                {
                    String url = gameCards.get(1).getUrl();
                    Picasso.get().load(url).resize(300,300).into(card2);

                    openedCard = card2;
                    openedObject = gameCards.get(1);

                    checkCard = true;
                }

                else
                {
                    if(!matchCards(gameCards.get(1)))
                    {
                        String url = gameCards.get(1).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card2);

                        Picasso.get().load(coverImage).resize(300,300).into(openedCard);
                        Animation showCard = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate_card);
                        openedCard.startAnimation(showCard);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wrongSoundMP.start();
                                        Picasso.get().load(coverImage).resize(300,300).into(card2);
                                    }
                                });

                            }
                        }).start();
                    }

                    else
                    {
                        String url = gameCards.get(1).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card2);
                        numMatches++;
                        correctSoundMP.start();
                        matched.setText(numMatches.toString() + " of 6 matches");
                    }

                    openedCard = null;
                    openedObject = null;

                    checkCard = false;
                }

            }
        });

        // set onClick for card3
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (card3 == openedCard)
                {
                    return;
                }

                else if (!checkCard)
                {
                    String url = gameCards.get(2).getUrl();
                    Picasso.get().load(url).resize(300,300).into(card3);

                    openedCard = card3;
                    openedObject = gameCards.get(2);

                    checkCard = true;
                }

                else
                {
                    if(!matchCards(gameCards.get(2)))
                    {
                        String url = gameCards.get(2).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card3);

                        Picasso.get().load(coverImage).resize(300,300).into(openedCard);
                        Animation showCard = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate_card);
                        openedCard.startAnimation(showCard);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wrongSoundMP.start();
                                        Picasso.get().load(coverImage).resize(300,300).into(card3);
                                    }
                                });

                            }
                        }).start();
                    }

                    else
                    {
                        String url = gameCards.get(2).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card3);
                        numMatches++;
                        correctSoundMP.start();
                        matched.setText(numMatches.toString() + " of 6 matches");
                    }

                    openedCard = null;
                    openedObject = null;

                    checkCard = false;
                }

            }
        });

        // set onClick for card4
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (card4 == openedCard)
                {
                    return;
                }

                else if (!checkCard)
                {
                    String url = gameCards.get(3).getUrl();
                    Picasso.get().load(url).resize(300,300).into(card4);

                    openedCard = card4;
                    openedObject = gameCards.get(3);

                    checkCard = true;
                }

                else
                {
                    if(!matchCards(gameCards.get(3)))
                    {
                        String url = gameCards.get(3).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card4);

                        Picasso.get().load(coverImage).resize(300,300).into(openedCard);
                        Animation showCard = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate_card);
                        openedCard.startAnimation(showCard);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wrongSoundMP.start();
                                        Picasso.get().load(coverImage).resize(300,300).into(card4);
                                    }
                                });

                            }
                        }).start();
                    }

                    else
                    {
                        String url = gameCards.get(3).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card4);
                        numMatches++;
                        correctSoundMP.start();
                        matched.setText(numMatches.toString() + " of 6 matches");
                    }

                    openedCard = null;
                    openedObject = null;

                    checkCard = false;
                }

            }
        });

        // set onClick for card5
        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (card5 == openedCard)
                {
                    return;
                }

                else if (!checkCard)
                {
                    String url = gameCards.get(4).getUrl();
                    Picasso.get().load(url).resize(300,300).into(card5);

                    openedCard = card5;
                    openedObject = gameCards.get(4);

                    checkCard = true;
                }

                else
                {
                    if(!matchCards(gameCards.get(4)))
                    {
                        String url = gameCards.get(4).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card5);

                        Picasso.get().load(coverImage).resize(300,300).into(openedCard);
                        Animation showCard = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate_card);
                        openedCard.startAnimation(showCard);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wrongSoundMP.start();
                                        Picasso.get().load(coverImage).resize(300,300).into(card5);
                                    }
                                });

                            }
                        }).start();
                    }

                    else
                    {
                        String url = gameCards.get(4).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card5);
                        numMatches++;
                        correctSoundMP.start();
                        matched.setText(numMatches.toString() + " of 6 matches");
                    }

                    openedCard = null;
                    openedObject = null;

                    checkCard = false;
                }

            }
        });

        // set onClick for card6
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (card6 == openedCard)
                {
                    return;
                }

                else if (!checkCard)
                {
                    String url = gameCards.get(5).getUrl();
                    Picasso.get().load(url).resize(300,300).into(card6);

                    openedCard = card6;
                    openedObject = gameCards.get(5);

                    checkCard = true;
                }

                else
                {
                    if(!matchCards(gameCards.get(5)))
                    {
                        String url = gameCards.get(5).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card6);

                        Picasso.get().load(coverImage).resize(300,300).into(openedCard);
                        Animation showCard = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate_card);
                        openedCard.startAnimation(showCard);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wrongSoundMP.start();
                                        Picasso.get().load(coverImage).resize(300,300).into(card6);
                                    }
                                });

                            }
                        }).start();
                    }

                    else
                    {
                        String url = gameCards.get(5).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card6);
                        numMatches++;
                        correctSoundMP.start();
                        matched.setText(numMatches.toString() + " of 6 matches");
                    }

                    openedCard = null;
                    openedObject = null;

                    checkCard = false;
                }

            }
        });

        // set onClick for card7
        card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (card7 == openedCard)
                {
                    return;
                }

                else if (!checkCard)
                {
                    String url = gameCards.get(6).getUrl();
                    Picasso.get().load(url).resize(300,300).into(card7);

                    openedCard = card7;
                    openedObject = gameCards.get(6);

                    checkCard = true;
                }

                else
                {
                    if(!matchCards(gameCards.get(6)))
                    {
                        String url = gameCards.get(6).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card7);

                        Picasso.get().load(coverImage).resize(300,300).into(openedCard);
                        Animation showCard = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate_card);
                        openedCard.startAnimation(showCard);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wrongSoundMP.start();
                                        Picasso.get().load(coverImage).resize(300,300).into(card7);
                                    }
                                });

                            }
                        }).start();
                    }

                    else
                    {
                        String url = gameCards.get(6).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card7);
                        numMatches++;
                        correctSoundMP.start();
                        matched.setText(numMatches.toString() + " of 6 matches");
                    }

                    openedCard = null;
                    openedObject = null;

                    checkCard = false;
                }

            }
        });

        // set onClick for card8
        card8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (card8 == openedCard)
                {
                    return;
                }

                else if (!checkCard)
                {
                    String url = gameCards.get(7).getUrl();
                    Picasso.get().load(url).resize(300,300).into(card8);

                    openedCard = card8;
                    openedObject = gameCards.get(7);

                    checkCard = true;
                }

                else
                {
                    if(!matchCards(gameCards.get(7)))
                    {
                        String url = gameCards.get(7).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card8);

                        Picasso.get().load(coverImage).resize(300,300).into(openedCard);
                        Animation showCard = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate_card);
                        openedCard.startAnimation(showCard);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wrongSoundMP.start();
                                        Picasso.get().load(coverImage).resize(300,300).into(card8);
                                    }
                                });

                            }
                        }).start();
                    }

                    else
                    {
                        String url = gameCards.get(7).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card8);
                        numMatches++;
                        correctSoundMP.start();
                        matched.setText(numMatches.toString() + " of 6 matches");
                    }

                    openedCard = null;
                    openedObject = null;

                    checkCard = false;
                }

            }
        });

        // set onClick for card9
        card9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (card9 == openedCard)
                {
                    return;
                }

                else if (!checkCard)
                {
                    String url = gameCards.get(8).getUrl();
                    Picasso.get().load(url).resize(300,300).into(card9);

                    openedCard = card9;
                    openedObject = gameCards.get(8);

                    checkCard = true;
                }

                else
                {
                    if(!matchCards(gameCards.get(8)))
                    {
                        String url = gameCards.get(8).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card9);

                        Picasso.get().load(coverImage).resize(300,300).into(openedCard);
                        Animation showCard = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate_card);
                        openedCard.startAnimation(showCard);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wrongSoundMP.start();
                                        Picasso.get().load(coverImage).resize(300,300).into(card9);
                                    }
                                });

                            }
                        }).start();
                    }

                    else
                    {
                        String url = gameCards.get(8).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card9);
                        numMatches++;
                        correctSoundMP.start();
                        matched.setText(numMatches.toString() + " of 6 matches");
                    }

                    openedCard = null;
                    openedObject = null;

                    checkCard = false;
                }

            }
        });

        // set onClick for card10
        card10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (card10 == openedCard)
                {
                    return;
                }

                else if (!checkCard)
                {
                    String url = gameCards.get(9).getUrl();
                    Picasso.get().load(url).resize(300,300).into(card10);

                    openedCard = card10;
                    openedObject = gameCards.get(9);

                    checkCard = true;
                }

                else
                {
                    if(!matchCards(gameCards.get(9)))
                    {
                        String url = gameCards.get(9).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card10);

                        Picasso.get().load(coverImage).resize(300,300).into(openedCard);
                        Animation showCard = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate_card);
                        openedCard.startAnimation(showCard);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wrongSoundMP.start();
                                        Picasso.get().load(coverImage).resize(300,300).into(card10);
                                    }
                                });

                            }
                        }).start();
                    }

                    else
                    {
                        String url = gameCards.get(9).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card10);
                        numMatches++;
                        correctSoundMP.start();
                        matched.setText(numMatches.toString() + " of 6 matches");
                    }

                    openedCard = null;
                    openedObject = null;

                    checkCard = false;
                }

            }
        });

        // set onClick for card11
        card11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (card11 == openedCard)
                {
                    return;
                }

                else if (!checkCard)
                {
                    String url = gameCards.get(10).getUrl();
                    Picasso.get().load(url).resize(300,300).into(card11);

                    openedCard = card11;
                    openedObject = gameCards.get(10);

                    checkCard = true;
                }

                else
                {
                    if(!matchCards(gameCards.get(10)))
                    {
                        String url = gameCards.get(10).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card11);

                        Picasso.get().load(coverImage).resize(300,300).into(openedCard);
                        Animation showCard = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate_card);
                        openedCard.startAnimation(showCard);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wrongSoundMP.start();
                                        Picasso.get().load(coverImage).resize(300,300).into(card11);
                                    }
                                });

                            }
                        }).start();
                    }

                    else
                    {
                        String url = gameCards.get(10).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card11);
                        numMatches++;
                        correctSoundMP.start();
                        matched.setText(numMatches.toString() + " of 6 matches");
                    }

                    openedCard = null;
                    openedObject = null;

                    checkCard = false;
                }

            }
        });

        // set onClick for card12
        card12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (card12 == openedCard)
                {
                    return;
                }

                else if (!checkCard)
                {
                    String url = gameCards.get(11).getUrl();
                    Picasso.get().load(url).resize(300,300).into(card12);

                    openedCard = card12;
                    openedObject = gameCards.get(11);

                    checkCard = true;
                }

                else
                {
                    if(!matchCards(gameCards.get(11)))
                    {
                        String url = gameCards.get(11).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card12);

                        Picasso.get().load(coverImage).resize(300,300).into(openedCard);
                        Animation showCard = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animate_card);
                        openedCard.startAnimation(showCard);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wrongSoundMP.start();
                                        Picasso.get().load(coverImage).resize(300,300).into(card12);
                                    }
                                });

                            }
                        }).start();
                    }

                    else
                    {
                        String url = gameCards.get(11).getUrl();
                        Picasso.get().load(url).resize(300,300).into(card12);
                        numMatches++;
                        correctSoundMP.start();
                        matched.setText(numMatches.toString() + " of 6 matches");
                    }

                    openedCard = null;
                    openedObject = null;

                    checkCard = false;
                }

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        bgSoundMP.stop();
    }

    public boolean matchCards(CardObject cardObj)
    {
        if (cardObj.getId() == openedObject.getId())
            return true;

        else
            return false;
    }

}
