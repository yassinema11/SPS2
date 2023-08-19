package smart.parking.solution;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement
{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionManagement(Context context)
    {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user)
    {
        //save session of user whenever user is logged in
        String Email = user.getEmail();
        String Pass = user.getPassword();


        editor.putString(SESSION_KEY,Email).commit();
        editor.putString(SESSION_KEY,Pass).commit();
    }

    public int getSession()
    {
        //return user id whose session is saved
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public void removeSession()
    {
        editor.putInt(SESSION_KEY,-1).commit();
    }
}
