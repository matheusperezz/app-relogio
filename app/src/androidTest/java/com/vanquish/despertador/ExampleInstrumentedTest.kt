package com.vanquish.despertador

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.vanquish.despertador.data.AppDatabase
import com.vanquish.despertador.data.dao.AlarmDao
import com.vanquish.despertador.data.models.Alarm
import com.vanquish.despertador.data.repository.AlarmRepository
import com.vanquish.despertador.ui.viewmodels.AlarmClockViewModel
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: AlarmDao
    private lateinit var repository: AlarmRepository
    private lateinit var viewModel: AlarmClockViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = database.alarmDao()
        repository = AlarmRepository(dao)
        viewModel = AlarmClockViewModel(repository)
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun testSaveAlarmOnDatabase(){
        runBlocking {
            val exampleAlarm = Alarm(
                timeString = "07:30",
                daysOfWeekString = "Monday, Wednesday, Friday",
                soundUriString = "content://com.android.providers.media.documents/document/audio%3A10",
                label = "Wake up!"
            )
            launch {
                dao.insertAlarm(exampleAlarm)
            }
            val savedAlarm = dao.getAlarm(exampleAlarm.id).singleOrNull()
            savedAlarm?.let { savedAlarm ->
                assertEquals(exampleAlarm, savedAlarm)
            }

        }
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.vanquish.despertador", appContext.packageName)
    }
}