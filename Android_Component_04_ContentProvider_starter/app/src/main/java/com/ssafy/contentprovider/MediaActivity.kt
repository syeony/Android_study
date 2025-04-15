package com.ssafy.contentprovider

import android.Manifest
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.contentprovider.util.PermissionChecker


private const val TAG = "MediaActivity_싸피"
class MediaActivity :AppCompatActivity() {

    /** permission check **/
    private val checker = PermissionChecker(this)
    private val runtimePermissions = arrayOf(Manifest.permission.READ_MEDIA_AUDIO)
    /** permission check **/

    private val musicList = mutableListOf<MusicDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        title = "Media"
        checkPermission()

    }

    private fun checkPermission(){
        /** permission check **/
        if (!checker.checkPermission(this, runtimePermissions)) {
            checker.setOnGrantedListener{ //퍼미션 획득 성공일때
                initData()
                initView()
            }

            checker.requestPermissionLauncher.launch(runtimePermissions) // 권한없으면 창 띄움
        } else { //이미 전체 권한이 있는 경우
            initData()
            initView()
        }
        /** permission check **/
    }

    private fun initView(){
        val musicAdapter = MusicAdapter(musicList)

        findViewById<RecyclerView>(R.id.recyle_view).apply{
            adapter = musicAdapter
            this.layoutManager = LinearLayoutManager(this@MediaActivity)
        }
    }

    private fun initData(){
        getAudio().use {
            if(it.moveToFirst()){
                do{
                    // getLong, getString으로 칼럼들을 읽는다.


                    //Dto로 생성하고, musicList에 담는다.

                }while(it.moveToNext())
            }
        }
    }


    private fun getAudio(): Cursor {
        val resolver = contentResolver
        val queryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        //정렬
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC";

        //가져올 컬럼명
        val what = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
        )
        return resolver.query(queryUri, what, null, null, sortOrder)!!
    }



    inner class MusicAdapter(val musicList:MutableList<MusicDto>): RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

        var uri: Uri = Audio.Media.EXTERNAL_CONTENT_URI

        inner class MusicViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
            var title = itemView.findViewById<TextView>(R.id.name)
            var artist = itemView.findViewById<TextView>(R.id.textView3)
            fun bind(music:MusicDto){
                title.text = music.title
                artist.text = music.artist
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_media, parent, false)
            return MusicViewHolder(view).apply {
                itemView.setOnClickListener{
                    Toast.makeText(parent.context, "musicList.id:${layoutPosition}", Toast.LENGTH_SHORT).show()
//                    val u: Uri = ContentUris.withAppendedId(uri, musicList[layoutPosition].id)
//                    val intent = Intent(Intent.ACTION_VIEW, u)
//                    this@MediaActivity.startActivity(intent)
                }
            }
        }

        override fun onBindViewHolder(holder: MusicAdapter.MusicViewHolder, position: Int) {
            holder.bind(musicList.get(position))
        }

        override fun getItemCount(): Int {
            return musicList.size
        }
    }
}