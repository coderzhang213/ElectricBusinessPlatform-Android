package com.sancell.xingqiu.view.home

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.sancell.xingqiu.R
import com.sancell.xingqiu.base.view.BaseFragment
import com.sancell.xingqiu.entity.banner.HomeBannerDataBean
import com.sancell.xingqiu.entity.live.HomeRecommendLiverBean
import com.sancell.xingqiu.entity.live.LiveFollowInfo
import com.sancell.xingqiu.mvvm.viewmodel.LiveViewModel
import com.sancell.xingqiu.view.home.adapter.BannerImageAdapter
import com.sancell.xingqiu.view.home.adapter.HomeLiveVideoAdapter
import com.sancell.xingqiu.view.home.adapter.HomeLiverRecommendAdapter
import com.youth.banner.indicator.RectangleIndicator
import com.youth.banner.transformer.RotateUpPageTransformer
import com.youth.banner.util.BannerUtils
import kotlinx.android.synthetic.main.fragment_live_home_page.*

/**
 * 新直播首页
 */
class LiveHomePageFgm : BaseFragment<LiveViewModel>() {

    private var mLiverRecommendAdapter: HomeLiverRecommendAdapter? = null
    private var mLiveVideoAdapter: HomeLiveVideoAdapter? = null
    private var mCurrentPage = 1

    override fun providerVMClass(): Class<LiveViewModel>? {
        return LiveViewModel::class.java
    }

    override fun getLayoutResId(): Int = R.layout.fragment_live_home_page

    override fun initView() {
        var height = 0
        val resourceId = context!!.applicationContext.resources.getIdentifier(
            "status_bar_height",
            "dimen",
            "android"
        );
        if (resourceId > 0) {
            height = this.context!!.applicationContext.resources.getDimensionPixelSize(resourceId)
        }
        //设置view 的padding状态栏高度
        rl_home_title.setPadding(0, height, 0, 0)
        rv_liver_recommend.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        rv_video_recommend.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_video_recommend.setHasFixedSize(true)
        val decoration = DividerItemDecoration(context, RecyclerView.HORIZONTAL)
        ContextCompat.getDrawable(context!!, R.drawable.shape_live_video_divider_7)
            ?.let { decoration.setDrawable(it) }
        rv_video_recommend.addItemDecoration(decoration)
        iv_home_search.setOnClickListener {
            // LiveSearchActivity.start(context!!)
        }
        iv_home_user.setOnClickListener {
            //  LiveOtherInfoActivity.start(context!!, AppUtils.getUserId())
        }
        initFreshListener()
    }

