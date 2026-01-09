package com.core.presentation.base

import app.cash.turbine.test
import com.core.presentation.data.AppError
import com.firebase.analytics.AnalyticsTracker
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verify.VerifyMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class BaseViewModelTest : KoinTest {

    // --- 1. Test Doubles ---
    data class TestState(val data: String = "Initial") : ViewState
    sealed class TestAction : ViewAction { data object DoSomething : TestAction() }
    sealed class TestEffect : ViewEffect { data object ShowSnackbar : TestEffect() }

    // --- 2. Concrete ViewModel ---
    class TestViewModel(initialState: TestState = TestState()) :
        BaseViewModel<TestState, TestAction, TestEffect>(initialState) {

        // Expose protected method 'updateState'
        fun triggerUpdate(newData: String) = updateState { copy(data = newData) }

        // Expose protected method 'sendEffect'
        fun triggerEffect() = sendEffect(TestEffect.ShowSnackbar)

        // Expose protected method 'launchSafe' and 'sendError'
        fun triggerSafeLaunch(shouldFail: Boolean = false, onRetry: (() -> Unit)? = null) {
            launchSafe(onRetry) {
                if (shouldFail) throw RuntimeException("KMP Error")
                updateState { copy(data = "Success") }
            }
        }

        // Override 'handleAction' to test logic
        override fun handleAction(action: TestAction) {
            when (action) {
                TestAction.DoSomething -> updateState { copy(data = "Action Handled") }
            }
        }

        var initialDataLoadedCount = 0
        override fun loadInitialData() { initialDataLoadedCount++ }
    }

    // --- 3. Setup ---
    private lateinit var viewModel: TestViewModel
    private val analyticsTracker = mock<AnalyticsTracker>()
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Stub Analytics (Strict by default in Mokkery)
        every { analyticsTracker.logScreen(any()) } returns Unit

        startKoin {
            modules(module { single { analyticsTracker } })
        }
        viewModel = TestViewModel()
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    // --- 4. Tests ---

    @Test
    fun givenIdleState_whenSubscribing_thenInitialDataLoadedOnce() = runTest {
        val job = viewModel.state.launchIn(this)
        advanceUntilIdle()

        assertEquals(1, viewModel.initialDataLoadedCount)

        verify(mode = VerifyMode.exactly(1)) {
            analyticsTracker.logScreen(screenName = "Test")
        }

        val job2 = viewModel.state.launchIn(this)
        advanceUntilIdle()

        assertEquals(1, viewModel.initialDataLoadedCount)

        job.cancel()
        job2.cancel()
    }

    @Test
    fun givenUpdateState_whenTriggered_thenStateIsUpdated() = runTest {
        viewModel.state.test {
            assertEquals("Initial", awaitItem().data)

            // This calls the helper function you asked about
            viewModel.triggerUpdate("New Data")

            assertEquals("New Data", awaitItem().data)
        }
    }

    @Test
    fun givenAction_whenHandleAction_thenLogicExecuted() = runTest {
        viewModel.state.test {
            assertEquals("Initial", awaitItem().data)

            viewModel.handleAction(TestAction.DoSomething)

            assertEquals("Action Handled", awaitItem().data)
        }
    }

    @Test
    fun givenEffect_whenTriggered_thenEffectEmitted() = runTest {
        viewModel.effect.test {
            viewModel.triggerEffect()
            assertEquals(TestEffect.ShowSnackbar, awaitItem())
        }
    }

    @Test
    fun givenSafeLaunchError_whenOccurs_thenAppErrorIsEmittedWithRetryAction() = runTest {
        var retryCount = 0
        val retryAction = { retryCount += 1 }

        viewModel.appError.test {
            // 1. Initial state is null
            assertNull(awaitItem())

            // 2. Trigger a failing action with a retry mechanism
            viewModel.triggerSafeLaunch(shouldFail = true, onRetry = retryAction)

            // 3. Assert an AppError is emitted
            val error = awaitItem()
            assertIs<AppError>(error)
            assertEquals("KMP Error", error.message)

            // 4. Invoke the error's action and verify it triggers the retry
            error.action()
            assertEquals(1, retryCount, "Retry action should be called once.")

            // 5. Verify that the retry action is a one-off
            error.action()
            assertEquals(1, retryCount, "Retry action should not be called again.")
        }
    }

    @Test
    fun givenErrorState_whenClearError_thenErrorIsNull() = runTest {
        viewModel.appError.test {
            // Initial state is null
            assertNull(awaitItem(), "Initial error state should be null")

            // Trigger an error
            viewModel.triggerSafeLaunch(shouldFail = true)
            assertIs<AppError>(awaitItem(), "An AppError should be emitted")

            // Clear the error
            viewModel.clearError()

            // Assert the error is cleared
            assertNull(awaitItem(), "Error state should be null after clearing")
        }
    }
}