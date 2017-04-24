package com.example.enrique.oroverde.clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.enrique.oroverde.R;

import java.util.ArrayList;

/**
 * Created by enrique on 24/04/2017.
 */

public class ListViewAdapter2 extends BaseAdapter {

    private ArrayList<Item2> ArrayListItem;
    private Context context;
    private LayoutInflater layoutInflater;

    public ListViewAdapter2(Context context, ArrayList<Item2> arrayListItem) {
        this.context = context;
        ArrayListItem = arrayListItem;
    }

    @Override
    public int getCount() {
        return ArrayListItem.size();
    }

    @Override
    public Object getItem(int i) {
        return ArrayListItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vistaItem = layoutInflater.inflate(R.layout.activity_itemdos, viewGroup, false);
        TextView tvfolio = (TextView) vistaItem.findViewById(R.id.tvFolio);
        TextView tvfecha = (TextView) vistaItem.findViewById(R.id.tvFecha);
        TextView tvtotal = (TextView) vistaItem.findViewById(R.id.tvTotal);

        //agarra los valores para insertarlos en los elementos
        tvfolio.setText(ArrayListItem.get(i).getFolio());
        tvfecha.setText(ArrayListItem.get(i).getFecha());
        tvtotal.setText(ArrayListItem.get(i).getTotal());
        return vistaItem;
    }

}
