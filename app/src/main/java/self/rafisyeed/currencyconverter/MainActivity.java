package self.rafisyeed.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<HashMap<String,Double>> currencylist;
    EditText amount;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencylist = new ArrayList<HashMap<String, Double>>();
        amount = (EditText) findViewById(R.id.editText);
        result = (TextView) findViewById(R.id.result);
        String url = "https://api.fixer.io/latest?base=USD";
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> categories = new ArrayList<String>();
        categories.add("AUD");
        categories.add("CAD");
        categories.add("GBP");
        categories.add("HKD");
        categories.add("JPY");

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        double total = Double.parseDouble(amount.getText().toString());
        switch (position){
            case (1):
                result.setText(Double.toString(aud*total));
                break;
            case (2):
                result.setText(Double.toString(cad*total));
                break;
            case (3):
                result.setText(Double.toString(gbp*total));
                break;
            case (4):
                result.setText(Double.toString(hkd*total));
                break;
            case (5):
                result.setText(Double.toString(jpy*total));
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
