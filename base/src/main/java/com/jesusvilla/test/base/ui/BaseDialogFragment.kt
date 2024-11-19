package com.jesusvilla.test.base.ui

import androidx.fragment.app.DialogFragment
import com.jesusvilla.test.base.R
import com.jesusvilla.test.base.constants.BaseConstants
import com.jesusvilla.test.base.models.GenericDialogModel
import com.jesusvilla.test.base.models.ProgressDialogModel
import com.jesusvilla.test.base.viewModels.BaseViewModel

@Suppress("TooManyFunctions")
open class BaseDialogFragment : DialogFragment() {

    private lateinit var dialog: GenericDialogFragment
    private lateinit var progressDialog: ProgressDialogFragment
    private lateinit var savingDialog: ProgressDialogFragment
    private lateinit var consultingInfoDialog: ProgressDialogFragment

    protected fun subscribeViewModel(viewModel: BaseViewModel) {

        viewModel.onShowGenericDialogChange().observe(viewLifecycleOwner) { showGenericDialog(it) }
        viewModel.onIsLoading().observe(viewLifecycleOwner) {
            showProgressDialog(it)
        }
        viewModel.onIsSavingInfo().observe(viewLifecycleOwner) {
            showSavingInfoDialog(it)
        }
        viewModel.onIsConsultingInfo().observe(viewLifecycleOwner) {
            showConsultingInfoDialog(it)
        }
    }
    fun showGenericDialog(genericDialogModel: GenericDialogModel) {
        dialog = GenericDialogFragment(genericDialogModel)
        dialog.show(parentFragmentManager, BaseConstants.EMPTY_TAG)
    }

    private fun showProgressDialog(isLoading: Boolean) {
        initLoader()
        if (isLoading) {
            if (!progressDialog.isAdded) progressDialog.showNow(
                childFragmentManager,
                ProgressDialogFragment.PROGRESS_DIALOG_TAG
            )
        } else {
            if (progressDialog.isAdded) progressDialog.dismissNow()
        }
    }

    private fun initLoader() {
        if (!this::progressDialog.isInitialized) {
            progressDialog = ProgressDialogFragment.newInstance(
                ProgressDialogModel(
                    message = "Cargando..."
                )
            )
        }
    }

    private fun showSavingInfoDialog(isLoading: Boolean) {
        if (isLoading) {
            savingDialog = ProgressDialogFragment.newInstance(
                ProgressDialogModel(
                    message = getString(R.string.saving_info_dialog_title)
                )
            )
            if (!savingDialog.isAdded) {
                savingDialog.showNow(
                    childFragmentManager,
                    ProgressDialogFragment.PROGRESS_DIALOG_TAG
                )
            }
        } else {
            if (::savingDialog.isInitialized && savingDialog.isAdded) {
                savingDialog.dismiss()
            }
        }
    }

    private fun showConsultingInfoDialog(isLoading: Boolean) {
        if (isLoading) {
            consultingInfoDialog = ProgressDialogFragment.newInstance(
                ProgressDialogModel(
                    message = getString(R.string.consulting_info_dialog_title)
                )
            )
            if (!consultingInfoDialog.isAdded) {
                consultingInfoDialog.showNow(
                    childFragmentManager,
                    ProgressDialogFragment.PROGRESS_DIALOG_TAG
                )
            }
        } else {
            if (::consultingInfoDialog.isInitialized && consultingInfoDialog.isAdded) {
                consultingInfoDialog.dismiss()
            }
        }
    }
}
