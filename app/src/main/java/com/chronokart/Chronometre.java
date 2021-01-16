package com.chronokart;

import android.content.pm.PackageManager;
import android.graphics.Typeface;

import android.os.Build;
import android.os.Bundle;



import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.os.SystemClock;
import android.view.HapticFeedbackConstants;
import android.view.View;

import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Chronometre extends AppCompatActivity {
    Position gps; // Création d'un objet Position
    public int i, nbre_tours = 50;
    public LocalTime heure_passage[] = new LocalTime[nbre_tours+2];
    public String temps_au_tour[] = new String[nbre_tours+2];
    public double lat_detection;
    public double long_detection;
    public double lat_piste[] = new double[120000]; // 2 min = 120000 ms (1er tour de piste)
    public double long_piste[] = new double[120000]; // 2 min = 120000 ms (1er tour de piste)
    private final static int INTERVAL = 100; //interval to update location in milliseconds
    private final static int TEMPS_INSTALLATION_KART = 5000; // Temps installation kart en milli secondes
    long time_in_ms = 0L;
    public boolean detection_established = false;

    public Chronometre(){ // Constructeur
        i = 0;
        heure_passage[i] = LocalTime.now();
        for (int j = 0; j<nbre_tours; j++){
            temps_au_tour[j] = "0:0:000";
        }
        lat_detection = 0;
        long_detection = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Permissions
        checkPermission("android.permission.ACCESS_FINE_LOCATION", 1500);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gps = new Position(this);
        // -------------------------------- Gestion interface graphique --------------------------------
        // -------------------------------- Polices des TextView ---------------------------------------
        Typeface typeface_1 = Typeface.createFromAsset(getAssets(),"fonts/Revamped.otf");
        Typeface typeface_2 = Typeface.createFromAsset(getAssets(),"fonts/DS-DIGI.TTF");

        TextView tour_actuel = findViewById(R.id.tour_actuel); tour_actuel.setTypeface(typeface_1);
        TextView chrono_actuel = findViewById(R.id.chrono_actuel); chrono_actuel.setTypeface(typeface_2);
        TextView tour_precedent = findViewById(R.id.tour_precedent); tour_precedent.setTypeface(typeface_1);
        TextView chrono_precedent = findViewById(R.id.chrono_precedent); chrono_precedent.setTypeface(typeface_2);
        TextView tour = findViewById(R.id.tour); tour.setTypeface(typeface_1);
        TextView temps = findViewById(R.id.temps); temps.setTypeface(typeface_2);
        TextView _1 = findViewById(R.id._1); _1.setTypeface(typeface_1); _1.setVisibility(View.INVISIBLE);
        TextView chrono1 = findViewById(R.id.chrono1); chrono1.setTypeface(typeface_2); chrono1.setVisibility(View.INVISIBLE);
        TextView _2 = findViewById(R.id._2); _2.setTypeface(typeface_1); _2.setVisibility(View.INVISIBLE);
        TextView chrono2 = findViewById(R.id.chrono2); chrono2.setTypeface(typeface_2); chrono2.setVisibility(View.INVISIBLE);
        TextView _3 = findViewById(R.id._3); _3.setTypeface(typeface_1); _3.setVisibility(View.INVISIBLE);
        TextView chrono3 = findViewById(R.id.chrono3); chrono3.setTypeface(typeface_2); chrono3.setVisibility(View.INVISIBLE);
        TextView _4 = findViewById(R.id._4); _4.setTypeface(typeface_1); _4.setVisibility(View.INVISIBLE);
        TextView chrono4 = findViewById(R.id.chrono4); chrono4.setTypeface(typeface_2); chrono4.setVisibility(View.INVISIBLE);
        TextView _5 = findViewById(R.id._5); _5.setTypeface(typeface_1); _5.setVisibility(View.INVISIBLE);
        TextView chrono5 = findViewById(R.id.chrono5); chrono5.setTypeface(typeface_2); chrono5.setVisibility(View.INVISIBLE);
        TextView _6 = findViewById(R.id._6); _6.setTypeface(typeface_1); _6.setVisibility(View.INVISIBLE);
        TextView chrono6 = findViewById(R.id.chrono6); chrono6.setTypeface(typeface_2); chrono6.setVisibility(View.INVISIBLE);
        TextView _7 = findViewById(R.id._7); _7.setTypeface(typeface_1); _7.setVisibility(View.INVISIBLE);
        TextView chrono7 = findViewById(R.id.chrono7); chrono7.setTypeface(typeface_2); chrono7.setVisibility(View.INVISIBLE);
        TextView _8 = findViewById(R.id._8); _8.setTypeface(typeface_1); _8.setVisibility(View.INVISIBLE);
        TextView chrono8 = findViewById(R.id.chrono8); chrono8.setTypeface(typeface_2); chrono8.setVisibility(View.INVISIBLE);
        TextView _9 = findViewById(R.id._9); _9.setTypeface(typeface_1); _9.setVisibility(View.INVISIBLE);
        TextView chrono9 = findViewById(R.id.chrono9); chrono9.setTypeface(typeface_2); chrono9.setVisibility(View.INVISIBLE);
        TextView _10 = findViewById(R.id._10); _10.setTypeface(typeface_1); _10.setVisibility(View.INVISIBLE);
        TextView chrono10 = findViewById(R.id.chrono10); chrono10.setTypeface(typeface_2); chrono10.setVisibility(View.INVISIBLE);
        TextView _11 = findViewById(R.id._11); _11.setTypeface(typeface_1); _11.setVisibility(View.INVISIBLE);
        TextView chrono11 = findViewById(R.id.chrono11); chrono11.setTypeface(typeface_2); chrono11.setVisibility(View.INVISIBLE);
        TextView _12 = findViewById(R.id._12); _12.setTypeface(typeface_1); _12.setVisibility(View.INVISIBLE);
        TextView chrono12 = findViewById(R.id.chrono12); chrono12.setTypeface(typeface_2); chrono12.setVisibility(View.INVISIBLE);
        TextView _13 = findViewById(R.id._13); _13.setTypeface(typeface_1); _13.setVisibility(View.INVISIBLE);
        TextView chrono13 = findViewById(R.id.chrono13); chrono13.setTypeface(typeface_2); chrono13.setVisibility(View.INVISIBLE);
        TextView _14 = findViewById(R.id._14); _14.setTypeface(typeface_1); _14.setVisibility(View.INVISIBLE);
        TextView chrono14 = findViewById(R.id.chrono14); chrono14.setTypeface(typeface_2); chrono14.setVisibility(View.INVISIBLE);
        TextView _15 = findViewById(R.id._15); _15.setTypeface(typeface_1); _15.setVisibility(View.INVISIBLE);
        TextView chrono15 = findViewById(R.id.chrono15); chrono15.setTypeface(typeface_2); chrono15.setVisibility(View.INVISIBLE);
        TextView _16 = findViewById(R.id._16); _16.setTypeface(typeface_1); _16.setVisibility(View.INVISIBLE);
        TextView chrono16 = findViewById(R.id.chrono16); chrono16.setTypeface(typeface_2); chrono16.setVisibility(View.INVISIBLE);
        TextView _17 = findViewById(R.id._17); _17.setTypeface(typeface_1); _17.setVisibility(View.INVISIBLE);
        TextView chrono17 = findViewById(R.id.chrono17); chrono17.setTypeface(typeface_2); chrono17.setVisibility(View.INVISIBLE);
        TextView _18 = findViewById(R.id._18); _18.setTypeface(typeface_1); _18.setVisibility(View.INVISIBLE);
        TextView chrono18 = findViewById(R.id.chrono18); chrono18.setTypeface(typeface_2); chrono18.setVisibility(View.INVISIBLE);
        TextView _19 = findViewById(R.id._19); _19.setTypeface(typeface_1); _19.setVisibility(View.INVISIBLE);
        TextView chrono19 = findViewById(R.id.chrono19); chrono19.setTypeface(typeface_2); chrono19.setVisibility(View.INVISIBLE);
        TextView _20 = findViewById(R.id._20); _20.setTypeface(typeface_1); _20.setVisibility(View.INVISIBLE);
        TextView chrono20 = findViewById(R.id.chrono20); chrono20.setTypeface(typeface_2); chrono20.setVisibility(View.INVISIBLE);
        TextView _21 = findViewById(R.id._21); _21.setTypeface(typeface_1); _21.setVisibility(View.INVISIBLE);
        TextView chrono21 = findViewById(R.id.chrono21); chrono21.setTypeface(typeface_2); chrono21.setVisibility(View.INVISIBLE);
        TextView _22 = findViewById(R.id._22); _22.setTypeface(typeface_1); _22.setVisibility(View.INVISIBLE);
        TextView chrono22 = findViewById(R.id.chrono22); chrono22.setTypeface(typeface_2); chrono22.setVisibility(View.INVISIBLE);
        TextView _23 = findViewById(R.id._23); _23.setTypeface(typeface_1); _23.setVisibility(View.INVISIBLE);
        TextView chrono23 = findViewById(R.id.chrono23); chrono23.setTypeface(typeface_2); chrono23.setVisibility(View.INVISIBLE);
        TextView _24 = findViewById(R.id._24); _24.setTypeface(typeface_1); _24.setVisibility(View.INVISIBLE);
        TextView chrono24 = findViewById(R.id.chrono24); chrono24.setTypeface(typeface_2); chrono24.setVisibility(View.INVISIBLE);
        TextView _25 = findViewById(R.id._25); _25.setTypeface(typeface_1); _25.setVisibility(View.INVISIBLE);
        TextView chrono25 = findViewById(R.id.chrono25); chrono25.setTypeface(typeface_2); chrono25.setVisibility(View.INVISIBLE);
        TextView _26 = findViewById(R.id._26); _26.setTypeface(typeface_1); _26.setVisibility(View.INVISIBLE);
        TextView chrono26 = findViewById(R.id.chrono26); chrono26.setTypeface(typeface_2); chrono26.setVisibility(View.INVISIBLE);
        TextView _27 = findViewById(R.id._27); _27.setTypeface(typeface_1); _27.setVisibility(View.INVISIBLE);
        TextView chrono27 = findViewById(R.id.chrono27); chrono27.setTypeface(typeface_2); chrono27.setVisibility(View.INVISIBLE);
        TextView _28 = findViewById(R.id._28); _28.setTypeface(typeface_1); _28.setVisibility(View.INVISIBLE);
        TextView chrono28 = findViewById(R.id.chrono28); chrono28.setTypeface(typeface_2); chrono28.setVisibility(View.INVISIBLE);
        TextView _29 = findViewById(R.id._29); _29.setTypeface(typeface_1); _29.setVisibility(View.INVISIBLE);
        TextView chrono29 = findViewById(R.id.chrono29); chrono29.setTypeface(typeface_2); chrono29.setVisibility(View.INVISIBLE);
        TextView _30 = findViewById(R.id._30); _30.setTypeface(typeface_1); _30.setVisibility(View.INVISIBLE);
        TextView chrono30 = findViewById(R.id.chrono30); chrono30.setTypeface(typeface_2); chrono30.setVisibility(View.INVISIBLE);
        TextView _31 = findViewById(R.id._31); _31.setTypeface(typeface_1); _31.setVisibility(View.INVISIBLE);
        TextView chrono31 = findViewById(R.id.chrono31); chrono31.setTypeface(typeface_2); chrono31.setVisibility(View.INVISIBLE);
        TextView _32 = findViewById(R.id._32); _32.setTypeface(typeface_1); _32.setVisibility(View.INVISIBLE);
        TextView chrono32 = findViewById(R.id.chrono32); chrono32.setTypeface(typeface_2); chrono32.setVisibility(View.INVISIBLE);
        TextView _33 = findViewById(R.id._33); _33.setTypeface(typeface_1); _33.setVisibility(View.INVISIBLE);
        TextView chrono33 = findViewById(R.id.chrono33); chrono33.setTypeface(typeface_2); chrono33.setVisibility(View.INVISIBLE);
        TextView _34 = findViewById(R.id._34); _34.setTypeface(typeface_1); _34.setVisibility(View.INVISIBLE);
        TextView chrono34 = findViewById(R.id.chrono34); chrono34.setTypeface(typeface_2); chrono34.setVisibility(View.INVISIBLE);
        TextView _35 = findViewById(R.id._35); _35.setTypeface(typeface_1); _35.setVisibility(View.INVISIBLE);
        TextView chrono35 = findViewById(R.id.chrono35); chrono35.setTypeface(typeface_2); chrono35.setVisibility(View.INVISIBLE);
        TextView _36 = findViewById(R.id._36); _36.setTypeface(typeface_1); _36.setVisibility(View.INVISIBLE);
        TextView chrono36 = findViewById(R.id.chrono36); chrono36.setTypeface(typeface_2); chrono36.setVisibility(View.INVISIBLE);
        TextView _37 = findViewById(R.id._37); _37.setTypeface(typeface_1); _37.setVisibility(View.INVISIBLE);
        TextView chrono37 = findViewById(R.id.chrono37); chrono37.setTypeface(typeface_2); chrono37.setVisibility(View.INVISIBLE);
        TextView _38 = findViewById(R.id._38); _38.setTypeface(typeface_1); _38.setVisibility(View.INVISIBLE);
        TextView chrono38 = findViewById(R.id.chrono38); chrono38.setTypeface(typeface_2); chrono38.setVisibility(View.INVISIBLE);
        TextView _39 = findViewById(R.id._39); _39.setTypeface(typeface_1); _39.setVisibility(View.INVISIBLE);
        TextView chrono39 = findViewById(R.id.chrono39); chrono39.setTypeface(typeface_2); chrono39.setVisibility(View.INVISIBLE);
        TextView _40 = findViewById(R.id._40); _40.setTypeface(typeface_1); _40.setVisibility(View.INVISIBLE);
        TextView chrono40 = findViewById(R.id.chrono40); chrono40.setTypeface(typeface_2); chrono40.setVisibility(View.INVISIBLE);
        TextView _41 = findViewById(R.id._41); _41.setTypeface(typeface_1); _41.setVisibility(View.INVISIBLE);
        TextView chrono41 = findViewById(R.id.chrono41); chrono41.setTypeface(typeface_2); chrono41.setVisibility(View.INVISIBLE);
        TextView _42 = findViewById(R.id._42); _42.setTypeface(typeface_1); _42.setVisibility(View.INVISIBLE);
        TextView chrono42 = findViewById(R.id.chrono42); chrono42.setTypeface(typeface_2); chrono42.setVisibility(View.INVISIBLE);
        TextView _43 = findViewById(R.id._43); _43.setTypeface(typeface_1); _43.setVisibility(View.INVISIBLE);
        TextView chrono43 = findViewById(R.id.chrono43); chrono43.setTypeface(typeface_2); chrono43.setVisibility(View.INVISIBLE);
        TextView _44 = findViewById(R.id._44); _44.setTypeface(typeface_1); _44.setVisibility(View.INVISIBLE);
        TextView chrono44 = findViewById(R.id.chrono44); chrono44.setTypeface(typeface_2); chrono44.setVisibility(View.INVISIBLE);
        TextView _45 = findViewById(R.id._45); _45.setTypeface(typeface_1); _45.setVisibility(View.INVISIBLE);
        TextView chrono45 = findViewById(R.id.chrono45); chrono45.setTypeface(typeface_2); chrono45.setVisibility(View.INVISIBLE);
        TextView _46 = findViewById(R.id._46); _46.setTypeface(typeface_1); _46.setVisibility(View.INVISIBLE);
        TextView chrono46 = findViewById(R.id.chrono46); chrono46.setTypeface(typeface_2); chrono46.setVisibility(View.INVISIBLE);
        TextView _47 = findViewById(R.id._47); _47.setTypeface(typeface_1); _47.setVisibility(View.INVISIBLE);
        TextView chrono47 = findViewById(R.id.chrono47); chrono47.setTypeface(typeface_2); chrono47.setVisibility(View.INVISIBLE);
        TextView _48 = findViewById(R.id._48); _48.setTypeface(typeface_1); _48.setVisibility(View.INVISIBLE);
        TextView chrono48 = findViewById(R.id.chrono48); chrono48.setTypeface(typeface_2); chrono48.setVisibility(View.INVISIBLE);
        TextView _49 = findViewById(R.id._49); _49.setTypeface(typeface_1); _49.setVisibility(View.INVISIBLE);
        TextView chrono49 = findViewById(R.id.chrono49); chrono49.setTypeface(typeface_2); chrono49.setVisibility(View.INVISIBLE);
        TextView _50 = findViewById(R.id._50); _50.setTypeface(typeface_1); _50.setVisibility(View.INVISIBLE);
        TextView chrono50 = findViewById(R.id.chrono50); chrono50.setTypeface(typeface_2); chrono50.setVisibility(View.INVISIBLE);

        final Button start = findViewById(R.id.Start);
        final Button stop= findViewById(R.id.Stop);
        stop.setVisibility(View.INVISIBLE);
        Toast toast = Toast.makeText(getApplicationContext(), "After pressing the start button, you will have " +
                TEMPS_INSTALLATION_KART/1000 + " seconds to jump in the kart and to go on the track!",Toast.LENGTH_LONG);
        toast.setGravity(1,0,400);
        // TODO afficher le Toast lorsqu'on clique sur start
        toast.show();

        // ----------------------------- Gestion boutons -----------------------------------------------
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //toast.show();
                start.setVisibility(View.INVISIBLE);
                RotateAnimation rotate = new RotateAnimation(0,360);
                rotate.setDuration(500);
                stop.startAnimation(rotate);
                stop.setVisibility(View.VISIBLE);
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                try {
                    Thread.sleep(TEMPS_INSTALLATION_KART);
                }
                catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
                mHandlerTask.run();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stop.setVisibility(View.INVISIBLE);
                RotateAnimation rotate = new RotateAnimation(0,360);
                rotate.setDuration(500);
                start.startAnimation(rotate);
                start.setVisibility(View.VISIBLE);
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY,HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
                mHandler.removeCallbacks(mHandlerTask);
            }
        });
    }

    protected void passage_endroit_detection(){
        if ((i !=0) && (i <nbre_tours+2)){
            heure_passage[i] = LocalTime.now();
            long minutes = ChronoUnit.MINUTES.between(heure_passage[i-1], heure_passage[i]);
            long seconds = ChronoUnit.SECONDS.between(heure_passage[i-1], heure_passage[i]);
            long millis = ChronoUnit.MILLIS.between(heure_passage[i-1], heure_passage[i]);
            temps_au_tour[i] = (""+ minutes + ":" + seconds + ":" + millis);
            System.out.println(temps_au_tour[i]);
            TextView chrono_precedent = findViewById(R.id.chrono_precedent);
            chrono_precedent.setText(temps_au_tour[i]);
            //TODO faire un affichage adapté à un nombre de tours inconnu
            switch(i) {
                case 2:
                    TextView _1 = findViewById(R.id._1); _1.setVisibility(View.VISIBLE);
                    TextView chrono1 = findViewById(R.id.chrono1); chrono1.setVisibility(View.VISIBLE);
                    chrono1.setText(temps_au_tour[i]);
                    break;
                case 3:
                    TextView _2 = findViewById(R.id._2); _2.setVisibility(View.VISIBLE);
                    TextView chrono2 = findViewById(R.id.chrono2); chrono2.setVisibility(View.VISIBLE);
                    chrono2.setText(temps_au_tour[i]);
                    break;
                case 4:
                    TextView _3 = findViewById(R.id._3); _3.setVisibility(View.VISIBLE);
                    TextView chrono3 = findViewById(R.id.chrono3); chrono3.setVisibility(View.VISIBLE);
                    chrono3.setText(temps_au_tour[i]);
                    break;
                case 5:
                    TextView _4 = findViewById(R.id._4); _4.setVisibility(View.VISIBLE);
                    TextView chrono4 = findViewById(R.id.chrono4); chrono4.setVisibility(View.VISIBLE);
                    chrono4.setText(temps_au_tour[i]);
                    break;
                case 6:
                    TextView _5 = findViewById(R.id._5); _5.setVisibility(View.VISIBLE);
                    TextView chrono5 = findViewById(R.id.chrono5); chrono5.setVisibility(View.VISIBLE);
                    chrono5.setText(temps_au_tour[i]);
                    break;
                case 7:
                    TextView _6 = findViewById(R.id._6); _6.setVisibility(View.VISIBLE);
                    TextView chrono6 = findViewById(R.id.chrono6); chrono6.setVisibility(View.VISIBLE);
                    chrono6.setText(temps_au_tour[i]);
                    break;
                case 8:
                    TextView _7 = findViewById(R.id._7); _7.setVisibility(View.VISIBLE);
                    TextView chrono7 = findViewById(R.id.chrono7); chrono7.setVisibility(View.VISIBLE);
                    chrono7.setText(temps_au_tour[i]);
                    break;
                case 9:
                    TextView _8 = findViewById(R.id._8); _8.setVisibility(View.VISIBLE);
                    TextView chrono8 = findViewById(R.id.chrono8); chrono8.setVisibility(View.VISIBLE);
                    chrono8.setText(temps_au_tour[i]);
                    break;
                case 10:
                    TextView _9 = findViewById(R.id._9); _9.setVisibility(View.VISIBLE);
                    TextView chrono9 = findViewById(R.id.chrono9); chrono9.setVisibility(View.VISIBLE);
                    chrono9.setText(temps_au_tour[i]);
                    break;
                case 11:
                    TextView _10 = findViewById(R.id._10); _10.setVisibility(View.VISIBLE);
                    TextView chrono10 = findViewById(R.id.chrono10); chrono10.setVisibility(View.VISIBLE);
                    chrono10.setText(temps_au_tour[i]);
                    break;
                case 12:
                    TextView _11 = findViewById(R.id._11); _11.setVisibility(View.VISIBLE);
                    TextView chrono11 = findViewById(R.id.chrono11); chrono11.setVisibility(View.VISIBLE);
                    chrono11.setText(temps_au_tour[i]);
                    break;
                case 13:
                    TextView _12 = findViewById(R.id._12); _12.setVisibility(View.VISIBLE);
                    TextView chrono12 = findViewById(R.id.chrono12); chrono12.setVisibility(View.VISIBLE);
                    chrono12.setText(temps_au_tour[i]);
                    break;
                case 14:
                    TextView _13 = findViewById(R.id._13); _13.setVisibility(View.VISIBLE);
                    TextView chrono13 = findViewById(R.id.chrono13); chrono13.setVisibility(View.VISIBLE);
                    chrono13.setText(temps_au_tour[i]);
                    break;
                case 15:
                    TextView _14 = findViewById(R.id._14); _14.setVisibility(View.VISIBLE);
                    TextView chrono14 = findViewById(R.id.chrono14); chrono14.setVisibility(View.VISIBLE);
                    chrono14.setText(temps_au_tour[i]);
                    break;
                case 16:
                    TextView _15 = findViewById(R.id._15); _15.setVisibility(View.VISIBLE);
                    TextView chrono15 = findViewById(R.id.chrono15); chrono15.setVisibility(View.VISIBLE);
                    chrono15.setText(temps_au_tour[i]);
                    break;
                case 17:
                    TextView _16 = findViewById(R.id._16); _16.setVisibility(View.VISIBLE);
                    TextView chrono16 = findViewById(R.id.chrono16); chrono16.setVisibility(View.VISIBLE);
                    chrono16.setText(temps_au_tour[i]);
                    break;
                case 18:
                    TextView _17 = findViewById(R.id._17); _17.setVisibility(View.VISIBLE);
                    TextView chrono17 = findViewById(R.id.chrono17); chrono17.setVisibility(View.VISIBLE);
                    chrono17.setText(temps_au_tour[i]);
                    break;
                case 19:
                    TextView _18 = findViewById(R.id._18); _18.setVisibility(View.VISIBLE);
                    TextView chrono18 = findViewById(R.id.chrono18); chrono18.setVisibility(View.VISIBLE);
                    chrono18.setText(temps_au_tour[i]);
                    break;
                case 20:
                    TextView _19 = findViewById(R.id._19); _19.setVisibility(View.VISIBLE);
                    TextView chrono19 = findViewById(R.id.chrono19); chrono19.setVisibility(View.VISIBLE);
                    chrono19.setText(temps_au_tour[i]);
                    break;
                case 21:
                    TextView _20 = findViewById(R.id._20); _20.setVisibility(View.VISIBLE);
                    TextView chrono20 = findViewById(R.id.chrono20); chrono20.setVisibility(View.VISIBLE);
                    chrono20.setText(temps_au_tour[i]);
                    break;
                case 22:
                    TextView _21 = findViewById(R.id._21); _21.setVisibility(View.VISIBLE);
                    TextView chrono21 = findViewById(R.id.chrono21); chrono21.setVisibility(View.VISIBLE);
                    chrono21.setText(temps_au_tour[i]);
                    break;
                case 23:
                    TextView _22 = findViewById(R.id._22); _22.setVisibility(View.VISIBLE);
                    TextView chrono22 = findViewById(R.id.chrono22); chrono22.setVisibility(View.VISIBLE);
                    chrono22.setText(temps_au_tour[i]);
                    break;
                case 24:
                    TextView _23 = findViewById(R.id._23); _23.setVisibility(View.VISIBLE);
                    TextView chrono23 = findViewById(R.id.chrono23); chrono23.setVisibility(View.VISIBLE);
                    chrono23.setText(temps_au_tour[i]);
                    break;
                case 25:
                    TextView _24 = findViewById(R.id._24); _24.setVisibility(View.VISIBLE);
                    TextView chrono24 = findViewById(R.id.chrono24); chrono24.setVisibility(View.VISIBLE);
                    chrono24.setText(temps_au_tour[i]);
                    break;
                case 26:
                    TextView _25 = findViewById(R.id._25); _25.setVisibility(View.VISIBLE);
                    TextView chrono25 = findViewById(R.id.chrono25); chrono25.setVisibility(View.VISIBLE);
                    chrono25.setText(temps_au_tour[i]);
                    break;
                case 27:
                    TextView _26 = findViewById(R.id._26); _26.setVisibility(View.VISIBLE);
                    TextView chrono26 = findViewById(R.id.chrono26); chrono26.setVisibility(View.VISIBLE);
                    chrono26.setText(temps_au_tour[i]);
                    break;
                case 28:
                    TextView _27 = findViewById(R.id._27); _27.setVisibility(View.VISIBLE);
                    TextView chrono27 = findViewById(R.id.chrono27); chrono27.setVisibility(View.VISIBLE);
                    chrono27.setText(temps_au_tour[i]);
                    break;
                case 29:
                    TextView _28 = findViewById(R.id._28); _28.setVisibility(View.VISIBLE);
                    TextView chrono28 = findViewById(R.id.chrono28); chrono28.setVisibility(View.VISIBLE);
                    chrono28.setText(temps_au_tour[i]);
                    break;
                case 30:
                    TextView _29 = findViewById(R.id._29); _29.setVisibility(View.VISIBLE);
                    TextView chrono29 = findViewById(R.id.chrono29); chrono29.setVisibility(View.VISIBLE);
                    chrono29.setText(temps_au_tour[i]);
                    break;
                case 31:
                    TextView _30 = findViewById(R.id._30); _30.setVisibility(View.VISIBLE);
                    TextView chrono30 = findViewById(R.id.chrono30); chrono30.setVisibility(View.VISIBLE);
                    chrono30.setText(temps_au_tour[i]);
                    break;
                case 32:
                    TextView _31 = findViewById(R.id._31); _31.setVisibility(View.VISIBLE);
                    TextView chrono31 = findViewById(R.id.chrono31); chrono31.setVisibility(View.VISIBLE);
                    chrono31.setText(temps_au_tour[i]);
                    break;
                case 33:
                    TextView _32 = findViewById(R.id._32); _32.setVisibility(View.VISIBLE);
                    TextView chrono32 = findViewById(R.id.chrono32); chrono32.setVisibility(View.VISIBLE);
                    chrono32.setText(temps_au_tour[i]);
                    break;
                case 34:
                    TextView _33 = findViewById(R.id._33); _33.setVisibility(View.VISIBLE);
                    TextView chrono33 = findViewById(R.id.chrono33); chrono33.setVisibility(View.VISIBLE);
                    chrono33.setText(temps_au_tour[i]);
                    break;
                case 35:
                    TextView _34 = findViewById(R.id._34); _34.setVisibility(View.VISIBLE);
                    TextView chrono34 = findViewById(R.id.chrono34); chrono34.setVisibility(View.VISIBLE);
                    chrono34.setText(temps_au_tour[i]);
                    break;
                case 36:
                    TextView _35 = findViewById(R.id._35); _35.setVisibility(View.VISIBLE);
                    TextView chrono35 = findViewById(R.id.chrono35); chrono35.setVisibility(View.VISIBLE);
                    chrono35.setText(temps_au_tour[i]);
                    break;
                case 37:
                    TextView _36 = findViewById(R.id._36); _36.setVisibility(View.VISIBLE);
                    TextView chrono36 = findViewById(R.id.chrono36); chrono36.setVisibility(View.VISIBLE);
                    chrono36.setText(temps_au_tour[i]);
                    break;
                case 38:
                    TextView _37 = findViewById(R.id._37); _37.setVisibility(View.VISIBLE);
                    TextView chrono37 = findViewById(R.id.chrono37); chrono37.setVisibility(View.VISIBLE);
                    chrono37.setText(temps_au_tour[i]);
                    break;
                case 39:
                    TextView _38 = findViewById(R.id._38); _38.setVisibility(View.VISIBLE);
                    TextView chrono38 = findViewById(R.id.chrono38); chrono38.setVisibility(View.VISIBLE);
                    chrono38.setText(temps_au_tour[i]);
                    break;
                case 40:
                    TextView _39 = findViewById(R.id._39); _39.setVisibility(View.VISIBLE);
                    TextView chrono39 = findViewById(R.id.chrono39); chrono39.setVisibility(View.VISIBLE);
                    chrono39.setText(temps_au_tour[i]);
                    break;
                case 41:
                    TextView _40 = findViewById(R.id._40); _40.setVisibility(View.VISIBLE);
                    TextView chrono40 = findViewById(R.id.chrono40); chrono40.setVisibility(View.VISIBLE);
                    chrono40.setText(temps_au_tour[i]);
                    break;
                case 42:
                    TextView _41 = findViewById(R.id._41); _41.setVisibility(View.VISIBLE);
                    TextView chrono41 = findViewById(R.id.chrono41); chrono41.setVisibility(View.VISIBLE);
                    chrono41.setText(temps_au_tour[i]);
                    break;
                case 43:
                    TextView _42 = findViewById(R.id._42); _42.setVisibility(View.VISIBLE);
                    TextView chrono42 = findViewById(R.id.chrono42); chrono42.setVisibility(View.VISIBLE);
                    chrono42.setText(temps_au_tour[i]);
                    break;
                case 44:
                    TextView _43 = findViewById(R.id._43); _43.setVisibility(View.VISIBLE);
                    TextView chrono43 = findViewById(R.id.chrono43); chrono43.setVisibility(View.VISIBLE);
                    chrono43.setText(temps_au_tour[i]);
                    break;
                case 45:
                    TextView _44 = findViewById(R.id._44); _44.setVisibility(View.VISIBLE);
                    TextView chrono44 = findViewById(R.id.chrono44); chrono44.setVisibility(View.VISIBLE);
                    chrono44.setText(temps_au_tour[i]);
                    break;
                case 46:
                    TextView _45 = findViewById(R.id._45); _45.setVisibility(View.VISIBLE);
                    TextView chrono45 = findViewById(R.id.chrono45); chrono45.setVisibility(View.VISIBLE);
                    chrono45.setText(temps_au_tour[i]);
                    break;
                case 47:
                    TextView _46 = findViewById(R.id._46); _46.setVisibility(View.VISIBLE);
                    TextView chrono46 = findViewById(R.id.chrono46); chrono46.setVisibility(View.VISIBLE);
                    chrono46.setText(temps_au_tour[i]);
                    break;
                case 48:
                    TextView _47 = findViewById(R.id._47); _47.setVisibility(View.VISIBLE);
                    TextView chrono47 = findViewById(R.id.chrono47); chrono47.setVisibility(View.VISIBLE);
                    chrono47.setText(temps_au_tour[i]);
                    break;
                case 49:
                    TextView _48 = findViewById(R.id._48); _48.setVisibility(View.VISIBLE);
                    TextView chrono48 = findViewById(R.id.chrono48); chrono48.setVisibility(View.VISIBLE);
                    chrono48.setText(temps_au_tour[i]);
                    break;
                case 50:
                    TextView _49 = findViewById(R.id._49); _49.setVisibility(View.VISIBLE);
                    TextView chrono49 = findViewById(R.id.chrono49); chrono49.setVisibility(View.VISIBLE);
                    chrono49.setText(temps_au_tour[i]);
                    break;
                case 51:
                    TextView _50 = findViewById(R.id._50); _50.setVisibility(View.VISIBLE);
                    TextView chrono50 = findViewById(R.id.chrono50); chrono50.setVisibility(View.VISIBLE);
                    chrono50.setText(temps_au_tour[i]);
                    break;
                default:
                    break;
            }
        }
        i++;
    }

    //----------------------------- Gestion tâche de fond ------------------------------------------
    Handler mHandler = new Handler();
    Runnable mHandlerTask = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void run() {
            TextView chrono_actuel = findViewById(R.id.chrono_actuel);
            System.out.println("Current location = " + gps.getLocation().getLatitude() + "-" + gps.getLocation().getLongitude());
            for (int i = 0; (i<120000 && detection_established == false); i++){
                lat_piste[i] = gps.getLocation().getLatitude();
                long_piste[i] = gps.getLocation().getLongitude();
                for (int j = 0; j<120000; j++){
                    if ((lat_piste[i] == lat_piste[j]) && (lat_piste[i] == lat_piste[j])){
                        lat_detection = lat_piste[i];
                        long_detection = long_piste[i];
                        detection_established = true;
                        System.out.println("Location detection = " + lat_detection + "-" + long_detection);
                        SystemClock.setCurrentTimeMillis(0);
                    }
                }
            }
            if ((gps.getLocation().getLatitude() == lat_detection) && (gps.getLocation().getLongitude() == long_detection)
            &&  (gps.getLocation().getLatitude() != 0) && (gps.getLocation().getLongitude() != 0)) {
                System.out.println("Passage endroit détection");
                passage_endroit_detection();
                time_in_ms = 0;
                chrono_actuel.setText("0:00:000");
            }else{
                time_in_ms = time_in_ms + INTERVAL;
                int secs = (int) (time_in_ms/1000);
                int mins = secs/60;
                secs%=60;
                int millis = (int) (time_in_ms%1000);
                chrono_actuel.setText(""+mins+":"+secs+":"+ millis);
            }
            mHandler.postDelayed(mHandlerTask, INTERVAL);
        }
    };

    // ----------------------- Fin gestion tâche de fond -------------------------------------------

    // Function to check and request permission
    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                Chronometre.this,
                permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat
                    .requestPermissions(
                            Chronometre.this,
                            new String[] { permission },
                            requestCode);
        }

    }
}