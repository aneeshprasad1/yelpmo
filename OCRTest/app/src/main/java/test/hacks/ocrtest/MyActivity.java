package test.hacks.ocrtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void TestOCR()throws UnirestException{
        HttpResponse<JsonNode> response = Unirest.post("https://camfind.p.mashape.com/image_requests")
                .header("X-Mashape-Key", "<required>")
                .field("focus[x]", "480")
                .field("focus[y]", "640")
                .field("image_request[altitude]", "27.912109375")
                .field("image_request[language]", "en")
                .field("image_request[latitude]", "35.8714220766008")
                .field("image_request[locale]", "en_US")
                .field("image_request[longitude]", "14.3583203002251")
                .field("image_request[remote_image_url]", "http://upload.wikimedia.org/wikipedia/en/2/2d/Mashape_logo.png")
                .asJson();
    }
}