    /**
     * 刷新的监听
     */
    private fun initFreshListener() {
        home_fresh.setOnRefreshListener {
            mCurrentPage = 1
            initData()
            home_fresh.finishRefresh()
        }
        home_fresh.setEnableLoadMore(false)
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            mFollowList.observe(this@LiveHomePageFgm, Observer {
                it.dataList.apply {
                    Log.i("mFollowList", "总共item: ${it.dataCount}")
                    setVideoAdapter(it.dataList, it.dataCount)
                }
            })
            mBannerData.observe(this@LiveHomePageFgm, Observer {
                setBanner(it.dataList)
            })
            mRecommendLiverList.observe(this@LiveHomePageFgm, Observer {
                setLiverAdapter(it.dataList)
            })
            errMsg.observe(this@LiveHomePageFgm, Observer {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })
            mException.observe(this@LiveHomePageFgm, Observer {
                Log.i("LiveHomePageFgm", it.toString())
            })
        }
    }

    private fun setBanner(data: List<HomeBannerDataBean.BannerBean>?) {

        home_banner.addBannerLifecycleObserver(this)
            .setAdapter(BannerImageAdapter(context!!, takeBannerImageList(data)))
            .setBannerRound(BannerUtils.dp2px(8f))
            .setIndicator(RectangleIndicator(context))
            .setIndicatorNormalWidth(BannerUtils.dp2px(10f).toInt())
            .setIndicatorRadius(BannerUtils.dp2px(2f).toInt())
            .setIndicatorSpace(BannerUtils.dp2px(4f).toInt())
            .setPageTransformer(RotateUpPageTransformer())
            .start()
    }

    //提取banner的图片集合
    private fun takeBannerImageList(data: List<HomeBannerDataBean.BannerBean>?): List<String> {
        val imgList: MutableList<String> = ArrayList()
        if (data.isNullOrEmpty()) {
            //todo 测试图片，记得删除
            imgList.add("https://t8.baidu.com/it/u=1484500186,1503043093&fm=79&app=86&size=h300&n=0&g=4n&f=jpeg?sec=1590989588&t=f081f4043a3274d5bf40818b2da74d44")
            imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590480807129&di=d11edada1556dcf8901f8b68dbd747dd&imgtype=0&src=http%3A%2F%2Ft8.baidu.com%2Fit%2Fu%3D3571592872%2C3353494284%26fm%3D79%26app%3D86%26f%3DJPEG%3Fw%3D1200%26h%3D1290")
            return imgList
        }
        for (item in data) {
            imgList.add(item.coverPic)
        }
        return imgList
    }


    //设置videoAdapter
    private fun setVideoAdapter(data: List<LiveFollowInfo>?, total: Int) {
        if (data.isNullOrEmpty()) {
            //无直播
            if (mCurrentPage == 1) {
                //todo 空页面
            }
            Toast.makeText(context, "暂无直播", Toast.LENGTH_SHORT).show()
            return
        }
        //预加载结束
        if (mCurrentPage > 1) {
            mLiveVideoAdapter!!.loadMoreComplete()
        }

        if (mLiveVideoAdapter == null) {
            mLiveVideoAdapter = HomeLiveVideoAdapter(data)
            mLiveVideoAdapter?.setPreLoadNumber(4)
            mLiveVideoAdapter?.setOnLoadMoreListener {
                try {
                    mCurrentPage++
                    mViewModel.getLiveFollowList(mCurrentPage)
                } catch (e: Exception) {
                    e.printStackTrace()
                    mLiveVideoAdapter?.loadMoreFail()
                }
            }
            mLiveVideoAdapter?.setOnItemClickListener(object :
                BaseQuickAdapter.OnItemClickListener {
                override fun onItemClick(
                    adapter: BaseQuickAdapter<*, *>?,
                    view: View?,
                    position: Int
                ) {
                    val info = mLiveVideoAdapter?.data?.get(position)
                    if (info?.liveStatus.equals("2")) {
                        info?.batchId?.let {
                            //  LivePlayBaseHoemActivity.startIntent(context!!, LivePlayType.LIVE_LIST.type, it)
                        }
                    } else {
                        info?.batchId?.let {

                            //  LivePlayBaseHoemActivity.startIntent(context!!, LivePlayType.RE_PLAY.type, it)

                        }
                    }
                }

            })

            rv_video_recommend.adapter = mLiveVideoAdapter
        } else {
            if (mCurrentPage == 1) {
                mLiveVideoAdapter?.setNewData(data)
            } else {
                mLiveVideoAdapter?.addData(data)
            }
        }
        //是否关闭下拉刷新
        if (mLiveVideoAdapter!!.data.size >= total) {
            mLiveVideoAdapter!!.setEnableLoadMore(false)
            mLiveVideoAdapter!!.loadMoreEnd()
        } else {
            mLiveVideoAdapter!!.setEnableLoadMore(true)
        }
    }

    //获取直播推荐视频列表
    private fun getRecommendVideoList() {
        mViewModel.getLiveFollowList(mCurrentPage)
    }

    override fun initData() {
        getRecommendVideoList()
        mViewModel.getBannerData("4")
        mViewModel.getRecommendLiver()
    }

    private fun setLiverAdapter(data: List<HomeRecommendLiverBean.LiverBean>?) {
        if (data.isNullOrEmpty()) {
            Toast.makeText(context, "暂无直播", Toast.LENGTH_SHORT).show()
            return
        }
        if (mLiverRecommendAdapter == null) {
            mLiverRecommendAdapter = HomeLiverRecommendAdapter(data)
            mLiverRecommendAdapter?.setOnItemClickListener(object :
                BaseQuickAdapter.OnItemClickListener {
                override fun onItemClick(
                    adapter: BaseQuickAdapter<*, *>?,
                    view: View?,
                    position: Int
                ) {
                   // LiveOtherInfoActivity.start(context!!, data[position].userId!!)
                }

            })
            rv_liver_recommend.adapter = mLiverRecommendAdapter
        } else {
            mLiverRecommendAdapter!!.setNewData(data)
        }

    }


}