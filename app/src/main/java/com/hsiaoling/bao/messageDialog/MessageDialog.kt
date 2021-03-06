package com.hsiaoling.bao.messageDialog

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import com.hsiaoling.bao.BaoApplication
import com.hsiaoling.bao.R

import com.hsiaoling.bao.databinding.DialogMessageBinding


class MessageDialog : AppCompatDialogFragment() {

    var iconRes: Drawable? = null
    var message: String? = null
    private val messageType by lazy {
        MessageDialogArgs.fromBundle(arguments!!).messageTypeKey
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MessageDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        init()
        val binding = DialogMessageBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.dialog = this


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler().postDelayed({ this.dismiss() }, 2000)
    }

    private fun init() {
        when (messageType) {
            MessageType.LOGIN_SUCCESS->{
                iconRes = BaoApplication.instance.getDrawable(R.drawable.ic_success)
                message = getString(R.string.login_success)
            }

            MessageType.ADDED_SUCCESS -> {
                iconRes = BaoApplication.instance.getDrawable(R.drawable.ic_success)
                message = getString(R.string.addto_success)
            }

            MessageType.DONE_SUCCESS -> {
                iconRes = BaoApplication.instance.getDrawable(R.drawable.ic_success)
                message = getString(R.string.done_success)
            }

            MessageType.DELETE_SUCCESS -> {
                iconRes = BaoApplication.instance.getDrawable(R.drawable.ic_success)
                message = getString(R.string.delete_success)
            }

            MessageType.MESSAGE -> {
                iconRes = BaoApplication.instance.getDrawable(R.drawable.ic_launcher_foreground)
                message = messageType.value.message
            }

            MessageType.MASTER_INFO -> {
                iconRes = BaoApplication.instance.getDrawable(R.drawable.close0)
                message = getString(R.string.master_info)
            }

            MessageType.ADD_REJECT -> {
                iconRes = BaoApplication.instance.getDrawable(R.drawable.close0)
                message = getString(R.string.add_reject)
            }

            MessageType.NO_SERVICE -> {
                iconRes = BaoApplication.instance.getDrawable(R.drawable.close0)
                message = getString(R.string.no_service)
            }

            else -> {

            }
        }
    }

    enum class MessageType(val value: Message) {
        LOGIN_SUCCESS(Message()),
        LOGIN_FAIL(Message()),
        ADDED_SUCCESS(Message()),
        DONE_SUCCESS(Message()),
        DELETE_SUCCESS(Message()),
        DONE_FAIL(Message()),
        MASTER_INFO(Message()),
        ADD_REJECT(Message()),
        NO_SERVICE(Message()),
        MESSAGE(Message())
    }

    interface IMessage {
        var message: String
    }

    class Message : IMessage {
        private var _message = ""
        override var message: String
            get() = _message
            set(value) { _message = value }
    }
}
