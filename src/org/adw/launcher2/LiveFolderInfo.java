/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.adw.launcher2;

import java.util.List;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

class LiveFolderInfo extends FolderInfo {

    /**
     * The base intent, if it exists.
     */
    Intent baseIntent;

    /**
     * The live folder's content uri.
     */
    Uri uri;

    /**
     * The live folder's display type.
     */
    int displayMode;

    LiveFolderInfo() {
        itemType = LauncherSettings.Favorites.ITEM_TYPE_LIVE_FOLDER;
    }

    @Override
    void onAddToDatabase(ContentValues values) {
        super.onAddToDatabase(values);
        values.put(LauncherSettings.Favorites.URI, uri.toString());
        if (baseIntent != null) {
            values.put(LauncherSettings.Favorites.INTENT, baseIntent.toUri(0));
        }
        values.put(LauncherSettings.Favorites.DISPLAY_MODE, displayMode);
    }

    private static final int ACTION_UNINSTALL = 2;

    @Override
	public void executeAction(EditAction action, View view, Launcher launcher) {
		switch(action.getId()) {
			case ACTION_UNINSTALL: {
				launcher.UninstallPackage(launcher.getPackageNameFromIntent(baseIntent));
			} break;
			default:
				super.executeAction(action, view, launcher);
		}
	}

	@Override
	public List<EditAction> getAvailableActions(View view) {
		List<EditAction> result = super.getAvailableActions(view);
		result.add(new EditAction(ACTION_UNINSTALL,
				android.R.drawable.ic_menu_manage,
				R.string.menu_uninstall));
		return result;
	}
}
