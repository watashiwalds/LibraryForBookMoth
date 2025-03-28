package com.lsdapps.uni.bookmoth_library.library.ui.reader;

import android.graphics.fonts.FontFamily;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.lsdapps.uni.bookmoth_library.R;
import com.lsdapps.uni.bookmoth_library.library.core.utils.InnerToast;

import java.util.ArrayList;

public class TextFormatFragment extends Fragment {
    private TextView contentView;

    private SeekBar textSize;
    private ArrayList<TextView> textDemos = new ArrayList<>();;
    private Spinner fontFamily;
    private ArrayList<String> availableFonts = new ArrayList<>();
    private ArrayAdapter<String> fontListAdapter;

    private float ori_textSize;
    private float var_textSize;
    private FontFamily format_fontFamily;

    private TextFormatFragment(TextView tv) {
        contentView = tv;
    }

    public static TextFormatFragment newInstance(TextView contentView) {
        return new TextFormatFragment(contentView);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reader_textformat, container, false);

        ori_textSize = contentView.getTextSize() / requireContext().getResources().getDisplayMetrics().scaledDensity;
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

        initialize();
        return v;
    }

    private void initialize() {
        textSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                var_textSize = ori_textSize + ((int)(ori_textSize / 4) * i);
                InnerToast.show(requireContext(), String.format("%.2f %.2f", var_textSize, ori_textSize));
                for (TextView tv: textDemos) tv.setTextSize(var_textSize);
                contentView.setTextSize(var_textSize);
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
}