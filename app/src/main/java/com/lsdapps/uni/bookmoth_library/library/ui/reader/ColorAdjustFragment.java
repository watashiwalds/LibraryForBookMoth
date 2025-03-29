package com.lsdapps.uni.bookmoth_library.library.ui.reader;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.ui.viewmodel.ReaderColorAdjustViewModel;

public class ColorAdjustFragment extends Fragment {
    private ReaderColorAdjustViewModel viewModel;

    private SeekBar brightness;
    private Spinner textColor;
    private Spinner frameColor;

    public ColorAdjustFragment() {
        // Required empty public constructor
    }

    public static ColorAdjustFragment newInstance() {
        return new ColorAdjustFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reader_coloradjust, container, false);
        brightness = v.findViewById(R.id.rdr_sb_brightness);
        textColor = v.findViewById(R.id.rdr_sp_textcolor);
        frameColor = v.findViewById(R.id.rdr_sp_framecolor);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReaderColorAdjustViewModel.class);
        initialize();
    }

    private void initialize() {
            //brightness
        brightness.setProgress(viewModel.getBrightness().getValue() - 30);
        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (!b) return;
                viewModel.setBrightness(i + 30);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}