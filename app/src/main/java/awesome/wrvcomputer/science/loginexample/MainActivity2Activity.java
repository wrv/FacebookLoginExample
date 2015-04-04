package awesome.wrvcomputer.science.loginexample;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;


public class MainActivity2Activity extends ActionBarActivity {

    private TextView textView;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String firstName = extras.getString("firstName");
        String lastName = extras.getString("lastName");
        String pic = extras.getString("picURI");

        textView = (TextView) findViewById(R.id.textviewNew);
        mp = MediaPlayer.create(this, R.raw.meow);
        textView.setText("Welcome, " + firstName+"!");

        setTitle(firstName + " " + lastName);
        new SetImageURI().execute(pic);

    }

    public void logout(View view){
        finish();
    }

    public void finish() {
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        super.finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
        return true;
    }

    public void pictureClick(View v){
        mp.start();
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

        return super.onOptionsItemSelected(item);
    }

    private class SetImageURI extends AsyncTask<String, Void, Drawable> {

        @Override
        protected Drawable doInBackground(String... params) {
            Drawable dr = null;
            try {
                URL url = new URL(params[0]);
                InputStream img =  (InputStream) url.getContent();
                dr = Drawable.createFromStream(img, "src");
            } catch (Exception e){
                textView.setText("Uh oh! An error occurred: " + e.toString());
            }
            return dr;
        }

        @Override
        protected void onPostExecute(Drawable result) {
            ImageButton imgView = (ImageButton) findViewById(R.id.imgView);
            imgView.setImageDrawable(result);
        }

    }
}
