package fares.restapp;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import floriankevin.restapp.R;

public class MainActivity extends ActionBarActivity implements View.OnTouchListener {

    private Button b = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button) findViewById(R.id.button);
        b.setOnTouchListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        new HttpRequestTask().execute();
        return true;
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                EditText City = (EditText) findViewById(R.id.editText);
                EditText C_Code = (EditText) findViewById(R.id.editText2);
                String city = City.getText().toString();
                String C_code = C_Code.getText().toString();
                final String url = "http://api.openweathermap.org/data/2.5/weather?q="+city+","+C_code+"&lang=fr&appid=2de143494c0b295cca9337e1e96b00e0";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                String greeting = restTemplate.getForObject(url, String.class);
                return greeting;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String greeting) {
            try {
                JSONObject obj1 = new JSONObject(greeting);
                TextView greetingContentText = (TextView) findViewById(R.id.content_value);
                TextView greetingContentText2 = (TextView) findViewById(R.id.textView11);
                TextView greetingContentText3 = (TextView) findViewById(R.id.textView12);
                TextView greetingContentText4 = (TextView) findViewById(R.id.textView13);
                TextView greetingContentText5 = (TextView) findViewById(R.id.textView14);
                TextView greetingContentText6 = (TextView) findViewById(R.id.textView15);
                TextView greetingContentText7 = (TextView) findViewById(R.id.textView16);
                TextView greetingContentText8 = (TextView) findViewById(R.id.textView17);


                JSONArray weather_arr = obj1.getJSONArray("weather");
                JSONObject weather = weather_arr.getJSONObject(0);
                greetingContentText.setText(weather.getString("description"));
                greetingContentText2.setText(weather.getString("main"));

                JSONObject obj2 = new JSONObject(greeting);

                JSONObject main = obj2.getJSONObject("main");
                greetingContentText3.setText(main.getString("temp"));
                greetingContentText4.setText(main.getString("temp_min"));
                greetingContentText5.setText(main.getString("temp_max"));
                greetingContentText6.setText(main.getString("humidity"));
                greetingContentText8.setText(main.getString("pressure"));

                JSONObject obj3 = new JSONObject(greeting);
                JSONObject wind = obj3.getJSONObject("wind");
                greetingContentText7.setText(wind.getString("speed"));




            } catch (JSONException e) {
                e.printStackTrace();
                TextView greetingContentText = (TextView) findViewById(R.id.content_value);
                greetingContentText.setText("error :"+ e.toString());
            }

        }

    }

}