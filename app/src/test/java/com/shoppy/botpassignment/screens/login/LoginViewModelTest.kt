package com.shoppy.botpassignment.screens.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    @Mock
    private lateinit var firestore: FirebaseFirestore

    @Mock
    private lateinit var authResultTask: Task<AuthResult>

    @Mock
    private lateinit var firebaseUser: FirebaseUser

    @Mock
    private lateinit var collectionReference: CollectionReference

    @Mock
    private lateinit var documentReference: DocumentReference

    @Mock
    private lateinit var documentSnapshot: DocumentSnapshot

    private lateinit var viewModel: LoginViewModel

    private lateinit var firestoreMockedStatic: org.mockito.MockedStatic<FirebaseFirestore>


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        // Mock FirebaseFirestore.getInstance()
        //firestoreMockedStatic = mockStatic(FirebaseFirestore::class.java)
       // firestoreMockedStatic.`when` { FirebaseFirestore.getInstance() }.thenReturn(firestore)

       // viewModel = object : LoginViewModel(firebaseAuth) {}
        viewModel = object : LoginViewModel(firebaseAuth, firestore) {}

    }

    @After
    fun tearDown() {
        //firestoreMockedStatic.close()
        Dispatchers.resetMain()
    }

    @Test
    fun loginUserSuccessTriggersNavigation() = runTest {
        val email = "xyz@gmail.com"
        val password = "xyz@1234"
        val navMock: () -> Unit = mock()

        `when`(firebaseAuth.signInWithEmailAndPassword(email, password))
            .thenReturn(authResultTask)
        `when`(authResultTask.addOnSuccessListener(any())).thenAnswer { invocation ->
            val listener = invocation.arguments[0] as com.google.android.gms.tasks.OnSuccessListener<AuthResult>
            listener.onSuccess(mock())
            authResultTask
        }

        viewModel.loginUser(email, password, nav = navMock)
        testDispatcher.scheduler.advanceUntilIdle()

        verify(navMock).invoke()
    }

    @Test
    fun loginUserFailureCallback() = runTest {
        // Arrange
        val email = "test@gmail.com"
        val password = "password123"
        val errorMock: (String) -> Unit = mock()
        val errorMessage = "Invalid credentials"

        // Mock the signInWithEmailAndPassword to return a Task, then trigger failure
        `when`(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(authResultTask)
        `when`(authResultTask.addOnFailureListener(any())).thenAnswer { invocation ->
            val listener = invocation.arguments[0] as com.google.android.gms.tasks.OnFailureListener
            listener.onFailure(Exception(errorMessage))
            authResultTask
        }

        // Act
        println("Calling loginUser")
        viewModel.loginUser(email, password, except = errorMock)
        println("Advancing dispatcher")
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        println("Verifying mock")
        verify(errorMock).invoke(errorMessage)
    }


}

private inline fun <reified T> any(): T = org.mockito.Mockito.any(T::class.java)