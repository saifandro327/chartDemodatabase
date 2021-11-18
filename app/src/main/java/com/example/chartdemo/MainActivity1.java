package com.example.chartdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity1 extends AppCompatActivity {
    private static String TAG = "MainActivity";

    private float[] yData;
    private String[] xData = {"Mitch", "Jessica" };
    PieChart pieChart;
    Description description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(this);
        List<EmployeeModelClass> employeeModelClasses = databaseHelperClass.getEmployeeList();

        Log.d(TAG, "onCreate: starting to create chart");

        pieChart = (PieChart) findViewById(R.id.idPieChart);
        pieChart.setHoleColor(Color.GREEN);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(0);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setRotationEnabled(false);

        pieChart.setDescription(description);

        LegendEntry l1=new LegendEntry("Attended Meetings", Legend.LegendForm.DEFAULT,10f,2f,null,Color.rgb(20,6,104) );
        LegendEntry l2=new LegendEntry("Upcoming Meetings", Legend.LegendForm.CIRCLE,10f,2f,null, Color.rgb(220,220,220));

        Legend l=pieChart.getLegend();
//                        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setCustom(new LegendEntry[]{l1,l2});
       // pieChart.setDescription("Sales by employee (In Thousands $)");
       /* pieChart.setRotationEnabled(true);

       pieChart.setUsePercentValues(true);
       pieChart.setHoleColor(Color.BLUE);
       pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setDescription(description);

        pieChart.setCenterText("Super Cool Chart");
       pieChart.setCenterTextSize(10);
       pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation
*/

        addDataSet();
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                Intent intent = new Intent(MainActivity1.this,ViewEmployeeActivity.class);
                startActivity(intent);
              }

            @Override
            public void onNothingSelected() {

            }
        });





    }


    private void addDataSet() {
        DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(this);
        List<EmployeeModelClass> employeeModelClasses = databaseHelperClass.getEmployeeList();

        yData= new float[]{Float.parseFloat(employeeModelClasses.get(0).getEmail()),Float.parseFloat(employeeModelClasses.get(0).getName())};

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        try{
            for(int i = 0; i < yData.length; i++){
                yEntrys.add(new PieEntry(yData[i] , i));



            }

            for(int i = 1; i < xData.length; i++){
                xEntrys.add(xData[i]);

            }
        }catch (Exception e){

        }




        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Meeting Actions");

        pieDataSet.setSliceSpace(2);

        pieDataSet.setValueTextSize(14);

        pieDataSet.setValueTextSize(12);


        //add colors to dataset
//        ArrayList<Integer> colors = new ArrayList<>();
        int [] color={  Color.rgb(220,220,220),Color.rgb(20,66,104),
        };

        pieDataSet.setColors(color);
        pieDataSet.setValueTextColor(Color.WHITE);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        // legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object




        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(pieData);

        pieChart.invalidate();
    }}