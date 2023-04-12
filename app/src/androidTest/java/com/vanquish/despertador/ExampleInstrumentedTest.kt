package com.vanquish.despertador

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.vanquish.despertador.database.AppDatabase
import com.vanquish.despertador.database.dao.AlarmDao
import com.vanquish.despertador.database.models.Alarm
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@CustomTestApplication(HiltTestApplication::class)
class ExampleInstrumentedTest {

    private lateinit var database: AppDatabase
    private lateinit var alarmDao: AlarmDao

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var context: Context

    @Before
    fun setup() {
        hiltRule.inject()
        val appContext = context.applicationContext
        database = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java)
            .allowMainThreadQueries().build()
        alarmDao = database.alarmDao()
    }


    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAlarmOnDatabase() = runBlocking {
        val alarm = Alarm(
            timeString = "08:30",
            daysOfWeekString = "Segunda, Quarta, Sexta",
            soundUriString = "content://media/external/audio/media/123",
            label = "Alarme 1"
        )
        alarmDao.insertAlarm(alarm)
        val retrievedAlarm = alarmDao.getAlarm(alarm.id)
        TestCase.assertEquals(alarm, retrievedAlarm)
    }
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.vanquish.despertador", appContext.packageName)
    }
}