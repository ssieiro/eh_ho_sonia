package io.keepcoding.eh_ho_sonia.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.keepcoding.eh_ho_sonia.R
import io.keepcoding.eh_ho_sonia.data.SignInModel
import io.keepcoding.eh_ho_sonia.inflate
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.labelCreateAccount
import java.lang.IllegalArgumentException

class SignInFragment: Fragment() {

    var signInInteractionListener: SignInInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SignInInteractionListener)
            signInInteractionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${SignInInteractionListener::class.java.canonicalName}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_sign_in)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        labelCreateAccount.setOnClickListener{
            signInInteractionListener?.onGoToSignUp()
        }
        buttonLogin.setOnClickListener{
            val signInModel = SignInModel(
                inputSignInUsername.text.toString(),
                inputSignInPassword.text.toString()
            )

            signInInteractionListener?.onSignIn(signInModel)
        }
    }

    override fun onDetach() {
        super.onDetach()
        signInInteractionListener = null
    }

    interface SignInInteractionListener {
        fun onGoToSignUp()
        fun onSignIn(signInModel: SignInModel)
    }
}