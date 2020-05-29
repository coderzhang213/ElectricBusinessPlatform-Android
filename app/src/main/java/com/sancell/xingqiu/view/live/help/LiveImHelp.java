package com.sancell.xingqiu.view.live.help;

import android.app.Activity;
import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import com.sancell.xingqiu.constants.UserManager;
import com.sancell.xingqiu.constants.utils.ConvertUtils;
import com.sancell.xingqiu.constants.utils.RSAUtils;
import com.sancell.xingqiu.dialog.VideoRelDialogFgm;
import com.sancell.xingqiu.entity.live.VideoRelationRes;
import com.sancell.xingqiu.help.ToastHelper;
import com.sancell.xingqiu.utils.ScreenUtil;
import com.sancell.xingqiu.utils.StringCheckUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zj on 2019/11/27.
 */
public class LiveImHelp {
    private Activity mActivity;
    private String roomId;
    private String commType = "1";//1 是普通商品查看，2是主播商品查看
    private String mAnchorId;//主播Id


    public LiveImHelp(Activity mActivity, String roomId, String commType) {
        this.mActivity = mActivity;
        this.roomId = roomId;
        this.commType = commType;
        initView();
        initData();
    }

    /**
     * 设置主播ID
     *
     * @param mAnchorId
     */
    public void setmAnchorId(String mAnchorId) {
        this.mAnchorId = mAnchorId;
    }


    private void initView() {
    }

    public void click(String type) {

        getData(roomId, type);//被关联的商品(a)或者群组(b)
    }

    private void initData() {

    }


    /**
     * 获取关联商品
     */
    private void getData(String roomId, final String type) {
        if (TextUtils.isEmpty(roomId)) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        String reqTime = StringCheckUtils.getCurrentTime();
        map.put("reqTime", reqTime);
        map.put("skey", UserManager.INSTANCE.getUserSkey());
        map.put("hashToken", RSAUtils.encryptByPublic(map));
        map.put("batchId", roomId);
        map.put("type", type);
    }


    /**
     * 群组
     *
     * @param datas
     */
    public void getVideoTeamSuccess(VideoRelationRes datas) {
        if (datas.dataList == null || datas.dataList.size() <= 0) {
            ToastHelper.showToast("本场直播没有推荐社群");
            return;
        }
        final VideoRelDialogFgm dialogFgm = VideoRelDialogFgm.newInstance("3", datas.dataList);
        dialogFgm.setTv_title("推荐群组");
        dialogFgm.setDialogHeight(getDialogHeight());
        dialogFgm.setTeamClickListener(new VideoRelDialogFgm.OnTeamClickListener() {
            @Override
            public void onApplyJoin(String teamId) {
                dialogFgm.dismiss();


            }

            @Override
            public void onChat(String teamId) {
                dialogFgm.dismiss();
            }
        });
        dialogFgm.show(((FragmentActivity) mActivity).getSupportFragmentManager(), "team");
    }

    public void getError(String error) {
        ToastHelper.showToast(mActivity, error);
    }

    public void addCartSuccess() {
    }

    /**
     * 商品
     *
     * @param datas
     */
    public void getVideoGoodsSuccess(VideoRelationRes datas) {
        if (datas.dataList == null || datas.dataList.size() <= 0) {
            ToastHelper.showToast("本场直播没有推荐商品");
            return;
        }
        final VideoRelDialogFgm dialogFgm = VideoRelDialogFgm.newInstance(commType, datas.dataList);
        dialogFgm.setTv_title("推荐商品");
        dialogFgm.setDialogHeight(getDialogHeight());
        dialogFgm.setGoodsListener(new VideoRelDialogFgm.OnGoodsListener() {
            @Override
            public void addCart(String goodId) {
                //加车
//                AddCartReq req = new AddCartReq();
//                req.goodsId = goodId;
//                req.goodsNum = "1";
//                LiveAddCart(req);
                //商品详情
//                Intent intent = new Intent(mActivity, ProductInfoActivity.class);
//                intent.putExtra(Constants.Key.KEY_1, goodId);
//                mActivity.startActivity(intent);
                //GoodsDetailActivity.start(mActivity, Integer.parseInt(goodId), roomId);
                dialogFgm.dismiss();
            }

            @Override
            public void goodDetail(String goodsId) {
                //商品详情
//                Intent intent = new Intent(mActivity, ProductInfoActivity.class);
//                intent.putExtra(Constants.Key.KEY_1, goodsId);
//                mActivity.startActivity(intent);
                //GoodsDetailActivity.start(mActivity, Integer.parseInt(goodsId), roomId);

                dialogFgm.dismiss();

            }
        });
        dialogFgm.show(((FragmentActivity) mActivity).getSupportFragmentManager(), "goods");
    }

    public int getDialogHeight() {
        return (int) (ScreenUtil.screenHeight * 0.6);
    }


}
