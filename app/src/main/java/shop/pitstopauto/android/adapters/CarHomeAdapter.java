package shop.pitstopauto.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import shop.pitstopauto.android.R;
import shop.pitstopauto.android.dependancies.Tools;
import shop.pitstopauto.android.dependancies.ViewAnimation;
import shop.pitstopauto.android.models.Car;

public class CarHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Car> items = new ArrayList<>();

    private Context context;
    private CarHomeAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(CarHomeAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CarHomeAdapter(Context context, List<Car> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {


        public TextView millage;
        public TextView nextService;
        public TextView vehicleMakeModel;


        public OriginalViewHolder(View v) {
            super(v);
            millage = (TextView) v.findViewById(R.id.txt_millage);
            nextService = (TextView) v.findViewById(R.id.txt_next_service);
            vehicleMakeModel = (TextView) v.findViewById(R.id.txt_vehicle_make_model);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_home, parent, false);
        vh = new CarHomeAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Car obj = items.get(position);
        if (holder instanceof CarHomeAdapter.OriginalViewHolder) {
            CarHomeAdapter.OriginalViewHolder view = (CarHomeAdapter.OriginalViewHolder) holder;

            view.vehicleMakeModel.setText(obj.getVehicle_make()+" "+obj.getVehicle_model());
            view.millage.setText("Millage: "+obj.getMilage()+"KMS");
            view.nextService.setText("Next Service: "+obj.getNext_service()+"KMS");



        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
