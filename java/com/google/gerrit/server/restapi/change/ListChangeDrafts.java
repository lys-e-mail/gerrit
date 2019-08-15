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

import com.google.gerrit.extensions.common.CommentInfo;
import com.google.gerrit.extensions.restapi.AuthException;
import com.google.gerrit.extensions.restapi.RestReadView;
import com.google.gerrit.reviewdb.client.Comment;
import com.google.gerrit.server.CommentsUtil;
import com.google.gerrit.server.change.ChangeResource;
import com.google.gerrit.server.permissions.PermissionBackendException;
import com.google.gerrit.server.query.change.ChangeData;
<<<<<<< HEAD   (dfeca3 Update git submodules)
=======
import com.google.gerrit.server.restapi.change.CommentJson.CommentFormatter;
import com.google.gwtorm.server.OrmException;
>>>>>>> BRANCH (067d06 Merge "Upgrade Go Bazel rules to the latest version" into st)
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import java.util.List;
import java.util.Map;

@Singleton
public class ListChangeDrafts implements RestReadView<ChangeResource> {
<<<<<<< HEAD   (dfeca3 Update git submodules)
  private final ChangeData.Factory changeDataFactory;
  private final Provider<CommentJson> commentJson;
  private final CommentsUtil commentsUtil;
=======
  protected final Provider<ReviewDb> db;
  protected final ChangeData.Factory changeDataFactory;
  protected final Provider<CommentJson> commentJson;
  protected final CommentsUtil commentsUtil;
>>>>>>> BRANCH (067d06 Merge "Upgrade Go Bazel rules to the latest version" into st)

  @Inject
  ListChangeDrafts(
      ChangeData.Factory changeDataFactory,
      Provider<CommentJson> commentJson,
      CommentsUtil commentsUtil) {
    this.changeDataFactory = changeDataFactory;
    this.commentJson = commentJson;
    this.commentsUtil = commentsUtil;
  }

  protected Iterable<Comment> listComments(ChangeResource rsrc) throws OrmException {
    ChangeData cd = changeDataFactory.create(db.get(), rsrc.getNotes());
    return commentsUtil.draftByChangeAuthor(db.get(), cd.notes(), rsrc.getUser().getAccountId());
  }

  protected boolean includeAuthorInfo() {
    return false;
  }

  public boolean requireAuthentication() {
    return true;
  }

  @Override
  public Map<String, List<CommentInfo>> apply(ChangeResource rsrc)
<<<<<<< HEAD   (dfeca3 Update git submodules)
      throws AuthException, PermissionBackendException {
    if (!rsrc.getUser().isIdentifiedUser()) {
=======
      throws AuthException, OrmException, PermissionBackendException {
    if (requireAuthentication() && !rsrc.getUser().isIdentifiedUser()) {
>>>>>>> BRANCH (067d06 Merge "Upgrade Go Bazel rules to the latest version" into st)
      throw new AuthException("Authentication required");
    }
<<<<<<< HEAD   (dfeca3 Update git submodules)
    ChangeData cd = changeDataFactory.create(rsrc.getNotes());
    List<Comment> drafts =
        commentsUtil.draftByChangeAuthor(cd.notes(), rsrc.getUser().getAccountId());
=======
    return getCommentFormatter().format(listComments(rsrc));
  }

  public List<CommentInfo> getComments(ChangeResource rsrc)
      throws AuthException, OrmException, PermissionBackendException {
    if (requireAuthentication() && !rsrc.getUser().isIdentifiedUser()) {
      throw new AuthException("Authentication required");
    }
    return getCommentFormatter().formatAsList(listComments(rsrc));
  }

  private CommentFormatter getCommentFormatter() {
>>>>>>> BRANCH (067d06 Merge "Upgrade Go Bazel rules to the latest version" into st)
    return commentJson
        .get()
        .setFillAccounts(includeAuthorInfo())
        .setFillPatchSet(true)
        .newCommentFormatter();
  }
}
