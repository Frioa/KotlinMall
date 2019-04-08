package com.kotlin.user.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.CropOptions
import com.jph.takephoto.model.InvokeParam
import com.jph.takephoto.model.TContextWrap
import com.jph.takephoto.model.TResult
import com.jph.takephoto.permission.InvokeListener
import com.jph.takephoto.permission.PermissionManager
import com.jph.takephoto.permission.TakePhotoInvocationHandler
import com.kotlin.base.common.BaseApplication.Companion.context
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.DateUtils
import com.kotlin.base.utils.GlideUtils
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.user.R
import com.kotlin.user.data.protocol.UserInfo
import com.kotlin.user.injection.component.DaggerUserComponent
import com.kotlin.user.injection.module.UserModule
import com.kotlin.user.presenter.UserInfoPresenter
import com.kotlin.user.presenter.view.UserInfoView
import com.kotlin.user.utils.UserPrefsUtils
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import kotlinx.android.synthetic.main.activity_user_info.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.File
import javax.inject.Inject
/*
    用户信息
 */

class UserInfoActivity @Inject constructor(): BaseMvpActivity<UserInfoPresenter>(), UserInfoView, View.OnClickListener, TakePhoto.TakeResultListener, InvokeListener {

    private lateinit var mTakePhoto:TakePhoto

    private lateinit var mTempFile:File
    private lateinit var invokeParam:InvokeParam

    private val mUploadManager: UploadManager by lazy { UploadManager() }

    private  var mLocalFileUrl:String? = null
    private  var mRemoteFileUrl:String? = null

    private var mUserIcon:String? = null
    private var mUserName:String? = null
    private var mUserMoblie:String? = null
    private var mUserGender:String? = null
    private var mUserSign:String? = null

    override fun injectComponent() {// Dagger2 的注册
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        getTakePhoto()  //初始化TakePhoto
        initView()
        initData()
    }

    /*
        初始化视图
     */
    private fun initView() {
        mUserIconView.onClick(this)
        mHeaderBar.getRightView().onClick{
            mPresenter.editUser(mRemoteFileUrl!!,
                    mUserNameEt.text?.toString()?:"",
                    if (mGenderMaleRb.isChecked) "0" else "1",
                    mUserSignEt.text?.toString()?:"")
        }
    }
    private fun initData() {
        mUserIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
        mUserName = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        mUserMoblie = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_MOBILE)
        mUserGender = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_GENDER)
        mUserSign = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)
        mRemoteFileUrl = mUserIcon

        if (mUserIcon != ""){
            GlideUtils.loadUrlImage(this,mUserIcon!!, mUserIconIv)
            Log.d("initData()",""+mUserIcon)
        }
        mUserNameEt.setText(mUserName)
        mUserMobileTv.setText(mUserMoblie)
        if (mUserGender == "0"){
            mGenderFemaleRb.isChecked = true
        } else {
            mGenderFemaleRb.isChecked = false
        }
        mUserSignEt.setText(mUserSign)
    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.mUserIconView -> {
                showAlertView()
            }

        }
    }

    private fun showAlertView() {
        AlertView("选择图片","请选择头像","取消",null, arrayOf("拍照","相册"),this
                ,AlertView.Style.ActionSheet,object :OnItemClickListener{
            override fun onItemClick(o: Any?, position: Int) {
                mTakePhoto.onEnableCompress( CompressConfig.ofDefaultConfig(), true)    //压缩图片
                when(position){
                    0 -> showCamera()
                    1 -> mTakePhoto.onPickFromGallery()
                }
            }
        }).show()
    }

    override fun takeSuccess(result: TResult?) {
        Log.d("TakePhoto：原始地址",result?.image?.originalPath)//原始地址
        Log.d("TakePhoto：压缩地址",result?.image?.compressPath)//压缩地址

        //mLocalFileUrl =result?.image?.originalPath //原图
        mLocalFileUrl =result?.image?.compressPath //压缩
        mPresenter.getUploadToken()
    }

    override fun takeCancel() {
        toast("takeCancel()")
    }

    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("TakeFail",msg + "    "+ result?.image?.originalPath)
        Log.e("TakeFail",msg + "    "+ result?.image?.compressPath)
        toast("takeFail"+msg)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode,resultCode,data)
    }

    fun createTempFile(){// 创建临时文件
        val tempFileName = "${DateUtils.curTime}.png"   // 文件名
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            this.mTempFile = File(Environment.getExternalStorageDirectory(),tempFileName)   //生成临时文件
            if (!this.mTempFile.parentFile.exists()) {
            val mkdirs:Boolean = this.mTempFile.mkdirs()
            Log.e("createTempFile()",tempFileName)
            if (!mkdirs) {
                toast("文件目录创建失败")
            }else{
                toast("文件创建成功")
            }
        }
            return
        }
        this.mTempFile = File(filesDir,tempFileName)
    }
    fun getTakePhoto(){
        mTakePhoto = TakePhotoInvocationHandler.of(this).bind(TakePhotoImpl(this,this)) as TakePhoto
    }
    fun showCamera(){
        createTempFile()    //获取图片
        mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //以下代码为处理Android6.0、7.0动态权限所需
        val type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults)
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this)
    }
    override fun invoke(invokeParam: InvokeParam?): PermissionManager.TPermissionType {
        val type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam!!.getMethod())
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam
        }
        return type
    }


    override fun onGetUploadTokenResult(result: String) {
        Log.d("test",result)
        mUploadManager.put(mLocalFileUrl,null,result,object :UpCompletionHandler{
            override fun complete(key: String?, info: ResponseInfo?, response: JSONObject?) {
                mRemoteFileUrl = BaseConstant.IMAGE_SERVER_ADDRESS + response!!.get("hash")
                Log.d("test",mRemoteFileUrl)

                GlideUtils.loadUrlImage(this@UserInfoActivity,mRemoteFileUrl!!,mUserIconIv)
            }
        },null)
    }
    override fun onEditUserResult(result: UserInfo) {
        toast("修改成功")
        UserPrefsUtils.putUserInfo(result)
    }
}
