package com.shoppy.botpassignment.screens.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class RegisterViewModelTest {

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

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = object : RegisterViewModel(firebaseAuth, firestore) {}
        println("Firestore instance in test: $firestore")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun createUserSuccessTriggersNavigation() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password123"
        val navMock: () -> Unit = mock()

        `when`(firebaseAuth.createUserWithEmailAndPassword(email, password))
            .thenReturn(authResultTask)
        `when`(authResultTask.addOnSuccessListener(any())).thenAnswer { invocation ->
            val listener = invocation.arguments[0] as com.google.android.gms.tasks.OnSuccessListener<AuthResult>
            listener.onSuccess(mock())
            authResultTask
        }

        // Act
        viewModel.createUser(email, password, nav = navMock)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        verify(navMock).invoke()
    }

    @Test
    fun createUserFailureTriggersCallback() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password123"
        val errorMock: (String) -> Unit = mock()
        val errorMessage = "Invalid credentials"

        `when`(firebaseAuth.createUserWithEmailAndPassword(email, password))
            .thenReturn(authResultTask)
        `when`(authResultTask.addOnFailureListener(any())).thenAnswer { invocation ->
            val listener = invocation.arguments[0] as com.google.android.gms.tasks.OnFailureListener
            listener.onFailure(Exception(errorMessage))
            authResultTask
        }

        // Act
        viewModel.createUser(email, password, regExcept = errorMock)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        verify(errorMock).invoke(errorMessage)
    }
/*
    @Test
    fun `addUserToDB saves user data correctly`() = runTest {
        // Arrange
        val userId = "user123"
        val email = "test@example.com"
        val name = "Test User"
        val password = "password123"
        val phone = "1234567890"
        val address = "123 Test St"

        `when`(firebaseAuth.currentUser).thenReturn(firebaseUser)
        `when`(firebaseUser.uid).thenReturn(userId)
        `when`(firestore.collection("Users")).thenReturn(collectionReference)
        `when`(collectionReference.document(email)).thenReturn(documentReference)
        val setTask = mock<Task<Void>>()
        `when`(documentReference.set(any())).thenReturn(setTask)

        // Act
        viewModel.addUserToDB(name, email, password, phone, address)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        verify(documentReference).set(any<Map<String, Any>>())
    }*/
}

private inline fun <reified T> any(): T = org.mockito.Mockito.any(T::class.java)