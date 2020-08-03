package com.bttoy.service_task_template;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ListCustomAdapter extends BaseAdapter {

    private Context ctx;
    private ArrayList<ListExampleItem> sauce;

    public ListCustomAdapter(Context ctx, ArrayList<ListExampleItem> sauce) {
        this.ctx = ctx;
        this.sauce = sauce;
    }

    @Override
    public int getCount() {
        return sauce.size();
    }

    @Override
    public Object getItem(int position) {
        return sauce.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ItemListHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.adapter_recycle_view, parent, false);
            holder = new ItemListHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemListHolder) convertView.getTag();
        }
        ListExampleItem item = (ListExampleItem) getItem(position);

        holder.title.setText(item.getTitle());
        holder.descr1.setText(item.getDescr1());
        holder.descr2.setText(item.getDescr2());

        return convertView;
    }


    static class ItemListHolder {
        TextView title;
        TextView descr1;
        TextView descr2;

        ItemListHolder(@NonNull View itemView) {
            title = itemView.findViewById(R.id.item_name);
            descr1 = itemView.findViewById(R.id.item_descr1);
            descr2 = itemView.findViewById(R.id.item_descr2);
        }
    }
}
