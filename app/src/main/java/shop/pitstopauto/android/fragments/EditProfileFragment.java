package shop.pitstopauto.android.fragments;

import static shop.pitstopauto.android.dependancies.AppController.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import shop.pitstopauto.android.R;
import shop.pitstopauto.android.dependancies.Constants;
import shop.pitstopauto.android.models.User;

public class EditProfileFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;

    @BindView(R.id.card_name)
    MaterialTextView card_name;

    @BindView(R.id.card_phone)
    MaterialTextView card_phone;

    @BindView(R.id.etxt_first_name)
    TextInputEditText tv_fname;

    @BindView(R.id.etxt_lastname)
    TextInputEditText tv_lname;

    @BindView(R.id.etxt_phone_number)
    TextInputEditText tv_phone;

    @BindView(R.id.etxt_email)
    TextInputEditText tv_email;

    @BindView(R.id.btn_update_profile)
    MaterialButton btn_update_profile;


    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        this.context = ctx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (User) Stash.getObject(Constants.LOGGED_IN_USER, User.class);

        card_name.setText(loggedInUser.getFirst_name()+" "+loggedInUser.getSurname());
        card_phone.setText(loggedInUser.getMsisdn());

        tv_fname.setText(loggedInUser.getFirst_name());
        tv_lname.setText(loggedInUser.getSurname());
        tv_phone.setText(loggedInUser.getMsisdn());
        tv_email.setText(loggedInUser.getEmail());

        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doUpdateUserDetails();
            }
        });

        return root;
    }

    private void doUpdateUserDetails() {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("email", tv_email.getText().toString());
            jsonObject.put("first_name", tv_fname.getText().toString());
            jsonObject.put("last_name", tv_lname.getText().toString());
            jsonObject.put("msisdn", tv_phone.getText().toString());



        } catch (JSONException e) {
            e.printStackTrace();
        }

        String auth_token = loggedInUser.getAuth_token();

        AndroidNetworking.put(Constants.ENDPOINT+Constants.UPDATE_USER)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept", "gzip, deflate, br")
                .addHeaders("Connection","keep-alive")
                .addHeaders("User-Agent","PostmanRuntime/7.28.3")
                .setContentType("application.json")
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, response.toString());

                        try {

                            boolean  status = response.has("success") && response.getBoolean("success");
                            String error = response.has("error") ? response.getString("error") : "";
                            String message = response.has("message") ? response.getString("message") : "";


                            if (message.equals("User Updated Successfully")){

                                NavHostFragment.findNavController(EditProfileFragment.this).navigate(R.id.nav_menu_profile);
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Snackbar.make(root.findViewById(R.id.frag_edit_profile), message, Snackbar.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorBody());

                        Snackbar.make(root.findViewById(R.id.frag_edit_profile), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}