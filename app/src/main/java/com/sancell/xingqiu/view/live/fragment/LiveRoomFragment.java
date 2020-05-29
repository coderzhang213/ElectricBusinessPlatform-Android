package com.sancell.xingqiu.view.live.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.sancell.xingqiu.R;
import com.sancell.xingqiu.base.view.BaseFragment;
import com.sancell.xingqiu.base.viewmodel.BaseViewModel;
import com.sancell.xingqiu.constants.LiveConstant;
import com.sancell.xingqiu.dialog.ImChatMsgInputDialog;
import com.sancell.xingqiu.entity.StartAudienceInfo;
import com.sancell.xingqiu.entity.live.LivePlayInfo;
import com.sancell.xingqiu.entity.live.PublishParam;
import com.sancell.xingqiu.entity.live.RedEnvelopeInfo;
import com.sancell.xingqiu.enump.PLAY_STATE;
import com.sancell.xingqiu.interfaces.OnActivityUiLinsenr;
import com.sancell.xingqiu.interfaces.OnBackPressedLinsener;
import com.sancell.xingqiu.interfaces.OnLiveToolLinsener;
import com.sancell.xingqiu.interfaces.OnPlayLinsenr;
import com.sancell.xingqiu.mvvm.viewmodel.LiveViewModel;
import com.sancell.xingqiu.utils.BackPressedUtils;
import com.sancell.xingqiu.view.live.fragment.tengxun.LivePlayerFragment;
import com.sancell.xingqiu.view.live.fragment.tengxun.LiveReplayerFragment;
import com.sancell.xingqiu.view.live.help.ClickLikeHelp;
import com.sancell.xingqiu.view.live.help.GifHelp;

import org.jetbrains.annotations.NotNull;


/**
 * Created by zhukkun on 1/5/17.
 */
@SuppressLint("Registered")
public class LiveRoomFragment extends BaseFragment<BaseViewModel> implements OnActivityUiLinsenr, OnPlayLinsenr, OnBackPressedLinsener {

    //各大板块容器
    private TengXunCameraPusherFragment captureFragment; //主播CaptureFragment

    private RecyclerView rl_gift_list;//礼物列表




    //直播参数
    private boolean isAudience = true; //默认为观众
    private String roomId;
    private boolean isLivePlay = true;//是直播还是回放
    private String yxid;
    private OnLiveToolLinsener mOnPlayLinsenr;
    private boolean isShowInputMsg = false;
    //用来区分是从哪过来的，
    private String mType;
    private GifHelp mGifHelp;
    private ImChatMsgInputDialog mImChatMsgInputDialog;
    private LottieAnimationView iv_gift_icon;
    //点赞辅助类
    private ClickLikeHelp mClickLikeHelp;
    private LiveViewModel mLiveViewModel;
    //直播间ID(我们自己平台的)
    private String batchId;
    private LivePlayInfo mEmpLivePlayInfo = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLivePlay = getArguments().getBoolean(LiveConstant.IS_LIVE_PLAY, true);
        isAudience = getArguments().getBoolean(LiveConstant.IS_AUDIENCE, true);
        mType = getArguments().getString(LiveConstant.LIVE_TYPE);
        loginIm();

