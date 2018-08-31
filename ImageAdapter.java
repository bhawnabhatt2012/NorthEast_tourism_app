package in.project.android.indiatouristplaces;

/**
 * Created by android on 22-12-2016.
 */

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;
//import in.project.android.indiatouristplaces.ImageLoader;

public class ImageAdapter extends BaseAdapter {

    private Activity activity;
    private Context context;
    private ArrayList<String> URL;
    private ArrayList<String> NAME;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    public ImageAdapter(Activity a, ArrayList<String> url, ArrayList<String> name) {
        activity = a;
        context = a.getApplicationContext();
        URL = url;
        NAME = name;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());

    }

    public int getCount() {
        return URL.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item, null);

        //TextView text=(TextView)vi.findViewById(R.id.stateName);;
        ImageView image=(ImageView)vi.findViewById(R.id.stateImage);

        image.setAdjustViewBounds(true);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,500));
        imageLoader.DisplayImage(URL.get(position), image,(NAME.get(position)).toString());
        return vi;
    }

}