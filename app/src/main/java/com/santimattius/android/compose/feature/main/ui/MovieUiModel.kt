package com.santimattius.android.compose.feature.main.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Content(
    open val title: String,
    open val id: String,
) : Parcelable

@Parcelize
data class MovieUiModel(
    override val title: String,
    override val id: String,
) : Parcelable, Content(title, id)

@Parcelize
data class SerieUiModel(
    override val title: String,
    override val id: String,
) : Parcelable, Content(title, id)
