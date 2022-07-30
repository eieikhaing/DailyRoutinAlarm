package com.test.dailyroutine.task_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.dailyroutine.R
import com.test.dailyroutine.database.ToDoTaskModel
import com.test.dailyroutine.databinding.ItemTaskBinding
import kotlinx.android.synthetic.main.item_task.view.*
import java.text.SimpleDateFormat
import java.util.*

class TaskListAdapter(val requireContext: Context) :
    RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    var taskList = emptyList<ToDoTaskModel>()
    fun setData(taskList: List<ToDoTaskModel>) {
        this.taskList = taskList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(taskList[position], requireContext)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun getItemId(position: Int): Long {
        return taskList[position].id
    }

    class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(todoModel: ToDoTaskModel, requireContext: Context) {
            val colors = itemView.resources.getIntArray(R.array.random_color)
            val randomColor = colors[Random().nextInt(colors.size)]
            itemView.viewColorTag.setBackgroundColor(randomColor)
            with(binding) {
                txtShowTitle.text = todoModel.taskTitle
                txtShowTask.text = todoModel.taskDesc
                formatTime(todoModel.taskTime)
                formatDate(todoModel.taskDate)

            }
        }

        private fun formatTime(time: Long) {
            //Mon, 5 Jan 2020
            val myformat = "h:mm a"
            val sdf = SimpleDateFormat(myformat)
            binding.txtShowTime.text = sdf.format(Date(time))

        }

        private fun formatDate(time: Long) {
            //Mon, 5 Jan 2020
            val myformat = "EEE, d MMM yyyy"
            val sdf = SimpleDateFormat(myformat)
            binding.txtShowDate.text = sdf.format(Date(time))

        }
    }

}