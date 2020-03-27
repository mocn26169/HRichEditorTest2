package com.huangdali.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hdl.hricheditorview.R;
import com.huangdali.base.EditorResultBean;
import com.huangdali.bean.EContent;
import com.huangdali.bean.ItemType;
import com.huangdali.bean.RichEditorAdapter;
import com.huangdali.utils.SimpleItemTouchHelperCallback;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.LocalMediaLoader;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.valuesfeng.picker.Picker;
import io.valuesfeng.picker.engine.GlideEngine;
import io.valuesfeng.picker.utils.PicturePickerUtils;

import static com.luck.picture.lib.model.FunctionConfig.MODE_MULTIPLE;


public class HRichEditorActivity extends Activity {
    /**
     * 整形区
     */
    private static final int ANIMATION_DURATION = 300;//移动时间
    private static final int REQUEST_CODE_CHOOSE_BG = 1001;//选择背景
    private static final int REQUEST_CODE_CHOOSE_ITEM_IMG = 1002;//更改item图片
    private static final int REQUEST_CODE_SET_TITLE = 1003;//设置标题
    private static final int REQUEST_CODE_CHOOSE_IMGS = 1004;//多选图片
    private static final int REQUEST_CODE_EDIT_TXT = 1005;//编辑文本
    private float translateDistance = 0;//移动的距离
    /**
     * 字符区
     */
//    private String articleTitle;

    /**
     * 组件区
     */
    private RecyclerView rvItemList;
    private LinearLayoutManager linearLayoutManager;
    private RichEditorAdapter adapter;
//    private TextView tvArtTitle;
//    private ImageView ivArtBGImg;

    //    private ImageView ivAdd, ivAddTxt, ivAddImg, ivAddVideo, ivAddClose;
    private ImageView iv_additem_foot;
//    private LinearLayout rvAddItemArea;

    private LinearLayout layout_bottom_pop;
    private LinearLayout layout_addImage;
    private LinearLayout layout_addWord;
    private CustomScrollView scrollView;

    /**
     * 数据区
     */
    private List<EContent> datas;
//    private String bgUri;//背景图片的uri

    //标题栏添加按钮状态
    private boolean isOpenAdd = false;

    private boolean isShowBottomPop = true;

    private boolean isScrollMoved = false;

