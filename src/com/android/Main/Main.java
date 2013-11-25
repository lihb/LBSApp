/***
 * Copyright (c) 2011 readyState Software Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.android.Main;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.android.Friends.FriendsMap;
import com.android.Nearby.NearbyActivity;

public class Main extends ListActivity implements OnItemClickListener {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mStrings));
        getListView().setOnItemClickListener(this);
    }

    private String[] mStrings = {"1. Nearby", "2.Friends"};

	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = null;
		switch (position) {
		case 0:
			intent = new Intent(this, NearbyActivity.class);
			startActivity(intent);
			break;
		case 1:
			intent = new Intent(this, FriendsMap.class);
			startActivity(intent);
			break;
		/*case 2:
			intent = new Intent(this, TapControlledMap.class);
			startActivity(intent);
			break;*/
		default:
			break;
		}
	}
	
}
