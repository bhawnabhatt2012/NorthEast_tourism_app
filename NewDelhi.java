package in.project.android.indiatouristplaces;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class NewDelhi extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_new_delhi,container,false);
        getActivity().setTitle("New Delhi");
        Toast.makeText(getActivity(),"New Delhi", Toast.LENGTH_SHORT).show();
        return v;
    }
}
