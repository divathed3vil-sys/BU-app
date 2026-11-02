package com.bu.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * BU — one app, two planes, one data core.
 * Hilt roots the DI graph; everything else hangs from here.
 */
@HiltAndroidApp
class BUApplication : Application()
