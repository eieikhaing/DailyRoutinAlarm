package com.test.dailyroutine.complete_task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.dailyroutine.R
import com.test.dailyroutine.database.ToDoTaskModel
import com.test.dailyroutine.databinding.CompleteItemTaskBinding
import com.test.dailyroutine.util.FormatDate.Companion.formatDate
import com.test.dailyroutine.util.FormatDate.Companion.formatTime
import kotlinx.android.synthetic.main.item_task.view.*
import java.util.*

class CompleteTaskListAdapter(val requireContext: CompletedTaskFragment) :
    RecyclerView.Adapter<CompleteTaskListAdapter.ViewHolder>() {

    var taskList = emptyList<ToDoTaskModel>()
    fun setData(taskList: List<ToDoTaskModel>) {
        this.taskList = taskList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CompleteItemTaskBinding.inflate(
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
        return taskList[position].notiId.toLong()
    }

    class ViewHolder(private val binding: CompleteItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(todoModel: ToDoTaskModel, requireContext: CompletedTaskFragment) {
            val colors = itemView.resources.getIntArray(R.array.random_color)
            val randomColor = colors[Random().nextInt(colors.size)]
            itemView.viewColorTag.setBackgroundColor(randomColor)
            with(binding) {
                txtShowTitle.text = todoModel.taskTitle
                txtShowTask.text = todoModel.taskDesc
                txtShowTime.text = formatTime(todoModel.taskTime)
                txtShowDate.text = formatDate(todoModel.taskDate)

            }
        }

    }

}