package miteat.miteat;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Itzik on 07/07/2016.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

    }
}
