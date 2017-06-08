package adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import geo.rutas.madrid.com.madridencantada.R;
import models.Lugar;

public class LugarAdapter extends BaseAdapter {

    private Context context;
    private List<Lugar> lugares;
    private ViewHolder holder;


    public LugarAdapter(Context context, List<Lugar> lugares) {
        this.context = context;
        this.lugares = lugares;
    }


    @Override
    public int getCount() {
        return this.lugares.size();
    }

    @Override
    public Object getItem(int position) {
        return this.lugares.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = new ViewHolder();

        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item_lugar, parent, false);
        }

        // Set data into the view.
        holder.ivImagenLugar = (ImageView) rowView.findViewById(R.id.iv_lugar);
        holder.tvTituloLugar = (TextView) rowView.findViewById(R.id.tv_nombre_item);

        Lugar item = this.lugares.get(position);
        holder.tvTituloLugar.setText(item.getNombre());
        holder.ivImagenLugar.setImageResource(item.getImagenPequena());

        return rowView;
    }



    static class ViewHolder{
        //economizar memoria respecto a los listview
        ImageView ivImagenLugar;
        TextView tvTituloLugar;
    }

}