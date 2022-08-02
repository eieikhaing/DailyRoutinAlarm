package com.test.dailyroutine.util

import java.text.SimpleDateFormat
import java.util.*

class FormatDate {
    companion object{
        fun formatTime(time: Long): String {
            //Mon, 5 Jan 2020
            val myformat = "h:mm a"
            val sdf = SimpleDateFormat(myformat)
           return sdf.format(Date(time))

        }

        fun formatDate(time: Long): String {
            //Mon, 5 Jan 2020
            val myformat = "EEE, d MMM yyyy"
            val sdf = SimpleDateFormat(myformat)
            return sdf.format(Date(time))

        }
    }
}