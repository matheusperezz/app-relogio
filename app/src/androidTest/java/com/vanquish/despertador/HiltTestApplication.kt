package com.vanquish.despertador

import android.app.Application
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltTestApplication

@CustomTestApplication(HiltTestApplication::class)
class HiltTestApplication: Application()