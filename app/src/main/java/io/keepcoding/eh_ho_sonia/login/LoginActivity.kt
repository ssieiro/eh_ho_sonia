package io.keepcoding.eh_ho_sonia.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import io.keepcoding.eh_ho_sonia.*
import io.keepcoding.eh_ho_sonia.data.RequestError
import io.keepcoding.eh_ho_sonia.data.SignInModel
import io.keepcoding.eh_ho_sonia.data.SignUpModel
import io.keepcoding.eh_ho_sonia.data.UserRepo
import io.keepcoding.eh_ho_sonia.topics.TopicsActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.container
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*

class LoginActivity : AppCompatActivity(),
    SignInFragment.SignInInteractionListener,
    SignUpFragment.SignUpInteractionListener{

    val signUpFragment = SignUpFragment()
    val signInFragment = SignInFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (isFirstTimeCreated(savedInstanceState)) {
            checkSession()
        }
    }

    private fun checkSession() {
        if (UserRepo.isLogged(this.applicationContext)) {
            showTopics()
        } else {
            onGoToSignIn()
        }
    }


    //val button: Button = findViewById(R.id.button_login)

        /*

        Manera 1, con una función
        val listener: View.OnClickListener = object : View.OnClickListener{
            override fun onClick(view: View?) {
                Toast.makeText(view?.context, "Welcome to Eh-Ho", Toast.LENGTH_SHORT).show()
            }

        }

        otra manera: lambda o función anónima

        val listenerLambda: (View) -> Unit = {view: View ->
            Toast.makeText(view?.context, "Welcome to Eh-Ho", Toast.LENGTH_SHORT).show()
        }

        todavía más corto, al recibir solo UN parametro te refieres a el como it y te lo ahorras

        val inputUsername: EditText = findViewById(R.id.input_username)

        button.setOnClickListener { it: View? ->
            val intent: Intent = Intent(this, TopicsActivity::class.java)
            startActivity(intent)
        }
        */

        private fun showTopics() {
            val intent = Intent(this, TopicsActivity::class.java)
            startActivity(intent)
            finish()

        }

    override fun onGoToSignUp() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, signUpFragment)
            .commit()
    }

    override fun onSignIn(signInModel: SignInModel) {
        enableLoading()
        if (isFormValid("signIn")) {
            UserRepo.signIn(this.applicationContext,
                signInModel,
                { showTopics() },
                { error ->
                    enableLoading(false)
                    handleError(error)
                }
            )
        } else {
            enableLoading(false)
            showErrors("signIn")
        }

    }


    override fun onGoToSignIn() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, signInFragment)
            .commit()
    }

    override fun onSignUp(signUpModel: SignUpModel) {
        enableLoading()
        if (isFormValid("signUp")) {
            UserRepo.signUp(this.applicationContext,
                signUpModel,
                {
                    enableLoading(false)
                    Snackbar.make(container, R.string.message_sign_up, Snackbar.LENGTH_LONG).show()
                },
                {
                    enableLoading(false)
                    handleError(it)
                }
            )
        } else {
            enableLoading(false)
            showErrors("signUp")
        }

    }

    private fun enableLoading(enabled: Boolean = true)  {
        if (enabled) {
            fragmentContainer.visibility = View.INVISIBLE
            viewLoading.visibility = View.VISIBLE
        } else {
            fragmentContainer.visibility = View.VISIBLE
            viewLoading.visibility = View.INVISIBLE
        }

    }

    private fun isFormValid(type: String): Boolean {
        if (type == "signIn") {
            return inputSignInUsername.text.isNotEmpty() && inputSignInPassword.text.isNotEmpty()
        }
        if (type == "signUp") {
            return inputEmail.text.isNotEmpty()
                    && inputSignUpUsername.text.isNotEmpty()
                    && inputSignUpPassword.text.isNotEmpty()
                    && inputConfirmPassword.text.isNotEmpty()
        } else { return false }
    }

    private fun showErrors(type: String) {
        if (type == "signIn") {
            if (inputSignInUsername.text.isEmpty())
                inputSignInUsername.error = getString(R.string.error_empty)

            if (inputSignInPassword.text.isEmpty())
                inputSignInPassword.error = getString(R.string.error_empty)
        }

        if (type == "signUp") {
            if (inputEmail.text.isEmpty())
                inputEmail.error = getString(R.string.error_empty)

            if (inputSignUpUsername.text.isEmpty())
                inputSignUpUsername.error = getString(R.string.error_empty)

            if (inputSignUpPassword.text.isEmpty())
                inputSignUpPassword.error = getString(R.string.error_empty)

            if (inputConfirmPassword.text.isEmpty())
                inputConfirmPassword.error = getString(R.string.error_empty)
        }


    }

    private fun handleError(error: RequestError) {
        if (error.messageResId != null)
            Snackbar.make(container, error.messageResId, Snackbar.LENGTH_LONG).show()
        else if(error.message != null)
            Snackbar.make(container, error.message, Snackbar.LENGTH_LONG).show()
        else
            Snackbar.make(container, R.string.error_default, Snackbar.LENGTH_LONG).show()
    }

    /*
    private fun simulateLoading(signInModel: SignInModel) {
       val runnable = Runnable {
            Thread.sleep(3000)
            //accedes a algo que tenga acceso al thread principal porque necesitas que showtopics se ejecute en el principal
            //VERSION MAS RUDIMENTARIA
           //cuando accedemos a un archivo de preferencias siempre le pasamos el contexto de la actividad para no generar memory leaks
            viewLoading.post {
                UserRepo.signIn(this.applicationContext, signInModel.username)
                showTopics()
            }
        }
        Thread(runnable).start()

    } */


}
