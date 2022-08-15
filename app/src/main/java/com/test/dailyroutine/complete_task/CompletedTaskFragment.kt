package com.test.dailyroutine.complete_task

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.dailyroutine.*
import com.test.dailyroutine.database.ToDoDao
import com.test.dailyroutine.database.ToDoDatabase
import com.test.dailyroutine.databinding.FragCompleteTaskListBinding
import com.test.dailyroutine.task_list.TaskListViewModel
import com.test.dailyroutine.task_list.TaskListViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class CompletedTaskFragment : Fragment() {

    private lateinit var binding: FragCompleteTaskListBinding
    private lateinit var taskListViewModel: TaskListViewModel
    lateinit var myCalendar: Calendar
    lateinit var dataSource: ToDoDao
    private lateinit var taskListAdapter: CompleteTaskListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragCompleteTaskListBinding.inflate(layoutInflater)
        val application = requireNotNull(this.activity).application

        dataSource = ToDoDatabase.getInstance(application).todoDao

        val viewModelFactory = TaskListViewModelFactory(dataSource, application)
        taskListViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(TaskListViewModel::class.java)
        binding.viewModel = taskListViewModel
        binding.lifecycleOwner = this
        taskListViewModel.getCompleteTask()
        taskListAdapter = CompleteTaskListAdapter(this)
        binding.rvTaskList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = taskListAdapter

        }
        taskListViewModel.responseTaskList?.observe(this,{
            taskListAdapter.setData(it)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myCalendar = Calendar.getInstance()
        initSwipe()
    }

    private fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    GlobalScope.launch(Dispatchers.IO) {
                        val deletedId = taskListAdapter.getItemId(position)
                        // val notiId = dataSource.getNotiId(deletedId)
                        dataSource.deleteTask(deletedId)
                        //deleteNotification(deletedId)
                    }
                }
            }

            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView

                    val paint = Paint()
                    val icon: Bitmap

                    if (dX > 0) {

                        // icon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
                        icon =
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_done)
                                ?.toBitmap()!!
                        paint.color = Color.parseColor("#388E3C")

                        canvas.drawRect(
                            itemView.left.toFloat(), itemView.top.toFloat(),
                            itemView.left.toFloat() + dX, itemView.bottom.toFloat(), paint
                        )

                        canvas.drawBitmap(
                            icon,
                            itemView.left.toFloat(),
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )


                    } else {
                        /*icon = BitmapFactory.decodeResource(requireContext().resources,
                            R.drawable.ic_baseline_delete
                        )*/
                        icon = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_baseline_delete
                        )?.toBitmap()!!

                        paint.color = Color.parseColor("#D32F2F")

                        canvas.drawRect(
                            itemView.right.toFloat() + dX, itemView.top.toFloat(),
                            itemView.right.toFloat(), itemView.bottom.toFloat(), paint
                        )

                        canvas.drawBitmap(
                            icon,
                            itemView.right.toFloat() - icon.width,
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )
                    }
                    viewHolder.itemView.translationX = dX


                } else {
                    super.onChildDraw(
                        canvas,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }


        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvTaskList)
    }

}