    /**
     * 视频回调方法
     */
    private PictureConfig.OnSelectResultCallback videoResultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            datas.get(adapter.getCurClickItemIndex()).setUrl(resultList.get(0).toString());
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_news);
        initView();
        //defaultChoiceIMG();
    }

    public void onAdd(View view) {
//        if (!isOpenAdd) {
//            showAddItemArea();
//            isOpenAdd = true;
//        } else {
//            hideAddArea();
//            isOpenAdd = false;
//        }
    }

    public void onPreview(View view) {
//        if (articleTitle == null || articleTitle.equals("点击设置标题") || articleTitle.trim().equals("")) {
//            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
//        }
//        else if (bgUri == null) {
//            Toast.makeText(this, "请更换封面", Toast.LENGTH_SHORT).show();
//        }
//        else {
        EditorResultBean resultBean = new EditorResultBean();
        resultBean.setContents(datas);
        String html = "";
        for (EContent data : datas) {
            html += data.getHtml();
        }
        Log.e("mylog", html);
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra("contents", resultBean);
//            intent.putExtra("articleTitle", articleTitle);
//            intent.putExtra("bgUri", bgUri);
        //this.setResult(Activity.RESULT_OK, intent);
        this.startActivity(intent);
//        }
    }

    public void onSubmit(View view) {
//        if (articleTitle == null || articleTitle.equals("点击设置标题") || articleTitle.trim().equals("")) {
//            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
//        }
//        else if (bgUri == null) {
//            Toast.makeText(this, "请更换封面", Toast.LENGTH_SHORT).show();
//        }
//        else {
        EditorResultBean resultBean = new EditorResultBean();
        resultBean.setContents(datas);
        String html = "";
        for (EContent data : datas) {
            html += data.getHtml();
        }
        Log.e("mylog", html);
        Intent intent = getIntent();
        intent.putExtra("contents", resultBean);
//            intent.putExtra("articleTitle", articleTitle);
//            intent.putExtra("bgUri", bgUri);
        this.setResult(Activity.RESULT_OK, intent);
        this.finish();
//        }
    }

    /**
     * 第一次进入页面，选择图片最多20张
     */
    private void defaultChoiceIMG() {
        Picker.from(this)
                .count(20)
                .enableCamera(true)
                .setEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE_IMGS);
    }


    /**
     * 初始化视图
     */
    private void initView() {
        /**
         * 初始化RecyclerView
         */
        rvItemList = (RecyclerView) findViewById(R.id.rv_itemlist);

//        ivAdd = (ImageView) findViewById(R.id.iv_add);
//        ivAddTxt = (ImageView) findViewById(R.id.iv_additem_txt);
//        ivAddImg = (ImageView) findViewById(R.id.iv_additem_img);
//        ivAddVideo = (ImageView) findViewById(R.id.iv_additem_video);
//        ivAddClose = (ImageView) findViewById(R.id.iv_additem_close);
        iv_additem_foot = (ImageView) findViewById(R.id.iv_additem_foot);

//        rvAddItemArea = (LinearLayout) findViewById(R.id.ll_additem_addarea);
        layout_bottom_pop = (LinearLayout) findViewById(R.id.layout_bottom_pop);
        layout_addWord = (LinearLayout) findViewById(R.id.layout_addWord);
        layout_addImage = (LinearLayout) findViewById(R.id.layout_addImage);
        scrollView = findViewById(R.id.scrollView);
//        scrollView.setOnTouchListener(mTouchListener);
//        rootView.setOnTouchListener(mTouchListener);

        scrollView.setOnScrollChangeListener(mScrollChangeListener);


//        /**
//         * 设置添加图片、文本、视频的监听
//         */
//        ivAddImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideAddArea();
//                EContent eContent = new EContent();
//                eContent.setType(ItemType.IMG);
//                datas.add(eContent);
//                adapter.notifyDataSetChanged();
//                rvItemList.scrollToPosition(adapter.getItemCount() - 1);
//            }
//        });
        /**
         * 设置添加图片、文本、视频的监听
         */
        layout_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                hideAddArea();
                if (datas != null) {
                    addMyImage(-100);
                }

//                hiddenScrollViewBottom();
//                EContent eContent = new EContent();
//                eContent.setType(ItemType.IMG);
//                datas.add(eContent);
//                adapter.notifyDataSetChanged();
//                rvItemList.scrollToPosition(adapter.getItemCount() - 1);
            }
        });

//        ivAddVideo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideAddArea();
//                EContent eContent = new EContent();
//                eContent.setType(ItemType.VIDEO);
//                datas.add(eContent);
//                adapter.notifyDataSetChanged();
//                rvItemList.scrollToPosition(adapter.getItemCount() - 1);
//            }
//        });

        layout_addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addMyWord(datas.size() - 1);
                if (datas != null) {
                    addMyWord(-100);
                }
            }
        });

//        ivAddTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideAddArea();
//                EContent eContent = new EContent();
//                eContent.setType(ItemType.TXT);
//                datas.add(eContent);
//                adapter.notifyDataSetChanged();
//                rvItemList.scrollToPosition(adapter.getItemCount() - 1);
//            }
//        });
//        ivAddClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                hideAddArea();
//                isOpenAdd = false;
//            }
//        });

        linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {//禁止滑动
                return false;
            }
        };
        rvItemList.setLayoutManager(linearLayoutManager);

        rvItemList.setItemAnimator(new DefaultItemAnimator());
        datas = new ArrayList<>();

