package com.kotlin.paysdk.injection.module
import com.kotlin.paysdk.service.PayService
import com.kotlin.paysdk.service.imp.PayServiceImpl
import dagger.Module
import dagger.Provides

@Module
class PayModule {

    @Provides
    fun providePayservice(payService: PayServiceImpl): PayService{
        return payService
    }
}