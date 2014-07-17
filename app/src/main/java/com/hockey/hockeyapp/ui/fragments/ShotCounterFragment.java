package com.hockey.hockeyapp.ui.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hockey.hockeyapp.R;
import com.hockey.hockeyapp.model.ShotLog;
import com.hockey.hockeyapp.ui.adapters.TextWatcherAdapter;
import com.hockey.hockeyapp.ui.utils.DatabaseHandler;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static android.R.layout.simple_spinner_item;


/**
 * Created by Owner on 7/10/2014.
 */

@EFragment(R.layout.shot_log_screen)
public class ShotCounterFragment extends Fragment {


    private DatabaseHandler db;
    private TextView recordLog;

    private static int[] COLORS = new int[]{Color.GREEN, Color.BLUE,Color.MAGENTA, Color.RED };
    private int shotValues;
    private static String[] NAME_LIST = new String[]{"Wrist", "Snap", "Slap", "Backhand"};
    private CategorySeries mSeries = new CategorySeries("");
    private DefaultRenderer mRenderer = new DefaultRenderer();
    private GraphicalView mChartView;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d", Locale.ENGLISH);

    public ShotCounterFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        db = new DatabaseHandler(this.getActivity());

            db.addShotType(new ShotLog("Wrist", 0));
            db.addShotType(new ShotLog("Snap", 0));
            db.addShotType(new ShotLog("Slap", 0));
            db.addShotType(new ShotLog("Backhand", 0));


        //List<ShotLog> shotLogList = db.getAllShotLogs();

        final View rootView = inflater.inflate(R.layout.shot_log_screen,container, false);

        recordLog = (TextView) rootView.findViewById(R.id.shot_log_screen_log_btn);

        recordLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onClickLogBtn(rootView);
            }
        });

        onSetupGraph(rootView);
        setupStatsView(rootView);
        //db.close();
        return rootView;

    }
    @Click(R.id.shot_log_screen_log_btn)
    public void onClickLogBtn(final View rootView)
    {
        final View view = rootView.inflate(getActivity(),R.layout.shot_log_dialog,null);
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        final EditText formEditText = (EditText) view.findViewById(R.id.shot_log_dialog_et);
        final String[] shotType = new String[1];
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.shotType, simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shotType[0] = parent.getSelectedItem().toString();
                //System.out.println(shotType[0]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final boolean[] isValid = {false};
        //formEditText.addValidator(new NotZeroValidator("Can't be zero"));
        //formEditText.addValidator(new NoSpaceValidator());
        formEditText.addTextChangedListener(new TextWatcherAdapter(){
            @Override
            public void afterTextChanged(Editable editable)
            {
                isValid[0] = true;
            }
        });

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view)
                .setTitle("Enter Shot Count")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isValid[0]) {
                            int count = Integer.parseInt(formEditText.getText().toString());

                           // String shotType = (String)spinner.getSelectedItem();
                            ShotLog wristUpdate = new ShotLog(shotType[0], count);
                            ShotLog snapUpdate = new ShotLog("Snap", 20);
                            System.out.println(shotType[0] + shotType[0].length());


                            db.updateShotLog(wristUpdate);
                            db.updateShotLog(snapUpdate);
                            setupStatsView(rootView);
                            updateGraph();
                            dialog.dismiss();

                        } else {
                            Toast.makeText(getActivity(), "Weight log was NOT recorded", Toast.LENGTH_SHORT).show();
                        }
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        alertDialog.show();

    }

    private void setupStatsView(View V)
    {
        TextView wristCount = (TextView) V.findViewById(R.id.wrist_log_current);
        TextView snapCount = (TextView) V.findViewById(R.id.snap_log);
        TextView slapCount = (TextView) V.findViewById(R.id.slap_log);
        TextView backhandCount = (TextView) V.findViewById(R.id.backhand_log);

        db = new DatabaseHandler(this.getActivity());


        wristCount.setText(String.valueOf((db.getShotLog("Wrist").get_shotCount())));
        snapCount.setText(String.valueOf((db.getShotLog("Snap").get_shotCount())));
        slapCount.setText(String.valueOf((db.getShotLog("Slap").get_shotCount())));
        backhandCount.setText(String.valueOf((db.getShotLog("Backhand").get_shotCount())));
    }

    public void onSetupGraph(View V)
    {
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.WHITE);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setChartTitle("Shot Log");
        mRenderer.setLabelsTextSize(25);
        mRenderer.setLegendTextSize(25);
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setMargins(new int[]{15, 15, 15, 0});
        mRenderer.setZoomButtonsVisible(false);
        mRenderer.setStartAngle(90);
        mRenderer.setDisplayValues(false);

        List<ShotLog> tempLog = db.getAllShotLogs();
        double j;
        int sum = 0;
        for (ShotLog temp: tempLog)
        {
            sum = sum + temp.get_shotCount();
        }

        for(int i = 0; i < 4;i++)
        {
            j = tempLog.get(i).get_shotCount();

            double percent = 0;
            if(sum != 0) {
                percent = j / sum * 100;
            }
            BigDecimal bd = new BigDecimal(percent);
            bd = bd.round(new MathContext(3));
            double rounded = bd.doubleValue();
            mSeries.add(NAME_LIST[i] + "  " + rounded +"% "  , j );
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
            mRenderer.addSeriesRenderer(renderer);


        }

        if(mChartView != null)
        {
            mChartView.repaint();
        }

    }

    public void updateGraph()
    {
        List<ShotLog> tempLog = db.getAllShotLogs();
        double j;
        int sum = 0;
        for (ShotLog temp: tempLog)
        {
            sum = sum + temp.get_shotCount();
        }

        for(int i = 0; i < 4;i++)
        {
            j = tempLog.get(i).get_shotCount();

            double percent = 0;
            if(sum != 0) {
                percent = j / sum * 100;
            }
            BigDecimal bd = new BigDecimal(percent);
            bd = bd.round(new MathContext(3));
            double rounded = bd.doubleValue();
            mSeries.set(i, NAME_LIST[i] + "  " + rounded +"% "  , j);

            //SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            //renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
            //mRenderer.addSeriesRenderer(renderer);


        }

        if(mChartView != null)
        {
            mChartView.repaint();
        }

    }


    @Override
    public void onResume()
    {
        super.onResume();
        setupStatsView(getView());
        if (mChartView == null) {
            LinearLayout layout = (LinearLayout) getView().findViewById(R.id.shot_log_graph);
            mChartView = ChartFactory.getPieChartView(getActivity(), mSeries, mRenderer);
            mRenderer.setClickEnabled(true);
            mRenderer.setSelectableBuffer(10);
            mRenderer.setPanEnabled(false);

            mChartView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();

                    if (seriesSelection == null) {
                        Toast.makeText(getActivity(), "No chart element was clicked", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(),"Chart element data point index "+ (seriesSelection.getPointIndex()+1) + " was clicked" + " point value="+ seriesSelection.getValue(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            mChartView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    SeriesSelection seriesSelection = mChartView.getCurrentSeriesAndPoint();
                    if (seriesSelection == null) {
                        Toast.makeText(getActivity(),"No chart element was long pressed", Toast.LENGTH_SHORT);
                        return false;
                    }
                    else {
                        Toast.makeText(getActivity(),"Chart element data point index "+ seriesSelection.getPointIndex()+ " was long pressed",Toast.LENGTH_SHORT);
                        return true;
                    }
                }
            });
            layout.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        else {
            mChartView.repaint();
        }


    }
    }



