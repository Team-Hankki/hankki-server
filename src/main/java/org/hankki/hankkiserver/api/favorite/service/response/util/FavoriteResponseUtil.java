package org.hankki.hankkiserver.api.favorite.service.response.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoriteResponseUtil {

  public static List<String> transformDetail(String detail) {
    List<String> details = new ArrayList<>();
    if (!isDetailNull(detail)) {
      details = Arrays.asList(detail.split(" "));
    }
    return details;
  }

  public static boolean isDetailNull(String detail) { return detail == null; }
}
