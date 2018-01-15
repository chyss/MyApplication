package com.chyss.myapplication.widget.gis;

import android.app.Activity;
import android.os.Bundle;

import com.chyss.myapplication.BaseActivity;
import com.chyss.myapplication.R;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;

public class GisActivity extends BaseActivity
{
	private MapView gis_mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gis);

		setTitle("ArcGIS地图开发");

		initView();
		initMapView();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		gis_mapView.resume();
	}

	@Override
	protected void onPause()
	{
		gis_mapView.pause();
		super.onPause();
	}

	private void initView()
	{
		gis_mapView = (MapView) findViewById(R.id.gis_mapView);
	}

	private void initMapView()
	{
		ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 34.056295, -117.195800, 16);
		gis_mapView.setMap(map);
	}
}
