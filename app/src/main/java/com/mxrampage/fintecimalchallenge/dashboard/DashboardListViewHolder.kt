package com.mxrampage.fintecimalchallenge.dashboard

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mxrampage.fintecimalchallenge.R
import com.mxrampage.fintecimalchallenge.database.PlacesRoomModel

class DashboardListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(place: PlacesRoomModel, onItemClick: ((PlacesRoomModel) -> Unit)) {
        if (place.isVisited) {
            imageVisitedIndicator.setImageResource(R.drawable.visited_indicator)
            textVisitedIndicator.text = itemView.resources.getString(R.string.visited_text)
            textVisitedIndicator.setTextColor(ContextCompat.getColor(itemView.context, R.color.visited_color))
        } else {
            imageVisitedIndicator.setImageResource(R.drawable.non_visited_indicator)
            textVisitedIndicator.text = itemView.resources.getString(R.string.pending_text)
            textVisitedIndicator.setTextColor(ContextCompat.getColor(itemView.context, R.color.non_visited_color))
        }
        textStreetName.text = place.entryStreetName
        textSuburbName.text = place.entrySuburb
        itemView.setOnClickListener {
            onItemClick(place)
        }
    }

    private val imageVisitedIndicator: ImageView = itemView.findViewById(R.id.image_visited_indicator)
    private val textVisitedIndicator: TextView = itemView.findViewById(R.id.text_visited_indicator)
    private val textStreetName: TextView = itemView.findViewById(R.id.text_street_name)
    private val textSuburbName: TextView = itemView.findViewById(R.id.text_suburb_name)
}
