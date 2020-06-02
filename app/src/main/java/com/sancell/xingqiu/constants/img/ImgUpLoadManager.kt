package com.sancell.xingqiu.constants.img

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.luck.picture.lib.entity.LocalMedia
import com.sancell.xingqiu.constants.MyOSSUtils
import com.sancell.xingqiu.constants.utils.RSAUtils
import com.sancell.xingqiu.entity.live.UpLoadPhotoInfoBean
import com.sancell.xingqiu.interfaces.OnImagUpLoadLinsener
import com.sancell.xingqiu.mvvm.viewmodel.ImagUploadViewModel

class ImgUpLoadManager {
    var mImagUploadViewModel: ImagUploadViewModel? = null
    private var activity: FragmentActivity? = null
    private var fragme: Fragment? = null
    private var context: Context? = null
    private var mOnImagUpLoadLinsener: OnImagUpLoadLinsener? = null
    private var mHandler: Handler? = null

    constructor(activity: FragmentActivity) {
        this.activity = activity
        context = activity
        mImagUploadViewModel = ViewModelProviders.of(activity).get(ImagUploadViewModel::class.java)
        startObs()
    }

    constructor(fragme: Fragment) {
        this.fragme = fragme
        context = fragme.context
        mImagUploadViewModel = ViewModelProviders.of(fragme).get(ImagUploadViewModel::class.java)
        startObs()
    }

    fun startObs() {
        mHandler = Handler(Looper.getMainLooper())
        mImagUploadViewModel?.apply {


            fragme?.apply {
                errMsg.observe(this, Observer {
                    mOnImagUpLoadLinsener?.onImagUploadError(it)

                })
            }
            activity?.apply {
                errMsg.observe(this, Observer {
                    mOnImagUpLoadLinsener?.onImagUploadError(it)
                })
            }
        }
    }

    fun uploadImage(fileType: String, objId: String, filePath: String, mOnImagUpLoadLinsener: OnImagUpLoadLinsener) {
        this.mOnImagUpLoadLinsener = mOnImagUpLoadLinsener
        val bitmap = BitmapFactory.decodeFile(filePath)
        val split = filePath.split(".")
        val imgType = split.get(split.size - 1)

        val mObs = mImagUploadViewModel?.upRemmoidtyName(fileType, objId, imgType, bitmap.width.toString(), bitmap.height.toString(), (bitmap.byteCount / 1024)
                .toString())


        mObs?.apply {

            fragme?.apply {
                mObs.observe(this, Observer {
                    toImgUploadAlyun(it, filePath)
                })
            }
            activity?.apply {
                mObs.observe(this, Observer {
                    toImgUploadAlyun(it, filePath)
                })
            }
        }
    }

    /**
     * 图片上传
     */
    fun uploadImageSelect(fileType: String, objId: String, mLocalMedia: LocalMedia, mOnImagUpLoadLinsener: OnImagUpLoadLinsener) {
        var mSelectImgPath = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mSelectImgPath = mLocalMedia.androidQToPath

        } else {
            mSelectImgPath = mLocalMedia.path
        }
        uploadImage(fileType, objId, mSelectImgPath, mOnImagUpLoadLinsener)
    }

    //把图片上传到阿里云
    fun toImgUploadAlyun(mUpLoadPhotoInfoBean: UpLoadPhotoInfoBean, filePath: String) {

        val bucketName: String = mUpLoadPhotoInfoBean.getBackName()
        val objectKey: String = mUpLoadPhotoInfoBean.getKeyName()
        val accessKeyId: String = mUpLoadPhotoInfoBean.getAccessKeyId()
        val accessKeySecret = RSAUtils.decryptByPublic(mUpLoadPhotoInfoBean.getAccessKeySecret())
        val securityToken: String = mUpLoadPhotoInfoBean.getSecurityToken()
        MyOSSUtils.getInstance().getOSs(context!!, mUpLoadPhotoInfoBean.getEndpoint(), accessKeyId, accessKeySecret, securityToken)

        MyOSSUtils.getInstance().upImage(context!!, object : MyOSSUtils.OssUpCallback {
            override fun successImg(img_url: String) {
                mHandler?.post {
                    mOnImagUpLoadLinsener?.onImagUploadSucess(img_url)
                }
            }

            override fun successVideo(video_url: String) {}
            override fun inProgress(progress: Long, zong: Long) {}
        }, bucketName, objectKey, filePath)
    }
}