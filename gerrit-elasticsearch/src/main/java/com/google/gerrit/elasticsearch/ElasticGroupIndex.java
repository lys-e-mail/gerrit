// Copyright (C) 2017 The Android Open Source Project
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

package com.google.gerrit.elasticsearch;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gerrit.elasticsearch.ElasticMapping.MappingProperties;
<<<<<<< HEAD   (e65498 Merge branch 'stable-2.14' into stable-2.15)
import com.google.gerrit.index.QueryOptions;
import com.google.gerrit.index.Schema;
import com.google.gerrit.index.query.DataSource;
import com.google.gerrit.index.query.Predicate;
import com.google.gerrit.index.query.QueryParseException;
=======
import com.google.gerrit.elasticsearch.builders.QueryBuilder;
import com.google.gerrit.elasticsearch.builders.SearchSourceBuilder;
>>>>>>> BRANCH (c33729 ElasticContainer: Allow to specify the docker container vers)
import com.google.gerrit.reviewdb.client.AccountGroup;
import com.google.gerrit.server.account.GroupCache;
import com.google.gerrit.server.config.GerritServerConfig;
import com.google.gerrit.server.config.SitePaths;
import com.google.gerrit.server.group.InternalGroup;
import com.google.gerrit.server.index.IndexUtils;
import com.google.gerrit.server.index.group.GroupField;
import com.google.gerrit.server.index.group.GroupIndex;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gwtorm.server.OrmException;
import com.google.gwtorm.server.ResultSet;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
<<<<<<< HEAD   (e65498 Merge branch 'stable-2.14' into stable-2.15)
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Bulk.Builder;
import io.searchbox.core.Search;
import io.searchbox.core.search.sort.Sort;
import io.searchbox.core.search.sort.Sort.Sorting;
=======
import com.google.inject.assistedinject.AssistedInject;
>>>>>>> BRANCH (c33729 ElasticContainer: Allow to specify the docker container vers)
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.eclipse.jgit.lib.Config;
import org.elasticsearch.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticGroupIndex extends AbstractElasticIndex<AccountGroup.UUID, InternalGroup>
    implements GroupIndex {
  static class GroupMapping {
    MappingProperties groups;

    GroupMapping(Schema<InternalGroup> schema) {
      this.groups = ElasticMapping.createMapping(schema);
    }
  }

  static final String GROUPS = "groups";

  private static final Logger log = LoggerFactory.getLogger(ElasticGroupIndex.class);

  private final GroupMapping mapping;
  private final Provider<GroupCache> groupCache;

  @Inject
  ElasticGroupIndex(
      @GerritServerConfig Config cfg,
      SitePaths sitePaths,
      Provider<GroupCache> groupCache,
<<<<<<< HEAD   (e65498 Merge branch 'stable-2.14' into stable-2.15)
      JestClientBuilder clientBuilder,
      @Assisted Schema<InternalGroup> schema) {
    super(cfg, sitePaths, schema, clientBuilder, GROUPS);
=======
      ElasticRestClientBuilder clientBuilder,
      @Assisted Schema<AccountGroup> schema) {
    // No parts of FillArgs are currently required, just use null.
    super(cfg, null, sitePaths, schema, clientBuilder, GROUPS);
>>>>>>> BRANCH (c33729 ElasticContainer: Allow to specify the docker container vers)
    this.groupCache = groupCache;
    this.mapping = new GroupMapping(schema);
  }

  @Override
<<<<<<< HEAD   (e65498 Merge branch 'stable-2.14' into stable-2.15)
  public void replace(InternalGroup group) throws IOException {
    Bulk bulk =
        new Bulk.Builder()
            .defaultIndex(indexName)
            .defaultType(GROUPS)
            .addAction(insert(GROUPS, group))
            .refresh(true)
            .build();
    JestResult result = client.execute(bulk);
    if (!result.isSucceeded()) {
=======
  public void replace(AccountGroup group) throws IOException {
    String bulk = toAction(GROUPS, getId(group), INDEX);
    bulk += toDoc(group);

    String uri = getURI(GROUPS, BULK);
    Response response = performRequest(HttpPost.METHOD_NAME, bulk, uri, getRefreshParam());
    int statusCode = response.getStatusLine().getStatusCode();
    if (statusCode != HttpStatus.SC_OK) {
>>>>>>> BRANCH (c33729 ElasticContainer: Allow to specify the docker container vers)
      throw new IOException(
          String.format(
              "Failed to replace group %s in index %s: %s",
              group.getGroupUUID().get(), indexName, statusCode));
    }
  }

  @Override
  public DataSource<InternalGroup> getSource(Predicate<InternalGroup> p, QueryOptions opts)
      throws QueryParseException {
    return new QuerySource(p, opts);
  }

  @Override
  protected String addActions(AccountGroup.UUID c) {
    return delete(GROUPS, c);
  }

  @Override
  protected String getMappings() {
    ImmutableMap<String, GroupMapping> mappings = ImmutableMap.of("mappings", mapping);
    return gson.toJson(mappings);
  }

  @Override
  protected String getId(InternalGroup group) {
    return group.getGroupUUID().get();
  }

<<<<<<< HEAD   (e65498 Merge branch 'stable-2.14' into stable-2.15)
  private class QuerySource implements DataSource<InternalGroup> {
    private final Search search;
=======
  private class QuerySource implements DataSource<AccountGroup> {
    private final String search;
>>>>>>> BRANCH (c33729 ElasticContainer: Allow to specify the docker container vers)
    private final Set<String> fields;

    QuerySource(Predicate<InternalGroup> p, QueryOptions opts) throws QueryParseException {
      QueryBuilder qb = queryBuilder.toQueryBuilder(p);
      fields = IndexUtils.groupFields(opts);
      SearchSourceBuilder searchSource =
          new SearchSourceBuilder()
              .query(qb)
              .from(opts.start())
              .size(opts.limit())
              .fields(Lists.newArrayList(fields));

      JsonArray sortArray = getSortArray(GroupField.UUID.getName());
      search = getSearch(searchSource, sortArray);
    }

    @Override
    public int getCardinality() {
      return 10;
    }

    @Override
    public ResultSet<InternalGroup> read() throws OrmException {
      try {
<<<<<<< HEAD   (e65498 Merge branch 'stable-2.14' into stable-2.15)
        List<InternalGroup> results = Collections.emptyList();
        JestResult result = client.execute(search);
        if (result.isSucceeded()) {
          JsonObject obj = result.getJsonObject().getAsJsonObject("hits");
=======
        List<AccountGroup> results = Collections.emptyList();
        String uri = getURI(GROUPS, SEARCH);
        Response response =
            performRequest(HttpPost.METHOD_NAME, search, uri, Collections.emptyMap());
        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
          String content = getContent(response);
          JsonObject obj =
              new JsonParser().parse(content).getAsJsonObject().getAsJsonObject("hits");
>>>>>>> BRANCH (c33729 ElasticContainer: Allow to specify the docker container vers)
          if (obj.get("hits") != null) {
            JsonArray json = obj.getAsJsonArray("hits");
            results = Lists.newArrayListWithCapacity(json.size());
            for (int i = 0; i < json.size(); i++) {
              Optional<InternalGroup> internalGroup = toInternalGroup(json.get(i));
              internalGroup.ifPresent(results::add);
            }
          }
        } else {
          log.error(statusLine.getReasonPhrase());
        }
        final List<InternalGroup> r = Collections.unmodifiableList(results);
        return new ResultSet<InternalGroup>() {
          @Override
          public Iterator<InternalGroup> iterator() {
            return r.iterator();
          }

          @Override
          public List<InternalGroup> toList() {
            return r;
          }

          @Override
          public void close() {
            // Do nothing.
          }
        };
      } catch (IOException e) {
        throw new OrmException(e);
      }
    }

<<<<<<< HEAD   (e65498 Merge branch 'stable-2.14' into stable-2.15)
    @Override
    public String toString() {
      return search.toString();
    }

    private Optional<InternalGroup> toInternalGroup(JsonElement json) {
=======
    private AccountGroup toAccountGroup(JsonElement json) {
>>>>>>> BRANCH (c33729 ElasticContainer: Allow to specify the docker container vers)
      JsonElement source = json.getAsJsonObject().get("_source");
      if (source == null) {
        source = json.getAsJsonObject().get("fields");
      }

      AccountGroup.UUID uuid =
          new AccountGroup.UUID(
              source.getAsJsonObject().get(GroupField.UUID.getName()).getAsString());
      // Use the GroupCache rather than depending on any stored fields in the
      // document (of which there shouldn't be any).
      return groupCache.get().get(uuid);
    }
  }
}
