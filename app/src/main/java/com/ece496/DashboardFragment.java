package com.ece496;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;

        import com.ece496.feature_handling.Assignment;
        import com.ece496.feature_handling.CustomEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Button button1 = (Button) view.findViewById(R.id.button_assignments);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Assignment.class);
                startActivity(i);
            }
        });

        Button button2 = (Button) view.findViewById(R.id.button_custom);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CustomEvent.class);
                startActivity(i);
            }
        });

        return view;
    }

}
