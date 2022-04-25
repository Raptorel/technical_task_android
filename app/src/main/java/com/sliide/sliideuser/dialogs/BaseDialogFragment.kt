package com.sliide.sliideuser.dialogs

import androidx.fragment.app.DialogFragment

/**
 * Created by Robert Ruxandrescu on 4/25/22.
 */
open class BaseDialogFragment: DialogFragment() {

    protected lateinit var callback: Callback

    companion object {
        const val KEY_STATE = "state"
        const val KEY_USER_ID = "user_id"
    }

    enum class UserMessageDialogType {
        DELETE_USER, ADD_USER
    }

    interface Callback {
        fun onPositiveButtonPressed(userMessageDialogType: UserMessageDialogType, userId: Long)
    }
}