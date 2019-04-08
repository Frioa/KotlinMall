#include <jni.h>
#include <string>

// 打印 Log 日志
#include <android/log.h>

#define LOG_TAG   "GOODLUCK"
#define LOGD(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// using namespace std;

/**
 * 1. 线程的附着与分离
 */
extern "C" JNIEXPORT jstring JNICALL

Java_com_kotlin_arithmetic_ui_activity_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject) {

         std::string hello = "Hello from C++";
         return env->NewStringUTF(hello.c_str());
}

