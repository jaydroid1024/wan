

package com.hannesdorfmann.mosby3.mvp.delegate;

import android.os.Bundle;
import java.util.HashMap;
import java.util.Map;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * A simple factor that creates Bundle Mocks
 * @author Hannes Dorfmann
 */
public class BundleMocker {

  private static Map<Bundle, Map<String, String>> internalBundleMap = new HashMap<>();

  public static Bundle create() {
    final Bundle bundle = Mockito.mock(Bundle.class);

    Mockito.doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        String key = (String) invocation.getArguments()[0];
        String value = (String) invocation.getArguments()[1];
        Map<String, String> internalMap = internalBundleMap.get(bundle);
        if (internalMap == null) {
          internalMap = new HashMap<String, String>();
        }

        internalMap.put(key, value);
        internalBundleMap.put(bundle, internalMap);
        return null;
      }
    }).when(bundle).putString(Mockito.anyString(), Mockito.anyString());

    Mockito.doAnswer(new Answer() {
      @Override public Object answer(InvocationOnMock invocation) throws Throwable {
        String key = (String) invocation.getArguments()[0];
        Map<String, String> internalMap = internalBundleMap.get(bundle);
        if (internalMap == null) {
          return null;
        }

        return internalMap.get(key);
      }
    }).when(bundle).getString(Mockito.anyString());

    return bundle;
  }
}
