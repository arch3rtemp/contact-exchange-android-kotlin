package dev.arch3rtemp.ui.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.arch3rtemp.tests.coroutines.FlowTestObserver
import dev.arch3rtemp.tests.coroutines.MainCoroutinesRule
import dev.arch3rtemp.tests.coroutines.TestObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class BaseViewModelTest {

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TestViewModel

    private lateinit var testObserver: TestObserver<TestState>
    private lateinit var effectObserver: FlowTestObserver<TestEffect>

    @BeforeTest
    fun setUp() {
        testObserver = TestObserver<TestState>()
        viewModel = TestViewModel()
        viewModel.state.observeForever(testObserver)
        runTest {
            effectObserver = FlowTestObserver<TestEffect>(this, viewModel.effect)
        }
    }

    @AfterTest
    fun tearDown() {
        viewModel.state.removeObserver(testObserver)
        testObserver.clear()
        effectObserver.cancel()
    }

    @Test
    fun testInitialState() = runTest {
        assertEquals(TestState(TestViewModel.INITIAL_STATE), viewModel.state.value)
    }

    @Test
    fun testSetEventAndHandleEvent() = runTest {
        viewModel.setEvent(TestEvent(TEST_EVENT))

        advanceUntilIdle()

        assertEquals(TestState("${TestViewModel.HANDLED_EVENT}$TEST_EVENT"), testObserver.latestValue)
    }

    @Test
    fun testSetEffect() = runTest {
        viewModel.triggerEffect(TestEffect(TEST_EFFECT))

        advanceUntilIdle()

        assertEquals(1, effectObserver.values.size)
        assertEquals(TestEffect(TEST_EFFECT), effectObserver.values[0])
    }

    companion object {
        const val TEST_EVENT = "Test Event"
        const val TEST_EFFECT = "Test Effect"
    }
}
