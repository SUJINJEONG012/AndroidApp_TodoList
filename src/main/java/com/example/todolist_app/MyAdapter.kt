package com.example.todolist_app
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_app.databinding.ItemRecyclerviewBinding


class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

class MyAdapter(val datas:MutableList<String>,val datas2:MutableList<String>,val datas3:MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    lateinit var db: DBHelper
    lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //DB 사용을 위한 사전 준비
        context = parent.context
        db = DBHelper(context)
        return MyViewHolder(
            ItemRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int,) {
        val binding = (holder as MyViewHolder).binding
        binding.itemData.text = datas[position]
        binding.itemData2.text = datas2[position]
        binding.itemData3.text = datas3[position]
        binding.itemData3.text = datas3[position]






        //수정 이벤트 추가 - 조건문
//        binding.itemData.setOnClickListener{
//            if (binding.updateBtn.visibility == View.GONE){
//                binding.updateBtn.visibility = View.VISIBLE
//                binding.updateBtn.setOnClickListener {
//                    val intent = Intent(binding.itemData.context, UpdateActivity::class.java)
//                    intent.putExtra("todo",binding.itemData.text.toString())
//                    intent.putExtra("todo2",binding.itemData2.text.toString())
//
//                    ContextCompat.startActivity(context, intent, null)
//                }
//            }else{
//                binding.updateBtn.visibility = View.GONE
//            }
//        }


        //수정 이벤트 추가
        binding.updateBtn.setOnClickListener {
            val intent = Intent(binding.itemData.context, UpdateActivity::class.java)
                    intent.putExtra("todo",binding.itemData.text.toString())
                    intent.putExtra("todo2",binding.itemData2.text.toString())

                    ContextCompat.startActivity(context, intent, null)
        }

        //삭제 이벤트 추가
        binding.delBtn.setOnClickListener {
            try {
                val data = binding.itemData.text.toString()
                val delDB = db.writableDatabase
                delDB.execSQL("delete from TODO_TB where todo = ?", arrayOf(data))
                delDB.close()

                //삭제후 화면 갱신
                datas.remove(datas!![position])
                notifyDataSetChanged()

            }catch (e:Exception){
                e.printStackTrace()
            }
        }

    }

    override fun getItemCount(): Int {
        return datas.size
    }


}