package self.rafisyeed.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String,Double>> currencylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currencylist = new ArrayList<HashMap<String, Double>>();
        String url = "https://api.fixer.io/latest?base=USD";
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonResponseObject) {
                Log.d("response>>>>>>", jsonResponseObject.toString());
                try{
                    JSONObject baseObj = jsonResponseObject.getJSONObject("base");
                    JSONObject rate = baseObj.getJSONObject("rates");
                    Double AUD = rate.getDouble("AUD");
                    Double CAD = rate.getDouble("CAD");
                    Double GBP = rate.getDouble("GBP");
                    Double HKD = rate.getDouble("HKD");
                    Double JPY = rate.getDouble("JPY");
                    HashMap<String,Double> map = new HashMap<String, Double>();
                    map.put("AUD",AUD);
                    map.put("CAD",CAD);
                    map.put("GBP",GBP);
                    map.put("HKD",HKD);
                    map.put("JPY",JPY);
                    currencylist.add(map);

                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
