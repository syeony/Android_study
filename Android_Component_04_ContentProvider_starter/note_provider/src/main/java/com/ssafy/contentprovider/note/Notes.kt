package com.ssafy.contentprovider.note

import android.net.Uri

object Notes {
    const val AUTHORITY = "com.ssafy.contentprovider.note"
    val CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/notes")

    const val DEFAULT_SORT_ORDER = "title"
    const val ID = "_id"
    const val TITLE = "title"
    const val BODY = "body"
}


