package in.project.android.indiatouristplaces;

import android.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class AndhraPradesh extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_andhra_pradesh,container,false);
        getActivity().setTitle("Andhra Pradesh");
        Toast.makeText(getActivity(),"Andhra Pradesh", Toast.LENGTH_SHORT).show();
        return v;
    }
}
