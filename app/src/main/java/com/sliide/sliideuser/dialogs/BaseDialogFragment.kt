package com.sliide.sliideuser.dialogs

import androidx.fragment.app.DialogFragment
import com.sliide.sliideuser.domain.User
import com.sliide.sliideuser.network.NetworkUser

/**
 * Created by Robert Ruxandrescu on 4/25/22.
 */
open class BaseDialogFragment: DialogFragment() {

    protected var deleteUserCallback: DeleteUserCallback? = null
    protected var addUserCallback: AddUserCallback? = null

    companion object {
        const val KEY_STATE = "state"
        const val KEY_USER_ID = "user_id"
    }

    interface DeleteUserCallback {
        fun onUserDelete(userId: Long)
    }

    interface AddUserCallback {
        fun onUserAdd(networkUser: NetworkUser)
    }
}