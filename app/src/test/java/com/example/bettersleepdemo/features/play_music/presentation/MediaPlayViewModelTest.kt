package com.example.bettersleepdemo.features.play_music.presentation

import app.cash.turbine.test
import com.example.bettersleepdemo.features.play_music.presentation.fakes.FakeMediaControllerUseCase
import com.example.bettersleepdemo.features.play_music.presentation.fakes.FakeSoundRepository
import com.example.bettersleepdemo.features.play_music.presentation.ui_event.MediaUIEvent
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class MediaPlayViewModelTest {
    private lateinit var mediaPlayViewModel: MediaPlayViewModel
    private lateinit var repo: FakeSoundRepository
    private lateinit var useCase: FakeMediaControllerUseCase
    private val testDispatcher = UnconfinedTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repo = FakeSoundRepository()
        useCase = FakeMediaControllerUseCase(repo)
        mediaPlayViewModel = MediaPlayViewModel(useCase, Utility.getFakeMediaFileIdMap())
    }

    @Test
    fun testTapClearButton() =

        //tapping clear button will take the app to init stage and hence all
        runTest {
            setup()
            mediaPlayViewModel.onEvent(MediaUIEvent.ClearAllMusic)
            mediaPlayViewModel.mediaUiState.test {
                val curValue = awaitItem()
                curValue.mediaButtonStateList.forEach {
                    Truth.assertThat(it.second).isFalse()
                }
            }

        }

    @Test
    fun testInitialSetup() = runTest {
        setup()
        mediaPlayViewModel =
            MediaPlayViewModel(useCase = useCase, Utility.getFakeMediaFileIdMap())
        mediaPlayViewModel.mediaUiState.test {
            val item = awaitItem()
            item.mediaButtonStateList.forEach {
                Truth.assertThat(it.second).isFalse()
            }
        }

    }

    @Test
    fun testDismissShowDialog() = runTest {
        setup()
        mediaPlayViewModel.onEvent(MediaUIEvent.DismissWarningDialog)
        mediaPlayViewModel.mediaUiState.test {
            val curItem = awaitItem()
            Truth.assertThat(curItem.showWarning).isFalse()
        }
    }


    @Test
    fun testShowDialog() = runTest {
        setup()
        //3 times 3 diff music played, so tapping 4 diff music will show the dialog
        //initial setup
        mediaPlayViewModel.onEvent(MediaUIEvent.PlayMusic(1))
        mediaPlayViewModel.onEvent(MediaUIEvent.PlayMusic(2))
        mediaPlayViewModel.onEvent(MediaUIEvent.PlayMusic(3))
        mediaPlayViewModel.onEvent(MediaUIEvent.PlayMusic(4))

        //execution
        mediaPlayViewModel.mediaUiState.test {
            val curItem = awaitItem()

            //assertion
            Truth.assertThat(curItem.showWarning).isTrue()
        }
    }


    @Test
    fun testPlayAllButton() = runTest {

        repo.saveSound(listOf(1, 2, 3))
        useCase = FakeMediaControllerUseCase(repo)
        mediaPlayViewModel = MediaPlayViewModel(useCase, Utility.getFakeMediaFileIdMap())
        mediaPlayViewModel.onEvent(MediaUIEvent.PlayAllMusic)
        mediaPlayViewModel.mediaUiState.test {
            val item = awaitItem()
            Truth.assertThat(item.isAllSelectedMusicPlaying).isTrue()
            item.mediaButtonStateList.forEach {
                //tap play all should set the button state to true (selected)
                Truth.assertThat(it.second).isTrue()
            }
        }
    }

    @Test
    fun testPauseAllButton() = runTest {
        mediaPlayViewModel.onEvent(MediaUIEvent.PauseAllMusic)
        mediaPlayViewModel.mediaUiState.test {
            val item = awaitItem()
            Truth.assertThat(item.isAllSelectedMusicPlaying).isFalse()
        }
    }

    @Test
    fun testCheckIfLimitExceededWhenLimitExceeded() = runTest {

        //3 music playing
        /*
        the logic is, as long as the currently selected media size is 0,1,2, it will permit new music
        to be added. So if the currently list is size 3, it wont permit new music to be added so,
        3 music adding will return false.
        * */
        mediaPlayViewModel.onEvent(MediaUIEvent.PlayMusic(1))
        mediaPlayViewModel.onEvent(MediaUIEvent.PlayMusic(2))
        mediaPlayViewModel.onEvent(MediaUIEvent.PlayMusic(3))

        //now if want to add another music, it will check and return false
        val ifExceeded = mediaPlayViewModel.checkIfInLimit()
        Truth.assertThat(ifExceeded).isFalse()
    }

    @Test
    fun testCheckIfLimitExceededWhenLimitNOTExceeded() = runTest {

        //2 music playing
        mediaPlayViewModel.onEvent(MediaUIEvent.PlayMusic(1))
        mediaPlayViewModel.onEvent(MediaUIEvent.PlayMusic(2))

        val ifExceeded = mediaPlayViewModel.checkIfInLimit()
        Truth.assertThat(ifExceeded).isTrue()
    }
}
