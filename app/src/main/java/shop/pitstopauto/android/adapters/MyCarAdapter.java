package shop.pitstopauto.android.adapters;

import android.annotation.SuppressLint;
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

public class MyCarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Car> items = new ArrayList<>();

    private Context context;
    private MyCarAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(MyCarAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyCarAdapter(Context context, List<Car> items) {
        this.items = items;
        this.context = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView vehicleTrim;
        public TextView vehicleStyle;
        public TextView millage;
        public TextView nextService;
        public ImageButton bt_expand;
        public TextView vehicleMakeModel;
        public View lyt_expand;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            vehicleTrim = (TextView) v.findViewById(R.id.txt_vehicle_trim);
            vehicleStyle = (TextView) v.findViewById(R.id.txt_vehicle_style);
            millage = (TextView) v.findViewById(R.id.txt_millage);
            nextService = (TextView) v.findViewById(R.id.txt_next_service);
            bt_expand = (ImageButton) v.findViewById(R.id.bt_expand);
            vehicleMakeModel = (TextView) v.findViewById(R.id.txt_vehicle_make_model);
            lyt_expand = (View) v.findViewById(R.id.lyt_expand);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        vh = new MyCarAdapter.OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Car obj = items.get(position);
        if (holder instanceof MyCarAdapter.OriginalViewHolder) {
            MyCarAdapter.OriginalViewHolder view = (MyCarAdapter.OriginalViewHolder) holder;

            view.vehicleMakeModel.setText(obj.getVehicle_make()+" "+obj.getVehicle_model());
            view.vehicleTrim.setText("Vehicle Trim: "+obj.getVehicle_trim());
            view.vehicleStyle.setText("Vehicle Style: "+obj.getVehicle_style());
            view.millage.setText("Millage: "+obj.getMilage()+"KMS");
            view.nextService.setText("Next Service: "+obj.getNext_service()+"KMS");


            view.bt_expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean show = toggleLayoutExpand(!obj.expanded, v, view.lyt_expand);
                    items.get(position).expanded = show;
                }
            });

        }
    }

    private boolean toggleLayoutExpand(boolean show, View view, View lyt_expand) {
        Tools.toggleArrow(show, view);
        if (show) {
            ViewAnimation.expand(lyt_expand);
        } else {
            ViewAnimation.collapse(lyt_expand);
        }
        return show;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