        BackPressedUtils.INSTANCE.bindOnBack(getActivity(), this);
    }

    private void loginIm() {//观众并且不是回放就直接初始化
        if (isAudience && isLivePlay) {//观众并且不是回放就直接初始化
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLiveViewModel = ViewModelProviders.of(this).get(LiveViewModel.class);
    }



    /**
     * 静态方法 启动主播
     */
    public static LiveRoomFragment startLive(String roomId, PublishParam param, String batchId) {
        LiveRoomFragment mFragment = new LiveRoomFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(LiveConstant.IS_AUDIENCE, false);
        bundle.putString(LiveConstant.EXTRA_ROOM_ID, roomId);
        bundle.putString(LiveConstant.YXID, batchId);
        bundle.putBoolean(LiveConstant.IS_LIVE_PLAY, true);
        bundle.putSerializable(PublishParam.EXTRA_PARAMS, param);
        mFragment.setArguments(bundle);
        return mFragment;
    }


    /**
     * 静态方法 启动观众
     *
     * @param nStartAudienceInfo
     * @return
     */
    public static LiveRoomFragment startAudience(StartAudienceInfo nStartAudienceInfo) {
        LiveRoomFragment mFragment = new LiveRoomFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(LiveConstant.IS_AUDIENCE, true);
        bundle.putBoolean(LiveConstant.IS_START, nStartAudienceInfo.isStart());
        bundle.putString(LiveConstant.EXTRA_ROOM_ID, nStartAudienceInfo.getRoomId());
        bundle.putBoolean(LiveConstant.IS_LIVE, true); //观众默认为直播, 另一个种模式为点播.
        bundle.putBoolean(LiveConstant.IS_SOFT_DECODE, nStartAudienceInfo.isSoftDecode());
        bundle.putString(LiveConstant.EXTRA_URL, nStartAudienceInfo.getUrl());
        bundle.putString(LiveConstant.YXID, nStartAudienceInfo.getBatchId());
        bundle.putInt(LiveConstant.LIVE_SUM, nStartAudienceInfo.getLiveSum());
        bundle.putBoolean(LiveConstant.IS_LIVE_PLAY, nStartAudienceInfo.isLivePaly());
        bundle.putInt(LiveConstant.LIVE_SHOW_POSTION, nStartAudienceInfo.getShowPostion());
        bundle.putString(LiveConstant.LIVE_TYPE, nStartAudienceInfo.getMType());
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void initView() {

        loadFragment(isAudience);
        iv_gift_icon = (LottieAnimationView) findByView(R.id.iv_gift_icon);
        mClickLikeHelp = new ClickLikeHelp(getContext(), iv_gift_icon);
        mClickLikeHelp.setPlayAinJsonName("data.json");
        rl_gift_list = (RecyclerView) findByView(R.id.rl_gift_list);

        //打算动画
        if (mGifHelp == null) {
            mGifHelp = new GifHelp(getContext(), rl_gift_list);
            mGifHelp.initAdapter();
        }
        initMemberOperate();
        initFinishLiveLayout();
        if (isAudience) {
            //观众 直接显示聊天列表与底部控制栏
            onStartLivingFinished();
        }

    }

    /**
     * 根据是否为观众,加载不同的Fragment
     *
     * @param isAudience 是否为观众
     */
    private void loadFragment(boolean isAudience) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        batchId = getArguments().getString(LiveConstant.YXID);
        roomId = getArguments().getString(LiveConstant.EXTRA_ROOM_ID);

        int postion = getArguments().getInt(LiveConstant.LIVE_SHOW_POSTION);
        if (isAudience) {//客户端
            Fragment audienceFragment = null;
            if (isLivePlay) {//如果是直播
                audienceFragment = LivePlayerFragment.getInstance(this, roomId,
                        batchId, getArguments().getString(LiveConstant.EXTRA_URL),
                        getArguments().getBoolean(LiveConstant.IS_START),
                        isLivePlay, postion, mType);
            } else {//回放
                audienceFragment = LiveReplayerFragment.getInstance(this, roomId,
                        batchId, getArguments().getString(LiveConstant.EXTRA_URL),
                        getArguments().getBoolean(LiveConstant.IS_START),
                        isLivePlay, postion, mType);
            }

            if (audienceFragment instanceof OnLiveToolLinsener) {
                mOnPlayLinsenr = (OnLiveToolLinsener) audienceFragment;
            }
            transaction.replace(R.id.layout_main_content, audienceFragment);
        } else {//主播端
            captureFragment = new TengXunCameraPusherFragment(this, roomId, batchId, postion);
            captureFragment.setArguments(getArguments());
            transaction.replace(R.id.layout_main_content, captureFragment);
        }

    }


    /**
     * 设置礼物
     */
    private void showGift(RedEnvelopeInfo mRedEnvelopeInfo) {

        mGifHelp.addGiftInfo(mRedEnvelopeInfo);
    }

    /**
     * 初始化直播结束布局
     */
    private void initFinishLiveLayout() {

    }


    /**
     * 初始化人员操作布局
     */
    private void initMemberOperate() {



    }


    /**
     * 正常结束直播
     */
    @Override
    public void normalFinishLive() {
        //主播发送离开房间请求
//        if (!isAudience) {
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BackPressedUtils.INSTANCE.unBindOnBack(getActivity(), this);

        if (mGifHelp != null) {
            mGifHelp.onDestroy();
        }
        if (mClickLikeHelp != null) {
            mClickLikeHelp.onDestroy();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (captureFragment != null) {
            captureFragment.destroyController();
        }

    }



    /**
     * 直播结束
     */
    private void liveEnd() {
        if (!isAudience) {
            //隐藏聊天输入框
            captureFragment.onToolSendMsgIsShow(false);
        } else {
            mOnPlayLinsenr.onToolSendMsgIsShow(false);
        }

        if (mOnPlayLinsenr != null) {
            mOnPlayLinsenr.onLiveEndPlay();
        }
        if (!isAudience) {//如果是主播端
            captureFragment.onLiveEndPlay();
        }

    }


    @Override
    public void onStartLivePlay() {
        if (!isAudience) {
        }

    }

    @Override
    public void onShowStreamline(boolean isShow) {
        if (isShow) {//精简模式
        } else {
        }
        if (!isAudience) {//如果是直播才有效果
            captureFragment.onToolSendMsgIsShow(isShow);
        } else {

            mOnPlayLinsenr.onToolSendMsgIsShow(isShow);
        }
    }

    @Override
    public void hideSendMsg() {

        if (!isAudience) {
            captureFragment.onToolSendMsgIsShow(false);
        } else {
            mOnPlayLinsenr.onToolSendMsgIsShow(false);
        }
    }

    @Override
    public void isShowStreamline(boolean isShow) {
        if (!isAudience) {
            captureFragment.onToolSendMsgIsShow(isShow);
        } else {
            mOnPlayLinsenr.onToolSendMsgIsShow(isShow);
        }


    }

    @Override
    public void inputMsg() {
        showInputPanel();
    }


    /**
     * 直播开始完成的回调
     */
    @Override
    public void onStartLivingFinished() {
        if (!isAudience) {
            captureFragment.onToolSendMsgIsShow(true);
        } else {
            mOnPlayLinsenr.onToolSendMsgIsShow(true);
        }
    }

    /**
     * 直播断开时的回调
     */
    @Override
    public void onLiveDisconnect() {
    }

    /**
     * 显示聊天输入布局 展开键盘
     */
    @Override
    public void showInputPanel() {
        setIsInputSendMsg(true);
        startInputActivity();
    }

    /**
     * 设置是否进入了
     *
     * @param isShowInputMsg
     */
    public void setIsInputSendMsg(boolean isShowInputMsg) {
        this.isShowInputMsg = isShowInputMsg;
    }

    /**
     * ***************************** 部分机型键盘弹出会造成布局挤压的解决方案 ***********************************
     */

    private void startInputActivity() {
        //showGift(new RedEnvelopeInfo());
        if (mImChatMsgInputDialog == null) {
            mImChatMsgInputDialog = new ImChatMsgInputDialog(getActivity());
            mImChatMsgInputDialog.setOnSendChatMsgLinsener(text -> {
//                if (chatRoomFragment == null) {
//                    Log.i("keey", "chatRoomFragment为空");
//                    ImLoginHelp.INSTANCE.imLogin(new OnChatLoginLisener() {
//                        @Override
//                        public void onLoginSucess() {
//                            //在从新登陆聊天室
//                            loginIm();
//                        }
//
//                        @Override
//                        public void onLoginError(@NotNull String error) {
//                            ToastHelper.showToast("请重新登陆在试试");
//
//                        }
//                    });
//                    return;
//                }
                //这里清除缓存，去拿拿最新的数据
//                LiveCache.setUserInfo(null);
//                String name = LiveCache.getUserInfo().getName();
//                if (SensitiveWordsUtils.contains(name)) {
//                    ToastHelper.showToast("你的名字不合法，请修改名称后在发送消息");
//                    return;
//                }
//
//                String msg = SensitiveWordsUtils.replaceSensitiveWord(text, '*');
//                // String msg = SensitiveWordsUtil.INSTANCE.sensitiveWords(text);
//                chatRoomFragment.onTextMessageSendButtonPressed(msg);
            });
        }
        if (mImChatMsgInputDialog.isShowing()) {
            mImChatMsgInputDialog.dismiss();
        }

        mImChatMsgInputDialog.show();
//        InputActivity.startActivityForResult(this, cacheInputString,
//                inputConfig, text -> {
//                    if (chatRoomFragment == null) {
//                        Log.i("keey", "chatRoomFragment为空");
//                        return;
//                    }
//                    String msg = SensitiveWordsUtils.replaceSensitiveWord(text, '*');
//                    // String msg = SensitiveWordsUtil.INSTANCE.sensitiveWords(text);
//                    chatRoomFragment.onTextMessageSendButtonPressed(msg);
//                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void showTextToast(String text) {
    }

    @Override
    public void onBaseStart() {
        Log.i("keey", "onBaseStart");
        if (mOnPlayLinsenr != null) {
            mOnPlayLinsenr.onBaseStart();
        }
    }

    @Override
    public void onBaseStop() {
        Log.i("keey", "onBaseStop");
        if (mOnPlayLinsenr != null) {
            mOnPlayLinsenr.onBaseStop();
        }
    }

    @NotNull
    @Override
    public PLAY_STATE onBasePlayStates() {
        if (mOnPlayLinsenr != null) {
            return mOnPlayLinsenr.onBasePlayStates();
        }
        return PLAY_STATE.PLAYT;
    }

    @Override
    public boolean onBackPressedLinsener() {
        if (isAudience) {
            mOnPlayLinsenr.onToolPagerCloce();
        } else {
            captureFragment.onPagerCloce();
        }
        return true;
    }


    @Override
    public void onLiveEndPlay() {

    }

    /**
     * 点赞人数
     *
     * @param sum
     */
    private void onUpLikeSum(int sum) {
        if (!isLivePlay) {
            return;
        }
        if (isAudience) {
            mOnPlayLinsenr.onToolUpLikeSum(sum);
        } else {
            captureFragment.onUpLikeSum(sum);
        }
    }

    /**
     * 设置观看人数
     *
     * @param sum
     */
    private void onUpLiveWahtSum(int sum) {
        if (!isLivePlay) {
            return;
        }
        if (isAudience) {
            mOnPlayLinsenr.onToolUpLiveWahtSum(sum);
        } else {
            captureFragment.onUpLiveWahtSum(sum);
        }
    }

    @Override
    public void onSetPlayStatus(boolean isPlay) {
        if (isAudience) {
            if (mOnPlayLinsenr != null) {
                mOnPlayLinsenr.onSetPlayStatus(isPlay);

            }
        } else {
            if (captureFragment != null) {
                captureFragment.onSetViewPlayStatus(isPlay);

            }
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_live_room;
    }

    @Override
    public void initData() {

    }

}
