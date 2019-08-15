// Copyright (C) 2015 The Android Open Source Project
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

package com.google.gerrit.server.restapi.change;

<<<<<<< HEAD   (dfeca3 Update git submodules)
import com.google.gerrit.extensions.common.CommentInfo;
import com.google.gerrit.extensions.restapi.AuthException;
import com.google.gerrit.extensions.restapi.RestReadView;
=======
import com.google.gerrit.reviewdb.client.Comment;
import com.google.gerrit.reviewdb.server.ReviewDb;
>>>>>>> BRANCH (067d06 Merge "Upgrade Go Bazel rules to the latest version" into st)
import com.google.gerrit.server.CommentsUtil;
import com.google.gerrit.server.change.ChangeResource;
import com.google.gerrit.server.query.change.ChangeData;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
<<<<<<< HEAD   (dfeca3 Update git submodules)
public class ListChangeComments implements RestReadView<ChangeResource> {
  private final ChangeData.Factory changeDataFactory;
  private final Provider<CommentJson> commentJson;
  private final CommentsUtil commentsUtil;
=======
public class ListChangeComments extends ListChangeDrafts {
>>>>>>> BRANCH (067d06 Merge "Upgrade Go Bazel rules to the latest version" into st)

  @Inject
  ListChangeComments(
      ChangeData.Factory changeDataFactory,
      Provider<CommentJson> commentJson,
      CommentsUtil commentsUtil) {
<<<<<<< HEAD   (dfeca3 Update git submodules)
    this.changeDataFactory = changeDataFactory;
    this.commentJson = commentJson;
    this.commentsUtil = commentsUtil;
=======
    super(db, changeDataFactory, commentJson, commentsUtil);
>>>>>>> BRANCH (067d06 Merge "Upgrade Go Bazel rules to the latest version" into st)
  }

  @Override
<<<<<<< HEAD   (dfeca3 Update git submodules)
  public Map<String, List<CommentInfo>> apply(ChangeResource rsrc)
      throws AuthException, PermissionBackendException {
    ChangeData cd = changeDataFactory.create(rsrc.getNotes());
    return commentJson
        .get()
        .setFillAccounts(true)
        .setFillPatchSet(true)
        .newCommentFormatter()
        .format(commentsUtil.publishedByChange(cd.notes()));
=======
  protected Iterable<Comment> listComments(ChangeResource rsrc) throws OrmException {
    ChangeData cd = changeDataFactory.create(db.get(), rsrc.getNotes());
    return commentsUtil.publishedByChange(db.get(), cd.notes());
  }

  @Override
  protected boolean includeAuthorInfo() {
    return true;
  }

  @Override
  public boolean requireAuthentication() {
    return false;
>>>>>>> BRANCH (067d06 Merge "Upgrade Go Bazel rules to the latest version" into st)
  }
}
