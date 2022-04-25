package com.sliide.sliideuser.utils

import android.widget.EditText

/**
 * Created by Robert Ruxandrescu on 4/25/22.
 */
fun EditText.getCleanText() = text.trim().toString()