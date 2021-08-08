package shop.pitstopauto.android.activities.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import shop.pitstopauto.android.activities.MainActivity;
import shop.pitstopauto.android.R;
import shop.pitstopauto.android.dependancies.Constants;
import shop.pitstopauto.android.models.User;

import static shop.pitstopauto.android.dependancies.AppController.TAG;

public class SignInActivity extends AppCompatActivity  {

    private TextInputLayout til_username;
    private TextInputEditText username;
    private TextInputLayout til_password;
    private TextInputEditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stash.init(this);
        setContentView(R.layout.activity_sign_in);
        AndroidNetworking.initialize(getApplicationContext());

        til_username = findViewById(R.id.til_username);
        username = findViewById(R.id.etxt_username);
        til_password = findViewById(R.id.til_password);
        password = findViewById(R.id.etxt_password);



        ((View) findViewById(R.id.btn_login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateNulls()){

                    doLoginRequest();

                }

            }
        });

        ((View) findViewById(R.id.tv_forgot_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mint = new Intent(SignInActivity.this, ResetPasswordActivity.class);
                mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mint);

            }
        });
        ((View) findViewById(R.id.tv_sign_up)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mint = new Intent(SignInActivity.this, SignUpActivity.class);
                mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mint);

            }
        });

        hideSoftKeyboard();

    }


    private void doLoginRequest() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", password.getText().toString());
            jsonObject.put("username", username.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.ENDPOINT+ Constants.LOGIN)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept-Encoding", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .addHeaders("User-Agent","PostmanRuntime/7.28.3")
                .addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

                        Log.e(TAG, response.toString());


                        try {
                            String token = response.has("token") ? response.getString("token") : "";
                            String error = response.has("error") ? response.getString("error") : "";
                            String message = response.has("message") ? response.getString("message") : "";

                            if (token.contains("")){

                                String access_token = response.has("token") ? response.getString("token") : "";
                                JSONObject user = response.getJSONObject("user");
                                    int id = user.has("id") ? user.getInt("id") : 0;
                                    String email = user.has("email") ? user.getString("email") : "";
                                    String first_name = user.has("first_name") ? user.getString("first_name") : "";
                                    String surname = user.has("last_name") ? user.getString("last_name") : "";
                                    String msisdn = user.has("msisdn") ? user.getString("msisdn") : "";
                                    JSONObject role = user.getJSONObject("role");
                                        int role_id = role.has("id") ? role.getInt("id") : 0;
                                        String role_name = role.has("role") ? role.getString("role") : "";
                                int code = response.has("code") ? response.getInt("code") : 0;

                                User newUser = new User(access_token,first_name,surname,email,msisdn,id,role_id,role_name,code);
                                Stash.put(Constants.LOGGED_IN_USER, newUser);

                                Intent mint = new Intent(SignInActivity.this, MainActivity.class);
                                Toast.makeText(SignInActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mint);


                            }else if (message.contains("")){

                                Snackbar.make(findViewById(R.id.activity_sign_in), message, Snackbar.LENGTH_LONG).show();

                            }else{

                                Snackbar.make(findViewById(R.id.activity_sign_in), error, Snackbar.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorDetail());

                        if (error.getErrorBody().contains("Incorrect username or password.")){

                            til_username.setError("Please confirm the username is correct!");
                            til_password.setError("Please confirm the password is correct!");

                        }
                        else if(error.getErrorCode() == 500 ){

                            Snackbar.make(findViewById(R.id.activity_sign_in), "Internal Server Error. Please try again later!" , Snackbar.LENGTH_LONG).show();

                        }

                        else {

                            Snackbar.make(findViewById(R.id.activity_sign_in), "" + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        }


                    }
                });

    }

    private boolean validateNulls() {
        boolean valid = true;

        if(TextUtils.isEmpty(username.getText().toString()))
        {
            til_username.setError(getString(R.string.username_required));
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(password.getText().toString()))
        {
            til_password.setError(getString(R.string.password_required));
            valid = false;
            return valid;
        }

        return valid;
    }

    public void hideSoftKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

}