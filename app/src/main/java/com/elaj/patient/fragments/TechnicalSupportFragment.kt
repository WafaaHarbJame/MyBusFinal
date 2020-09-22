//package com.elaj.patient.fragments
//
//import android.Manifest
//import android.app.Activity
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.Fragment
//import com.aminography.choosephotohelper.ChoosePhotoHelper
//import com.aminography.choosephotohelper.callback.ChoosePhotoCallback
//import com.elaj.patient.Model.ResultAPIModel
//import com.elaj.patient.R
//import com.elaj.patient.Utils.FileUtil
//import com.elaj.patient.Utils.NumberHandler
//import com.elaj.patient.apiHandlers.DataFeacher
//import com.elaj.patient.apiHandlers.DataFetcherCallBack
//import com.elaj.patient.classes.Constants
//import com.elaj.patient.classes.GlobalData
//import com.elaj.patient.dialogs.PickImageDialog
//import com.elaj.patient.dialogs.SuccessDialog
//import com.bumptech.glide.Glide
//import com.kcode.permissionslib.main.OnRequestPermissionsCallBack
//import com.kcode.permissionslib.main.PermissionCompat
//import kotlinx.android.synthetic.main.fragment_technical_support.*
//import java.io.File
//
//
///**
// * A simple [Fragment] subclass.
// */
//
//class TechnicalSupportFragment : FragmentBase() {
//
//    var activity: Activity? = null
//    var successDialog: SuccessDialog? = null
//
//    var pickImageDialog: PickImageDialog? = null
//
//    val REQUEST_PICK_IMAGE = 11
//    var choosePhotoHelper: ChoosePhotoHelper? = null
//    var selectedPhotoFile: File? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view: View = inflater.inflate(R.layout.fragment_technical_support, container, false)
//        activity = getActivity()
//        return view
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        activity = getActivity()
//
//        sendBtn.setOnClickListener {
//            sendTicket()
//        }
//
//        addImage.setOnClickListener {
//            openPicker()
//        }
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//        activity = getActivity()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        choosePhotoHelper?.onActivityResult(
//            requestCode,
//            resultCode,
//            data
//        )
//
//        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
//            try {
//                Log.i(javaClass.simpleName, "Log $data")
//
//                if (data?.data != null) {
//                    val selectedImageUri: Uri? = data.data
//
//                    selectedPhotoFile = FileUtil.from(requireActivity(), selectedImageUri)
//
//                    Glide.with(requireActivity())
//                        .asBitmap()
//                        .load(selectedImageUri)
//                        .placeholder(R.drawable.error_logo)
//                        .into(addImage)
//
//                }
//
//            } catch (e: java.lang.Exception) {
//                Log.e("FileSelectorActivity", "File select error", e)
//            }
//        }
//
//
//    }
//
//    private fun openPicker() {
//        try {
//            val builder = PermissionCompat.Builder(requireActivity())
//            builder.addPermissions(
//                arrayOf(
//                    Manifest.permission.CAMERA,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                )
//            )
//            builder.addPermissionRationale(getString(R.string.should_allow_permission))
//            builder.addRequestPermissionsCallBack(object : OnRequestPermissionsCallBack {
//                override fun onGrant() {
//                    pickImage()
//                }
//
//                override fun onDenied(permission: String) {
//                    Toast(R.string.some_permission_denied)
//                }
//            })
//            builder.build().request()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun pickImage() {
//        if (pickImageDialog == null) {
//            pickImageDialog = PickImageDialog(requireActivity(),
//                object : DataFetcherCallBack {
//                    override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
//                        if (func == Constants.CAPTURE) {
//                            choosePhotoHelper = ChoosePhotoHelper.with(requireActivity())
//                                .asUri()
//                                .build(ChoosePhotoCallback<Uri> {
//
//                                    selectedPhotoFile = FileUtil.from(requireActivity(), it)
//
//                                    Glide.with(requireActivity())
//                                        .asBitmap()
//                                        .load(it)
//                                        .placeholder(R.drawable.error_logo)
//                                        .into(addImage)
//
//                                })
//                            choosePhotoHelper?.takePhoto()
//                        } else if (func == Constants.PICK) {
//                            val intent = Intent()
//                            intent.type = "image/*"
//                            intent.action = Intent.ACTION_GET_CONTENT
//                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//                            startActivityForResult(
//                                Intent.createChooser(intent, ""),
//                                REQUEST_PICK_IMAGE
//                            )
//                        }
//                    }
//                }
//            )
//            pickImageDialog!!.setOnDismissListener {
//                pickImageDialog = null
//            }
//        }
//    }
//
//    private fun sendTicket() {
//
//        val titleStr = NumberHandler.arabicToDecimal(messageTitleTxt.text.toString().trim())
//        val messageStr = NumberHandler.arabicToDecimal(detailsTxt.text.toString().trim())
//
//        if (titleStr.isNullOrEmpty()) {
//            messageTitleTxt.error = getString(R.string.invalid_input)
//            return
//        }
//
//        if (messageStr.isNullOrEmpty()) {
//            detailsTxt.error = getString(R.string.invalid_input)
//            return
//        }
//
//        GlobalData.progressDialog(
//            activity,
//            R.string.send_support,
//            R.string.please_wait_sending,
//            true
//        )
//        DataFeacher(object : DataFetcherCallBack {
//            override fun Result(obj: Any?, func: String?, IsSuccess: Boolean) {
//
//                GlobalData.progressDialog(
//                    activity,
//                    R.string.send_support,
//                    R.string.please_wait_sending,
//                    false
//                )
//                if (func == Constants.ERROR_DATA) {
//                    val result = obj as ResultAPIModel<*>?
//                    var message = getString(R.string.fail_to_send)
//                    if (result?.error != null) {
//                        val errors = result.error.details
//                        for (error in errors) {
//                            message += "\n---\n $error"
//                        }
//                    }
//                    GlobalData.errorDialog(activity, R.string.send_support, message, true)
//
//                } else if (func == Constants.FAIL_DATA) {
//                    Toast(R.string.fail_to_send)
//                } else if (func == Constants.NO_CONNECTION) {
//                    Toast(R.string.no_internet_connection)
//                } else {
//                    val result: ResultAPIModel<Any> =
//                        obj as ResultAPIModel<Any>
//                    if (func == Constants.SUCCESS) {
//
//                        messageTitleTxt.setText("")
//                        detailsTxt.setText("")
//                        selectedPhotoFile = null
//
//                        addImage.setImageDrawable(
//                            ContextCompat.getDrawable(
//                                requireActivity(),
//                                R.drawable.add_photo_support
//                            )
//                        )
//
//                        if (successDialog == null) {
//                            successDialog = SuccessDialog(activity)
//                            successDialog!!.setOnDismissListener {
//                                successDialog = null
//                            }
//                        }
//                    }
//
//                }
//            }
//        }, true).sendSupport(titleStr, messageStr, selectedPhotoFile)
//
//    }
//
//}