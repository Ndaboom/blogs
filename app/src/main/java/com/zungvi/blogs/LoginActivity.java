package com.zungvi.blogs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.zungvi.blogs.network.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout code_textinput_layout;
    TextInputLayout phone_number;
    Button next_btn;
    public String keyword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        code_textinput_layout = findViewById(R.id.code_textinput_layout);
        next_btn = findViewById(R.id.next_btn);
        phone_number = findViewById(R.id.textInputLayout);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        next_btn.setOnClickListener(this);

        code_textinput_layout.getEditText().setText("243");
        code_textinput_layout.setEnabled(false);
    }

    private void userLogin() {
        keyword = phone_number.getEditText().toString().trim();
        if(keyword != ""){
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(
                    Request.Method.POST,
                    Constants.URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                if (!obj.getBoolean("error")) {
                                    DataStore.getInstance(getApplicationContext()).userLogin(
                                            obj.getInt("id"),
                                            obj.getString("username"),
                                            obj.getString("email")
                                    );
//                                    startActivity(new Intent(getApplicationContext(), Main2Activity.class));
//                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            obj.getString("message"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(
                                    getApplicationContext(),
                                    /**error.getMessage()**/"Something went wrong...please try again",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("identifiant", keyword);
                    return params;
                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }else{
            if(Locale.getDefault().getLanguage() == "fr") {
                Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view == next_btn){

        }
    }
}