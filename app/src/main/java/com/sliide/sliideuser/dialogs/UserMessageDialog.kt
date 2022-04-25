package com.sliide.sliideuser.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.sliide.sliideuser.R

/**
 * Created by Robert Ruxandrescu on 4/24/22.
 */
class UserMessageDialog : BaseDialogFragment() {

    private var userId: Long = -1

    companion object {
        fun newInstance(callback: Callback, userId: Long): UserMessageDialog {
            val dialog = UserMessageDialog().apply {
                this.callback = callback
                this.arguments = Bundle().apply { putLong(KEY_USER_ID, userId) }
            }
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        arguments?.let {
            userId = it.getLong(KEY_USER_ID)
        }

        return AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.delete_user_confirmation))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                callback.onPositiveButtonPressed(
                    UserMessageDialogType.DELETE_USER,
                    userId
                )
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> dismiss() }
            .create()
    }
}