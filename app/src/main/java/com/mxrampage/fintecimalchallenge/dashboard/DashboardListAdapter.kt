package com.mxrampage.fintecimalchallenge.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mxrampage.fintecimalchallenge.R
import com.mxrampage.fintecimalchallenge.database.PlacesRoomModel

class DashboardListAdapter : RecyclerView.Adapter<DashboardListViewHolder>() {
    var placesList = listOf<PlacesRoomModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onItemClick: ((PlacesRoomModel) -> Unit) = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.places_item_list, parent, false)
        return DashboardListViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardListViewHolder, position: Int) {
        holder.bind(placesList[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return placesList.size
    }
}
