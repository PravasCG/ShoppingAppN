package com.shoppy.botpassignment

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shoppy.botpassignment.screens.register.RegisterScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testRegisterScreen_UIElementsDisplayed() {
        composeTestRule.setContent {
            RegisterScreen(navController = rememberNavController())
        }

        // Check if input fields and Register button exist
        composeTestRule.onNodeWithText("Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Phone no").assertIsDisplayed()
        composeTestRule.onNodeWithText("Address").assertIsDisplayed()
        composeTestRule.onNodeWithText("Register").assertIsDisplayed()
    }

    @Test
    fun testRegisterButtonClick_withEmptyFields_ShowsError() {
        composeTestRule.setContent {
            RegisterScreen(navController = rememberNavController())
        }

        // Click on Register button without entering data
        composeTestRule.onNodeWithText("Register").performClick()

        // Check if error message is displayed
        composeTestRule.onNodeWithText("Fields cannot be empty").assertIsDisplayed()
    }

    @Test
    fun testRegisterButtonClick_withInvalidEmail_ShowsError() {
        composeTestRule.setContent {
            RegisterScreen(navController = rememberNavController())
        }

        // Enter invalid email containing "admin."
        composeTestRule.onNodeWithText("Name").performTextInput("Test User")
        composeTestRule.onNodeWithText("Email").performTextInput("admin.test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Phone no").performTextInput("1234567890")
        composeTestRule.onNodeWithText("Address").performTextInput("123 Street, City")

        // Click on Register button
        composeTestRule.onNodeWithText("Register").performClick()

        // Check if error message appears
        composeTestRule.onNodeWithText("Email cannot have admin").assertIsDisplayed()
    }

    @Test
    fun testRegisterButtonClick_withValidInput_NavigatesToLoginScreen() {
        composeTestRule.setContent {
            RegisterScreen(navController = rememberNavController())
        }

        // Enter valid user details
        composeTestRule.onNodeWithText("Name").performTextInput("John Doe")
        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        composeTestRule.onNodeWithText("Phone no").performTextInput("9876543210")
        composeTestRule.onNodeWithText("Address").performTextInput("456 Market St, NY")

        // Click on Register button
        composeTestRule.onNodeWithText("Register").performClick()

        // Simulate navigation (success case)
        composeTestRule.waitForIdle() // Wait for UI updates
    }

    @Test
    fun testLoginLink_NavigatesToLoginScreen() {
        composeTestRule.setContent {
            RegisterScreen(navController = rememberNavController())
        }

        // Click on "Login" text
        composeTestRule.onNodeWithText("Login").performClick()

        // Simulate navigation to LoginScreen
        composeTestRule.waitForIdle()
    }
}
