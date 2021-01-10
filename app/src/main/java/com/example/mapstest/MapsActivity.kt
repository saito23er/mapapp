package com.example.mapstest


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.testviewpager1.MyAdapter
import com.example.testviewpager1.MyModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var searchView: SearchView
    // 検索インターフェースのSearchViewを定義

    //Actionbar
    private var actionBar: ActionBar? = null

    //UI Views
    private lateinit var myModelList: ArrayList<MyModel>
    private lateinit var myAdapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        //メソッド

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // layout>activity_maps.xmlのデータをBundleインスタンスに詰め込んだ？


        //init actionbar
        loadCards()







        //add page change listener
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //change title of actionbar
                val data = myModelList[position]

                // 緯度経度情報をもとにカメラ位置を変更
                mMap.animateCamera(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition(
                            LatLng(
                                data.latitude,
                                data.longitude
                            ),
                            mMap.cameraPosition.zoom,
                            mMap.cameraPosition.tilt,
                            mMap.cameraPosition.bearing
                        )
                    )
                )
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })


        searchView = findViewById(R.id.sv_location);
        // activity_maps.xmlのSearchViewで定義した@+id/sv_locationを拾ってきた

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //検索ボタン押かEnterキーで実行
                //入力内容でDB検索してマーカーを再設置したい
                val location = searchView.query.toString()
                //SearchViewから文字列抽出

                var addressList: List<Address>? = null
                //<>ジェネリクス　総型
                //このAddressは「何らかの特定の型」で呼び出し側でどの型にするのか定義している


                if (location.isNotEmpty()) {
                    val geocoder = Geocoder(this@MapsActivity, Locale.getDefault())
                    //Geocoderクラス設定　Geocoder(Context context, Locale locale)
                    //Locale.getDefault()で使用している端末のデフォルトが指定される
                    try {
                        addressList = geocoder.getFromLocationName(location, 1)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }


                    // 検索結果が1件以上ある場合に実施
                    if (addressList!!.isNotEmpty()) {
                        val address = addressList[0]
                        val latLng = LatLng(address.latitude, address.longitude)


                        mMap.addMarker(MarkerOptions().position(latLng).title(location))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                    }
                }

                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //入力されるたびに呼び出される、入力候補を検索して、検索フォーム下に表示したい
                return false

            }


        })


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction();
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        transaction.commit()
    }

    //カードの設定
    private fun loadCards() {
        //init list
        myModelList = ArrayList()

        //add items to list
        // 都庁の緯度経度
        myModelList.add(
            MyModel(
                "Android",
                "Desctiption 02",
                "03/08/2020",
                R.drawable.christmas_afternoon_tea__sunshinecity__ikebukuro,
                35.689815,
                139.6898573,
                "m0",
                    "p0"


            )
        )

        // 池袋
        myModelList.add(
            MyModel(
                "Android Nougat",
                "Desctiption 02",
                "03/09/2020",
                R.drawable.christmas_afternoon_tea__sunshinecity__ikebukuro,
                35.729888,
                139.710752,
                "m1",
                    "p1"
            )
        )

        // 伊勢原
        myModelList.add(
            MyModel(
                "Isehara",
                "Desctiption 02",
                "03/09/2020",
                R.drawable.christmas_afternoon_tea__sunshinecity__ikebukuro,
                35.6048,
                139.1988,
                "m2",
                    "p2"
            )
        )

        //setup adapter to viewpager
        myAdapter = MyAdapter(this, myModelList)

        //set adapter to viewpager
        viewPager.adapter = myAdapter

        //set default padding
        viewPager.setPadding(100, 0, 100, 0)


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val isehara = LatLng(35.3960, 139.3136)
        mMap.addMarker(MarkerOptions().position(isehara).title("Marker in isehara"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(isehara, 10f))

        val sagamilake = LatLng(35.6048, 139.1988)
        mMap.addMarker(MarkerOptions().position(sagamilake).title("Marker in isehara"))

        val komaMt = LatLng(35.3209363, 139.3061822)
        mMap.addMarker(MarkerOptions().position(komaMt).title("Marker in isehara"))

        val yabitsuPark = LatLng(35.4263203, 139.2180733)
        mMap.addMarker(MarkerOptions().position(yabitsuPark).title("Marker in isehara"))

        val enoisland = LatLng(35.2994513, 139.482428)
        mMap.addMarker(MarkerOptions().position(enoisland).title("Marker in isehara"))

        val enoaquarium = LatLng(35.3100092, 139.4772393)
        mMap.addMarker(MarkerOptions().position(enoaquarium).title("Marker in isehara"))

        val Germanvillage = LatLng(35.4057342, 140.0511276)
        mMap.addMarker(MarkerOptions().position(Germanvillage).title("Marker in isehara"))

        val mitsuioutlet = LatLng(35.4344811, 139.9770982)
        mMap.addMarker(MarkerOptions().position(mitsuioutlet).title("Marker in isehara"))

        val YokohamaZoo = LatLng(35.4929784, 139.5242601)
        mMap.addMarker(MarkerOptions().position(YokohamaZoo).title("Marker in isehara"))

        val IzuSkyline = LatLng(35.0134368, 138.9169147)
        mMap.addMarker(MarkerOptions().position(IzuSkyline).title("Marker in isehara"))

        val OwakuValley = LatLng(35.2485301, 139.0055652)
        mMap.addMarker(MarkerOptions().position(OwakuValley).title("Marker in isehara"))

        val RivieraZushi = LatLng(35.2962613, 139.5515494)
        mMap.addMarker(MarkerOptions().position(RivieraZushi).title("Marker in isehara"))


        // パーミッションのチェックを行う
        if (!checkPermission()) {
            requestPermission()
        }

        return
    }

    /**
     * パーミッションが許可されいているかどうかを判定
     */
    fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * パーミッションの許可を求めるダイアログを表示
     */
    fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            100
        )
    }


    // クリック処理　画像をクリックしたら画面遷移
    fun viewpager(v: View?) {
        val intent = Intent(this, Page1::class.java) // 画面指定
        startActivity(intent) //  画面を開く
    }


    // クリック処理　画像をクリックしたら画面遷移
    fun onButton2(v: View?) {
        val intent = Intent(this, Page2::class.java) // 画面指定
        startActivity(intent) //  画面を開く
    }


}