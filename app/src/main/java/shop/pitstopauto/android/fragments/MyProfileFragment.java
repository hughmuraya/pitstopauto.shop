package shop.pitstopauto.android.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fxn.stash.Stash;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import shop.pitstopauto.android.R;
import shop.pitstopauto.android.adapters.CarProfileAdapter;
import shop.pitstopauto.android.adapters.MyCarAdapter;
import shop.pitstopauto.android.dependancies.Constants;
import shop.pitstopauto.android.models.Car;
import shop.pitstopauto.android.models.User;


public class MyProfileFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    private User loggedInUser;

    private CarProfileAdapter mAdapter;
    private ArrayList<Car> mycarArrayList;

    @BindView(R.id.tv_full_name)
    MaterialTextView tv_full_name;

    @BindView(R.id.tv_fname)
    MaterialTextView tv_fname;

    @BindView(R.id.tv_lname)
    MaterialTextView tv_lname;

    @BindView(R.id.tv_phone)
    MaterialTextView tv_phone;

    @BindView(R.id.tv_email)
    MaterialTextView tv_email;

    @BindView(R.id.shimmer_my_container)
    ShimmerFrameLayout shimmer_my_container;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.no_vehicles_lyt)
    LinearLayout no_vehicle_lyt;

    @BindView(R.id.error_lyt)
    LinearLayout error_lyt;

    @BindView(R.id.shimmers_my_container)
    ShimmerFrameLayout shimmers_my_container;

    @BindView(R.id.fab_edit_profile)
    FloatingActionButton fab_edit_profile;

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
        root = inflater.inflate(R.layout.fragment_my_profile, container, false);
        unbinder = ButterKnife.bind(this, root);

        loggedInUser = (User) Stash.getObject(Constants.LOGGED_IN_USER, User.class);

        tv_full_name.setText(loggedInUser.getFirst_name()+" "+loggedInUser.getSurname());
        tv_fname.setText(loggedInUser.getFirst_name());
        tv_lname.setText(loggedInUser.getSurname());
        tv_phone.setText(loggedInUser.getMsisdn());
        tv_email.setText(loggedInUser.getEmail());

        mycarArrayList = new ArrayList<>();
        mAdapter = new CarProfileAdapter(context, mycarArrayList);


        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        recyclerView.setAdapter(mAdapter);

        loadMyCar();

        fab_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(MyProfileFragment.this).navigate(R.id.nav_edit_profile);

            }
        });

        return root;
    }

    private void loadMyCar() {

        String auth_token = loggedInUser.getAuth_token();


        AndroidNetworking.get(Constants.ENDPOINT+Constants.MY_CAR)
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

                        mycarArrayList.clear();

                        if (recyclerView!=null)
                            recyclerView.setVisibility(View.VISIBLE);

                        if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }

                        try {

                            JSONArray Carsarray = response.getJSONArray("cars");
                            String  message = response.has("message") ? response.getString("message") : "" ;
                            String  errors = response.has("errors") ? response.getString("errors") : "" ;

                            if (Carsarray.length()>0){

                                JSONArray myArray = response.getJSONArray("cars");

                                if (myArray.length() > 0){


                                    for (int i = 0; i < myArray.length(); i++) {

                                        JSONObject item = (JSONObject) myArray.get(i);

                                        int id = item.has("id") ? item.getInt("id") : 0;
                                        String uuid = item.has("uuid") ? item.getString("uuid") : "";
                                        String vehicle_make = item.has("vehicle_make") ? item.getString("vehicle_make") : "";
                                        String vehicle_model = item.has("vehicle_model") ? item.getString("vehicle_model") : "";
                                        String vehicle_year = item.has("vehicle_year") ? item.getString("vehicle_year") : "";
                                        String vehicle_trim = item.has("vehicle_trim") ? item.getString("vehicle_trim") : "";
                                        String vehicle_style = item.has("vehicle_style") ? item.getString("vehicle_style") : "";
                                        String chasis_number = item.has("chasis_number") ? item.getString("chasis_number") : "";
                                        String next_service = item.has("next_service") ? item.getString("next_service") : "";
                                        String milage = item.has("milage") ? item.getString("milage") : "";
                                        String created_at = item.has("created_at") ? item.getString("created_at") : "";
                                        String updated_at = item.has("updated_at") ? item.getString("updated_at") : "";
                                        String deleted_at = item.has("deleted_at") ? item.getString("deleted_at") : "";
                                        int created_by = item.has("created_by_id") ? item.getInt("created_by_id") : 0;

                                        Car newCar = new Car(id,uuid,vehicle_make,vehicle_model,vehicle_year,vehicle_trim,vehicle_style,chasis_number,next_service,milage,created_at,updated_at,created_by);

                                        mycarArrayList.add(newCar);
                                        mAdapter.notifyDataSetChanged();

                                    }

                                }else {
                                    //not data found
                                    no_vehicle_lyt.setVisibility(View.VISIBLE);

                                }

                            }
                            else {

                                no_vehicle_lyt.setVisibility(View.VISIBLE);
//                                Snackbar.make(root.findViewById(R.id.frag_my_profile),message, Snackbar.LENGTH_LONG).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error

                        if (recyclerView!=null)
                            recyclerView.setVisibility(View.VISIBLE);

                        if (shimmer_my_container!=null){
                            shimmer_my_container.stopShimmerAnimation();
                            shimmer_my_container.setVisibility(View.GONE);
                        }

//                        Log.e(TAG, String.valueOf(error.getErrorCode()));

                        if (error.getErrorCode() == 0){

                            no_vehicle_lyt.setVisibility(View.VISIBLE);
                        }
                        else {

                            error_lyt.setVisibility(View.VISIBLE);
                            Snackbar.make(root.findViewById(R.id.frag_my_profile), "Error: " + error.getErrorBody(), Snackbar.LENGTH_LONG).show();

                        }


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
        shimmer_my_container.startShimmerAnimation();
        shimmers_my_container.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmer_my_container.stopShimmerAnimation();
        shimmers_my_container.startShimmerAnimation();

    }
}