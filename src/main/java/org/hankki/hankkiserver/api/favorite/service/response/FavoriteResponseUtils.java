package org.hankki.hankkiserver.api.favorite.service.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoriteResponseUtils {

  public static List<String> getDetail(String detail) {
    List<String> details = new ArrayList<>();
    if (!isDetailNull(detail)) {
      details = Arrays.asList(detail.split(" "));
    }
    return details;
  }

  public static boolean isDetailNull(String detail) { return detail == null; }
}
