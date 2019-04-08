package com


import com.alipay.api.AlipayApiException
import com.alipay.api.DefaultAlipayClient
import com.alipay.api.domain.AlipayTradeAppPayModel
import com.alipay.api.request.AlipayTradeAppPayRequest
import com.kotlin.arithmetic.sort.list.impl.MyArrayList
import com.kotlin.arithmetic.sort.method.builder.BaseBuider
import com.kotlin.arithmetic.sort.method.model.bubbing.BubbingModel
import com.kotlin.arithmetic.sort.method.model.heap.impl.HeapModul
import com.kotlin.arithmetic.sort.method.model.insert.InsertModel
import com.kotlin.arithmetic.sort.method.model.merge.impl.MergeLowModel
import com.kotlin.arithmetic.sort.method.model.merge.impl.MergeTopModel
import com.kotlin.arithmetic.sort.method.model.quick.impl.QuickOneModel
import com.kotlin.arithmetic.sort.method.model.quick.impl.QuickThreeModel
import com.kotlin.arithmetic.sort.method.model.quick.impl.QuickTwoModel
import com.kotlin.arithmetic.sort.method.model.select.SelectModel



/*
    1. 扩展函数扩展操作符问题
    2. 建造者模式
    3. 模板模式
    4. 外观模式
    5. 深拷贝与浅拷贝问题
 */
