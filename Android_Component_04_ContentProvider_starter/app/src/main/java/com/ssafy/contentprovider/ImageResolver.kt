package com.ssafy.contentprovider

import android.Manifest
import android.content.ContentResolver
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateFormat
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.contentprovider.databinding.ActivityContentProviderBinding
import com.ssafy.contentprovider.util.PermissionChecker
import java.util.*

private const val TAG = "ImageResolver_싸피"
class ImageResolver : AppCompatActivity() {

    /** permission check **/
    private val checker = PermissionChecker(this)
    private val runtimePermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    /** permission check **/

    private lateinit var binding: ActivityContentProviderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Image"

        //권한 체크
        checkPermission()
    }

    private fun checkPermission(){
        /** permission check **/
        if (!checker.checkPermission(this, runtimePermissions)) {
            checker.setOnGrantedListener{ //퍼미션 획득 성공일때
                initView()
            }

            checker.requestPermissionLauncher.launch(runtimePermissions) // 권한없으면 창 띄움
        } else { //이미 전체 권한이 있는 경우
            initView()
        }
        /** permission check **/
    }


    private fun initView() {
        getImage()?.use { cursor ->
            if (cursor.moveToFirst()) {
                // 컬럼의 열 인덱스 확인.
                val idColNum = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
                val titleColNum = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.TITLE)
                val dateTakenColNum = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATE_TAKEN)

                var index = 0;
                do {
                    //열 인덱스로 데이터 구하기.
                    val id = cursor.getLong(idColNum)
                    val title = cursor.getString(titleColNum)
                    val dateTaken = cursor.getLong(dateTakenColNum)
                    val imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    Log.d(TAG, "imageUri: ${imageUri}") //  content://media/external/images/media/31
                    val text = DateFormat.format("yyyy/MM/dd (E) kk:mm:ss", Date(dateTaken)).toString()

                    //View에 데이터 세팅
                    val textId = resources.getIdentifier("textView_" + (++index), "id", packageName)
                    val textView = findViewById<TextView>(textId);

                    val imgId = resources.getIdentifier("imageView_" + index, "id", packageName)
                    val imgView = findViewById<ImageView>(imgId);

                    textView.text = "촬영일시: $text"
                    imgView.setImageURI(imageUri)
                } while (cursor.moveToNext())
            }
        }
    }

    private fun getImage(): Cursor? {
        val resolver = contentResolver
        var queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        //가져올 컬럼명(Projection)
        val what = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.TITLE,
            MediaStore.Images.ImageColumns.DATE_TAKEN
        )


        //android version 대응. 10이상.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            var queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            //정렬 & 건수제한
            val bundle = Bundle().apply {
                putStringArray(ContentResolver.QUERY_ARG_SORT_COLUMNS,  arrayOf(MediaStore.Images.ImageColumns.DATE_TAKEN))
                putInt(ContentResolver.QUERY_ARG_SORT_DIRECTION, ContentResolver.QUERY_SORT_DIRECTION_DESCENDING)
                putInt(ContentResolver.QUERY_ARG_OFFSET, 0)
                putInt(ContentResolver.QUERY_ARG_LIMIT, 2)
            }

            return resolver.query(queryUri, what, bundle, null)
        }else{
            //이전버전 정렬 & 건수제한
            queryUri = queryUri.buildUpon().appendQueryParameter("limit", "2").build()
            Log.d(TAG, "getImage: $queryUri") //content://media/external/images/media?limit=3
            val orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"

            return resolver.query(queryUri, what, null, null, orderBy)
        }
    }
}


