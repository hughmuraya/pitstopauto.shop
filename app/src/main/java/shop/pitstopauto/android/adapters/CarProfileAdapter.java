package shop.pitstopauto.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import shop.pitstopauto.android.R;
import shop.pitstopauto.android.models.Car;

public class CarProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Car> items = new ArrayList<>();

    private Context context;
    private CarProfileAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(CarProfileAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CarProfileAdapter(Context context, List<Car> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView vehicleStyle;
        public TextView vehicleMakeModel;

        public OriginalViewHolder(View v) {
            super(v);
            vehicleStyle = (TextView) v.findViewById(R.id.txt_vehicle_style);
            vehicleMakeModel = (TextView) v.findViewById(R.id.txt_vehicle_make_model);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_profile, parent, false);
        vh = new CarProfileAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Car obj = items.get(position);
        if (holder instanceof CarProfileAdapter.OriginalViewHolder) {
            CarProfileAdapter.OriginalViewHolder view = (CarProfileAdapter.OriginalViewHolder) holder;

            view.vehicleMakeModel.setText(obj.getVehicle_make()+" "+obj.getVehicle_model());
            view.vehicleStyle.setText("Vehicle Style: "+obj.getVehicle_style());


        }
    }

       @Override
    public int getItemCount() {
        return items.size();
    }
}

