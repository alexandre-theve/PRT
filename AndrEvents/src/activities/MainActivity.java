/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package activities;

import model.User;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;

import com.ig2i.andrevents.R;

import controller.UserController;
import fragments.AroundMeFragment;
import fragments.AtAnEventListFragment;
import fragments.HomeFragment;
import fragments.SearchFragment;

public class MainActivity extends Activity implements OnQueryTextListener {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mSectionsTitles;

	private UserController userControler;
	private Fragment displayedFragment;
	private MenuItem searchItem;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication andrEvents = ((MyApplication) getApplicationContext());
		this.userControler = andrEvents.getUserController();
		userControler.setUserConnected((User) getIntent().getExtras()
				.getSerializable("user"));

		setContentView(R.layout.activity_main);
		mTitle = mDrawerTitle = getTitle();
		mSectionsTitles = getResources().getStringArray(R.array.titles_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mSectionsTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		if (savedInstanceState == null) {
			selectItem(0, true);
		}
		

	}
	 @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		 if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
		        // do something on back.
			 searchItem.collapseActionView();
			 ((SearchView)searchItem.getActionView()).setQuery("", false);
		    }
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		searchItem = menu.findItem(R.id.action_eventSearch);
		SearchView searchView = (SearchView) searchItem.getActionView();
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(new OnCloseListener() {
			
			@Override
			public boolean onClose() {
				// TODO Auto-generated method stub
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}

	protected void displaySearch() {
		Bundle args = new Bundle();
		displayedFragment = new SearchFragment();
		displayedFragment.setArguments(args);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, displayedFragment)
				.addToBackStack("home").commit();
		
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_eventSearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_preferences:
			Intent intent = new Intent(this, PreferencesActivity.class);

			startActivity(intent);
			break;
		}
		return true;
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		selectItem(position, false, "");
	}
	
	private void selectItem(int position, Boolean start) {
		selectItem(position, start, "");
	}

	private void selectItem(int position, String query) {
		selectItem(position, false, query);
	}
	
	private void selectItem(int position, Boolean start, String query) {
		// update the main content by replacing fragments
		displayedFragment = null;
		Bundle args = new Bundle();
		switch (position) {
		// accueil
		case 0:
			displayedFragment = new HomeFragment();
			args.getInt(((HomeFragment) displayedFragment).FRAGMENT_NUMBER,
					position);
			displayedFragment.setArguments(args);
			break;
		case 1:
			displayedFragment = new AroundMeFragment();
			args.getInt(((AroundMeFragment) displayedFragment).FRAGMENT_NUMBER,
					position);
			displayedFragment.setArguments(args);
			break;
		case 2:
			displayedFragment = new AtAnEventListFragment();
			args.getInt(
					((AtAnEventListFragment) displayedFragment).FRAGMENT_NUMBER,
					position);
			displayedFragment.setArguments(args);
			break;
		
		}

		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, displayedFragment)
				.addToBackStack("home").commit();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.content_frame, displayedFragment);
		if(!start)
			fragmentTransaction.addToBackStack("home");
		fragmentTransaction.commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mSectionsTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		Log.i("com.ig2i.andrevent", "setTitle " + title);
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onQueryTextChange(String arg0) {
		if (arg0.equals("") && !(displayedFragment instanceof SearchFragment)){
			return true;
		}
		
		if (!(displayedFragment instanceof SearchFragment))  {
			displaySearch();
		}
		if(displayedFragment != null){
		((SearchFragment) displayedFragment).updateQuery(arg0);
		}
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		if (!(displayedFragment instanceof SearchFragment))  {
			displaySearch();
		
		}
		if(displayedFragment != null){
			((SearchFragment) displayedFragment).updateQuery(query);
			}
		return true;
	}

}
