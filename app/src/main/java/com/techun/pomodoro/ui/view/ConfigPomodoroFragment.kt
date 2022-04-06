package com.techun.pomodoro.ui.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.techun.pomodoro.data.model.TaskModel
import com.techun.pomodoro.databinding.FragmentConfigPomodoroBinding
import com.techun.pomodoro.ui.view.adapters.AllTasksAdapter
import com.techun.pomodoro.ui.view.adapters.CompletedTaskAdapter


data class Score(
    val name: String,
    val score: Int,
)

class ConfigPomodoroFragment : Fragment(), OnChartValueSelectedListener {
    private var _binding: FragmentConfigPomodoroBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: AllTasksAdapter

    //    private lateinit var barChart: BarChart
    private lateinit var lineChart: LineChart
    private var scoreList = ArrayList<Score>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfigPomodoroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//       pieGraphic()

        lineChart = binding.chart
        initLineChart()
        setDataToLineChart()

        val allTasks = listOf(
            TaskModel(
                "Mobile app design",
                "30 minutes",
                "3/4",
                "25 min",
                0
            ),
            TaskModel(
                "Ui animation",
                "0 minutes",
                "1/2",
                "25 min",
                1
            ),
            TaskModel(
                "Study languages",
                "0 minutes",
                "1/1",
                "25 min",
                2
            ),
            TaskModel(
                "do homework",
                "0 minutes",
                "0/2",
                "25 min",
                1
            ),
            TaskModel(
                "Study AI",
                "0 minutes",
                "1/1",
                "25 min",
                2
            )
        )
        AllTasksAdapter(context).setMenu(allTasks)
        recyclerInit(allTasks)
        /* scoreList = getScoreList()

         initBarChart()


         //now draw bar chart with dynamic data
         val entries: ArrayList<BarEntry> = ArrayList()

         //you can replace this data object with  your custom object
         for (i in scoreList.indices) {
             val score = scoreList[i]
             entries.add(BarEntry(i.toFloat(), score.score.toFloat()))
         }

         val barDataSet = BarDataSet(entries, "")
         barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

         val data = BarData(barDataSet)
         barChart.data = data

         barChart.invalidate()*/
    }

    private fun recyclerInit(temp: List<TaskModel>) {
        adapter = AllTasksAdapter(context)
        adapter.setMenu(temp)
        binding.rvAllTasksStats.layoutManager = LinearLayoutManager(context)
        binding.rvAllTasksStats.adapter = adapter
    }

    private fun initLineChart() {
        //hide grid lines
        lineChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        lineChart.axisRight.isEnabled = false

        //remove legend
        lineChart.legend.isEnabled = false


        //remove description label
        lineChart.description.isEnabled = false

        //add animation
        lineChart.animateX(1000, Easing.EaseInSine)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(false)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f
    }


    /* private fun initBarChart() {
 //        hide grid lines
         barChart.axisLeft.setDrawGridLines(false)
         val xAxis: XAxis = barChart.xAxis
         xAxis.setDrawGridLines(false)
         xAxis.setDrawAxisLine(false)

         //remove right y-axis
         barChart.axisRight.isEnabled = false

         //remove legend
         barChart.legend.isEnabled = false


         //remove description label
         barChart.description.isEnabled = false


         //add animation
         barChart.animateY(1000)

         // to draw label on xAxis
         xAxis.position = XAxis.XAxisPosition.BOTTOM
         xAxis.valueFormatter = MyAxisFormatter()
         xAxis.setDrawLabels(true)
         xAxis.granularity = 1f
         xAxis.labelRotationAngle = +90f

     }*/


    inner class MyAxisFormatter : IndexAxisValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            println("MyAxisFormatter - getAxisLabel: index $index")
            return if (index < scoreList.size) {
                scoreList[index].name
            } else {
                ""
            }
        }
    }

    private fun setDataToLineChart() {
        //now draw bar chart with dynamic data
        val entries: ArrayList<Entry> = ArrayList()

        scoreList = getScoreList()

        //you can replace this data object with  your custom object
        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(Entry(i.toFloat(), score.score.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "")

        val data = LineData(lineDataSet)
        lineChart.data = data

        lineChart.invalidate()
    }


    private fun getScoreList(): ArrayList<Score> {
        scoreList.add(Score("task1", 90))
        scoreList.add(Score("task2", 75))
        scoreList.add(Score("task3", 85))
        scoreList.add(Score("task4", 45))
        scoreList.add(Score("task5", 63))

        return scoreList
    }


/*    private fun pieGraphic() {

        binding.chart.setUsePercentValues(true)
        binding.chart.description.isEnabled = false
        binding.chart.setExtraOffsets(5f, 10f, 5f, 5f)

        binding.chart.dragDecelerationFrictionCoef = 0.95f

        binding.chart.setCenterTextTypeface(Typeface.DEFAULT)
        binding.chart.centerText = generateCenterSpannableText()

        binding.chart.isDrawHoleEnabled = true
        binding.chart.setHoleColor(Color.WHITE)

        binding.chart.setTransparentCircleColor(Color.WHITE)
        binding.chart.setTransparentCircleAlpha(110)

        binding.chart.holeRadius = 58f
        binding.chart.transparentCircleRadius = 61f

        binding.chart.setDrawCenterText(true)

        binding.chart.rotationAngle = 0f
        // enable rotation of the chart by touch
        binding.chart.isRotationEnabled = true
        binding.chart.isHighlightPerTapEnabled = true

        binding.chart.setOnChartValueSelectedListener(this)

        binding.seekBarX.progress = 4
        binding.seekBarY.progress = 10

        binding.chart.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);
        val l: Legend = binding.chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f
        binding.chart.setEntryLabelColor(Color.BLACK)
        binding.chart.setEntryLabelTypeface(Typeface.SANS_SERIF)
        binding.chart.setEntryLabelTextSize(12f)

        setData(10, 6f)
    }*/

    /* private fun setData(count: Int, range: Float) {
         val entries: ArrayList<PieEntry> = ArrayList()

         // NOTE: The order of the entries when being added to the entries array determines their position around the center of
         // the chart.
         for (i in 0 until count) {
             entries.add(
                 PieEntry(
                     (Math.random() * range + range / 5).toFloat(),
                     "Name",
                     resources.getDrawable(R.drawable.alert_dark_frame)
                 )
             )
         }
         val dataSet = PieDataSet(entries, "Election Results")
         dataSet.setDrawIcons(false)
         dataSet.sliceSpace = 3f
         dataSet.iconsOffset = MPPointF(0f, 40f)
         dataSet.selectionShift = 5f

         // add a lot of colors
         val colors: ArrayList<Int> = ArrayList()
         for (c in ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
         for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
         for (c in ColorTemplate.COLORFUL_COLORS) colors.add(c)
         for (c in ColorTemplate.LIBERTY_COLORS) colors.add(c)
         for (c in ColorTemplate.PASTEL_COLORS) colors.add(c)
         colors.add(ColorTemplate.getHoloBlue())
         dataSet.colors = colors
 //        dataSet.selectionShift = 0f;
         val data = PieData(dataSet)
         data.setValueFormatter(PercentFormatter())
         data.setValueTextSize(11f)
         data.setValueTextColor(Color.BLACK)
         data.setValueTypeface(Typeface.SANS_SERIF)
         binding.chart.data = data

         // undo all highlights
         binding.chart.highlightValues(null)
         binding.chart.invalidate()
     }*/

    private fun generateCenterSpannableText(): SpannableString {
        val s = SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda")
        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length - 15, 0)
        s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length - 15, 0)
        s.setSpan(RelativeSizeSpan(.8f), 14, s.length - 15, 0)
        s.setSpan(StyleSpan(Typeface.ITALIC), s.length - 14, s.length, 0)
        s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length - 14, s.length, 0)
        return s
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e == null)
            return
        println(
            "VAL SELECTED" +
                    "Value: " + e.y + ", index: " + h?.x
                    + ", DataSet index: " + h?.dataSetIndex
        )
    }

    override fun onNothingSelected() {
        println("PieChart nothing selected")
    }
}