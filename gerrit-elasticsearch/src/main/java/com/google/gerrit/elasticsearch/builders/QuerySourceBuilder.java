<<<<<<< HEAD   (aa2547 Merge "Measure and report time to reply to changes")
=======
// Copyright (C) 2018 The Android Open Source Project, 2009-2015 Elasticsearch
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.elasticsearch.builders;

import java.io.IOException;

/** A trimmed down and modified version of org.elasticsearch.action.support.QuerySourceBuilder. */
class QuerySourceBuilder {

  private final QueryBuilder queryBuilder;

  QuerySourceBuilder(QueryBuilder queryBuilder) {
    this.queryBuilder = queryBuilder;
  }

  void innerToXContent(XContentBuilder builder) throws IOException {
    builder.field("query");
    queryBuilder.toXContent(builder);
  }
}
>>>>>>> BRANCH (5c831c Merge "Merge branch 'stable-2.14' into stable-2.15" into sta)