//        rvItemList.setHasFixedSize(true);//最重要的这句，不然recycleview不显示
        adapter = new RichEditorAdapter(this, datas);
        rvItemList.setAdapter(adapter);

        adapter.setOnDownUpChangeListener(new RichEditorAdapter.OnDownUpChangeListener() {
            @Override
            public void onDown(final View view, int postion) {
                swapDown(postion);
            }

            @Override
            public void onUp(View view, int postion) {
                swapUp(postion);
            }

            @Override
            public void onDrop(View view, int postion) {
                dropItem(postion);
            }
        });
        adapter.setOnChoiseVideoListener(new RichEditorAdapter.OnChoiseVideoListener() {
            @Override
            public void onStart() {
                getVideo();
            }
        });

        adapter.setOnActivityListener(mActivityListener);

        adapter.setOnItemClickListener(new RichEditorAdapter.OnItemClickListener() {
            @Override
            public void onClick(String itemType, int index) {
                EContent eContent = new EContent();
                switch (itemType) {
                    case ItemType.IMG:
                        eContent.setType(ItemType.IMG);
                        break;
                    case ItemType.VIDEO:
                        eContent.setType(ItemType.VIDEO);
                        break;
                    case ItemType.TXT:
                        eContent.setType(ItemType.TXT);
                        break;
                }
                datas.add(index, eContent);
                adapter.notifyDataSetChanged();
            }
        });

        //创建SimpleItemTouchHelperCallback
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter, rvItemList);
        //用Callback构造ItemtouchHelper
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //调用ItemTouchHelper的attachToRecyclerView方法建立联系
        touchHelper.attachToRecyclerView(rvItemList);

        /**
         * 初始化文章标题
         */
//        tvArtTitle = (TextView) findViewById(R.id.tv_richeditor_title);
        /**
         * 初始化封面图片
         */
