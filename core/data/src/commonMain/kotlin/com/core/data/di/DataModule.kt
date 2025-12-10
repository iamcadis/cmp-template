package com.core.data.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [NetworkModule::class])
@ComponentScan("com.core.data")
class DataModule