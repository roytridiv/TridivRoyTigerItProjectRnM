package com.tridiv.tridivroytigeritproject.data.domain.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.tridiv.tridivroytigeritproject.R
import com.tridiv.tridivroytigeritproject.databinding.CustomSnackbarLayoutBinding

class CustomSnackbar private constructor(
    parent: ViewGroup, content: View,
    callback: com.google.android.material.snackbar.ContentViewCallback
) : BaseTransientBottomBar<CustomSnackbar>(parent, content, callback) {

    fun setText(text: CharSequence): CustomSnackbar {
        val textView = getView().findViewById(R.id.snackbar_text) as TextView
        textView.text = text
        return this
    }

    private class CustomContentViewCallback(private val content: View) :
        com.google.android.material.snackbar.ContentViewCallback {

        override fun animateContentIn(delay: Int, duration: Int) {
            content.startAnimation(
                AnimationUtils.loadAnimation(
                    content.context,
                    R.anim.slide_up_enter
                )
            )
        }

        override fun animateContentOut(delay: Int, duration: Int) {
        }
    }

    companion object {
        fun make(parent: ViewGroup, duration: Int): CustomSnackbar {
            val inflater = LayoutInflater.from(parent.context)
            val content = CustomSnackbarLayoutBinding.inflate(inflater)
            val viewCallback = CustomContentViewCallback(content.root)

            return CustomSnackbar(parent, content.root, viewCallback).run {
                getView().setPadding(0,0,0,0)
                getView().startAnimation(
                    AnimationUtils.loadAnimation(
                        getView().context,
                        R.anim.slide_up_enter
                    )
                )
                this.duration = duration
                this
            }
        }
    }
}