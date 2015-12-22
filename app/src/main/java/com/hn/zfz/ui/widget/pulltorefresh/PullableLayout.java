//package com.hn.zfz.ui.widget.pulltorefresh;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//import android.webkit.WebView;
//import android.widget.AbsListView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import com.hn.zfz.ui.widget.pulltorefresh.PullableUtil.STATE;
//import com.hn.zfz.ui.widget.pulltorefresh.PullableUtil.DIRECTION;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
///**
// * 自定义的布局，用来管理三个子控件，其中一个是下拉头，一个是普通View，还有一个上拉的布局
// * 只实现了垂直方向上刷新和加载，请注意使用场景
// *
// * @author goujiabo
// */
//public class PullableLayout extends RelativeLayout {
//    public static final int SUCCEED = 0;// 刷新成功，供外部调用
//    public static final int FAIL = 1;// 刷新失败，供外部调用
//    private static final int INIT = 0;// 初始状态
//    private static final int RELEASE_TO_REFRESH = 1;// 释放刷新
//    private static final int REFRESHING = 2;// 正在刷新
//    private static final int RELEASE_TO_LOAD = 3;// 释放加载
//    private static final int LOADING = 4;// 正在加载
//    private static final int DONE = 5;// 操作完毕
//    private Context mContext;
//    private PullableUtil.DIRECTION direction = PullableUtil.DIRECTION.BOTH;//支持方向，默认上下均可
//    private int state = INIT;// 当前状态
//    private float refreshDist = 200;// 释放刷新的距离
//    private float loadmoreDist = 200;// 释放加载的距离
//    private float downY, lastY;// 按下Y坐标，上一个事件点Y坐标
//    private float pullDownY = 0;// 下拉的距离。注意：pullDownY和pullUpY不可能同时不为0
//    private float pullUpY = 0;// 上拉的距离
//    private float MOVE_SPEED = 8;// 回滚速度
//    private boolean isLayout = false;// 第一次执行布局
//    private boolean isTouch = false;// 在刷新过程中滑动操作
//    private float radio = 2;// 手指滑动距离与下拉头的滑动距离比，中间会随正切函数变化
//
//    // 这两个变量用来控制pull的方向，如果不加控制，当情况满足可上拉又可下拉时没法下拉
//    private boolean canPullDown = true;
//    private boolean canPullUp = true;
//
//    private PullableHeadView refreshView;// 下拉view
//    private PullableFooterView loadView;// 上拉view
//    private OnRefreshListener mListener;// 刷新回调接口
//    private MyTimer timer;
//    /**
//     * 执行自动回滚的handler
//     */
//    @SuppressLint("HandlerLeak")
//    Handler updateHandler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            // 回弹速度随下拉距离moveDeltaY增大而增大
//            MOVE_SPEED = (float) (8 + 5 * Math.tan(Math.PI / 2 / getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))));
//            if (!isTouch) {
//                // 正在刷新，且没有往上推的话则悬停，显示"正在刷新..."
//                if (state == REFRESHING && pullDownY <= refreshDist) {
//                    pullDownY = refreshDist;
//                    timer.cancel();
//                } else if (state == LOADING && -pullUpY <= loadmoreDist) {
//                    pullUpY = -loadmoreDist;
//                    timer.cancel();
//                }
//            }
//            if (pullDownY > 0) {
//                pullDownY -= MOVE_SPEED;
//            } else if (pullUpY < 0) {
//                pullUpY += MOVE_SPEED;
//            }
//            if (pullDownY < 0) {
//                // 已完成回弹
//                pullDownY = 0;
//                refreshView.removeAnimation();
//                // 隐藏下拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
//                if (state != REFRESHING && state != LOADING) {
//                    changeState(INIT);
//                }
//                timer.cancel();
//                requestLayout();
//            }
//            if (pullUpY > 0) {
//                // 已完成回弹
//                pullUpY = 0;
//                loadView.removeAnimation();
//                // 隐藏上拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
//                if (state != REFRESHING && state != LOADING) {
//                    changeState(INIT);
//                }
//                timer.cancel();
//                requestLayout();
//            }
//            // 刷新布局,会自动调用onLayout
//            requestLayout();
//            // 没有拖拉或者回弹完成
//            if (pullDownY + Math.abs(pullUpY) == 0) timer.cancel();
//        }
//
//    };
//    private View pullableView;//中间的自定义view
//    private int mEvents;// 过滤多点触碰
//    private float xDistance, yDistance, xLast, yLast;//横向时间冲突解决变量
//
//    public PullableLayout(Context context) {
//        super(context);
//        initView(context);
//    }
//
//    public PullableLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initView(context);
//    }
//
//    public PullableLayout(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        initView(context);
//    }
//
//    /**
//     * 设置监听器，下拉和上拉
//     *
//     * @param listener 拉动监听器
//     */
//    public void setOnRefreshListener(OnRefreshListener listener) {
//        mListener = listener;
//    }
//
//    private void initView(Context context) {
//        mContext = context;
//        addHeaderView();
//        timer = new MyTimer(updateHandler);
//    }
//
//    private void hide() {
//        timer.schedule(5);
//    }
//
//    /**
//     * 完成刷新操作，显示刷新结果。注意：刷新完成后一定要调用这个方法
//     *
//     * @param refreshResult PullableLayout.SUCCEED代表成功，PullableLayout.FAIL代表失败
//     */
//    @SuppressLint("HandlerLeak")
//    public void refreshFinish(int refreshResult) {
//        switch (refreshResult) {
//            case SUCCEED:
//                // 刷新成功
//                refreshView.setHeadState(STATE.SUCCESS);
//                break;
//            case FAIL:
//            default:
//                // 刷新失败
//                refreshView.setHeadState(STATE.FAILED);
//                break;
//        }
//        if (pullDownY > 0) {
//            // 刷新结果停留1秒
//            new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    changeState(DONE);
//                    hide();
//                }
//            }.sendEmptyMessageDelayed(0, 1000);
//        } else {
//            changeState(DONE);
//            hide();
//        }
//    }
//
//    /**
//     * 加载完毕，显示加载结果。注意：加载完成后一定要调用这个方法，否则不能收回布局
//     *
//     * @param refreshResult PullableLayout.SUCCEED代表成功，PullableLayout.FAIL代表失败
//     */
//    @SuppressLint("HandlerLeak")
//    public void loadmoreFinish(int refreshResult) {
//        switch (refreshResult) {
//            case SUCCEED:
//                // 加载成功
//                loadView.setFooterState(PullableUtil.STATE.SUCCESS);
//                break;
//            case FAIL:
//            default:
//                // 加载失败
//                loadView.setFooterState(PullableUtil.STATE.FAILED);
//                break;
//        }
//        if (pullUpY < 0) {
//            // 刷新结果停留1秒
//            new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//                    changeState(DONE);
//                    hide();
//                }
//            }.sendEmptyMessageDelayed(0, 1000);
//        } else {
//            changeState(DONE);
//            hide();
//        }
//    }
//
//    private void changeState(int to) {
//        state = to;
//        switch (state) {
//            case INIT:
//                // 下拉布局初始状态
//                refreshView.setHeadState(STATE.PULL);
//                // 上拉布局初始状态
//                loadView.setFooterState(STATE.PULL);
//                break;
//            case RELEASE_TO_REFRESH:
//                // 释放刷新状态
//                refreshView.setHeadState(STATE.RELEASE);
//                break;
//            case REFRESHING:
//                // 正在刷新状态
//                refreshView.setHeadState(STATE.REFRESHING);
//                break;
//            case RELEASE_TO_LOAD:
//                // 释放加载状态
//                loadView.setFooterState(STATE.RELEASE);
//                break;
//            case LOADING:
//                // 正在加载状态
//                loadView.setFooterState(STATE.LOADING);
//                break;
//            case DONE:
//                // 刷新或加载完毕，啥都不做
//                break;
//        }
//    }
//
//    /**
//     * 不限制上拉或下拉
//     */
//    private void releasePull() {
//        canPullDown = true;
//        canPullUp = true;
//    }
//
//    /*
//     * （非 Javadoc）由父控件决定是否分发事件，防止事件冲突
//     *
//     * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
//     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                //横向时间冲突解决代码----begain
//                xDistance = yDistance = 0f;
//                xLast = ev.getX();
//                yLast = ev.getY();
//                //横向时间冲突解决代码----end
//                downY = ev.getY();
//                lastY = downY;
//                timer.cancel();
//                mEvents = 0;
//                releasePull();
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//            case MotionEvent.ACTION_POINTER_UP:
//                // 过滤多点触碰
//                mEvents = -1;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                //横向时间冲突解决代码----begain
//                final float curX = ev.getX();
//                final float curY = ev.getY();
//
//                xDistance += Math.abs(curX - xLast);
//                yDistance += Math.abs(curY - yLast);
//                xLast = curX;
//                yLast = curY;
//
//                if (xDistance > yDistance) {
//                    return super.dispatchTouchEvent(ev);
//                }
//                //横向时间冲突解决代码----end
//
//                if (mEvents == 0) {
//                    if (pullDownY > 0 || (canPullDown(pullableView) && canPullDown && state != LOADING && (direction == DIRECTION.BOTH || direction == DIRECTION.HEAD_ONLY))) {
//                        // 可以下拉，正在加载时不能下拉
//                        // 对实际滑动距离做缩小，造成用力拉的感觉
//                        pullDownY = pullDownY + (ev.getY() - lastY) / radio;
//                        if (pullDownY < 0) {
//                            pullDownY = 0;
//                            canPullDown = false;
//                            canPullUp = true;
//                        }
//                        if (pullDownY > getMeasuredHeight()) pullDownY = getMeasuredHeight();
//                        if (state == REFRESHING) {
//                            // 正在刷新的时候触摸移动
//                            isTouch = true;
//                        }
//                    } else if (pullUpY < 0 || (canPullUp(pullableView) && canPullUp && state != REFRESHING && (direction == DIRECTION.BOTH || direction == DIRECTION.BOTTOM_ONLY))) {
//                        // 可以上拉，正在刷新时不能上拉
//                        pullUpY = pullUpY + (ev.getY() - lastY) / radio;
//                        if (pullUpY > 0) {
//                            pullUpY = 0;
//                            canPullDown = true;
//                            canPullUp = false;
//                        }
//                        if (pullUpY < -getMeasuredHeight()) pullUpY = -getMeasuredHeight();
//                        if (state == LOADING) {
//                            // 正在加载的时候触摸移动
//                            isTouch = true;
//                        }
//                    } else releasePull();
//                } else mEvents = 0;
//                lastY = ev.getY();
//                // 根据下拉距离改变比例
//                radio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))));
//                if (pullDownY > 0 || pullUpY < 0) {
//                    requestLayout();
//                }
//                if (pullDownY > 0) {
//                    refreshView.setHeadPullProgress((float) pullDownY / refreshDist);//在下拉有效距离内，通知头部下拉的百分比
//                    if (pullDownY <= refreshDist && (state == RELEASE_TO_REFRESH || state == DONE)) {
//                        // 如果下拉距离没达到刷新的距离且当前状态是释放刷新，改变状态为下拉刷新
//                        changeState(INIT);
//                    }
//                    if (pullDownY >= refreshDist && state == INIT) {
//                        // 如果下拉距离达到刷新的距离且当前状态是初始状态刷新，改变状态为释放刷新
//                        changeState(RELEASE_TO_REFRESH);
//                    }
//                } else if (pullUpY < 0) {
//                    loadView.setFooterPullProgress((float) -pullUpY / loadmoreDist);//在上拉有效距离内，通知底部上拉的百分比
//                    // 下面是判断上拉加载的，同上，注意pullUpY是负值
//                    if (-pullUpY <= loadmoreDist && (state == RELEASE_TO_LOAD || state == DONE)) {
//                        changeState(INIT);
//                    }
//                    // 上拉操作
//                    if (-pullUpY >= loadmoreDist && state == INIT) {
//                        changeState(RELEASE_TO_LOAD);
//                    }
//                }
//                // 因为刷新和加载操作不能同时进行，所以pullDownY和pullUpY不会同时不为0，因此这里用(pullDownY +
//                // Math.abs(pullUpY))就可以不对当前状态作区分了
//                if ((pullDownY + Math.abs(pullUpY)) > 8) {
//                    // 防止下拉过程中误触发长按事件和点击事件
//                    ev.setAction(MotionEvent.ACTION_CANCEL);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                if (pullDownY > refreshDist || -pullUpY > loadmoreDist) {
//                    // 正在刷新时往下拉（正在加载时往上拉），释放后下拉头（上拉头）不隐藏
//                    isTouch = false;
//                }
//                if (state == RELEASE_TO_REFRESH) {
//                    changeState(REFRESHING);
//                    // 刷新操作
//                    if (mListener != null) mListener.onRefresh(this);
//                } else if (state == RELEASE_TO_LOAD) {
//                    changeState(LOADING);
//                    // 加载操作
//                    if (mListener != null) {
//                        mListener.onLoadMore(this);
//                    }
//                }
//                hide();
//            default:
//                break;
//        }
//        // 事件分发交给父类
//        return super.dispatchTouchEvent(ev);
//        //        return true;
//    }
//
//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        addFooterView();
//        initContentAdapterView();
//    }
//
//    private void initContentAdapterView() {
//        int count = getChildCount();
//        if (count < 3) {
//            throw new IllegalArgumentException("this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
//        }
//        if (pullableView instanceof RecyclerView) {
//            if (!(((RecyclerView) pullableView).getLayoutManager() instanceof LinearLayoutManager)) {
//                throw new IllegalArgumentException("RecyclerView only support LinearLayoutManager.");
//            }
//        }
//    }
//
//    /**
//     * 自动刷新
//     */
//    public void autoRefresh() {
//        AutoRefreshAndLoadTask task = new AutoRefreshAndLoadTask();
//        task.execute(20);
//    }
//
//    /**
//     * 自动加载
//     */
//    public void autoLoad() {
//        pullUpY = -loadmoreDist;
//        requestLayout();
//        changeState(LOADING);
//        // 加载操作
//        if (mListener != null) {
//            mListener.onLoadMore(this);
//        }
//    }
//
//    // 初始化下拉布局
//    private void addHeaderView() {
//        refreshView = new PullableHeadView(mContext);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        addView(refreshView, params);
//    }
//
//    //初始化上拉布局
//    private void addFooterView() {
//        loadView = new PullableFooterView(mContext);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        addView(loadView, params);
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        if (!isLayout) {
//            pullableView = getChildAt(1);
//            //无论布局文件中如何设置中间view的宽高，在这个地方一律设置为MATCH_PARENT，避免显示异常。
//            LayoutParams lp = (LayoutParams) pullableView.getLayoutParams();
//            lp.height = LayoutParams.MATCH_PARENT;
//            lp.width = LayoutParams.MATCH_PARENT;
//            pullableView.setLayoutParams(lp);
//
//            setOverScroll(pullableView);
//
//            isLayout = true;
//            refreshDist = refreshView.getChildAt(0).getMeasuredHeight();
//            loadmoreDist = loadView.getChildAt(0).getMeasuredHeight();
//        }
//        // 改变子控件的布局，这里直接用(pullDownY + pullUpY)作为偏移量，这样就可以不对当前状态作区分
//        refreshView.layout(0, (int) (pullDownY + pullUpY) - refreshView.getMeasuredHeight(), refreshView.getMeasuredWidth(), (int) (pullDownY + pullUpY));
//
//        pullableView.layout(0, (int) (pullDownY + pullUpY), pullableView.getMeasuredWidth(), (int) (pullDownY + pullUpY) + pullableView.getMeasuredHeight());
//
//        loadView.layout(0, (int) (pullDownY + pullUpY) + pullableView.getMeasuredHeight(), loadView.getMeasuredWidth(), (int) (pullDownY + pullUpY) + pullableView.getMeasuredHeight() + loadView.getMeasuredHeight());
//    }
//
//    //去除中间布局的OverScrollMode属性，避免如魅族等机型的下拉悬停功能影响刷新
//    private void setOverScroll(View view) {
//        if (view instanceof AbsListView) {
//            AbsListView listView = (AbsListView) view;
//            listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        } else if (view instanceof RecyclerView) {
//            RecyclerView recyclerView = (RecyclerView) view;
//            recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        } else if (view instanceof WebView) {
//            WebView webView = (WebView) view;
//            webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        } else if (view instanceof ScrollView) {
//            ScrollView scrollView = (ScrollView) view;
//            scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        }
//    }
//
//    //判断是否满足下拉条件
//    private boolean canPullDown(View view) {
//        if (view instanceof AbsListView) {
//            AbsListView listView = (AbsListView) view;
//            if (listView.getCount() == 0) {
//                // 没有item的时候也可以下拉刷新
//                return true;
//            } else if (listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() >= 0) {
//                // 滑到顶部了
//                return true;
//            } else {
//                return false;
//            }
//        } else if (view instanceof RecyclerView) {
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                int position = linearLayoutManager.findFirstVisibleItemPosition();
//                int count = linearLayoutManager.getItemCount();
//                if (count == 0) {
//                    return true;
//                } else if (position == 0 && recyclerView.getChildAt(0).getTop() >= 0) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } else {
//                return false;
//            }
//        } else if (view instanceof WebView) {
//            WebView webView = (WebView) view;
//            if (webView.getScrollY() == 0) {
//                return true;
//            } else {
//                return false;
//            }
//        } else if (view instanceof ScrollView) {
//            ScrollView scrollView = (ScrollView) view;
//            if (scrollView.getScrollY() == 0) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    //判断是否满足上拉条件
//    @SuppressWarnings("deprecation")
//    private boolean canPullUp(View view) {
//        if (view instanceof AbsListView) {
//            AbsListView listView = (AbsListView) view;
//            if (listView.getCount() == 0) {
//                // 没有item的时候也可以上拉加载
//                return true;
//            } else if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
//                // 滑到底部了
//                if (listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition()) != null && listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition()).getBottom() <= listView.getMeasuredHeight())
//                    return true;
//            }
//        } else if (view instanceof RecyclerView) {
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                int position = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//                int count = linearLayoutManager.getItemCount();
//                if (count == 0) {
//                    return true;
//                } else if (position == (count - 1)) {
//                    return true;
//                }
//            }
//        } else if (view instanceof WebView) {
//            WebView webView = (WebView) view;
//            if (webView.getScrollY() >= webView.getContentHeight() * webView.getScale() - webView.getMeasuredHeight()) {
//                return true;
//            } else {
//                return false;
//            }
//        } else if (view instanceof ScrollView) {
//            ScrollView scrollView = (ScrollView) view;
//            if (scrollView.getScrollY() >= (scrollView.getChildAt(0).getHeight() - scrollView.getMeasuredHeight())) {
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 设置头部背景色
//     *
//     * @param color 目标背景色
//     */
//    public void setHeadColor(int color) {
//        refreshView.setHeadBackgroundColor(color);
//    }
//
//    /**
//     * 设置底部背景色
//     *
//     * @param color 目标背景色
//     */
//    public void setFooterColor(int color) {
//        loadView.setFooterBackgroundColor(color);
//    }
//
//    /**
//     * 设置头部文字色
//     *
//     * @param color 目标背景色
//     */
//    public void setHeadTextColor(int color) {
//        refreshView.setHeadTextColor(color);
//    }
//
//    /**
//     * 设置底部文字色
//     *
//     * @param color 目标背景色
//     */
//    public void setFooterTextColor(int color) {
//        loadView.setFooterTextColor(color);
//    }
//
//    /**
//     * 设置方向，默认上下均可以
//     *
//     * @param direction 设置方向，枚举变量
//     */
//    public void setRefreshModel(DIRECTION direction) {
//        this.direction = direction;
//    }
//
//    /**
//     * 刷新和上拉加载回调接口
//     *
//     * @author GouJiabo
//     */
//    public interface OnRefreshListener {
//        /**
//         * 刷新操作
//         */
//        void onRefresh(PullableLayout pullableLayout);
//
//        /**
//         * 加载操作
//         */
//        void onLoadMore(PullableLayout pullableLayout);
//    }
//
//    /**
//     * @author chenjing 自动模拟手指滑动的task
//     */
//    private class AutoRefreshAndLoadTask extends AsyncTask<Integer, Float, String> {
//
//        @Override
//        protected String doInBackground(Integer... params) {
//            while (pullDownY < 4 / 3 * refreshDist) {
//                pullDownY += MOVE_SPEED;
//                publishProgress(pullDownY);
//                try {
//                    Thread.sleep(params[0]);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            changeState(REFRESHING);
//            // 刷新操作
//            if (mListener != null) {
//                mListener.onRefresh(PullableLayout.this);
//            }
//            hide();
//        }
//
//        @Override
//        protected void onProgressUpdate(Float... values) {
//            if (pullDownY > refreshDist) {
//                changeState(RELEASE_TO_REFRESH);
//            }
//            requestLayout();
//        }
//    }
//
//    class MyTimer {
//        private Handler handler;
//        private Timer timer;
//        private MyTask mTask;
//
//        public MyTimer(Handler handler) {
//            this.handler = handler;
//            timer = new Timer();
//        }
//
//        public void schedule(long period) {
//            if (mTask != null) {
//                mTask.cancel();
//                mTask = null;
//            }
//            mTask = new MyTask(handler);
//            timer.schedule(mTask, 0, period);
//        }
//
//        public void cancel() {
//            if (mTask != null) {
//                mTask.cancel();
//                mTask = null;
//            }
//        }
//
//        class MyTask extends TimerTask {
//            private Handler handler;
//
//            public MyTask(Handler handler) {
//                this.handler = handler;
//            }
//
//            @Override
//            public void run() {
//                handler.obtainMessage().sendToTarget();
//            }
//        }
//    }
//}
