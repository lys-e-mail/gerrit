package com.google.gerrit.server.restapi.project;

import static java.util.Comparator.comparing;

import com.google.gerrit.extensions.api.projects.TagInfo;
import com.google.gerrit.extensions.common.ListTagSortOption;
import com.google.inject.Inject;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;

public class TagSorter {
  @Inject
  public TagSorter() {}

  /** Sort the tags by the given sort option, in place */
  public void sort(ListTagSortOption sortBy, List<TagInfo> tags, boolean descendingOrder) {
    switch (sortBy) {
      case CREATION_TIME:
        Comparator<Timestamp> nullsComparator =
            descendingOrder
                ? Comparator.nullsFirst(Comparator.naturalOrder())
                : Comparator.nullsLast(Comparator.naturalOrder());
        tags.sort(comparing(t -> t.created, nullsComparator));
        break;
      case REF:
      default:
        tags.sort(comparing(t -> t.ref));
        break;
    }
  }
}
