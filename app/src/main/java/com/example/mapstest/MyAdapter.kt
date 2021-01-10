package com.example.testviewpager1

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.viewpager.widget.PagerAdapter
import com.example.mapstest.MapsActivity
import com.example.mapstest.Page1
import com.example.mapstest.R
import kotlinx.android.synthetic.main.card_item.view.*
import android.widget.Toast.makeText as toastMakeText

class MyAdapter (private val context: Context, private val myModelArrayList: ArrayList<MyModel>): PagerAdapter(){
    override fun getCount(): Int {
        return myModelArrayList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view = LayoutInflater.from(context).inflate(R.layout.card_item, container, false)

        //get
        val model = myModelArrayList[position]
        val title = model.title
        val description = model.description
        val date = model.date
        val image = model.image
        val locationid = model.locationid
        val pageid = model.pageid


        //set data to UI views
        view.bannerIv.setImageResource(image)
        view.titleTv.text = title
        view.descriptionTv.text = date


        //handle Item/card click
        view.setOnClickListener{
            Toast.makeText(context, "$title \n $description", Toast.LENGTH_SHORT).show()

            //pageidを取得

            //pageidのページを開く
            var intent = Intent(context, Page1::class.java)
            //startActivity(intent)
        }



        //add view to container
        container.addView(view, position)

        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}