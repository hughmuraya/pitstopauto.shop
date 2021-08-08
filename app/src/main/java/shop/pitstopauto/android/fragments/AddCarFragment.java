package shop.pitstopauto.android.fragments;

import static shop.pitstopauto.android.dependancies.AppController.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fxn.stash.Stash;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import shop.pitstopauto.android.R;
import shop.pitstopauto.android.activities.auth.SignInActivity;
import shop.pitstopauto.android.activities.auth.SignUpActivity;
import shop.pitstopauto.android.dependancies.Constants;
import shop.pitstopauto.android.models.User;


public class AddCarFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;

    private int carMakeID = 0;
    private int carModelID = 0;
    private int carYearID = 0;

    private String carMake = "";
    private String carModel = "";
    private String carYear = "";


    ArrayList<String> carMakeList;
    ArrayList<String> carModelList;
    ArrayList<String> carYearList;

    @BindView(R.id.carmake_Spinner)
    SearchableSpinner carmake_Spinner;

    @BindView(R.id.carmodel_Spinner)
    SearchableSpinner carmodel_Spinner;

    @BindView(R.id.caryear_Spinner)
    SearchableSpinner caryear_Spinner;

    @BindView(R.id.til_vehicle_trim)
    TextInputLayout til_vehicle_trim;

    @BindView(R.id.etxt_vehicle_trim)
    TextInputEditText etxt_vehicle_trim;

    @BindView(R.id.til_vehicle_style)
    TextInputLayout til_vehicle_style;

    @BindView(R.id.etxt_vehicle_style)
    TextInputEditText etxt_vehicle_style;

    @BindView(R.id.til_chasis_number)
    TextInputLayout til_chasis_number;

    @BindView(R.id.etxt_chasis_number)
    TextInputEditText etxt_chasis_number;

    @BindView(R.id.til_millage)
    TextInputLayout til_millage;

    @BindView(R.id.etxt_millage)
    TextInputEditText etxt_millage;

    @BindView(R.id.til_next_service)
    TextInputLayout til_next_service;

    @BindView(R.id.etxt_next_service)
    TextInputEditText etxt_next_service;

    @BindView(R.id.btn_cancel)
    MaterialButton btn_cancel;

    @BindView(R.id.btn_submit_car)
    MaterialButton btn_submit_car;

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
        root = inflater.inflate(R.layout.fragment_add_car, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (User) Stash.getObject(Constants.LOGGED_IN_USER, User.class);

        loadCarMake();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(AddCarFragment.this).navigate(R.id.nav_menu_my_vehicle);

            }
        });

        btn_submit_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateNulls()){

                    doCarAddition();
                }
            }
        });

        return root;
    }

    private void loadCarMake() {

        String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.get(Constants.ENDPOINT+Constants.CAR_MAKE)
                .addHeaders("Authorization","Token "+ auth_token)
                .addHeaders("Content-Type", "application.json")
                .addHeaders("Accept", "*/*")
                .addHeaders("Accept-Encoding", "gzip, deflate, br")
                .addHeaders("User-Agent","PostmanRuntime/7.28.3")
                .addHeaders("Connection","keep-alive")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response

//                        Log.e(TAG, response.toString());

                        carMakeList = new ArrayList<String>();
                        carMakeList.clear();

                        try {

                            JSONArray Carsarray = response.getJSONArray("cars");

                            if (Carsarray.length()>0){

                                JSONArray myArray = response.getJSONArray("cars");

                                if (myArray.length() > 0){


                                    for (int i = 0; i < myArray.length(); i++) {

                                        String item = (String) myArray.get(i);

                                        carMakeList.add(item);

                                    }

                                    carMakeList.add("Select your car make here...");


                                    ArrayAdapter<String> aa=new ArrayAdapter<String>(context,
                                            android.R.layout.simple_spinner_dropdown_item,
                                            carMakeList){
                                        @Override
                                        public int getCount() {
                                            return super.getCount(); // you don't display last item. It is used as hint.
                                        }
                                    };

                                    int pos = carMakeList.indexOf(new String(""));
                                    if (pos >= carMakeID)
                                        pos=0;

                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                    if (carmake_Spinner != null){
                                        carmake_Spinner.setAdapter(aa);
                                        carmake_Spinner.setSelection(pos);


                                        carmake_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                              carMake = carmake_Spinner.getSelectedItem().toString();
                                              loadCarModel();


                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });

                                    }

                                }else {
                                    //not data found

                                }

                            }
                            else {

//                                Snackbar.make(root.findViewById(R.id.frag_my_vehicle),message, Snackbar.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error

//                        Log.e(TAG, String.valueOf(error.getErrorCode()));

                        Snackbar.make(root.findViewById(R.id.frag_add_car), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();



                    }
                });
    }

    private void loadCarModel() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("make", carMake);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.ENDPOINT+ Constants.CAR_MODEL)
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


                        carModelList = new ArrayList<String>();
                        carModelList.clear();

                        try {

                            JSONArray Modelarray = response.getJSONArray("model");

                            if (Modelarray.length()>0){

                                JSONArray myArray = response.getJSONArray("model");

                                if (myArray.length() > 0){


                                    for (int i = 0; i < myArray.length(); i++) {

                                        String item = (String) myArray.get(i);

                                        carModelList.add(item);

                                    }

                                    carModelList.add("Select your car model here...");


                                    ArrayAdapter<String> aa=new ArrayAdapter<String>(context,
                                            android.R.layout.simple_spinner_dropdown_item,
                                            carModelList){
                                        @Override
                                        public int getCount() {
                                            return super.getCount(); // you don't display last item. It is used as hint.
                                        }
                                    };

                                    int pos = carModelList.indexOf(new String(""));
                                    if (pos >= carModelID)
                                        pos=0;

                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                    if (carmodel_Spinner != null){
                                        carmodel_Spinner.setAdapter(aa);
                                        carmodel_Spinner.setSelection(pos);


                                        carmodel_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                                carModel = carmodel_Spinner.getSelectedItem().toString();
                                                loadCarYear();


                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });

                                    }

                                }else {
                                    //not data found

                                }

                            }
                            else {

//                                Snackbar.make(root.findViewById(R.id.frag_my_vehicle),message, Snackbar.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorDetail());

                        Snackbar.make(root.findViewById(R.id.frag_add_car),error.getErrorDetail(), Snackbar.LENGTH_LONG).show();



                    }
                });

    }

    private void loadCarYear() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("make", carMake);
            jsonObject.put("model", carModel);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.ENDPOINT+ Constants.CAR_YEAR)
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


                        carYearList = new ArrayList<String>();
                        carYearList.clear();

                        try {

                            JSONArray yearArray = response.getJSONArray("year");

                            if (yearArray.length()>0){

                                JSONArray myArray = response.getJSONArray("year");

                                if (myArray.length() > 0){


                                    for (int i = 0; i < myArray.length(); i++) {

                                        String item = (String) myArray.get(i);

                                        carYearList.add(item);

                                    }

                                    carYearList.add("Select your cars year of manufacture here...");


                                    ArrayAdapter<String> aa=new ArrayAdapter<String>(context,
                                            android.R.layout.simple_spinner_dropdown_item,
                                            carYearList){
                                        @Override
                                        public int getCount() {
                                            return super.getCount(); // you don't display last item. It is used as hint.
                                        }
                                    };

                                    int pos = carYearList.indexOf(new String(""));
                                    if (pos >= carYearID)
                                        pos=0;

                                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                    if (caryear_Spinner != null){
                                        caryear_Spinner.setAdapter(aa);
                                        caryear_Spinner.setSelection(pos);


                                        caryear_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                                                carYear = caryear_Spinner.getSelectedItem().toString();



                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });

                                    }

                                }else {
                                    //not data found

                                }

                            }
                            else {

//                                Snackbar.make(root.findViewById(R.id.frag_my_vehicle),message, Snackbar.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e(TAG, error.getErrorDetail());

                        Snackbar.make(root.findViewById(R.id.frag_add_car),error.getErrorDetail(), Snackbar.LENGTH_LONG).show();



                    }
                });

    }

    private boolean validateNulls() {
        boolean valid = true;

        if(TextUtils.isEmpty(etxt_vehicle_trim.getText().toString()))
        {
            til_vehicle_trim.setError(getString(R.string.add_car_trim));
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(etxt_vehicle_style.getText().toString()))
        {
            til_vehicle_style.setError(getString(R.string.add_vehicle_style));
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(etxt_chasis_number.getText().toString()))
        {
            til_chasis_number.setError(getString(R.string.add_chasis_number));
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(etxt_millage.getText().toString()))
        {
            til_millage.setError(getString(R.string.add_millage));
            valid = false;
            return valid;
        }

        if(TextUtils.isEmpty(etxt_next_service.getText().toString()))
        {
            til_next_service.setError(getString(R.string.add_next_service));
            valid = false;
            return valid;
        }

        return valid;
    }

    private void doCarAddition() {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("vehicle_make", carMake);
            jsonObject.put("vehicle_model", carModel);
            jsonObject.put("vehicle_year", carYear);
            jsonObject.put("vehicle_trim", etxt_vehicle_trim.getText().toString());
            jsonObject.put("vehicle_style", etxt_vehicle_style.getText().toString());
            jsonObject.put("next_service", etxt_next_service.getText().toString());
            jsonObject.put("milage", etxt_millage.getText().toString());
            jsonObject.put("chasis_number", etxt_chasis_number.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post(Constants.ENDPOINT+Constants.ADD_CAR)
                .addHeaders("Authorization","Token "+ loggedInUser.getAuth_token())
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
//                        Log.e(TAG, response.toString());

                        try {

                            String  messages = response.has("message") ? response.getString("message") : "" ;
                            String  errors = response.has("error") ? response.getString("error") : "" ;

                            if (messages.contains("Vehicle details recorded successfully")){

                                Toast.makeText(context, messages, Toast.LENGTH_SHORT).show();
                                NavHostFragment.findNavController(AddCarFragment.this).navigate(R.id.nav_menu_my_vehicle);

                            }
                            else{

                                Snackbar.make(root.findViewById(R.id.frag_add_car), errors, Snackbar.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
//                        Log.e(TAG, error.getErrorBody());



                        Snackbar.make(root.findViewById(R.id.frag_add_car), "Error: "+error.getErrorBody(), Snackbar.LENGTH_LONG).show();
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