package com.kotlin.provider.router

object RouterPath{
    class UserCenter{
        companion object {
            const val PATH_LOGIN = "/userCenter/login" //前缀不能重复
        }
    }

    class OrderCenter1{
        companion object {
            const val PATH_ORDER_CONFIRM = "/o/confirm" //前缀不能重复
        }
    }
    class OrderCenter{
        companion object {
            const val PATH_ORDER_CONFIRM = "/orderCenter/confirm" //前缀不能重复
        }
    }

    class PaySDK{
        companion object {
            const val PATH_PAY = "/paySDK/pay"
        }
    }

    class MessageCenter{
        companion object {
            const val PATH_MESSAGE_PUSH = "/messageCenter/push"
            const val PATH_MESSAGE_ORDER = "/messageCenter/order"
        }
    }
}