package com.shoppy.botpassignment.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.shoppy.botpassignment.data.SuccessOrError
import com.shoppy.botpassignment.models.MUser
import com.shoppy.botpassignment.models.SignInResultData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

open class LoginViewModel(
    open val mAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    open val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {

    private val _state = MutableStateFlow(SuccessOrError())
    val state = _state.asStateFlow()

    fun loginUser(
        email: String, password: String,
        //except: (String) -> Unit = { message -> },
        except: (String) -> Unit = { },
        nav: () -> Unit = {}
    ) {
        viewModelScope.launch {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    nav()
                }
                .addOnFailureListener {
                    except(it.message.toString())
                }

        }
    }

    fun onSignInResult(resultData: SignInResultData) {
        _state.update {
            it.copy(
                isSuccess = resultData.data != null,
                error = resultData.errorMessage
            )
        }
    }

    fun addUserToDB() {
        viewModelScope.launch {
            val currentUser = mAuth.currentUser
            val fb = firestore.collection("Users").document(currentUser?.email!!)

            var image: String? = ""
            var phone_no: String? = ""
            var address: String? = ""

            fb.get().addOnSuccessListener { docSnap ->
                phone_no = docSnap.data?.getValue("phone_no").toString()
                address = docSnap.data?.getValue("address").toString()
                image = docSnap.data?.getValue("profile_image").toString()
            }.await()

            delay(800)

            val user = MUser(
                id = currentUser.uid,
                name = currentUser.displayName,
                email = currentUser.email,
                password = "Google SignIn",
                phone_no = phone_no?.ifEmpty { "" },
                address = address?.ifEmpty { "" },
                profile_image = image?.ifEmpty { currentUser.photoUrl.toString() }
            ).convertToMap()

            fb.set(user)
        }
    }

    fun forgotPassword(email: String, success: () -> Unit, newPassword: String, error: (String) -> Unit) {
        viewModelScope.launch {
            mAuth.sendPasswordResetEmail(email).addOnSuccessListener { success()
                firestore.collection("Users").document(email).update("password", newPassword)
            }.addOnFailureListener { e -> error(e.message.toString()) }
        }
    }
}