package com.bishe.nongcun.fragment;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.activity.OKActivity;
import com.bishe.nongcun.adapter.ImagePickerAdapter;
import com.bishe.nongcun.bean.MyUser;
import com.bishe.nongcun.bean.PriceItem;
import com.bishe.nongcun.imageloader.GlideImageLoader0;
import com.bishe.nongcun.utils.ChooseCityInterface;
import com.bishe.nongcun.utils.ChooseCityUtil;
import com.bishe.nongcun.utils.LogUtils;
import com.bishe.nongcun.view.LoadDialog;
import com.bishe.nongcun.view.SelectDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by 郑卫超 on 2017/5/3.
 * 发布供应
 */

public class Fragment2_sale extends BaseFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    @Bind(R.id.et_push_sale_title)
    EditText etPushSaleTitle;
    @Bind(R.id.tv_select_mou)
    TextView tvSelectMou;
    @Bind(R.id.et_push_sale_desc)
    EditText etPushSaleDesc;
    @Bind(R.id.et_push_sale_count)
    EditText etPushSaleCount;
    @Bind(R.id.sp_push_sale_unites)
    Spinner spPushSaleUnites;
    @Bind(R.id.et_push_sale_price)
    EditText etPushSalePrice;
    @Bind(R.id.sp_push_sale_unit)
    Spinner spPushSaleUnit;
    @Bind(R.id.recyclerView0)
    RecyclerView recyclerView0;
    @Bind(R.id.bt_push_sale_enter)
    Button btPushSaleEnter;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 3;               //允许选择图片最大数

    private String kind1;
    private String kind2;
    private String kind3;

    private String priceUnit = "/斤";
    private ArrayList<String> photos;
    String[] filePaths;

    private String title;
    private String price;
    private String desc;

    @Override
    protected View initView() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_2_push, null);
        return inflate;
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader0());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    @Override
    public void initData() {
        initImagePicker();
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(mActivity, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView0.setLayoutManager(new GridLayoutManager(mActivity, 4));
        recyclerView0.setHasFixedSize(true);
        recyclerView0.setAdapter(adapter);
        //        种类选择
        tvSelectMou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseCityUtil cityUtil = new ChooseCityUtil();
                String[] oldCityArray = {"水果", "苹果", "苹果"};
                cityUtil.createDialog(mActivity, oldCityArray, new ChooseCityInterface() {
                    @Override
                    public void sure(String[] newCityArray) {
                        //oldCityArray为传入的默认值 newCityArray为返回的结果
                        tvSelectMou.setText(newCityArray[0] + "-" + newCityArray[1] + "-" + newCityArray[2]);

                        kind1 = newCityArray[0];
                        kind2 = newCityArray[1];
                        kind3 = newCityArray[2];

                    }
                });
            }
        });
    }

    @Override
    public void initListener() {
        spPushSaleUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] units = getResources().getStringArray(R.array.units);
                priceUnit = units[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 压缩图片
     *
     * @param paths
     */
    private void initLuban(List<String> paths) {
        photos = new ArrayList<>();
        filePaths=new String[selImageList.size()];
        Luban.with(mActivity)
                .load(paths)                                   // 传入要压缩的图片列表
                .ignoreBy(100)                                  // 忽略不压缩图片的大小
                .setTargetDir(getPath())                        // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        LoadDialog.show(mActivity, "正在进行图片压缩ing……");
                    }

                    @Override
                    public void onSuccess(File file) {
                        LogUtils.e("压缩成功");
                        LoadDialog.dismiss(mActivity);
                        photos.add(file.getAbsolutePath());

                        if (photos.size() == selImageList.size()) {
                            for (int i = 0; i < selImageList.size(); i++) {
                                filePaths[i] = photos.get(i);
//                                filePaths = (String[]) selImageList.toArray(new String[i]);
                            }
                            BmobUpSale();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        LoadDialog.dismiss(mActivity);
                        Toast.makeText(mActivity, "压缩失败！", Toast.LENGTH_SHORT).show();
                    }
                }).launch();    //启动压缩
    }

    /**
     * 上传
     */
    private void BmobUpSale() {
        LoadDialog.show(mActivity, "发布供应中...");
        LogUtils.e("开始上传……");

        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                LogUtils.e("s :" + urls.size() + "-----" + files + "----" + urls);
                if (urls.size() == filePaths.length) {//如果全部上传完，则更新该条记录
                    PriceItem priceItem = new PriceItem();
                    MyUser user = BmobUser.getCurrentUser(MyUser.class);
                    priceItem.setAuthor(user);
                    priceItem.setTitle(title);
                    priceItem.setContent(desc);
                    priceItem.setPrice(price + "元" + priceUnit);
                    priceItem.setKind1(kind1);
                    priceItem.setKind2(kind2);
                    priceItem.setKind3(kind3);
                    if (filePaths.length == 1) {
                        priceItem.setPic1(files.get(0));
                    } else if (filePaths.length == 2) {
                        priceItem.setPic1(files.get(0));
                        priceItem.setPic2(files.get(1));
                    } else if (filePaths.length == 3) {
                        priceItem.setPic1(files.get(0));
                        priceItem.setPic2(files.get(1));
                        priceItem.setPic3(files.get(2));
                    }

                    priceItem.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null) {
                                LogUtils.e("上传成功……"+ objectId);
                                Toast.makeText(mActivity, "发布出售成功", Toast.LENGTH_SHORT).show();
                                initNull();
                                Intent intent = new Intent(mActivity, OKActivity.class);
                                intent.putExtra("kind1", kind1);
                                intent.putExtra("kind2", kind2);
                                intent.putExtra("need", "wantbuy");
                                startActivity(intent);
                                LoadDialog.dismiss(mActivity);
                            } else {
                                LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                                LoadDialog.dismiss(mActivity);
                            }
                        }
                    });

                } else {
                    //有可能上传不完整，中间可能会存在未上传成功的情况，你可以自行处理
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                LogUtils.e("错误码" + statuscode + ",错误描述：" + errormsg);
                Toast.makeText(mActivity, "发生错误，请稍后重试", Toast.LENGTH_SHORT).show();
                LoadDialog.dismiss(mActivity);
            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                Log.i("life", "insertBatchDatasWithOne -onProgress :" + curIndex + "---" + curPercent + "---" + total + "----" + totalPercent);
//                LoadDialog.dismiss(mActivity);
            }

        });

    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    private void initNull() {
        etPushSaleDesc.setText("");
        etPushSaleTitle.setText("");
        etPushSalePrice.setText("");
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(mActivity, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!mActivity.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                /**
                                 * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
                                 *
                                 * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
                                 *
                                 * 如果实在有所需要，请直接下载源码引用。
                                 */
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(mActivity, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(mActivity, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }

                    }
                }, names);


                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(mActivity, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }

    }

    ArrayList<ImageItem> images = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String path = images.get(0).path;
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }

    @OnClick(R.id.bt_push_sale_enter)
    public void onViewClicked() {
        desc = etPushSaleDesc.getText().toString().trim();
        price = etPushSalePrice.getText().toString().trim();
        title = etPushSaleTitle.getText().toString().trim();

        if (TextUtils.isEmpty(desc) || TextUtils.isEmpty(price) || TextUtils.isEmpty(title)) {
            Toast.makeText(mActivity, "请您完整地填写信息", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(kind1)) {
            Toast.makeText(mActivity, "请选择分类", Toast.LENGTH_SHORT).show();
            return;
        }

/*        if (selImageList.size() != 3) {
            Toast.makeText(mActivity, "请选择3张图片", Toast.LENGTH_SHORT).show();
            return;
        }*/
        if (selImageList.size() != 0) {
            LogUtils.e("开始压缩……");
            ArrayList<String> paths = new ArrayList<>();
            for (int i = 0; i < selImageList.size(); i++) {
                paths.add(selImageList.get(i).path);
            }
            initLuban(paths);
        } else {
            BmobUpSaleNoPic();
        }

    }

    private void BmobUpSaleNoPic() {
        LoadDialog.show(mActivity, "发布供应中...");

        PriceItem priceItem = new PriceItem();
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        priceItem.setAuthor(user);
        priceItem.setTitle(title);
        priceItem.setContent(desc);
        priceItem.setPrice(price + "元" + priceUnit);
        priceItem.setKind1(kind1);
        priceItem.setKind2(kind2);
        priceItem.setKind3(kind3);

        priceItem.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    LogUtils.e("发布成功……"+ objectId);
                    Toast.makeText(mActivity, "发布出售成功", Toast.LENGTH_SHORT).show();
                    initNull();
                    Intent intent = new Intent(mActivity, OKActivity.class);
                    intent.putExtra("kind1", kind1);
                    intent.putExtra("kind2", kind2);
                    intent.putExtra("need", "wantbuy");
                    startActivity(intent);
                    LoadDialog.dismiss(mActivity);
                } else {
                    LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                    Toast.makeText(mActivity, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    LoadDialog.dismiss(mActivity);
                }
            }
        });
    }

}