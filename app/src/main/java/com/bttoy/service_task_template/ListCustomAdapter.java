package com.bttoy.service_task_template;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListCustomAdapter extends ArrayAdapter<ListExampleItem> {

    private ArrayList<ListExampleItem> source;
    private Context ctx;

    public ListCustomAdapter(@NonNull Context context, @NonNull ArrayList<ListExampleItem> objects) {
        super(context, 0, objects);
        ctx = context;
        source = objects;
    }

    @Nullable
    @Override
    public ListExampleItem getItem(int position) {
        return source.get(position);
    }

    @Override
    public int getPosition(@Nullable ListExampleItem item) {
        return source.indexOf(item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ItemListHolder holder;
        ListExampleItem item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.adapter_recycle_view, parent, false);
            holder = new ItemListHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemListHolder) convertView.getTag();
        }
        if (item != null) {
            holder.title.setText(item.getTitle());
            holder.descr1.setText(item.getDescr1());
            holder.descr2.setText(item.getDescr2());
        }
        return convertView;
    }

    static class ItemListHolder {
        TextView title;
        TextView descr1;
        TextView descr2;

        public ItemListHolder(@NonNull View itemView) {
            title = itemView.findViewById(R.id.item_name);
            descr1 = itemView.findViewById(R.id.item_descr1);
            descr2 = itemView.findViewById(R.id.item_descr2);
        }
    }
}
