package com.vanquish.despertador

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vanquish.despertador.data.AppDatabase
import com.vanquish.despertador.data.dao.AlarmDao
import com.vanquish.despertador.data.models.Alarm
import com.vanquish.despertador.data.repository.AlarmRepository
import com.vanquish.despertador.ui.viewmodels.AlarmClockViewModel
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseInstrumentedTest {
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

    /**
     * Integration Tests
     */

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
            savedAlarm?.let {
                Assert.assertEquals(exampleAlarm, it)
            }
        }
    }

    @Test
    fun testDeleteAlarmOnDatabase(){
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
            launch {
                dao.deleteAlarm(exampleAlarm)
            }
            val savedAlarm = dao.getAlarm(exampleAlarm.id).singleOrNull()
            Assert.assertNull(savedAlarm)
        }
    }

    @Test
    fun testUpdateAlarmOnDatabase(){
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
            val updatedAlarm = exampleAlarm.copy(
                timeString = "08:00",
                daysOfWeekString = "Monday, Wednesday, Friday, Sunday",
                soundUriString = "content://com.android.providers.media.documents/document/audio%3A11",
                label = "Wake up, please!"
            )
            launch {
                dao.updateAlarm(updatedAlarm)
            }
            val savedAlarm = dao.getAlarm(exampleAlarm.id).singleOrNull()
            savedAlarm?.let {
                Assert.assertEquals(updatedAlarm, it)
            }
        }
    }
}