//        ivArtBGImg = (ImageView) findViewById(R.id.iv_richeditor_bg);

        iv_additem_foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout_bottom_pop.getVisibility() == View.GONE) {
                    showScrollViewBottom();
                } else {
                    hiddenScrollViewBottom();
                }

            }
        });
    }


    private void addMyWord(int pos) {
        hiddenScrollViewBottom();
        EContent eContent = new EContent();
        eContent.setType(ItemType.TXT);
        if (pos == -100) {
            datas.add(eContent);
        } else {
            datas.add(pos, eContent);
        }

        adapter.notifyDataSetChanged();
        rvItemList.scrollToPosition(adapter.getItemCount() - 1);
    }

    private void addMyImage(int pos) {
        hiddenScrollViewBottom();
        EContent eContent = new EContent();
        eContent.setType(ItemType.IMG);
        if (pos == -100) {
            datas.add(eContent);
        } else {
            datas.add(pos, eContent);
        }
        adapter.notifyDataSetChanged();
        rvItemList.scrollToPosition(adapter.getItemCount() - 1);
    }

    /**
     * 删除item
     *
     * @param postion
     */
    private void dropItem(int postion) {
        datas.remove(postion);
        adapter.notifyDataSetChanged();
    }


    /**
     * 向下交换
     *
     * @param postion
     */
    private void swapDown(final int postion) {
        if (datas.size() > 1) {
            if (translateDistance == 0) {
                translateDistance = adapter.getItemHight(linearLayoutManager) + 10;
            }
            ObjectAnimator animatorDown = ObjectAnimator.ofFloat(linearLayoutManager.getChildAt(postion), "TranslationY", 0, translateDistance);
            animatorDown.setDuration(ANIMATION_DURATION);
            animatorDown.start();
            animatorDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    if (animatedValue == translateDistance) {
                        Collections.swap(datas, postion, postion + 1);
                        adapter.notifyDataSetChanged();
                        rvItemList.setAdapter(adapter);//需要重新设置适配器才有效果
                    }
                }
            });
            ObjectAnimator animatorUp = ObjectAnimator.ofFloat(linearLayoutManager.getChildAt(postion + 1), "TranslationY", 0, -translateDistance);
            animatorUp.setDuration(ANIMATION_DURATION);
            animatorUp.start();
        } else {
            Toast.makeText(this, "φ(>ω<*) 不能向下移动哦，请添加跟多内容", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 向上交换
     *
     * @param postion
     */
    private void swapUp(final int postion) {
        if (translateDistance == 0) {
            translateDistance = adapter.getItemHight(linearLayoutManager) + 10;
        }
        ObjectAnimator animatorUp = ObjectAnimator.ofFloat(linearLayoutManager.getChildAt(postion), "TranslationY", 0, -translateDistance);
        animatorUp.setDuration(ANIMATION_DURATION);
        animatorUp.start();
        animatorUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                if (animatedValue == -translateDistance) {
                    Collections.swap(datas, postion, postion - 1);
                    adapter.notifyDataSetChanged();
                    rvItemList.setAdapter(adapter);
                }
            }
        });
        ObjectAnimator animatorDown = ObjectAnimator.ofFloat(linearLayoutManager.getChildAt(postion - 1), "TranslationY", 0, translateDistance);
        animatorDown.setDuration(ANIMATION_DURATION);
        animatorDown.start();
    }

    /**
     * 更换背景
     *
     * @param view
     */
    public void onChangeBG(View view) {
        Picker.from(this)
                .count(1)
                .enableCamera(true)
                .setEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE_BG);
    }

    /**
     * 获取视频
     */
    private void getVideo() {
        FunctionConfig config = new FunctionConfig();
        config.setType(LocalMediaLoader.TYPE_VIDEO);
        config.setCompress(true);
        config.setMaxSelectNum(1);
        config.setSelectMode(MODE_MULTIPLE);
        config.setShowCamera(true);
        config.setEnablePreview(true);
        config.setPreviewVideo(true);
        config.setRecordVideoSecond(60 * 60);// 视频秒数
        config.setCompressFlag(1);
        config.setCheckNumMode(true);
        PictureConfig.init(config);
        PictureConfig.getPictureConfig().openPhoto(this, videoResultCallback);
    }

    public void onBack(View view) {
        this.finish();
    }


