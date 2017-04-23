package com.smart.home.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 冯子杰(fengzijie@machine.com)
 * Date: 14/12/29
 */
public class ActivityStack {

    private static List<WeakReference<Activity>> sGlobalActivityStack = new LinkedList<WeakReference<Activity>>();

    public static void addActivity(Activity activity) {
        for (WeakReference<Activity> ref : sGlobalActivityStack) {
            if (ref.get() == activity) {
                sGlobalActivityStack.remove(ref);
                break;
            }
        }
        removeEmptyStack();
        sGlobalActivityStack.add(0, new WeakReference<Activity>(activity));
    }

    public static List<WeakReference<Activity>> allActivity() {
        return sGlobalActivityStack;
    }

    public static void deleteActivity(Activity activity) {
        if (sGlobalActivityStack.size() > 0) {
            WeakReference<Activity> a = sGlobalActivityStack.get(0);
            if (a.get() == activity) {
                sGlobalActivityStack.remove(0);
            } else {
                removeEmptyStack();
            }
        }
    }

    public static void clearStack() {
        for (WeakReference<Activity> aSGlobalActivityStack : sGlobalActivityStack) {
            if (aSGlobalActivityStack.get() != null) {
                Activity activity = aSGlobalActivityStack.get();
                activity.finish();
            }
        }
        sGlobalActivityStack.clear();
    }

    private static void removeEmptyStack() {
        for (int idx = 0; idx < sGlobalActivityStack.size(); idx++) {
            if (sGlobalActivityStack.get(idx).get() == null) {
                sGlobalActivityStack.remove(idx);
                idx--;
            }
        }
    }

    public static Activity getCurrentActivity() {
        removeEmptyStack();

        Activity result = null;
        if (sGlobalActivityStack.size() > 0) {
            result = sGlobalActivityStack.get(0).get();
        }
        return result;
    }

}
