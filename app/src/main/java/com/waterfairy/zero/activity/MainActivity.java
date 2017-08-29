package com.waterfairy.zero.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.waterfairy.utils.ImageUtils;
import com.waterfairy.utils.ToastUtils;
import com.waterfairy.utils.ToolBarUtils;
import com.waterfairy.zero.R;
import com.waterfairy.zero.presenter.MainPresenter;
import com.waterfairy.zero.utils.MyImgUtil;
import com.waterfairy.zero.view.MainView;
import com.waterfairy.zero.widget.PlayProgressBar;

public class MainActivity extends AppCompatActivity implements MainView, ToolBarUtils.OnToolBarBackClickListener, CompoundButton.OnCheckedChangeListener, PlayProgressBar.OnProgressChangedListener {
    private static final String TAG = "main";
    //presenter
    private MainPresenter mPresenter;
    //view
    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ImageView mIVBgBlur;
    private ImageView mIVAlbum;
    private CardView mCV;
    private TextView mTVMusicName;
    private TextView mTVMusicSinger;
    private TextView mTVMusicTotalTime;
    private TextView mTVMusicCurrentTime;
    private TextView mTVMusicCurrentNumAndTotal;
    private PlayProgressBar mPlayProgressBar;
    private CheckBox mCBPlay;

    //data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        findView();
        initView();

    }

    private void initData() {
        mPresenter = new MainPresenter(this);
        mPresenter.initMusic();
    }

    private void initView() {
        ToolBarUtils.initToolBarMenu(this, R.id.drawer_layout, R.id.actionbar, 0, true);
        Bitmap bitmap = ImageUtils.selfBlur1(BitmapFactory.decodeResource(getResources(), R.mipmap.bg_1), 60, false);
        mIVBgBlur.setImageBitmap(bitmap);
        MyImgUtil.loadAlbum(this, mCV, mIVAlbum, R.mipmap.bg_1, false);
        mCBPlay.setOnCheckedChangeListener(this);
        mPlayProgressBar.setOnProgressChanged(this);
    }

    private void findView() {
        mToolbar = (Toolbar) findViewById(R.id.actionbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mIVAlbum = (ImageView) findViewById(R.id.iv_album);
        mIVBgBlur = (ImageView) findViewById(R.id.bg_img_blur);
        mCV = (CardView) findViewById(R.id.card_view);
        //play  info
        mTVMusicName = (TextView) findViewById(R.id.tv_music_name);
        mTVMusicSinger = (TextView) findViewById(R.id.tv_music_singer);
        mTVMusicTotalTime = (TextView) findViewById(R.id.tv_play_total_time);
        mTVMusicCurrentTime = (TextView) findViewById(R.id.tv_play_time);
        mTVMusicCurrentNumAndTotal = (TextView) findViewById(R.id.tv_play_num);
        mPlayProgressBar = (PlayProgressBar) findViewById(R.id.play_progressbar);
        mCBPlay = (CheckBox) findViewById(R.id.bt_play);
    }

    @Override
    public void onToolBarBackClick() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                ToastUtils.show(R.string.search);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showMusicList(View view) {
        startActivity(new Intent(this, MusicListActivity.class));
    }

    public void action(View view) {
        switch (view.getId()) {
            case R.id.bt_next:
                mPresenter.next();
                break;
            case R.id.bt_previous:
                mPresenter.previous();
                break;
        }
    }

    public void onClickSlideMenu(View view) {
        switch (view.getId()) {
            case R.id.file_folder:
                break;
            case R.id.my_music:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }


    @Override
    public void setMusicInfoSin(String musicName, int totalNum, int currentPos) {
        mTVMusicName.setText(musicName);
        mTVMusicCurrentNumAndTotal.setText(currentPos + "/" + totalNum);
        mTVMusicCurrentTime.setText("00:00");
        mTVMusicTotalTime.setText("00:00");
        mPlayProgressBar.setProgress(0);
    }

    @Override
    public void setMusicInfoTime(float radio, String duration, String currentPosition) {
        mTVMusicCurrentTime.setText(currentPosition);
        mTVMusicTotalTime.setText(duration);
        mPlayProgressBar.setProgress(radio);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.bt_play) {
            if (isChecked) {
                mPresenter.play();
            } else {
                mPresenter.pause();
            }
        }
    }

    @Override
    public void onProgressChange(int progress, float rate) {
        mPresenter.onSeek(rate);
    }
}