//    /**
//     * 显示增加图片、文字、视频区域，隐藏添加按钮
//     */
//    private void showAddItemArea() {
//        ivAdd.animate().rotation(45);//角度旋转
//        rvAddItemArea.setVisibility(View.VISIBLE);
//    }
//
//    /**
//     * 隐藏增加图片、文字、视频区域，显示添加按钮
//     */
//    private void hideAddArea() {
//        ivAdd.animate().rotation(0);//角度旋转
//        rvAddItemArea.setVisibility(View.GONE);
//    }


    private void showBottomPop(View view, int pos) {
        TestPop testPop = new TestPop(HRichEditorActivity.this, popListener, pos);
        if (testPop.isShowing()) {
            testPop.dismiss();
        } else {
            testPop.showAtLocationGravityBottom(view, 5);
        }
    }

    TestPop.OnActivityListener popListener = new TestPop.OnActivityListener() {
        @Override
        public void onAddWord(int pos) {
            addMyWord(pos);
        }

        @Override
        public void onAddImage(int pos) {
            addMyImage(pos);
        }
    };

    RichEditorAdapter.OnActivityListener mActivityListener = new RichEditorAdapter.OnActivityListener() {
        @Override
        public void onHidden() {
            iv_additem_foot.setVisibility(View.GONE);
            for (int i = 0; i < rvItemList.getChildCount(); i++) {
                LinearLayout layout = (LinearLayout) rvItemList.getChildAt(i);
                ImageView tv_content = layout.findViewById(R.id.iv_additem_add);
                tv_content.setVisibility(View.GONE);
            }
        }

        @Override
        public void onShow() {
            iv_additem_foot.setVisibility(View.VISIBLE);
            for (int i = 0; i < rvItemList.getChildCount(); i++) {
                LinearLayout layout = (LinearLayout) rvItemList.getChildAt(i);
                ImageView tv_content = layout.findViewById(R.id.iv_additem_add);
                tv_content.setVisibility(View.VISIBLE);

            }
        }

        @Override
        public void onShowAddPop(View v, int pos) {

            showBottomPop(v, pos);
//            showPopWindows( v,0);
        }


    };


    private void hiddenScrollViewBottom() {
        Log.e("TAG", "隐藏==");
        if (layout_bottom_pop.getVisibility() == View.VISIBLE) {
            Animation animBottomOut = AnimationUtils.loadAnimation(HRichEditorActivity.this,
                    R.anim.popu_middle_out);
            layout_bottom_pop.setVisibility(View.GONE);
            layout_bottom_pop.startAnimation(animBottomOut);
        }
    }

    private void showScrollViewBottom() {
        Log.e("TAG", "显示==");
        if (layout_bottom_pop.getVisibility() == View.GONE) {
            Animation animBottomIn = AnimationUtils.loadAnimation(HRichEditorActivity.this,
                    R.anim.popu_middle_in);
            layout_bottom_pop.setVisibility(View.VISIBLE);
            layout_bottom_pop.startAnimation(animBottomIn);
        }

    }

    CustomScrollView.OnScrollChangeListener mScrollChangeListener = new CustomScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollToStart() {
            Log.e("TAG", "到顶了");

        }

        @Override
        public void onScrollToEnd() {
            Log.e("TAG", "到底了");
            showScrollViewBottom();
        }

        @Override
        public void onActionDown() {
            hiddenScrollViewBottom();
        }

        @Override
        public void onActionUp() {
            showScrollViewBottom();
        }

        @Override
        public void onActionMove() {

        }


    };

    /**
     * 页面跳转回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE_SET_TITLE && resultCode == REQUEST_CODE_SET_TITLE) {//设置标题回调
//            articleTitle = data.getStringExtra("title");//记录文章标题
//            tvArtTitle.setText(articleTitle);
//        }

//        else if (requestCode == REQUEST_CODE_CHOOSE_BG && resultCode == RESULT_OK) {//选择背景
//            bgUri = PicturePickerUtils.obtainResult(data).get(0).toString();
//            Log.d("bgUri", "bgUri: " + bgUri);
//            Glide.with(this)
//                    .load(bgUri)
//                    .placeholder(R.mipmap.default_adv)
//                    .error(R.mipmap.default_adv)
//                    .into(ivArtBGImg);
//
//        }

        if (requestCode == REQUEST_CODE_CHOOSE_ITEM_IMG && resultCode == RESULT_OK) {//选择item的图片
            datas.get(adapter.getCurClickItemIndex()).setUrl(PicturePickerUtils.obtainResult(data).get(0).toString());
            adapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_CODE_EDIT_TXT && resultCode == REQUEST_CODE_EDIT_TXT) {//编辑文字
            EContent eContent = (EContent) data.getSerializableExtra("eContent");
            datas.get(adapter.getCurClickItemIndex()).setContent(eContent.getContent());
            datas.get(adapter.getCurClickItemIndex()).setStyle(eContent.getStyle());
            adapter.notifyDataSetChanged();
        } else if (requestCode == REQUEST_CODE_CHOOSE_IMGS && resultCode == RESULT_OK) {//第一次进入页面需要选择图片
            List<Uri> uris = PicturePickerUtils.obtainResult(data);
            if (uris != null && uris.size() > 0) {
                EContent eContent;
                for (Uri uri : uris) {
                    eContent = new EContent();
                    eContent.setUrl(uri.toString());
                    eContent.setType(ItemType.IMG);
                    datas.add(eContent);
                }
                adapter.notifyDataSetChanged();
            }

        }
    }
}
