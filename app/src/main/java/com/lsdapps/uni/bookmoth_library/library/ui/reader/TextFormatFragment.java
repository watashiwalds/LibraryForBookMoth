package com.lsdapps.uni.bookmoth_library.library.ui.reader;

import android.graphics.fonts.FontFamily;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.core.utils.InnerToast;
import com.lsdapps.uni.bookmoth_library.library.ui.viewmodel.ReaderTextFormatViewModel;

import java.util.ArrayList;

public class TextFormatFragment extends Fragment {
    private ReaderTextFormatViewModel viewModel;

    private SeekBar textSize;
    private ArrayList<TextView> textDemos = new ArrayList<>();;
    private Spinner fontFamily;
    private ArrayList<String> availableFonts = new ArrayList<>();
    private ArrayAdapter<String> fontListAdapter;

    private float ori_textSize;
    private float var_textSize;
    private FontFamily format_fontFamily;

    public TextFormatFragment() {}

    public static TextFormatFragment newInstance() {
        return new TextFormatFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reader_textformat, container, false);

        textSize = v.findViewById(R.id.rdr_stg_textsize);
        textDemos.add(v.findViewById(R.id.textdemo_regular));
        textDemos.add(v.findViewById(R.id.textdemo_bold));
        textDemos.add(v.findViewById(R.id.textdemo_italic));
        textDemos.add(v.findViewById(R.id.textdemo_bolditalic));

        fontFamily = v.findViewById(R.id.rdr_sp_fontfamily);
        availableFonts.add("Alegreya");
        availableFonts.add("Bitter");
        availableFonts.add("Bookely");
        availableFonts.add("Literata");

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(ReaderTextFormatViewModel.class);
        ori_textSize = viewModel.getTextSize().getValue();
        initialize();
    }

    private void initialize() {
        if (ori_textSize > textDemos.get(0).getTextSize() / requireContext().getResources().getDisplayMetrics().scaledDensity) {
            float deltaSize = ori_textSize - textDemos.get(0).getTextSize() / requireContext().getResources().getDisplayMetrics().scaledDensity;
            setDemoTextSize(ori_textSize);
            ori_textSize -= deltaSize;
            textSize.setProgress((int)(deltaSize / (ori_textSize/4)));
        }

        textSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                var_textSize = ori_textSize + ((int)(ori_textSize / 4) * i);
                setDemoTextSize(var_textSize);
                viewModel.setTextSize(var_textSize);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        fontListAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, availableFonts);
        fontFamily.setAdapter(fontListAdapter);
        fontListAdapter.notifyDataSetChanged();
    }

    private void setDemoTextSize(float value) {
        for (TextView tv: textDemos) tv.setTextSize(value);
    }
}