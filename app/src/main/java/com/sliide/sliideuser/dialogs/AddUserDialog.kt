package com.sliide.sliideuser.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.sliide.sliideuser.R
import com.sliide.sliideuser.databinding.DialogAddUserBinding
import com.sliide.sliideuser.network.NetworkUser
import com.sliide.sliideuser.utils.getCleanText

/**
 * Created by Robert Ruxandrescu on 4/25/22.
 */
class AddUserDialog : BaseDialogFragment() {

    private var _binding: DialogAddUserBinding? = null

    // This property is only valid between onCreateDialog and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance(addUserCallback: AddUserCallback): AddUserDialog {
            val dialog = AddUserDialog().apply {
                this.addUserCallback = addUserCallback
            }
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddUserBinding.inflate(LayoutInflater.from(context))
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddUser.setOnClickListener {
            if (formIsValid()) {
                val networkUser = createUser(binding.etName.getCleanText(), binding.etEmail.getCleanText())
                addUserCallback?.onUserAdd(networkUser)
                dismiss()
            } else {
                showFormError()
            }
        }
    }

    private fun showFormError() {
        if (binding.etName.text.isBlank()) binding.etName.error = getString(R.string.blank_field)
        if (binding.etEmail.text.isBlank()) binding.etName.error = getString(R.string.blank_field)
    }

    private fun formIsValid(): Boolean =
        binding.etName.text.isNotBlank() && binding.etEmail.text.isNotBlank()

    private fun createUser(userName: String, userEmail: String): NetworkUser {
        return NetworkUser(name = userName, email = userEmail)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}