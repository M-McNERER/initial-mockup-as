package com.intialmockup.as.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.intialmockup.as.R;
import com.intialmockup.as.others.Event;
import com.intialmockup.as.others.GuidedWorkflow;
import com.intialmockup.as.others.Utils;
import com.intialmockup.as..others.WorkflowEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class SidebarFragment extends Fragment {

    private static final String TAG = "SidebarFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View iconPatInfo;
    private View iconMaterials;
    private View iconAssessment;
    private View iconCoding;
    private View iconResults;

    private TextView labelPatInfo;
    private TextView labelMaterials;
    private TextView labelAssessment;
    private TextView labelCoding;
    private TextView labelResults;

    private OnFragmentInteractionListener mListener;

    public SidebarFragment() { /* Required empty public constructor */ }

    public static SidebarFragment newInstance(String param1, String param2) {
        SidebarFragment fragment = new SidebarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sidebar, container, false);

        iconPatInfo         = view.findViewById(R.id.ic_pat_info);
        iconMaterials       = view.findViewById(R.id.ic_checklist);
        iconAssessment      = view.findViewById(R.id.ic_child);
        iconCoding          = view.findViewById(R.id.ic_coding);
        iconResults         = view.findViewById(R.id.ic_results);

        labelPatInfo        = view.findViewById(R.id.sbPatientInfoTextView);
        labelMaterials      = view.findViewById(R.id.sbMaterialsTextView);
        labelAssessment     = view.findViewById(R.id.sbTasksTextView);
        labelCoding         = view.findViewById(R.id.sbCodingTextView);
        labelResults        = view.findViewById(R.id.sbResultsTextView);

        iconPatInfo.setBackground(Utils.styleDrawable(this, R.style.sb_icon_default, R.drawable.ic_pat_info));
        iconMaterials.setBackground(Utils.styleDrawable(this, R.style.sb_icon_default, R.drawable.ic_checklist));
        iconAssessment.setBackground(Utils.styleDrawable(this, R.style.sb_icon_default, R.drawable.ic_child));
        iconCoding.setBackground(Utils.styleDrawable(this, R.style.sb_icon_default, R.drawable.ic_coding));
        iconResults.setBackground(Utils.styleDrawable(this, R.style.sb_icon_default, R.drawable.ic_results));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onWorkflowEvent(Event event) {

        if (event instanceof WorkflowEvent) {
            GuidedWorkflow workflow = GuidedWorkflow.getInstance();

            View target = null;
            View[] icons = new View[] {
                    iconPatInfo,
                    iconMaterials,
                    iconAssessment,
                    iconCoding,
                    iconResults
            };

            TextView[] labels = new TextView[] {
                    labelPatInfo,
                    labelMaterials,
                    labelAssessment,
                    labelCoding,
                    labelResults
            };

            int[] ids = new int[]{
                    R.drawable.ic_pat_info,
                    R.drawable.ic_checklist,
                    R.drawable.ic_child,
                    R.drawable.ic_coding,
                    R.drawable.ic_results
            };

            switch (workflow.getState()) {
                case INFO:
                    target = iconPatInfo;
                    break;
                case MATERIALS:
                    target = iconMaterials;
                    break;
                case FREE_PLAY:
                case ITEM_1_TIMER:
                case ITEM_2:
                case ITEM_3:
                case ITEM_4:
                case ITEM_5:
                case ITEM_6:
                case ITEM_7:
                case ITEM_8:
                case ITEM_9:
                case ITEM_9_TIMER:
                    target = iconAssessment;
                    break;
                case CODING_ITEM_1:
                case CODING_ITEM_2:
                case CODING_ITEM_3:
                case CODING_ITEM_4:
                case CODING_ITEM_5:
                case CODING_ITEM_6:
                case CODING_ITEM_7:
                    target = iconCoding;
                    break;
                case SUMMARY:
                case CONCLUSION:
                    target = iconResults;
                    break;
                default:
                    Log.w(TAG, "Executing a default case. state: " + workflow.getState());
            }

            for (int i = 0; i < icons.length; i++) {
                if (icons[i] == target) {
                    icons[i].setBackground(Utils.styleDrawable(this, R.style.sb_icon_highlighted, ids[i]));
                    labels[i].setTextColor(getResources().getColor(R.color.light_green));
                } else {
                    icons[i].setBackground(Utils.styleDrawable(this, R.style.sb_icon_default, ids[i]));
                    labels[i].setTextColor(getResources().getColor(R.color.black));
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSidebarInteraction(Uri uri);
    }
}
