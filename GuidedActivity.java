package com.initialmockup.as.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.initialmockup.as.R;
import com.initialmockup.as.fragments.GridLauncher;
import com.initialmockup.as.fragments.CodingFragment;
import com.initialmockup.as.fragments.AudioTipFragment;
import com.initialmockup.as.fragments.SequencedInteraction;
import com.initialmockup.asfragments.VideoControllerFragment;
import com.initialmockup.asfragments.VideoTutorialFragment;
import com.initialmockup.asfragments.InformationFragment;
import com.initialmockup.as.fragments.NavigationFragment;
import com.initialmockup.as.fragments.ScreeningFragment;
import com.initialmockup.as.fragments.SidebarFragment;

import com.initialmockup.as.models.CodeModel;

import com.initialmockup.as.models.IconModel;
import com.initialmockup.as.others.SequenceDirective;

import com.initialmockup.as.others.CodingDirective;
import com.initialmockup.as.others.ContextState;
import com.initialmockup.as.others.Event;
import com.initialmockup.as.others.GuidedWorkflow;
import com.initialmockup.as.others.MediaEvent;
import com.initialmockup.as.others.Utils;
import com.initialmockup.as.others.WorkflowEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.URI;

public class GuidedActivity extends AppCompatActivity implements
        InformationFragment.OnFragmentInteractionListener,
        SidebarFragment.OnFragmentInteractionListener,
        NavigationFragment.OnFragmentInteractionListener,
        AudioTipFragment.OnFragmentInteractionListener,
        VideoTutorialFragment.OnFragmentInteractionListener,
        VideoControllerFragment.OnFragmentInteractionListener {

    private static final String TAG = "GuidedActivity";

    private InformationFragment informationFragment;
    private ScreeningFragment screeningFragment;
    private NavigationFragment navigationFragment;
    private AudioTipFragment audioTipFragment;
    private VideoTutorialFragment videoTutorialFragment;
    private VideoControllerFragment videoControllerFragment;
    private CodingFragment codingFragment;

    private View activityHeader;
    private TextView activityHeaderText;
    private TextView activitySubheadingTextView;
    private View activityNavigationView;
    private FrameLayout primaryContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided);

        Window window = getWindow();

        ImageView appBarMenu            = findViewById(R.id.appBarMenu);

        activityHeader                  = findViewById(R.id.action_bar);
        activityHeaderText              = findViewById(R.id.headingTextView);
        activitySubheadingTextView      = findViewById(R.id.subheadingTextView);
        activityNavigationView          = findViewById(R.id.navigation_container);

        primaryContainer                = findViewById(R.id.primary_container);

        appBarMenu.setImageDrawable(Utils.styleDrawable(this, R.style.nav_icon_default, R.drawable.ic_checklist));
        appBarMenu.setOnClickListener(v -> {
            Log.e(TAG, "onCreate: uh oh!" );
        });

        if (findViewById(R.id.navigation_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            navigationFragment = NavigationFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.navigation_container, navigationFragment)
                    .commit();
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.light_green));
    }

    @Override
    public void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
        GuidedWorkflow.getInstance().nextState();
    }

    public void onNavigateTasks() {

    }

    public void onNavigateAudioTip() {

        navigationFragment = null;
        int resource = 0;

        switch (GuidedWorkflow.getInstance().getState()) {
            case INFO:
                resource = R.raw.enter_subject_information;
                break;
            case MATERIALS:
                resource = R.raw.materials_load_page;
                break;
            case FREE_PLAY:
                resource = R.raw.en_free_play_audio;
                break;
            case ITEM_2:
                break;
            case ITEM_3:
                resource = R.raw.item3_audio_tip;
                break;
            case ITEM_4:
                resource = R.raw.item4_audio_tip;
                break;
            case ITEM_5:
                resource = R.raw.item5_audio_tip;
                break;
            case ITEM_6:
                resource = R.raw.item6_audio_tip;
                break;
            case ITEM_7:
                resource = R.raw.item7_audio_tip;
                break;
            case ITEM_8:
                resource = R.raw.item8_audio_tip;
                break;
            case ITEM_9:
                resource = R.raw.item9_audio_tip;
                break;
            default:
                Log.w(TAG, "executing a default switch statement");
        }

        if (findViewById(R.id.navigation_container) != null && resource != 0) {

            audioTipFragment = AudioTipFragment.newInstance(resource);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.navigation_container, audioTipFragment)
                    .commit();
        }
    }

    public void onNavigateVideoTutorial() {

        int resource = -1;
        int subtitleId = 0;
        GuidedWorkflow.State state = GuidedWorkflow.getInstance().getState();

        switch (state) {
            case FREE_PLAY:
                resource = 1;
                subtitleId = R.raw.item1_subs;
                break;
            case ITEM_2:
                resource = 2;
                subtitleId = R.raw.item2_subs;
                break;
            case ITEM_3:
                resource = 3;
                subtitleId = R.raw.item3_subs;
                break;
            case ITEM_4:
                resource = 4;
                subtitleId = R.raw.item4_subs;
                break;
            case ITEM_5:
                resource = 5;
                subtitleId = R.raw.item5_subs;
                break;
            case ITEM_6:
                resource = 6;
                subtitleId = R.raw.item6_subs;
                break;
            case ITEM_7:
                resource = 7;
                subtitleId = R.raw.item7_subs;
                break;
            case ITEM_8:
                resource = 8;
                subtitleId = R.raw.item8_subs;
                break;
            case ITEM_9:
                resource = 9;
                subtitleId = R.raw.item9_subs;
                break;
            default:
                Log.w(TAG, "Executing a default case in updateVideoTutorial(). CTX: " + state.toString());
        }

        String uri = getResources().getString(R.string.baseUri);
        uri += resource;
        uri += ".mp4";

        if (findViewById(R.id.navigation_container) != null && resource != -1) {

            videoTutorialFragment = VideoTutorialFragment.newInstance(URI.create(uri), subtitleId);
            videoControllerFragment = VideoControllerFragment.newInstance("hi", "there");

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.primary_container, videoTutorialFragment)
                    .replace(R.id.navigation_container, videoControllerFragment)
                    .commit();

            navigationFragment = null;
        }
    }

    public void onSidebarInteraction(Uri uri) {

    }

    public void onInformationInteraction(Uri uri) {

    }

    public void onVideoTutorialClose() {

        updateLayout(GuidedWorkflow.getInstance().getState());
        videoTutorialFragment = null;
    }

    public void onVideoControllerClose() {

        if (findViewById(R.id.navigation_container) != null) {

            navigationFragment = new NavigationFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.navigation_container, navigationFragment)
                    .commit();
        }
        videoControllerFragment = null;
    }

    public void onAudioTipClose() {

        if (activityNavigationView != null) {
            navigationFragment = new NavigationFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.navigation_container, navigationFragment)
                    .commit();
        }
        audioTipFragment = null;
    }

    @Subscribe
    public void onMediaEvent(Event event) {

        if (event instanceof MediaEvent) {

            audioTipFragment = null;

            switch (event.getMsg()) {
                case MediaEvent.MAT_BAG_OF_TOYS:
                    audioTipFragment = AudioTipFragment.newInstance(R.raw.materials_item1);
                    break;
                case MediaEvent.MAT_JAR_OF_BUBBLES:
                    audioTipFragment = AudioTipFragment.newInstance(R.raw.materials_item2);
                    break;
                case MediaEvent.MAT_JAR_OF_SNACKS:
                    audioTipFragment = AudioTipFragment.newInstance(R.raw.materials_snacks);
                    break;
                case MediaEvent.MAT_NOISE_MAKER:
                    audioTipFragment = AudioTipFragment.newInstance(R.raw.materials_noisemaker);
                    break;
                case MediaEvent.MAT_CAUSE_EFFECT:
                    audioTipFragment = AudioTipFragment.newInstance(R.raw.materials_cause_effect);
                    break;
                default:
                    Log.w(TAG, "onMediaEvent: Executing default case, msg: " + event.getMsg());
            }

            if (audioTipFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.navigation_container, audioTipFragment)
                        .commit();
            }
        }
    }

    @Subscribe
    public void onWorkflowEvent(Event event) {

        final FrameLayout fragmentContainer = findViewById(R.id.primary_container);
        if (fragmentContainer == null) {
            final RuntimeException exc = new RuntimeException("No container available for fragment");

            Log.e(TAG, "", exc);
            throw exc;
        }

        if (event instanceof WorkflowEvent) {
            updateLayout(GuidedWorkflow.getInstance().getState());
        }
    }

    private void updateLayout(GuidedWorkflow.State state) {
        switch (state) {
            case CODING_ITEM_1:

                Resources res = getResources();
                TypedArray ta = res.obtainTypedArray(R.array.asdExam);

                CodingDirective directive = new CodingDirective();

                String[] prompts = res.getStringArray(R.array.asd_prompts);

                activityHeader.setBackgroundColor(getResources().getColor(R.color.red));
                getWindow().setStatusBarColor(getResources().getColor(R.color.red));
                activityHeaderText.setText(R.string.coding_header);
                activitySubheadingTextView.setText("(18 - 36 month cohort)");
                activitySubheadingTextView.setVisibility(View.VISIBLE);

                for (int i = 0; i < prompts.length; i++) {
                    int id = ta.getResourceId(i, 0);
                    if (id > 0) {
                        CodeModel cm = new CodeModel(prompts[i], res.getStringArray(id));
                        directive.addModel(cm);
                    } else {
                        throw new RuntimeException("Error while parsing XML resources");
                    }
                }
                ta.recycle();

                codingFragment = CodingFragment.newInstance(directive);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.primary_container, codingFragment)
                        .commit();
                break;
            case INFO:
                activityHeader.setBackgroundColor(getResources().getColor(R.color.light_green));
                activityHeaderText.setText(R.string.infoActivityHeader);
                activitySubheadingTextView.setVisibility(View.GONE);

                informationFragment = InformationFragment.newInstance();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.primary_container, informationFragment)
                        .commit();
                break;
            case MATERIALS:
                /*Create arrays for audio  tips and drawable resources */
                int[] audioRes = new int[] {
                        R.raw.materials_item1,
                        R.raw.materials_item2,
                        R.raw.materials_snacks,
                        R.raw.materials_cause_effect
                };

                int[] imageRes = new int[] {

                        R.drawable.free_play,
                        R.drawable.balloons,
                        R.drawable.snacks,
                        R.drawable.free_play2
                };

                Resources resources = getResources();
                String[] iconText = resources.getStringArray(R.array.iconText);
                int N = iconText.length;

                /*Icon with caption from XML and image from array*/

                IconModel[] iconModelArray = new IconModel[N];
                for (int i = 0; i < N; i++) {
                    final int j = i;
                    IconModel icon = new IconModel(iconText[i], imageRes[i]) {


                        @Override
                        public void performAction() {
                            /*Audio tip added OnClick */
                            audioTipFragment = AudioTipFragment.newInstance(audioRes[j]);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.navigation_container, audioTipFragment)
                                    .commit();

                        }
                    };
                    iconModelArray[i] = icon;
                }
                /* Icon placed in grid */
                GridLauncher gridLauncher = GridLauncher.newInstance(iconModelArray);


                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.primary_container, gridLauncher)
                        .commit();

                break;
            case FREE_PLAY:
                SequenceDirective sequenceDirective = new SequenceDirective() {
                    @Override
                    public void onFinish() {
                        throw new AssertionError("WAAAAA!");

                    }

                    @Override
                    public void onTimeExpired() {
                        Log.e(TAG, "Emitted when time has expired for this attempt");
                    }
                };

                sequenceDirective.setDuration(1);
                sequenceDirective.setIterations(3);
                sequenceDirective.setEarlyExit("title","msg", "opt1", "opt2");
                sequenceDirective.addTeleprompter(getString(R.string.instructions), getResources().openRawResource(R.raw.en_us_free_play_cues));
                sequenceDirective.addTeleprompter(getString(R.string.observations), getResources().openRawResource(R.raw.en_us_free_play_ant));

                screeningFragment = SequencedInteraction.newInstance(sequenceDirective);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.primary_container, screeningFragment)
                        .commit();
               break;
            default:
                Log.w(TAG, "Executing a default case for onWorkflowEvent(). CTX: " + ContextState.getInstance().toString());
        }
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}