fun main() {
    var position = true
    var n = 500

    val array = MyArrayList(20, false)
            .generateOrderArrayList()
            .generateAlmostArrayList(20)
            .getInstance()


    println("对 mArray 数组，size = ${array.array.size}, 随机范围 [1 ,${array.array.size}] 进行排序")

//    bubbing.startSort("")
//    bubbing.printmList()
//    println("${bubbing.TAG} : ${bubbing.getTime()} 秒 ")

//    select.startSort("")
//    select.printmList()
//    println("${select.TAG} : ${select.getTime()} 秒 ")
//
//    inset.startSort("")
//    inset.printmList()
//    println("${inset.TAG} : ${inset.getTime()} 秒 ")
//
//    mergeTop.startSort("")
//    mergeTop.printmList()
//    println("${mergeTop.TAG} : ${mergeTop.getTime()} 秒 ")
//
//    mergeLow.startSort("")
//    mergeLow.printmList()
//    println("${mergeLow.TAG} : ${mergeLow.getTime()} 秒 ")
//
//    quickOne.startSort("")
//    quickOne.printmList()
//    println("${quickOne.TAG} : ${quickOne.getTime()} 秒 ")
//
//    quickTwo.startSort("")
//    quickTwo.printmList()
//    println("${quickTwo.TAG} : ${quickTwo.getTime()} 秒 ")
//
//    quickThree.startSort("")
//    quickThree.printmList()
//    println("${quickThree.TAG} : ${quickThree.getTime()} 秒 ")
//
//    heap.startSort("")
//    heap.printmList()
//    println("${heap.TAG} : ${heap.getTime()} 秒 ")

    //实例化客户端
    val alipayClient = DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", "2016092500596506", "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDQ+m098MmdGYzOQSUg0NBqNtQs9SF4Ks/CkcWVX1FkRSyl2jZ5UqCE8NdK/v2RSiDTKrX8a4tX5J2Mf8Iq6wEr9rOo3fOklLx26gBj+sIXp5py+qq0Crfsru/D6xX39l2ju3NI+1kj0GtzcyVPkk7t5jK/HlPlMNuL0dYIogRrS89qhsBkqPw7CaHDQ94uf91+XjnVC3kxZur0zXEHF8v0YbvpiSvcYmiVxdsivLvf6w523Ib0yEZMW7oo5PaGktfGY+qZbuzrs5RF31b3gomK4C+3Q1+LDufBUnIpX00oQCOHfdl0Fh7ak+BZcv7Cowv50D7OB8VSE/W0rr1LLGQbAgMBAAECggEBAKCzgyA3swKhLDtLs1551VtoUF9GHffHjD7GVFealf9yhnP/yxYe6RtyDU05qwp4I6ffV1UF6oCraFArysewMhV5wAyiqYKtcgRLzAMWSP1hwI6cnnqXJ3rVx8E0XY9yH+4R7wR3bMPyuT5tlIqNKy8cb3eyvbcojBwIP/whgx5yKCunjD3jTQyj3DES9T9zueUDLHa2AgUZ7Y/UXqLwSUyEM63G3Tsp9bmIi7CWQBYQlcxc7e9wvsdw1UrhjJod1i40nRT3wNMjqXf55FFOsriMxEX+Tb4iVHKnlVLDquBd8Hn6YCp/uZt/3LTzSENoHSapvBYHMV3NV5aG4yiAiMECgYEA+FPkgnduaR4g/lhrf0OLYcotuuyulz2spkcYs5J/rIFgKFwotWcCKFBQM7K/tCfIHm3WO7OU4DybjAhUPkT3Wf+ob2IvsHZoxQj4nhNlIuOZeEh0/pFhj5SDQivCjm5O3sj96SaaheX4PFRgGGWZOpXZ5ttriH+c0kx5kxT2fvsCgYEA129OTQlWQQvOU5c5ud396Mk1cN+FlRDPGLxOKfnUR7d+Ji6aSiSx1MH1+wXQQSnkaFo+qVk7+3GFYmdM7I7oVHaQ6tVeJFSObpqo8RtTX0/iFtcicECyEhX7LCN1/8gOAUgQH9LXAWw8oSp+7EhnH55VVFuKIbOp7D9j38CEJWECgYAnKCNaJzbzwFp3gL3CnYX274hydyu4kXMN7RwKBMm5C9V7x7xrjkiazr82x7LO148IrU0gsNqC2Uu1swKFpx5RxKxsk9DtRF6U4ytA0dIaxETI4LQCCC6YE1T3NCtDhkVNf+f5waqP/ok8Chn0/uBAiqyHaIX2ShkSWBi7hoyvzQKBgH6BaeVq2i+QEzSa1NcEOJl9rdf+KZ8DI9lOmkej4LfMVFNvgkKPka1xyFQDcesSAoIUnD8tmz9nxf+m0VEICf2vMiHz8Tg8PvdL8nDCrw6FBiqLYjmwg+CC799XY28ztqWh/3XwhTjcqi2pmqZ9TbEPdOP4bQClU97ayncVwYOBAoGBAIrRuFm/9bTk7Y3mP0TYLvJxMgaKYQtXS6CSDghNEIpj6AUDC3GwuzQQWKmYnjDTPnHVhbBIMV8/z6mKDW/JNFqdkr5CRj7JGmItubxH4GJQwk8xDZqSBZdxvfbILhwOjEklRYMlptU4izeIb+qDmx2RuCoL8zwhl7UWwIzPRzPF", "json", "utf-8", "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw0O0BZyBD5yeAXpclWIIVF2oVRHZMFEQz9/e/z71duVb25bhEJ9gzYzUBWaNd00ZjFDZ9FXpOeUylbvgxY6rICNSU7wIySMkoTYxWPfStKzHk1rOkKOLNaJqARZGSCR6jHtSOeLU3Y3m1dZJXQdiceut8zXREG78tejigzdequ7OM8H9+SzdTcrX2aXXxGtTaoPxxPlMg17tfh7QqHnkAb4xBHW56SLsXqeOwAgR7qQIWKV7pQzJCobQQnC1zrja7BZRVQcocIriKURXQ4EPxZCFD8tXewbNFOnHN1ZsfXt3P6lgcGWjPcSEuc9Tqv9UtJKfZQM459a27RN79lOyiwIDAQAB", "RSA2")
//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
    val request = AlipayTradeAppPayRequest()
//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
    val model = AlipayTradeAppPayModel()
    model.body = "我是测试数据"
    model.subject = "App支付测试Java"
    model.timeoutExpress = "30m"
    model.totalAmount = "0.01"

    request.bizModel = model
    request.notifyUrl = "商户外网可以访问的异步地址"
    try {
        //这里和普通的接口调用不同，使用的是sdkExecute
        val response = alipayClient.sdkExecute(request)
        println(response.body)//就是orderString 可以直接给客户端请求，无需再做处理。
    } catch (e: AlipayApiException) {
        e.printStackTrace()
    }


}


