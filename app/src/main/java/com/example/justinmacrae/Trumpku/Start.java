package com.example.justinmacrae.Trumpku;

import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Random;

import io.fabric.sdk.android.Fabric;

public class Start extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "zDaFiVHwepiHgfwUMfUtWtxA5 ";
    private static final String TWITTER_SECRET = "eUDV4DVpxyvmferBDB8jzpkvYRactPJIuIQSUrbrmV7xCjtnld";


    TextToSpeech t1;
    TextView help,line1,line2,line3;
    Button b1,b2;
    String myname1;
    String[] syllable;
    String[] sentences= new String [19];
    String[]id_array=new String[19];
    char[] chars;
    Integer count,current_count;
    public static final String URL_ROOT_TWITTER_API = "https://api.twitter.com";
    public static final String URL_SEARCH = URL_ROOT_TWITTER_API + "/1.1/search/tweets.json?q=";
    public static final String URL_AUTHENTICATION = URL_ROOT_TWITTER_API + "/oauth2/token";

    public static final String URL_WORLD_TRENDING = "https://api.twitter.com/1.1/trends/place.json?id=1";


    public static final String CONSUMER_KEY = "zDaFiVHwepiHgfwUMfUtWtxA5";
    public static final String CONSUMER_SECRET = "eUDV4DVpxyvmferBDB8jzpkvYRactPJIuIQSUrbrmV7xCjtnld";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_start);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        help = (TextView) findViewById(R.id.help);
        line1 = (TextView) findViewById(R.id.lineone);
        line2 = (TextView) findViewById(R.id.linetwo);
        line3 = (TextView) findViewById(R.id.linethree);
        count = 0;
        current_count = 0;
        Random rand = new Random();
        final int n = rand.nextInt(15);
        String log_message=String.valueOf(n);
        Log.d("StackOverflow",log_message );
        BufferedReader reader;
        int z=0;
        try{
            final InputStream file = getAssets().open("tweet.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){
                sentences[z]=line;
                Log.d("StackOverflow", line);
                line = reader.readLine();
                z++;
                if(z==19){
                    break;
                }
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
        z=0;
        try{
            final InputStream file = getAssets().open("id.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();
            while(line != null){
                id_array[z]=line;
                Log.d("StackOverflow", line);
                line = reader.readLine();
                z++;
                if(z==19){
                    break;
                }
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }



//When it starts up
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
                if (status == TextToSpeech.SUCCESS) {
                    t1.speak("Let's do this.", TextToSpeech.QUEUE_FLUSH, null);
                }

            }
        }
        );

//When you click the button, we need it to generate a string of the poem and read it out.
        //Right now just reads out what you type in
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setText("Listen again?");
                b2.setVisibility(View.VISIBLE);
                int flag=0;
                ///////////////////////////////////////////
                count = 0;
                current_count = 0;



                String toSpeak = sentences[n].trim().toLowerCase();
                String build="";
                String buildtotal="";
                syllable = toSpeak.split(" ");
                for (int i = 0; i < syllable.length; i++) {
                    build+=syllable[i]+" ";
                    if(flag==0){
                        if(count==5){
                            line1.setText(build);
                            buildtotal+=build;
                            build="";
                            flag+=1;
                            count=0;
                        }
                    }
                    if(flag==1){
                        if (count==7){
                            line2.setText(build);
                            buildtotal+=build;
                            build="";
                            flag+=1;
                            count=0;
                        }
                    }
                    if(flag==2){
                        if (count==5 || i==syllable.length-1){
                            line3.setText(build);
                            buildtotal+=build;
                            build="";
                            flag+=1;
                            count=0;
                        }



                    }


                    syllable[i].toLowerCase();
                    chars = syllable[i].toCharArray();
                    current_count = 0;
                    for (int x = 0; x < chars.length; x++) {
                        String temp = "";
                        temp = String.valueOf(chars[x]);
                        if ("aeiou".contains(temp)) {
                            if (x == chars.length - 1) {
                                current_count += 0;

                            } else if ("aeiou".contains(String.valueOf(chars[x + 1]))) {
                                current_count += 0;
                            } else {
                                current_count += 1;
                            }


                        }
                    }

                    if (current_count == 0) {
                        current_count += 1;
                    }
                    count += current_count;


                }

                //Toast.makeText(getApplicationContext(), count.toString(), Toast.LENGTH_SHORT).show();
                t1.speak(buildtotal, TextToSpeech.QUEUE_FLUSH, null);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      // TODO: Use a more specific parent
                                      final ViewGroup parentView = (ViewGroup) getWindow().getDecorView().getRootView();
                                      // TODO: Base this Tweet ID on some data from elsewhere in your app
                                      long tweetId = Long.parseLong(id_array[n]);
                                      TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
                                                  @Override
                                                  public void success(Result<Tweet> result) {
                                                      TweetView tweetView = new TweetView(Start.this, result.data);
                                                      parentView.addView(tweetView);


                                                  }

                                                  @Override
                                                  public void failure(TwitterException exception) {
                                                      Log.d("TwitterKit", "Load Tweet failure", exception);
                                                  }

                                              });
                                      AlertDialog alertDialog = new AlertDialog.Builder(Start.this).create();
                                      alertDialog.setTitle("Exit");
                                      alertDialog.setMessage("Done seeing the tweet? Click around to get rid of this alert and open your browser.");
                                      alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                      new DialogInterface.OnClickListener() {
                                                  public void onClick(DialogInterface dialog, int which) {
                                                      dialog.dismiss();
                                                      Intent intent = getIntent();
                                                      finish();
                                                      startActivity(intent);

                                                  }
                                              });
                                      WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();

                                      wmlp.gravity = Gravity.TOP | Gravity.LEFT;
                                      wmlp.x = 600;   //x position
                                      wmlp.y = 2000;   //y position
                                      alertDialog.show();



                                  }
                              }
        );
    }





    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}
