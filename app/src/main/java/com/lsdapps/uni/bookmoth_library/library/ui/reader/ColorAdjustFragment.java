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
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.ui.adapter.ImageTextSpinnerAdapter;
import com.lsdapps.uni.bookmoth_library.library.ui.viewdata.ImageTextItem;
import com.lsdapps.uni.bookmoth_library.library.ui.viewmodel.ReaderColorAdjustViewModel;

import java.util.ArrayList;
import java.util.List;

public class ColorAdjustFragment extends Fragment {
    private ReaderColorAdjustViewModel viewModel;

    private SeekBar brightness;
    private Spinner textColor;
    private Spinner frameColor;

    private List<ImageTextItem> textValues;
    private List<ImageTextItem> frameValues;

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

        textValues = new ArrayList<>();
        textValues.add(new ImageTextItem(getResources().getString(R.string.color_white), false, 0xFFF8F8F8));
        textValues.add(new ImageTextItem(getResources().getString(R.string.color_black), false, 0xFF080808));
        textValues.add(new ImageTextItem(getResources().getString(R.string.color_ink), false, 0xFF15195D));
        frameValues = new ArrayList<>();
        frameValues.add(new ImageTextItem(getResources().getString(R.string.color_amoled), false, 0xFF000000));
        frameValues.add(new ImageTextItem(getResources().getString(R.string.color_light), false, 0xFFFFFFFF));
        frameValues.add(new ImageTextItem(getResources().getString(R.string.color_paper), false, 0xFFFFFFEE));

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

            //color things

        ImageTextSpinnerAdapter textCAdapter = new ImageTextSpinnerAdapter(requireContext(), textValues);
        textColor.setAdapter(textCAdapter);
        textColor.setSelection(textValues.indexOf(textValues.stream().filter(k -> k.getRid() == viewModel.getTextColor().getValue()).findFirst().get()));
        textColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.setTextColor(textValues.get(i).getRid());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        ImageTextSpinnerAdapter frameCAdapter = new ImageTextSpinnerAdapter(requireContext(), frameValues);
        frameColor.setAdapter(frameCAdapter);
        frameColor.setSelection(frameValues.indexOf(frameValues.stream().filter(k -> k.getRid() == viewModel.getFrameColor().getValue()).findFirst().get()));
        frameColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.setFrameColor(frameValues.get(i).getRid());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }
}