package com.sliide.sliideuser.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.sliide.sliideuser.R

/**
 * Created by Robert Ruxandrescu on 4/24/22.
 */
class DeleteUserDialog : BaseDialogFragment() {

    private var userId: Long = -1

    companion object {
        fun newInstance(deleteUserCallback: DeleteUserCallback, userId: Long): DeleteUserDialog {
            val dialog = DeleteUserDialog().apply {
                this.deleteUserCallback = deleteUserCallback
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
                deleteUserCallback?.onUserDelete(
                    userId
                )
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> dismiss() }
            .create()
    }
}