package com.uabart.twitchemotes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

public class Main extends AppCompatActivity {

    TwitchEmotesGlobal emotes;
    TextView mLargeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mLargeText = (TextView) findViewById(R.id.large_text);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void setNewLargeText(final String ourString) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLargeText.setText(ourString);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_load_json) {
            new JsonParser("https://twitchemotes.com/api_cache/v2/global.json", TwitchEmotesGlobal.class, new JsonParser.MyCallbackInterface() {
                @Override
                public void onDownloadFinished(Object result) {
                    emotes = (TwitchEmotesGlobal) result;
                    StringBuilder string = new StringBuilder();
                    for (Map.Entry<String, Emotes> emote : emotes.emotes.entrySet()) {
                        string.append(emote.getKey());
                        string.append(": ");
//                            string.append(emote.getValue().description);
                        string.append(emote.getValue().image_id);
                        string.append("\n");
                    }
                    setNewLargeText(string.toString());
                }
            });
            Toast.makeText(this, "Loading JSON", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
