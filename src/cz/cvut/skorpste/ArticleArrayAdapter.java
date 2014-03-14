package cz.cvut.skorpste;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by stopka on 13.3.14.
 */
public class ArticleArrayAdapter extends ArrayAdapter {


    public ArticleArrayAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listitem, parent, false);

        Feed item=(Feed)getItem(position);

        TextView title = (TextView) rowView.findViewById(R.id.title);
        title.setText(item.getTitle());

        return rowView;
    }


}
