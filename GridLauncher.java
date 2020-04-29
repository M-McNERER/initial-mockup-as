package com.initialmockup.as.fragments;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.widget.TextView;
import android.widget.ImageView;
import android.widget.GridLayout;

import com.initialmockup.as.R;
import com.initialmockup.as.interfaces.Launcher;
import com.initialmockup.as.others.Event;
import com.initialmockup.as.models.IconModel;

import java.util.ArrayList;

public class GridLauncher extends ScreeningFragment implements Launcher {

    private static final String TAG = "GridLauncher";
    private ArrayList<IconModel> models = new ArrayList<>();
    private GridLayout gridLayout;


    public static GridLauncher newInstance(IconModel... params) {
        GridLauncher gridLauncher = new GridLauncher();

        for (IconModel icon : params) {
            gridLauncher.models.add(icon);
        }
        return gridLauncher;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_launcher, container, false);
        rootContainer = container;
        
        /*Inflate grid layout and add it to GridLauncher */
        gridLayout = view.findViewById(R.id.grid_launcher);

        for (IconModel icon : models) {
            this.addChild(icon);
        }
        return view;
    }

    public void addChild(IconModel iconModel) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_tile, gridLayout, false);

        /*format Icons in IconModel */
        ImageView iconView = view.findViewById(R.id.image);
        TextView captionView = view.findViewById(R.id.caption);
        captionView.setText(iconModel.getCaption());
        iconView.setImageResource(iconModel.getDrawableID());

        /*Preform an action when clicked */
        view.setOnClickListener(v -> iconModel.performAction());
        gridLayout.addView(view);
    }
    public void removeChild(int index) {
        gridLayout.removeViewAt(index);
    }

    public void onNextListener(Event event) {
         if (event instanceof WorkflowEvent) {
+            rootContainer.removeAllViews();
+        }
        
    }

    public void onPrevListener(Event event) {
         if (event instanceof WorkflowEvent) {
+            rootContainer.removeAllViews();
+        }
    }
}


