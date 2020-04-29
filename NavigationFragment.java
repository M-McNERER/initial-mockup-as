package com.innovateatc.autoscreen.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.innovateatc.autoscreen.R;
import com.innovateatc.autoscreen.others.ContextState;
import com.innovateatc.autoscreen.others.Event;
import com.innovateatc.autoscreen.others.GuidedWorkflow;
import com.innovateatc.autoscreen.models.PatientViewModel;
import com.innovateatc.autoscreen.others.WorkflowEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NavigationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NavigationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationFragment extends Fragment {

    private static final String TAG = "NavigationFragment";

    private ContextState contextState = ContextState.getInstance();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button homeButton;
    private View navNext;

    private TextView navBackText;
    private TextView navNextText;
    private View navNextImageView;

    private ContextThemeWrapper normalWrapper, disabledWrapper;

    private OnFragmentInteractionListener mListener;

    public NavigationFragment() { /* Required empty public constructor */ }

    public static NavigationFragment newInstance() {

        NavigationFragment fragment = new NavigationFragment();
//        Bundle args = new Bundle();
//
//        args.putString(Settings.CTX_STATE, state.toString());
//        fragment.setArguments(args);

        Log.d(TAG, "Created new instance.");
        return fragment;
    }

    public interface OnFragmentInteractionListener {
        void onNavigateTasks();
        void onNavigateAudioTip();
        void onNavigateVideoTutorial();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d(TAG, "Has args, but not doing anything with em'");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.launch_layout, container, false);

        Drawable drawable;

        View navBack                    = view.findViewById(R.id.navBack);
        navBackText                     = view.findViewById(R.id.navBackTextView);
        View navBackImageView           = view.findViewById(R.id.navBackImageView);

        View navMaterials               = view.findViewById(R.id.navMaterials);
        View navMaterialsImageView      = view.findViewById(R.id.navMaterialsImageView);

        View navAudioTip                = view.findViewById(R.id.navAudioTip);
        View navAudioTipImageView       = view.findViewById(R.id.navAudioTipImageView);

        View navVideoTutorial           = view.findViewById(R.id.navVideoTutorial);
        View navVideoTutorialImageView  = view.findViewById(R.id.navVideoTutorialImageView);

        View navNotes                   = view.findViewById(R.id.navNotes);
        View navNotesImageView          = view.findViewById(R.id.navNotesImageView);

        navNext                         = view.findViewById(R.id.navNext);
        navNextText                     = view.findViewById(R.id.navNextTextView);
        navNextImageView                = view.findViewById(R.id.navNextImageView);

        normalWrapper = new ContextThemeWrapper(getActivity(), R.style.nav_icon_default);
        disabledWrapper = new ContextThemeWrapper(getActivity(), R.style.nav_icon_disabled);
        /*
        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back, normalWrapper.getTheme());
        navBackImageView.setBackground(drawable);

        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_toys, normalWrapper.getTheme());
        navMaterialsImageView.setBackground(drawable);

        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_volume, normalWrapper.getTheme());
        navAudioTipImageView.setBackground(drawable);

        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_videocam, normalWrapper.getTheme());
        navVideoTutorialImageView.setBackground(drawable);

        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_pencil, normalWrapper.getTheme());
        navNotesImageView.setBackground(drawable);

        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_forward, disabledWrapper.getTheme());       navNextImageView.setBackground(drawable);


         */
        navBack.setOnClickListener(v -> GuidedWorkflow.getInstance().prevState());
        navBackText.setOnClickListener(v -> GuidedWorkflow.getInstance().prevState());

        navMaterials.setOnClickListener(v -> {});
        navAudioTip.setOnClickListener(v -> navigateAudioTip());
        navVideoTutorial.setOnClickListener(v -> navigateVideoTutorial());
        navNotes.setOnClickListener(v -> {});

        navNext.setOnClickListener(v -> GuidedWorkflow.getInstance().nextState());
        navNextText.setOnClickListener(v -> GuidedWorkflow.getInstance().nextState());

        updateNavigation();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    private void navigateAudioTip() {
        mListener.onNavigateAudioTip();
    }

    private void navigateVideoTutorial() {

        mListener.onNavigateVideoTutorial();
    }

    @Subscribe
    public void onNavigate(Event event) {

        if (event instanceof WorkflowEvent) {
            updateNavigation();
        }
    }

    private void updateNavigation() {
        GuidedWorkflow guidedWorkflow = GuidedWorkflow.getInstance();

        switch (guidedWorkflow.getState()) {
            case INFO:
                navNextText.setText(R.string.navMaterials);
                navBackText.setText(R.string.home);
                break;
            case MATERIALS:
                navNextText.setText(R.string.navItem1);
                navBackText.setText(R.string.navInfo);
                break;
            case FREE_PLAY:
                navNextText.setText(R.string.navItem2);
                navBackText.setText(R.string.navMaterials);
                break;
            case ITEM_2:
                navNextText.setText(R.string.navItem3);
                navBackText.setText(R.string.navItem1);
                break;
            case ITEM_3:
                navNextText.setText(R.string.navItem4);
                navBackText.setText(R.string.navItem2);
                break;
            case ITEM_4:
                navNextText.setText(R.string.navItem5);
                navBackText.setText(R.string.navItem3);
                break;
            case ITEM_5:
                navNextText.setText(R.string.navItem6);
                navBackText.setText(R.string.navItem4);
                break;
            case ITEM_6:
                navNextText.setText(R.string.navItem7);
                navBackText.setText(R.string.navItem5);
                break;
            case ITEM_7:
                navNextText.setText(R.string.navItem8);
                navBackText.setText(R.string.navItem6);
                break;
            case ITEM_8:
                navNextText.setText(R.string.navItem9);
                navBackText.setText(R.string.navItem7);
                break;
            case ITEM_9:
                navNextText.setText(R.string.navCoding);
                navBackText.setText(R.string.navItem8);
                break;
            default:
                Log.w(TAG, "Executing a default case in navigateNext(). CTX: " + contextState.toString());
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
}
