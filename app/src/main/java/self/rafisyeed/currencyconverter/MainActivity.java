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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<HashMap<String,Double>> currencylist;
    EditText amount;
    TextView result;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencylist = new ArrayList<HashMap<String, Double>>();
        amount = (EditText) findViewById(R.id.editText);
        result = (TextView) findViewById(R.id.result);
        String url = "http://api.fixer.io/latest?base=USD";
        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> categories = new ArrayList<String>();
        categories.add("Select your currency");
        categories.add("AUD");
        categories.add("CAD");
        categories.add("GBP");
        categories.add("HKD");
        categories.add("JPY");

        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setOnItemSelectedListener(MainActivity.this);
            }
        });

        ArrayAdapter spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonResponseObject) {
                Log.d("response>>>>>>>>", jsonResponseObject.toString());
                try{

                    JSONObject rate = jsonResponseObject.getJSONObject("rates");
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
        double aud,cad,jpy,hkd,gbp;
        DecimalFormat twodecimal = new DecimalFormat("0.00");

        // On selecting a spinner item
       //Log.d("",currencylist.toString());
        // String item = parent.getItemAtPosition(position).toString();
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(),"abrar" , Toast.LENGTH_LONG).show

        aud = currencylist.get(0).get("AUD");
        cad = currencylist.get(0).get("CAD");
        gbp = currencylist.get(0).get("GBP");
        hkd = currencylist.get(0).get("HKD");
        jpy = currencylist.get(0).get("JPY");

        switch (position){
            case 1:
                result.setText(twodecimal.format(Double.parseDouble(amount.getText().toString())* aud));
                break;
            case 2:
                result.setText(twodecimal.format(Double.parseDouble(amount.getText().toString())* cad));
                break;
            case 3:
                result.setText(twodecimal.format(Double.parseDouble(amount.getText().toString())* gbp));
                break;
            case 4:
                result.setText(twodecimal.format(Double.parseDouble(amount.getText().toString())* hkd));
                break;
            case 5:
                result.setText(twodecimal.format(Double.parseDouble(amount.getText().toString())* jpy));
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
