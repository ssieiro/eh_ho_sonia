package io.keepcoding.eh_ho_sonia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.isFirstTimeCreated(savedInsTanceState: Bundle?) : Boolean
    = savedInsTanceState == null

fun ViewGroup.inflate(idLayout: Int, attachToRoot: Boolean = false): View
        = LayoutInflater.from(this.context).inflate(idLayout, this, attachToRoot)