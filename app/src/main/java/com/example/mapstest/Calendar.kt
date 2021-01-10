package info.paveway.samplecalendarview

import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mapstest.R

class Calendar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page1)

        // CalendarViewに現在日時を設定します。
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.date = System.currentTimeMillis()

        // CalendarViewで日にちが選択された時に呼び出されるリスナー
        val listener = DateChangeListener()
        calendarView.setOnDateChangeListener(listener)
    }

    // CalendarViewで日にちが選択された時に呼び出されるリスナークラス
    private inner class DateChangeListener : CalendarView.OnDateChangeListener {
        override fun onSelectedDayChange(
            calendarView: CalendarView,
            year: Int,
            month: Int,
            dayOfMonth: Int
        ) {
            // monthは0起算のため+1します。
            val displayMonth = month + 1
            Toast.makeText(
                applicationContext,
                "$year/$displayMonth/$dayOfMonth",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}


