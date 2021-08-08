package shop.pitstopauto.android.activities.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import shop.pitstopauto.android.R;
import shop.pitstopauto.android.dependancies.Constants;

import static shop.pitstopauto.android.dependancies.AppController.TAG;


public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText username;
    private TextInputEditText email;
    private TextInputEditText msisdn;
    private TextInputEditText first_name;
    private TextInputEditText last_name;
    private TextInputEditText password;
    private TextInputEditText re_password;

    private TextInputLayout tilUsername;
    private TextInputLayout tilEmail;
    private TextInputLayout tilMsisdn;
    private TextInputLayout tilFirstname;
    private TextInputLayout tilLastname;
    private TextInputLayout tilPassword;
    private TextInputLayout tilRepassword;

    private ProgressBar pBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initToolbar();
        intialize();

        ((View) findViewById(R.id.btn_register_user)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateNulls()){
                    pBar.setVisibility(View.VISIBLE);
                    doRegistration();
                }

            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
        hideSoftKeyboard();
    }

    private void intialize(){
        username = findViewById(R.id.etxt_reg_username);
        tilUsername = findViewById(R.id.til_reg_username);
        email = findViewById(R.id.etxt_reg_email);
        tilEmail = findViewById(R.id.til_reg_email);
        msisdn = findViewById(R.id.etxt_reg_phone);
        tilMsisdn = findViewById(R.id.til_reg_phone);
        first_name = findViewById(R.id.etxt_reg_fname);
        tilFirstname = findViewById(R.id.til_reg_fname);
        last_name = findViewById(R.id.etxt_reg_lname);
        tilLastname = findViewById(R.id.til_reg_lname);
        password = findViewById(R.id.etxt_reg_password);
        tilPassword = findViewById(R.id.til_reg_password);
        re_password = findViewById(R.id.etxt_reg_repassword);
        tilRepassword = findViewById(R.id.til_reg_repassword);
        pBar = findViewById(R.id.signup_progress);
    }

    private boolean validateNulls() {
        boolean valid = true;

        if(TextUtils.isEmpty(first_name.getText().toString()))
        {
            tilFirstname.setError(getString(R.string.fname_required));
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(last_name.getText().toString()))
        {
            tilLastname.setError(getString(R.string.surname_required));
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(email.getText().toString()))
        {
            tilEmail.setError(getString(R.string.email_required));
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(msisdn.getText().toString()))
        {
            tilMsisdn.setError(getString(R.string.phone_number_required));
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(password.getText().toString()))
        {
            tilPassword.setError(getString(R.string.password_required));
            valid = false;
            return valid;
        }


        if(!password.getText().toString().equals(re_password.getText().toString()))
        {
            tilRepassword.setError(getString(R.string.must_match));
            valid = false;
            return valid;
        }

        return valid;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void hideSoftKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void doRegistration() {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username.getText().toString());
            jsonObject.put("email", email.getText().toString());
            jsonObject.put("msisdn", msisdn.getText().toString());
            jsonObject.put("first_name", first_name.getText().toString());
            jsonObject.put("last_name", last_name.getText().toString());
            jsonObject.put("password", password.getText().toString());
            jsonObject.put("role_id", 3);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.ENDPOINT+Constants.REGISTER)
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .addHeaders("User-Agent","PostmanRuntime/7.28.3")
                .setContentType("application.json")
                .setMaxAgeCacheControl(300000, TimeUnit.MILLISECONDS)
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.e(TAG, response.toString());

                        pBar.setVisibility(View.GONE);

                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String  errors = response.has("error") ? response.getString("error") : "" ;
                            String  messages = response.has("message") ? response.getString("message") : "" ;


                            if (messages.contains("User Added Successfully")){

                                Intent mint = new Intent(SignUpActivity.this, SignInActivity.class);
                                Toast.makeText(SignUpActivity.this, "Registration Successful. Welcome to the family.", Toast.LENGTH_LONG).show();
                                mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mint);

                            }
                            else{

                                Toast.makeText(SignUpActivity.this, errors, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());

                        pBar.setVisibility(View.GONE);

                        Snackbar.make(findViewById(R.id.activity_sign_up), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();
                    }
                });


    }


}