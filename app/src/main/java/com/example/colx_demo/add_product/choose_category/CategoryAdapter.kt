package com.example.colx_demo.add_product.choose_category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.colx_demo.R

class CategoryAdapter(private val catList : ArrayList<CategoryDC>): RecyclerView.Adapter<CategoryAdapter.MyViewholder> () {


    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.choose_cat_item,parent,false)
        return MyViewholder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        val currentItem = catList[position]
        holder.catName.text = currentItem.title
        holder.catImage.setImageResource(currentItem.image)
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    class MyViewholder(itemView : View, listener:OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val catImage: ImageView = itemView.findViewById(R.id.catImage)
        val catName: TextView = itemView.findViewById(R.id.catName)

        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)
            }
        }

    }

}