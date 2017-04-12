package com.example.enrique.oroverde.clases;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enrique.oroverde.R;

import java.util.ArrayList;

/**
 * Created by enrique on 12/04/2017.
 */

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<Item> ArrayListItem;
    private Context context;
    private LayoutInflater layoutInflater;

    public ListViewAdapter(Context context, ArrayList<Item> arrayListItem) {
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
        View vistaItem = layoutInflater.inflate(R.layout.activity_item, viewGroup, false);
        ImageView ivImagen = (ImageView) vistaItem.findViewById(R.id.lvImagen);
        TextView tvTexto = (TextView) vistaItem.findViewById(R.id.tvTitulo);
        TextView tvContenido = (TextView) vistaItem.findViewById(R.id.tvContenido);

        byte[] decodeString = Base64.decode(ArrayListItem.get(i).getImagen(),Base64.DEFAULT);
        Bitmap decoded = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);

        ivImagen.setImageBitmap(decoded);
        tvTexto.setText(ArrayListItem.get(i).getTitulo());
        tvContenido.setText(ArrayListItem.get(i).getContenido());
        return vistaItem;
    }
}
