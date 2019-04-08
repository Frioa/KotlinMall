package com.kotlin.user.service

import com.kotlin.user.data.protocol.UserInfo
import rx.Observable

interface UploadService{

    fun getUploadToken(): Observable<String>
}