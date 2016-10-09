package com.example.abhay.abhaystranslator;

import java.util.Locale;



import android.app.Activity;

import android.content.Intent;

import android.os.AsyncTask;

import android.os.Bundle;

import android.speech.tts.TextToSpeech;

import android.support.design.widget.Snackbar;
import android.util.Log;

import android.view.Menu;

import android.view.View;

import android.view.View.OnClickListener;

import android.widget.Button;

import android.widget.EditText;

import android.widget.Spinner;

import android.widget.TextView;

import android.widget.Toast;



import com.memetix.mst.language.Language;

import com.memetix.mst.translate.Translate;



public class MainActivity extends Activity implements TextToSpeech.OnInitListener {



    private int check_code = 0;

    Spinner lang;

    Button say;

    EditText text;

    TextView translatedText;

    String original=null;

    String translated=null;

    String langSelected;

    private TextToSpeech convert;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        lang=(Spinner)findViewById(R.id.selectLanguage);

        say=(Button)findViewById(R.id.say);

        text=(EditText)findViewById(R.id.text);

        translatedText=(TextView)findViewById(R.id.translatedtext);



        Intent check = new Intent();

        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);

        startActivityForResult(check, check_code);



        say.setOnClickListener(new OnClickListener() {



            @Override

            public void onClick(View v) {



                class bgStuff extends AsyncTask<Void, Void, Void>{



                    @Override

                    protected Void doInBackground(Void... params) {

                        // TODO Auto-generated method stub



                        Translate.setClientId("abhay1994"); //Change this

                        Translate.setClientSecret("Lih+h+wbact5pplCuLkOdazimFPn3Ssua3qoenGt8DY=");  //Change this



                        Log.i("calling.","speak");

                        original=text.getText().toString();

                        Log.i("Original",original);

                        langSelected=String.valueOf(lang.getSelectedItem());

                        Log.i("SELECTED LANGUAGE",langSelected);



                        if (text!=null && text.length()>0) {

                            try

                            {

                                if (langSelected.equalsIgnoreCase("ENGLISH"))

                                {

                                    convert.setLanguage(Locale.US);

                                    translated = Translate.execute(original, Language.ENGLISH);
                                    Snackbar.make(findViewById(android.R.id.content), "HEARD WHAT YOU TYPED", Snackbar.LENGTH_LONG)
                                            .show();
                                    Snackbar.make(findViewById(android.R.id.content), "NOW SELECTED HINDI AND LEARN IT", Snackbar.LENGTH_LONG)
                                            .show();
                                    Log.i("t.", translated);

                                } else if (langSelected.equalsIgnoreCase("HINDI"))

                                {

                                    convert.setLanguage(Locale.GERMAN);

                                    translated = Translate.execute(original, Language.HINDI);
                                    Snackbar.make(findViewById(android.R.id.content), "CONGO NOW YOU KNOW HINDI :P", Snackbar.LENGTH_LONG)
                                            .show();

                                    Log.i("t", translated);

                                }

                            }

                            catch(Exception e)

                            {

                                Log.i("Error",e.toString());

                            }

                        }

                        return null;

                    }



                    @Override

                    protected void onPostExecute(Void result) {

                        translatedText.setText(translated);

                        Toast.makeText(MainActivity.this, "SPEAK UP :- " + translated, Toast.LENGTH_LONG).show();

                        convert.speak(translated, TextToSpeech.QUEUE_ADD, null);



                    }

                }

                new bgStuff().execute();

            }

        });

    }



    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == check_code) {

            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {

                // success, create the TTS instance

                convert = new TextToSpeech(this, this);

            }

            else {

                // missing data, install it

                Intent installIntent = new Intent();

                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);

                startActivity(installIntent);

            }

        }

    }



    @Override

    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            Toast.makeText(MainActivity.this,"Engine is initialized", Toast.LENGTH_LONG).show();



            int result = convert.setLanguage(Locale.GERMAN);



            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

                Log.i("TTS", "This Language is not supported");

            }

            else {

                //speakOut("Ich");

                Log.i("TTS", "This Language is supported");

            }

        }

        else if (status == TextToSpeech.ERROR) {

            Toast.makeText(MainActivity.this,"Error occurred while initializing engine", Toast.LENGTH_LONG).show();

        }

    }

}