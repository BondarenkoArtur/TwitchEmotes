package com.uabart.twitchemotes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Map;

public class Main extends AppCompatActivity {

    TwitchEmotesGlobal emotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .diskCacheSize(50 * 1024 * 1024) // 50 MiB
                .build();
        ImageLoader.getInstance().init(config);
    }

    public void updateView(final TwitchEmotesGlobal emotes) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageGridFragment fr = new ImageGridFragment();
                for (Map.Entry<String, Emotes> emote : emotes.emotes.entrySet()) {
                    fr.imageNames.add(emote.getKey());
                    String url = emotes.template.large.replaceAll("\\{image_id\\}", emote.getValue().image_id + "");
                    fr.imageUrls.add(url);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main, fr).commit();
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
            new JsonParser(TwitchEmotesGlobal.JSON_URL, TwitchEmotesGlobal.class, new JsonParser.MyCallbackInterface() {
                @Override
                public void onDownloadFinished(Object result) {
                    emotes = (TwitchEmotesGlobal) result;
                    updateView(emotes);
                }
            });
            Toast.makeText(this, "Loading JSON", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
