package com.employees.screen.main

import android.net.Uri

sealed interface MainEvent {
    data class FilePicked(val file: Uri) : MainEvent
}