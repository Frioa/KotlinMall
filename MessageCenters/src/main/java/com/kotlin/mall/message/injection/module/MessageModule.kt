package com.kotlin.mall.message.injection.module

import com.kotlin.mall.message.service.MessageService
import com.kotlin.mall.message.service.imp.MessageSericeImpl
import dagger.Module
import dagger.Provides

@Module
class MessageModule {
    @Provides
    fun provideMessageService(messageService: MessageSericeImpl): MessageService {
        return messageService
    }
}