package com.test.dailyroutine.update_task

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.test.dailyroutine.AlarmNotification
import com.test.dailyroutine.database.ToDoDatabase
import com.test.dailyroutine.database.ToDoTaskModel
import com.test.dailyroutine.databinding.FragUpdateTaskBinding
import com.test.dailyroutine.messageExtra
import com.test.dailyroutine.notificationID
import com.test.dailyroutine.titleExtra
import com.test.dailyroutine.util.FormatDate
import kotlinx.android.synthetic.main.frag_add_task.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UpdateTaskFragment : Fragment() {

    private lateinit var binding: FragUpdateTaskBinding
    lateinit var myCalendar: Calendar
    lateinit var todoModel : ToDoTaskModel

    lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener
    private var minute = 0
    private var hour = 0
    private var day = 0
    private var month = 0
    private var year = 0
    private var notiTime = ArrayList<Int>()
    var finalDate = 0L
    var finalTime = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragUpdateTaskBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        val dataSource = ToDoDatabase.getInstance(application).todoDao

        val viewModelFactory = UpdateTaskViewModelFactory(dataSource, application)
        val updateTaskViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(UpdateTaskViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = updateTaskViewModel
        todoModel = arguments?.getSerializable("task") as ToDoTaskModel
        with(binding) {
            edtTaskTitle.setText(todoModel.taskTitle)
            edtTask.setText(todoModel.taskDesc)
            edtSetDate.setText(FormatDate.formatDate(todoModel.taskDate))
            edtTime.setText(FormatDate.formatTime(todoModel.taskTime))
            finalDate = todoModel.taskDate
            finalTime = todoModel.taskTime
            btnSaveTask.setOnClickListener {
                val notiTimeString =
                    year.toString() + "," + month + "," + day + "," + hour + "," + minute
                notiTime.add(year)
                notiTime.add(month)
                notiTime.add(day)
                notiTime.add(hour)
                notiTime.add(minute)

                if (isValidData()) {
                    val notificationId = todoModel.notiId
                    updateTaskViewModel.updateTask(
                        edt_task_title.text.toString(),
                        edt_task.text.toString(),
                        finalDate,
                        finalTime,
                        notiTimeString,
                        notificationId
                    )
                    findNavController().popBackStack()
                    scheduleNotification(notificationId)
                }
            }

            edtSetDate.setOnClickListener {
                setDateListener()
            }

            edtTime.setOnClickListener {
                setTimeListener()
            }
        }
        return binding.root
    }

    private fun setDateListener() {
        myCalendar = Calendar.getInstance()

        dateSetListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                this.year = year
                this.month = month
                this.day = dayOfMonth
                formatDate()
            }

        val datePickerDialog = DatePickerDialog(
            requireContext(), dateSetListener, myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun formatDate() {
        //Mon, 5 Jan 2020
        val myformat = "EEE, d MMM yyyy"
        val sdf = SimpleDateFormat(myformat)
        finalDate = myCalendar.time.time
        binding.edtSetDate.setText(sdf.format(myCalendar.time))

        binding.tlTime.visibility = View.VISIBLE

    }

    private fun setTimeListener() {
        myCalendar = Calendar.getInstance()

        timeSetListener =
            TimePickerDialog.OnTimeSetListener() { _: TimePicker, hourOfDay: Int, min: Int ->
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myCalendar.set(Calendar.MINUTE, min)
                hour = hourOfDay
                minute = min
                formatTime()
            }

        val timePickerDialog = TimePickerDialog(
            requireContext(), timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY),
            myCalendar.get(Calendar.MINUTE), false
        )
        timePickerDialog.show()
    }

    private fun formatTime() {
        //Mon, 5 Jan 2020
        val myformat = "h:mm a"
        val sdf = SimpleDateFormat(myformat)
        finalTime = myCalendar.time.time
        binding.edtTime.setText(sdf.format(myCalendar.time))

    }

    private fun isValidData(): Boolean {
        binding.apply {
            if (TextUtils.isEmpty(edtTaskTitle.text)) {
                tlTaskTitle.error = "Enter Task Title"
                return false
            }
        }
        return true
    }

    private fun scheduleNotification(notificationId: Int) {
        val intent = Intent(requireContext(), AlarmNotification::class.java)
        val title = binding.edtTaskTitle.text.toString()
        val message = binding.edtTask.text.toString()
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)
        notificationID = notificationId
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )

    }

    private fun getTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }
}