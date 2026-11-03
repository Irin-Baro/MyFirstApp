package com.example.myapplication.domain

interface AppRepository {
    fun getApps(): List<AppDomain>
}