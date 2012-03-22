
package com.ushahidi.android.app.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ushahidi.android.app.Preferences;
import com.ushahidi.android.app.R;
import com.ushahidi.android.app.models.ListMapModel;

public class ListMapAdapter extends BaseListAdapter<ListMapModel> {

    private int[] colors;

    private ListMapModel listMapModel;

    public ListMapAdapter(Context context) {
        super(context);
        listMapModel = new ListMapModel();
        colors = new int[] {
                R.color.table_odd_row_color, R.color.table_even_row_color
        };
    }

    @Override
    // Use Context instead of FragmentActivity
    public void refresh() {
        final boolean loaded = listMapModel.load();
        if (loaded) {
            this.setItems(listMapModel.getMaps());
        }
    }

    public void refresh(String query) {
        final boolean loaded = listMapModel.filter(context, query);
        if (loaded) {
            this.setItems(listMapModel.getMaps());
        }
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        final int mapId = Preferences.activeDeployment;
        View row = inflater.inflate(R.layout.list_map_item, viewGroup, false);
        int colorPosition = position % colors.length;
        row.setBackgroundResource(colors[colorPosition]);

        Widgets widget = (Widgets)row.getTag();

        if (widget == null) {
            widget = new Widgets(row);
            row.setTag(widget);
        }

        // initialize view with content
        widget.mapName.setText(getItem(position).getName());
        widget.mapDesc.setText(getItem(position).getDesc());
        widget.mapUrl.setText(getItem(position).getUrl());
        widget.mapId.setText(String.valueOf(getItem(position).getId()));

        if (getItem(position).getId() == mapId ) {
            widget.arrow.setImageResource(R.drawable.deployment_selected);
        } else {
            widget.arrow.setImageResource(R.drawable.menu_arrow);
        }

        return row;

    }

    public class Widgets extends com.ushahidi.android.app.views.View {

        TextView mapName;

        TextView mapDesc;

        TextView mapUrl;

        TextView mapId;

        ImageView arrow;

        public Widgets(View convertView) {
            super(convertView);
            mapName = (TextView)convertView.findViewById(R.id.map_list_name);
            mapDesc = (TextView)convertView.findViewById(R.id.map_list_desc);
            mapUrl = (TextView)convertView.findViewById(R.id.map_list_url);
            mapId = (TextView)convertView.findViewById(R.id.map_list_id);
            arrow = (ImageView)convertView.findViewById(R.id.map_arrow);
        }
    }
}