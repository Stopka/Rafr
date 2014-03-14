package cz.cvut.skorpste;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by stopka on 13.3.14.
 */
public class ListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listfragment, container, false);

        ListView articleList = (ListView) view.findViewById(R.id.list);
        String[] values = new String[]{"Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile"};//TODO nahradit objektem
        ArticleArrayAdapter adapter = new ArticleArrayAdapter(this.getActivity(), R.layout.listitem, values);
        articleList.setAdapter(adapter);
        articleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                Toast.makeText(parent.getContext(),
                        "Click ListItem Number " + item, Toast.LENGTH_LONG)
                        .show();
                Intent i = new Intent(parent.getContext(), FeedActivity.class);
                i.putExtra(FeedActivity.ARTICLE, item);//TODO posílat identifikátor
                startActivity(i);
            }

        });
        return view;
    }
}