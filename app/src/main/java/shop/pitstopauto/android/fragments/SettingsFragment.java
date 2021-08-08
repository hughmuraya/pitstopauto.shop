package shop.pitstopauto.android.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import shop.pitstopauto.android.R;


public class SettingsFragment extends Fragment {

    private Unbinder unbinder;
    private View root;
    private Context context;

    @BindView(R.id.terms_lyt)
    LinearLayout terms_lyt;

    @BindView(R.id.privacy_lyt)
    LinearLayout privacy_lyt;

    @BindView(R.id.about_app)
    LinearLayout about_app;


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
        root = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, root);

        terms_lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                NavHostFragment.findNavController(SettingsFragment.this).navigate(R.id.nav_terms);
            }
        });

        privacy_lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                NavHostFragment.findNavController(SettingsFragment.this).navigate(R.id.nav_privacy);
            }
        });

        about_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NavHostFragment.findNavController(SettingsFragment.this).navigate(R.id.nav_about_app);
            }
        });


        return root;
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