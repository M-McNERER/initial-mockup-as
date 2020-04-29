package com.initialmockup.as.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.initialmockup.as.R;
import com.initialmockup.as.models.TimerViewModel;
import com.initialmockup.as.thers.Utils;

/*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimerFragment extends Fragment {

    private static final String ARG_DURATION = "param1";

    // Timer
    private CountDownTimer countDownTimer;
    private TimerViewModel timerViewModel;

    // Controls
    private Button playPauseButton;
    private Button resetButton;
    private TextView timerDisplayText;
    private TextView timerSubheading;

    // Values
    private boolean timerIsRunning = false;
    private long timeRemaining;
    private long timerDuration;
    private long taskDuration;

    private Drawable styledPlayIcon;
    private Drawable styledPauseIcon;
    private Drawable styledResetIcon;

//    private OnFragmentInteractionListener mListener;

    public TimerFragment() {
        // Required empty public constructor
    }

    public static TimerFragment newInstance(Long duration) {

        Bundle args = new Bundle();
        TimerFragment fragment = new TimerFragment();

        args.putLong(ARG_DURATION, duration);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        timerViewModel = new ViewModelProvider(getActivity()).get(TimerViewModel.class);
        timerViewModel.getDuration().observe(this, duration ->
                taskDuration = duration
        );

        timerViewModel.getIterations().observe(this, i-> {
            if (i == 0) {
                playPauseButton.setVisibility(View.GONE);
                resetButton.setVisibility(View.GONE);
                timerDisplayText.setVisibility(View.GONE);
                timerSubheading.setVisibility(View.GONE);
            }
        });

        if (args != null) {
            long duration = args.getLong(ARG_DURATION);
            if (duration <= 0){
                throw new IllegalArgumentException("Duration must be natural number. dur: " + duration);
            }
            timerViewModel.setDuration(duration);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frame_timer, container, false);

        timerSubheading             = view.findViewById(R.id.subheadingTextView);

        timerDisplayText            = view.findViewById(R.id.timerDisplayTextView);
        playPauseButton             = view.findViewById(R.id.playPauseButton);
        resetButton                 = view.findViewById(R.id.resetButton);

        timerSubheading.setText("Press play when ready");
        timerDisplayText.setVisibility(View.INVISIBLE);

        styledPlayIcon = Utils.styleDrawable(this, R.style.sb_icon_default, R.drawable.ic_media_play);
        styledPauseIcon = Utils.styleDrawable(this, R.style.sb_icon_default, R.drawable.ic_media_pause);
        styledResetIcon = Utils.styleDrawable(this, R.style.sb_icon_default, R.drawable.ic_refresh);

        playPauseButton.setBackground(styledPlayIcon);
        playPauseButton.setOnClickListener(v -> {
            if (timerIsRunning){
                playPauseButton.setBackground(styledPlayIcon);
                countDownTimer.cancel();
                countDownTimer = null;
                timerIsRunning = false;
            } else {
                playPauseButton.setBackground(styledPauseIcon);
                timerSubheading.setVisibility(View.INVISIBLE);
                startCountdown();
            }
        });

        resetButton.setBackground(styledResetIcon);
        resetButton.setOnClickListener(v -> {
            playPauseButton.setBackground(styledPlayIcon);
            playPauseButton.setVisibility(View.VISIBLE);
            resetButton.animate().rotationBy(360).setDuration(333);
            this.reset();
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    public void startCountdown() {

        playPauseButton.setBackground(styledPauseIcon);
        playPauseButton.setVisibility(View.VISIBLE);

        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        if (timeRemaining != 0) {
            timerDuration = timeRemaining;
        } else {
            timerDuration = taskDuration;
        }

        countDownTimer = new CountDownTimer(timerDuration, 250) {

            public void onTick(long millisUntilFinished) {

                timeRemaining = millisUntilFinished;
                timerDisplayText.setText(formatMilliSecondsToTime(millisUntilFinished));
                timerViewModel.setPosition(taskDuration - millisUntilFinished);
            }

            public void onFinish() {

                timerViewModel.iterationFinish();
                reset();

                if (timerViewModel.isIterating()) {
                    playPauseButton.setBackground(styledPlayIcon);
                    playPauseButton.setVisibility(View.VISIBLE);
                    timerDisplayText.setText(formatMilliSecondsToTime(timerDuration));
                } else {
                    playPauseButton.setVisibility(View.GONE);
                    resetButton.setVisibility(View.GONE);
                    timerSubheading.setVisibility(View.GONE);
                }
            }
        };

        countDownTimer.start();
        timerIsRunning = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    private void reset() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        timerIsRunning = false;
        timeRemaining = 0;
        timerDuration = taskDuration;
    }

    private String formatMilliSecondsToTime(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        return minutes + ":" + formatSeconds(seconds);
    }

    private String formatSeconds(long seconds) {

        if (seconds == 0) { return "00"; }
        if (seconds < 10) { return "0" + seconds; }

        return String.valueOf(seconds);
    }
}
