/*
 * Created by Dmitry Lipski on 12.02.21 15:28
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 12.02.21 15:04
 */

package com.lipssoftware.manchester.united.ui.fixtures

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.lipssoftware.manchester.united.data.model.domain.MatchDomain
import com.lipssoftware.manchester.united.databinding.ItemFixtureBinding
import com.lipssoftware.manchester.united.utils.DATE_PATTERN_OUT
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

class FixturesAdapter(private val fixtures: List<MatchDomain>) :
    RecyclerView.Adapter<FixturesAdapter.FixturesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixturesViewHolder =
        FixturesViewHolder(
            ItemFixtureBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FixturesViewHolder, position: Int) {
        holder.bind(fixtures[position])
    }

    override fun getItemCount(): Int = fixtures.size

    class FixturesViewHolder(private val item: ItemFixtureBinding) :
        RecyclerView.ViewHolder(item.root) {

        fun bind(match: MatchDomain) {
            item.tvItemFixtureTournament.text = match.leagueName
            item.tvItemFixtureTime.text = SimpleDateFormat(
                DATE_PATTERN_OUT,
                Locale.getDefault()
            ).format(Date(TimeUnit.SECONDS.toMillis(match.timestamp)))
            item.tvItemFixtureScoreHomeTeam.text =
                if (match.homeTeamGoals != null) match.homeTeamGoals.toString() else ""
            item.ivItemFixtureLogoHomeTeam.load(match.homeTeamLogo) {
                crossfade(true)
            }
            item.tvItemFixtureNameHomeTeam.text = match.homeTeamName
            item.tvItemFixtureScoreAwayTeam.text =
                if (match.awayTeamGoals != null) match.awayTeamGoals.toString() else ""
            item.ivItemFixtureLogoAwayTeam.load(match.awayTeamLogo) {
                crossfade(true)
            }
            item.tvItemFixtureScoreDivider.isVisible = match.statusShort != "NS"
            item.tvItemFixtureNameAwayTeam.text = match.awayTeamName
            item.tvItemFixtureStatus.text = match.statusLong
            item.tvItemFixtureVenue.text = "${match.venueName ?: "Unknown Stadium"}, ${match.venueCity ?: "Somewhere"}"
            item.tvItemFixtureReferee.text = "Referee - ${match.referee ?: "Unknown"}"
        }
    }

    class BoundsOffsetDecoration(private val isVertical: Boolean) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            val itemPosition = parent.getChildAdapterPosition(view)

            // It is crucial to refer to layoutParams.width
            // (view.width is 0 at this time)!
            val itemSize = if(isVertical) view.layoutParams.height else view.layoutParams.width
            val offset = if(isVertical) (parent.height - itemSize) / 2 else (parent.width - itemSize) / 2

            if (itemPosition == 0) {
                if (isVertical) outRect.top = offset / 2
                else outRect.left = offset / 2
            } else if (itemPosition == state.itemCount - 1) {
                if(isVertical) outRect.bottom = offset / 2
                else outRect.right = offset / 2
            }
        }
    }

    internal class ProminentLayoutManager(
        context: Context,
        private val minScaleDistanceFactor: Float = 1.5f,
        private val scaleDownBy: Float = 0.2f,
        private val desiredOrientation: Int
    ) : LinearLayoutManager(context, desiredOrientation, false) {

        override fun onLayoutCompleted(state: RecyclerView.State?) =
            super.onLayoutCompleted(state).also { scaleChildren(desiredOrientation) }

        override fun scrollVerticallyBy(
            dy: Int,
            recycler: RecyclerView.Recycler,
            state: RecyclerView.State
        ) = super.scrollVerticallyBy(dy, recycler, state).also {
            scaleChildren(desiredOrientation)
        }

        override fun scrollHorizontallyBy(
            dx: Int,
            recycler: RecyclerView.Recycler?,
            state: RecyclerView.State?
        ) = super.scrollHorizontallyBy(dx, recycler, state).also {
            scaleChildren(desiredOrientation)
        }

        private fun scaleChildren(orient: Int) {
            val containerCenter = if (orient == VERTICAL) height / 2f else width / 2f

            // Any view further than this threshold will be fully scaled down
            val scaleDistanceThreshold = minScaleDistanceFactor * containerCenter

            for (i in 0 until childCount) {
                val child = getChildAt(i)!!

                val childCenter =
                    if (orient == VERTICAL) (child.top + child.bottom) / 2f else (child.left + child.right) / 2f
                val distanceToCenter = kotlin.math.abs(childCenter - containerCenter)

                val scaleDownAmount = (distanceToCenter / scaleDistanceThreshold).coerceAtMost(1f)
                val scale = 1f - scaleDownBy * scaleDownAmount

                child.scaleX = scale
                child.scaleY = scale

                val translationDirection = if (childCenter > containerCenter) -1 else 1
                val translationXFromScale = translationDirection * child.width * (1 - scale) / 2f
                val translationYFromScale = translationDirection * child.height * (1 - scale) / 2f
                if (orient == VERTICAL) child.translationY = translationYFromScale else
                    child.translationX = translationXFromScale

                var translationYForward = 0f

                if (orient == VERTICAL) {
                    if (translationYFromScale > 0 && i >= 1) {
                        // Edit previous child
                        getChildAt(i - 1)!!.translationY += 2 * translationYFromScale
                    }
                } else {
                    if (translationXFromScale > 0 && i >= 1) {
                        // Edit previous child
                        getChildAt(i - 1)!!.translationX += 2 * translationXFromScale
                    }
                }
//                else if (translationYFromScale < 0) {
//                    // Pass on to next child
//                    translationYForward = 2 * translationYFromScale
//                }
            }
        }

        override fun getExtraLayoutSpace(state: RecyclerView.State): Int {
            // Since we're scaling down items, we need to pre-load more of them offscreen.
            // The value is sort of empirical: the more we scale down, the more extra space we need.
            return (height / (1 - scaleDownBy)).roundToInt()
        }
    }
}