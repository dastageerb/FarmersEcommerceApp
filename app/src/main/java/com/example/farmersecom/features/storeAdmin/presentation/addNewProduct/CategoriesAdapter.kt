package com.example.farmersecom.features.storeAdmin.presentation.addNewProduct

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.farmersecom.databinding.LayoutCategoriesItemBinding
import com.example.farmersecom.features.home.domain.model.sharedModel.CategoryItem
import com.example.farmersecom.features.storeAdmin.domain.model.categories.Category

class CategoriesAdapter(val onCategoryClicked:(Category)->Unit,context: Context, resource: Int,private var list: MutableList<Category>)
    : ArrayAdapter<Category>(context, resource, list)
{


    override fun getCount(): Int
    {
        return list.size
    }

    override fun getItem(position: Int): Category?
    {
        return list[position]
    }

    override fun getItemId(position: Int): Long
    {
        return 0
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
    {
        val binding: LayoutCategoriesItemBinding =
            LayoutCategoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val data: Category = list[position]
        binding.layoutCategoryItemCategoryNameTextView.text = data.name

        binding.root.setOnClickListener()
        {
                onCategoryClicked(list[position])
        }
                //categoryClicked!!.onCategoryClicked(data)
        return binding.root
    } // getView closed


}