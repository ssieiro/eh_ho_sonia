package io.keepcoding.eh_ho_sonia.data

import com.android.volley.VolleyError

class RequestError(
    val volleyError: VolleyError? = null,
    val message: String? = null,
    //identificador de una cadena de texto que vive en los recursos
    val messageResId: Int? = null //R.string.Error_not_found por ejemplo
)
